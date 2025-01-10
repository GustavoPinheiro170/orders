package com.amcom.order.services;

import com.amcom.order.entitys.OrderTable;
import com.amcom.order.entitys.Product;
import com.amcom.order.models.OrderTotalResponse;
import com.amcom.order.repository.OrderRepository;
import com.amcom.order.repository.ProductRepository;
import com.amcom.order.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private final OrderTable currentOrderTable = new OrderTable();

    // Adiciona um produto ao pedido, retornando um status de conflito se já existir.
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<HttpStatus> addProductToOrder(Product product) throws Exception {
        try {
            if (productRepository.findByName(product.getName()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            saveProductAndAddToOrder(product);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // Salva o produto no repositório e o adiciona à tabela do pedido atual
    @Transactional(isolation = Isolation.READ_COMMITTED)
    private void saveProductAndAddToOrder(Product product){
            productRepository.save(product);
            currentOrderTable.addProduct(product);
    }

    // Remove um produto do pedido
    @Transactional
    public void removeProductFromOrder(Product product) {
        productRepository.delete(product);
        currentOrderTable.removeProduct(product);
    }

    // Calcula o total do pedido e atualiza a tabela do pedido ou cria uma nova
    @Transactional
    public ResponseEntity<OrderTotalResponse> calculateOrderTotal() throws Exception {

        try {
            double total = currentOrderTable.calculateTotal();
            currentOrderTable.setTotal(total);

            if (currentOrderTable.getId() != null) {
                updateExistingOrderTotal(total);
            } else {
                orderRepository.save(currentOrderTable);
            }

            return ResponseEntity.ok(OrderTotalResponse.fromTotal(total));
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    // Atualiza o total do pedido existente
    private void updateExistingOrderTotal(double total) {
        Optional<OrderTable> existingOrder = orderRepository.findById(currentOrderTable.getId());
        existingOrder.ifPresent(order -> {
            order.setTotal(total);
            orderRepository.save(order);  // Salvar a atualização do pedido
        });
    }

    // Envia os pedidos para o sistema externo B
    public ResponseEntity<List<OrderTable>> sendOrderToExternalSystemB() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    // Recebe o pedido do sistema externo A e salva os produtos no pedido
    public String receiveOrderFromExternalSystemA(OrderTable order) {
        order.getProducts().forEach(this::saveProductAndAddToOrder);
        return "Pedido recebido do sistema externo A.";
    }

    // Retorna o pedido atual
    public OrderTable getCurrentOrder() {
        return currentOrderTable;
    }
}

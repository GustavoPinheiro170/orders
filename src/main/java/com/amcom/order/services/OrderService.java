package com.amcom.order.services;

import com.amcom.order.entitys.OrderTable;
import com.amcom.order.entitys.Product;
import com.amcom.order.models.OrderTotalResponse;
import com.amcom.order.repository.OrderRepository;
import com.amcom.order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private OrderTable currentOrderTable;

    public OrderService() {
        this.currentOrderTable = new OrderTable();
    }

    public void addProductToOrder(Product product) {
        productRepository.save(product);
        currentOrderTable.addProduct(product);
    }

    public void removeProductFromOrder(Product product) {
        productRepository.delete(product);
        currentOrderTable.removeProduct(product);
    }

    public ResponseEntity<OrderTotalResponse> calculateOrderTotal() {
        double total = currentOrderTable.calculateTotal();
        currentOrderTable.setTotal(total);
        orderRepository.save(currentOrderTable);
        return ResponseEntity.ok(OrderTotalResponse.fromTotal(total));
    }

    public ResponseEntity<List<OrderTable>> sendOrderToExternalSystemB() {
        List<OrderTable> orders = orderRepository.findAll();
        return ResponseEntity.ok().body(orders);
    }

    public String receiveOrderFromExternalSystemA(OrderTable order) {
        order.getProducts().forEach(this::addProductToOrder);
        return "Pedido recebido do sistema externo A.";
    }

    public OrderTable getCurrentOrder() {
        return currentOrderTable;
    }
}

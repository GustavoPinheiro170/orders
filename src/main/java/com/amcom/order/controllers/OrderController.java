package com.amcom.order.controllers;

import com.amcom.order.entitys.OrderTable;
import com.amcom.order.entitys.Product;
import com.amcom.order.models.OrderTotalResponse;
import com.amcom.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Recebe o pedido de um sistema externo A
    @PostMapping("/receive-from-external-A")
    public String receiveOrderFromExternalA(@RequestBody List<Product> products) {
        OrderTable order = new OrderTable();
        products.forEach(order::addProduct);
        return orderService.receiveOrderFromExternalSystemA(order);
    }

    // Envia o pedido para o sistema externo B
    @PostMapping("/send-to-external-B")
    public ResponseEntity<List<OrderTable>> sendOrderToExternalB() {
        return orderService.sendOrderToExternalSystemB();
    }

    // Adiciona um produto ao pedido
    @PostMapping("/add-product")
    public void addProductToOrder(@RequestBody Product product) {
        orderService.addProductToOrder(product);
    }

    // Calcula o total do pedido
    @GetMapping("/total")
    public ResponseEntity<OrderTotalResponse> calculateOrderTotal() {
        return orderService.calculateOrderTotal();
    }

    // Obtém todos os produtos do pedido
    @GetMapping("/products")
    public List<Product> getOrderProducts() {
        return orderService.getCurrentOrder().getProducts();
    }
}

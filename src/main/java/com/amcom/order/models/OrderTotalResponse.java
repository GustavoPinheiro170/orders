package com.amcom.order.models;


public class OrderTotalResponse {
    private double total;
    private String message;

    // Construtores
    public OrderTotalResponse(double total, String message) {
        this.total = total;
        this.message = message;
    }

    // Getters e Setters
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Método útil para criar uma resposta com um total e uma mensagem
    public static OrderTotalResponse fromTotal(double total) {
        return new OrderTotalResponse(total, "Total calculado com sucesso.");
    }
}
package com.example.taller5.Models;

import java.time.LocalDate;

public class DetalleVenta {
    private LocalDate fecha;
    private String cliente;
    private String producto;
    private int cantidad;
    private double subtotal;

    public DetalleVenta(LocalDate fecha, String cliente, String producto, int cantidad, double subtotal) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }
}

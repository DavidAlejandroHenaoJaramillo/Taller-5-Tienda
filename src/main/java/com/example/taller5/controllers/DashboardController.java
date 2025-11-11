package com.example.taller5.controllers;

import com.example.taller5.app.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardController {
    @FXML
    private AnchorPane layout;

    @FXML private void cargarClientes() throws IOException {
        VBox vistaCliente = FXMLLoader.load(getClass().getResource("/com/example/taller5/view/Clientes.fxml"));
        layout.getChildren().setAll(vistaCliente);
    }
    @FXML private void cargarProductos() throws IOException {
        VBox vistaProductos = FXMLLoader.load(getClass().getResource("/com/example/taller5/view/Productos.fxml"));
        layout.getChildren().setAll(vistaProductos);
    }
    @FXML private void cargarVentas() throws IOException {
        VBox vistaVentas = FXMLLoader.load(getClass().getResource("/com/example/taller5/view/Ventas.fxml"));
        layout.getChildren().setAll(vistaVentas);
    }



}

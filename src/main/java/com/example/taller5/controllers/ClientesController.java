package com.example.taller5.controllers;

import com.example.taller5.Models.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class ClientesController {
    @FXML
    private TableView<Cliente> tableClientes;
    @FXML
    private TableColumn<Cliente, String> colId;
    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableColumn<Cliente, String> colTelefono;
    @FXML
    private TableColumn<Cliente, String> colCorreo;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button BtnEliminar;
    @FXML
    private Button btnModificar;

    private final ObservableList<Cliente> clientes =  FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tableClientes.setItems(clientes);

        clientes.add(new Cliente("1119150996",
                "alejandro henao","3042485751","a.henaojaramilo87@gmail.com"));
    }

    @FXML
    private void agregarClientes() {
        try {
            String id = txtId.getText();
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();

            if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                mostrarAlerta("Campos vacios", "Complete todos los campos");
                return;
            }
            Cliente cliente = new Cliente(id, nombre, telefono, correo);
            clientes.add(cliente);
            tableClientes.refresh();

            txtId.clear();
            txtNombre.clear();
            txtTelefono.clear();
            txtCorreo.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void eliminarClientes() {}

    @FXML
    private void modificarClientes() {}

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

package com.example.taller5.controllers;

import com.example.taller5.Models.Cliente;
import com.example.taller5.repositories.ClienteRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;


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

    private ClienteRepository clienteRepository = ClienteRepository.getInstance();

    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();



    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        clientes.addAll(clienteRepository.getClientes());
        tableClientes.setItems(clientes);

        tableClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtNombre.setText(newSelection.getNombre());
                txtTelefono.setText(newSelection.getTelefono());
                txtCorreo.setText(newSelection.getCorreo());
            }
        });
    }

    @FXML
    private void agregarClientes() {
        try {
            String id = txtId.getText();
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();

            if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                mostrarAlerta("Campos vacios", "Complete todos los campos", Alert.AlertType.ERROR);
                return;
            }
            Cliente cliente = new Cliente(id, nombre, telefono, correo);
            clientes.add(cliente);
            tableClientes.refresh();


            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cargarClientes() {
        tableClientes.setItems(clientes);
    }

    @FXML
    private void eliminarClientes() {
        Cliente cliente = tableClientes.getSelectionModel().getSelectedItem();
        if(cliente == null){
            mostrarAlerta("Error", "Seleccione un cliente para eliminar." , Alert.AlertType.ERROR);
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro?");
        confirmacion.setContentText("¿Deseas eliminar el cliente: " + cliente.getNombre() + "?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
            try{
                clienteRepository.removeCliente(cliente);
                clientes.remove(cliente);
                tableClientes.refresh();
                mostrarAlerta("Exito", "cliente eliminado correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } catch (Exception e) {
                mostrarAlerta("Exito", "Error al eliminar el Cliente: "+ e.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }

    @FXML
    private void modificarClientes() {
        String id = txtId.getText();
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            mostrarAlerta("Campos vacios", "Complete todos los campos", Alert.AlertType.ERROR);
            return;
        }
        Cliente cliente = tableClientes.getSelectionModel().getSelectedItem();
        if(cliente == null){
            mostrarAlerta("Error", "Seleccione un cliente para modificar.", Alert.AlertType.WARNING);
            return;
        }
        try{
            cliente.setNombre(txtNombre.getText());
            cliente.setTelefono(txtTelefono.getText());
            cliente.setCorreo(txtCorreo.getText());
            actualizarTabla();
            tableClientes.refresh();
            mostrarAlerta("Éxito", "Cliente modificado correctamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al modificar el cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private void limpiarCampos (){
        txtId.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtCorreo.clear();
    }
    public void actualizarTabla () { cargarClientes();}
}

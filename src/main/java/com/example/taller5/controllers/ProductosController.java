package com.example.taller5.controllers;

import com.example.taller5.Models.Productos;
import com.example.taller5.repositories.ClienteRepository;
import com.example.taller5.repositories.ProductosRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class ProductosController {

    @FXML
    private TableView<Productos> tableProductos;
    @FXML
    private TableColumn<Productos, String> colCodigo;
    @FXML
    private TableColumn<Productos, String> colNombreProducto;
    @FXML
    private TableColumn<Productos, Double> colPrecio;
    @FXML
    private TableColumn<Productos, Integer> colStock;

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombreProducto;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtStock;
    @FXML
    private Button btnAgregarProducto;
    @FXML
    private Button btnEliminarProducto;
    @FXML
    private Button btnModificarProducto;

    private ProductosRepository productosRepository = ProductosRepository.getInstance();
    private final ObservableList<Productos> productos = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productos.addAll(productosRepository.getProductos());
        tableProductos.setItems(productos);

        tableProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtCodigo.setText(newSelection.getCodigo());
                txtNombreProducto.setText(newSelection.getNombre());
                txtPrecio.setText(String.valueOf(newSelection.getPrecio()));
                txtStock.setText(String.valueOf(newSelection.getStock()));
            }
        });
    }

    @FXML
    private void agregarProductos() {
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombreProducto.getText();
            String precioTexto = txtPrecio.getText();
            String stockTexto = txtStock.getText();

            if (codigo.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty() || stockTexto.isEmpty()) {
                mostrarAlerta("Campos vacíos", "Complete todos los campos", Alert.AlertType.ERROR);
                return;
            }

            double precio = Double.parseDouble(precioTexto);
            int stock = Integer.parseInt(stockTexto);

            Productos producto = new Productos(codigo, nombre, precio, stock);
            producto.setCodigo(codigo);
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);

            productos.add(producto);
            tableProductos.refresh();
            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "El precio o stock deben ser numéricos.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarProductos() {
        Productos producto = tableProductos.getSelectionModel().getSelectedItem();

        if (producto == null) {
            mostrarAlerta("Error", "Seleccione un producto para eliminar.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro?");
        confirmacion.setContentText("¿Deseas eliminar el producto: " + producto.getNombre() + "?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            productos.remove(producto);
            tableProductos.refresh();
            mostrarAlerta("Éxito", "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
        }
    }

    @FXML
    private void modificarProductos() {
        Productos producto = tableProductos.getSelectionModel().getSelectedItem();

        if (producto == null) {
            mostrarAlerta("Error", "Seleccione un producto para modificar.", Alert.AlertType.WARNING);
            return;
        }

        String codigo = txtCodigo.getText();
        String nombre = txtNombreProducto.getText();
        String precioTexto = txtPrecio.getText();
        String stockTexto = txtStock.getText();

        if (codigo.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty() || stockTexto.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        try {
            double precio = Double.parseDouble(precioTexto);
            int stock = Integer.parseInt(stockTexto);

            producto.setCodigo(codigo);
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);

            tableProductos.refresh();
            mostrarAlerta("Éxito", "Producto modificado correctamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "El precio o stock deben ser numéricos.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al modificar el producto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombreProducto.clear();
        txtPrecio.clear();
        txtStock.clear();
    }
}

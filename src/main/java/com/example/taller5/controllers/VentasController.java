package com.example.taller5.controllers;

import com.example.taller5.Models.Cliente;
import com.example.taller5.Models.DetalleVenta;
import com.example.taller5.Models.Productos;
import com.example.taller5.repositories.ClienteRepository;
import com.example.taller5.repositories.ProductosRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VentasController {
    @FXML
    private ComboBox<Cliente> cmbCliente;
    @FXML
    private ComboBox<Productos> cmbProducto;
    @FXML
    private Button btnVender;
    @FXML
    private Spinner<Integer> spnCantidad;
    @FXML
    private TableView<DetalleVenta> tableVentas;
    @FXML
    private TableColumn<DetalleVenta, String> colFecha;
    @FXML
    private TableColumn<DetalleVenta, String> colCantidad;
    @FXML
    private TableColumn<DetalleVenta, String> colProductoVenta;
    @FXML
    private TableColumn<DetalleVenta, String> colSubtotal;
    @FXML
    private TableColumn<DetalleVenta, String> colClienteVenta;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblSubtotal;

    private ObservableList<DetalleVenta> listaVentas;
    private ProductosRepository productosRepository = ProductosRepository.getInstance();
    private ClienteRepository clienteRepository =  ClienteRepository.getInstance();
    private double precioUnitario = 0.0;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    @FXML
    public void initialize() {
        productosRepository = ProductosRepository.getInstance();
        clienteRepository = ClienteRepository.getInstance();
        listaVentas = FXCollections.observableArrayList();
        configurarTabla();
        inicializarComponentes();
        configListeners();
        actualizarHora();
    }

    @FXML
    public void inicializarComponentes() {
        cargarClientes();
        cargarProductos();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1, 1);
        spnCantidad.setValueFactory(valueFactory);
        lblTotal.setText("$ 0.00");
        lblSubtotal.setText("$ 0.00");
    }

    private void configListeners() {
        cmbProducto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                precioUnitario = newValue.getPrecio();
                int stock = newValue.getStock();
                lblTotal.setText("$ " + String.format("%2f", precioUnitario));
                actualizarSpinner(stock);
                calcularSubtotal();
            } else {
                lblTotal.setText("$ 0.00");
                lblSubtotal.setText("$ 0.00");
                precioUnitario = 0.0;
                actualizarSpinner(1);
            }
        });

        spnCantidad.valueProperty().addListener((observable, oldValue, newValue) -> {
            calcularSubtotal();
        });

    }
    private void actualizarHora(){ lblFecha.setText(LocalDateTime.now().format(TIME_FORMATTER));}

    private void actualizarSpinner(int stock){
        SpinnerValueFactory.IntegerSpinnerValueFactory factory= (SpinnerValueFactory.IntegerSpinnerValueFactory) spnCantidad.getValueFactory();
        factory.setMax(Math.max(1, stock));
        if(spnCantidad.getValue()>stock){
            spnCantidad.getValueFactory().setValue(stock);
        }
    }
    private void calcularSubtotal() {
        if (precioUnitario > 0 && spnCantidad.getValue() != null) {
            int cantidad = spnCantidad.getValue();
            double subtotal = precioUnitario * cantidad;
            lblTotal.setText("$ " + String.format("%.2f", subtotal));
        } else {
            lblTotal.setText("$ 0.00");
        }
    }
    private void cargarClientes() {
        cmbCliente.setItems(FXCollections.observableArrayList(clienteRepository.getClientes()));

        cmbCliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente cliente) {
                return (cliente != null) ? cliente.getNombre() : "";
            }
            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });
    }
    private void cargarProductos() {
        ObservableList<Productos> productosObservable =
                FXCollections.observableList(productosRepository.getProductos());
        cmbProducto.setItems(productosObservable);
        cmbProducto.setConverter(new StringConverter<Productos>() {
            @Override
            public String toString(Productos producto) {
                return (producto != null) ? producto.getNombre(): "";
            }

            @Override
            public Productos fromString(String s) {
                return null;
            }
        });
    }

    @FXML
    void venderProducto(ActionEvent event) {
        Cliente cliente = cmbCliente.getSelectionModel().getSelectedItem();
        if(cliente == null){
            mostrarAlerta("Error de Venta", "Debe seleccionar un cliente.", Alert.AlertType.WARNING);
            return;
        }
        Productos producto = cmbProducto.getSelectionModel().getSelectedItem();
        if (producto == null) {
            mostrarAlerta("Error de Venta", "Debe seleccionar un producto.", Alert.AlertType.WARNING);
            return;
        }
        int cantidad = spnCantidad.getValue();
        if (cantidad > producto.getStock()) {
            mostrarAlerta("Error de Stock", "No hay suficiente stock. Disponible: " + producto.getStock(), Alert.AlertType.ERROR);
            return;
        } try{
            int nuevoStock = producto.getStock()-cantidad;
            producto.setStock(nuevoStock);
            double subtotal = precioUnitario*cantidad;
            DetalleVenta detalle = new DetalleVenta(
                    LocalDate.now(),
                    cliente.getNombre(),
                    producto.getNombre(),
                    cantidad,
                    subtotal
            );
            listaVentas.add(detalle);
            limpiarCampos();
            mostrarAlerta("Venta Exitosa", "Venta registrada en la tabla.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al procesar la venta: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    private void limpiarCampos() {
        cmbProducto.getSelectionModel().clearSelection();
        lblTotal.setText("$ 0.00");
        lblSubtotal.setText("$ 0.00");


        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) spnCantidad.getValueFactory();
        factory.setMax(1);
        factory.setValue(1);
    }
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private void configurarTabla() {
        tableVentas.setItems(listaVentas);
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colClienteVenta.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colProductoVenta.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        actualizarHora();
        cmbCliente.getSelectionModel().clearSelection();

    }
}

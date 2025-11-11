package com.example.taller5.repositories;

import com.example.taller5.Models.Cliente;
import com.example.taller5.Models.Productos;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;

public class ProductosRepository {
    private static ProductosRepository instance;
    private ObservableList<Productos> productos;

    private ProductosRepository() {
        productos = FXCollections.observableArrayList();
        showExamples();
    }
    public static ProductosRepository getInstance() {
        if(instance == null){
            instance = new ProductosRepository();
        }
        return instance;
    }
    private void showExamples(){
        productos.add(new Productos("2007", "Coca-Cola" , 3000 , 20));
    }
    public ObservableList<Productos> getProductos() {
        return productos;
    }
    public void agregarProductos(Productos producto){
        productos.add(producto);
    }
    public void eliminarProductos(Productos producto){
        productos.remove(producto);
    }
}

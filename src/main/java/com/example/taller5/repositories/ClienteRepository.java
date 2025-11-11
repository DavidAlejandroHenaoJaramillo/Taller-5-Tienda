package com.example.taller5.repositories;

import com.example.taller5.Models.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ClienteRepository {
    private static ClienteRepository instance;
    private ObservableList<Cliente> clientes;
    public ClienteRepository() {
        clientes = FXCollections.observableArrayList();
        showExamples();
    }
    public static ClienteRepository getInstance() {
        if(instance == null){
            instance = new ClienteRepository();
        }
        return instance;
    }
    private void showExamples(){
        clientes.add(new Cliente("119150996", "Alejandro Henao", "3042485751",
                "a.henaojaramillo@gmail.com"));
    }
    public ObservableList<Cliente> getClientes(){
        return clientes;
    }
    public void addCliente(Cliente cliente){
        clientes.add(cliente);
    }
    public void removeCliente(Cliente cliente){
        clientes.remove(cliente);
    }

}
package com.example.taller5.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
    private final StringProperty id;
    private final StringProperty nombre;
    private final StringProperty telefono;
    private final StringProperty correo;

    public Cliente(String id, String nombre, String telefono, String correo) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.telefono = new SimpleStringProperty(telefono);
        this.correo = new SimpleStringProperty(correo);
    }

    // Getters y setters tipo Property
    public String getId() { return id.get(); }
    public void setId(String id) { this.id.set(id); }
    public StringProperty idProperty() { return id; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public StringProperty nombreProperty() { return nombre; }

    public String getTelefono() { return telefono.get(); }
    public void setTelefono(String telefono) { this.telefono.set(telefono); }
    public StringProperty telefonoProperty() { return telefono; }

    public String getCorreo() { return correo.get(); }
    public void setCorreo(String correo) { this.correo.set(correo); }
    public StringProperty correoProperty() { return correo; }

}

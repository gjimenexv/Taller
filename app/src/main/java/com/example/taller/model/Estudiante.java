package com.example.taller.model;

//Es una clase POJO - Plain Old Java Object
//Usada para mapear una tabla de SQLite en el sistema
//Solo tinee atributos,constructores,sets y gets.
public class Estudiante {
    //id int primary key, nombre text, edad int
    private int id;
    private String nombre;
    private int edad;

    public Estudiante() {
    }

    public Estudiante(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

}

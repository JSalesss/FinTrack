package com.joao.fintrack.model;

public class Despesa {
    private int id;
    private double valor;
    private String categoria;
    private String data;

    public Despesa() {}

    public Despesa(int id, double valor, String categoria, String data) {
        this.id = id;
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}

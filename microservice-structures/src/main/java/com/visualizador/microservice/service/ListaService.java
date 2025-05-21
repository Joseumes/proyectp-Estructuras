package com.visualizador.microservice.service;

import java.util.LinkedList;
import java.util.List;

public class ListaService {
    private final List<Integer> lista = new LinkedList<>();

    public void insertar(int valor) {
        lista.add(valor); // agrega al final
    }

    public void eliminar() {
        if (!lista.isEmpty()) {
            lista.remove(0); // elimina al inicio
        }
    }

    public String generarDot() {
        StringBuilder dot = new StringBuilder("digraph lista {\nrankdir=LR;\n");

        for (int i = 0; i < lista.size(); i++) {
            dot.append(String.format("n%d [label=\"%d\"];\n", i, lista.get(i)));
            if (i > 0) {
                dot.append(String.format("n%d -> n%d;\n", i - 1, i));
            }
        }

        dot.append("}");
        return dot.toString();
    }
}

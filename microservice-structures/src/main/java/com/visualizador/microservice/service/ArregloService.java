package com.visualizador.microservice.service;

import java.util.ArrayList;
import java.util.List;

public class ArregloService {
    private final List<Integer> arreglo = new ArrayList<>();

    public void insertar(int valor) {
        arreglo.add(valor);
    }

    public void eliminar() {
        if (!arreglo.isEmpty()) {
            arreglo.remove(arreglo.size() - 1); // eliminar último (como pila)
        }
    }

    public String generarDot() {
        StringBuilder dot = new StringBuilder("digraph arreglo {\nrankdir=LR;\n");

        for (int i = 0; i < arreglo.size(); i++) {
            dot.append(String.format("n%d [label=\"%d\"];\n", i, arreglo.get(i)));
            if (i > 0) {
                dot.append(String.format("n%d -> n%d;\n", i - 1, i));
            }
        }

        dot.append("}");
        return dot.toString();
    }
}

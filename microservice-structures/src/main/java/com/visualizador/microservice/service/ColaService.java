package com.visualizador.microservice.service;
import org.springframework.stereotype.Service;

import lombok.Getter;
import java.util.LinkedList;
import java.util.Queue;


@Service
@Getter
public class ColaService {
    private Queue<Integer> cola = new LinkedList<>();

    public void insertar(int valor) {
        cola.offer(valor);
    }

    public void eliminar() {
        if (!cola.isEmpty()) {
            cola.poll();
        }
    }
    public void limpiar() {
    cola.clear();
}


    public String generarDot() {
        StringBuilder dot = new StringBuilder("digraph cola {\nrankdir=LR;\n");
        int i = 0;
        for (Integer val : cola) {
            dot.append(String.format("n%d [label=\"%d\"];\n", i, val));
            if (i > 0) dot.append(String.format("n%d -> n%d;\n", i - 1, i));
            i++;
        }
        dot.append("}");
        return dot.toString();
    }
}

package com.visualizador.microservice.service;
import org.springframework.stereotype.Service;

import lombok.Getter;
import java.util.Stack;

@Getter
@Service
public class PilaService {
    private Stack<Integer> pila = new Stack<>();

    public void insertar(int valor) {
        pila.push(valor);
    }

    public void eliminar() {
        if (!pila.isEmpty()) {
            pila.pop();
        }
    }

    public String generarDot() {
        StringBuilder dot = new StringBuilder("digraph pila {\nrankdir=LR;\n");
        int i = 0;
        for (Integer val : pila) {
            dot.append(String.format("n%d [label=\"%d\"];\n", i, val));
            if (i > 0) dot.append(String.format("n%d -> n%d;\n", i - 1, i));
            i++;
        }
        dot.append("}");
        return dot.toString();
    }
}

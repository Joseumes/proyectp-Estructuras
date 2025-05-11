package com.visualizador.microservice.service;

import com.visualizador.microservice.util.GraphvizUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class EstructuraService {

    private final PilaService pila;
    private final ColaService cola;

    public String procesarCSV(MultipartFile file) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String linea;
        boolean skip = true;
        String pathFinal = null;

        while ((linea = reader.readLine()) != null) {
            if (skip) { skip = false; continue; }
            String[] partes = linea.split(",");
            if (partes.length < 2) continue;

            String estructura = partes[0].trim().toLowerCase();
            String operacion = partes[1].trim().toLowerCase();
            Integer valor = (partes.length > 2 && !partes[2].isEmpty()) ? Integer.parseInt(partes[2].trim()) : null;

            String path = null;

            switch (estructura) {
                case "pila":
                    if ("insertar".equals(operacion)) pila.insertar(valor);
                    else pila.eliminar();
                    path = GraphvizUtil.generarImagen(pila.generarDot(), "pila");
                    break;

                case "cola":
                    if ("insertar".equals(operacion)) cola.insertar(valor);
                    else cola.eliminar();
                    path = GraphvizUtil.generarImagen(cola.generarDot(), "cola");
                    break;

                default:
                    throw new IllegalArgumentException("Estructura no permitida: " + estructura);
            }

            pathFinal = path;
        }

        return pathFinal;
    }
}

package com.visualizador.microservice.service;

import com.visualizador.microservice.util.GraphvizUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstructuraService {

    private final PilaService pila;
    private final ColaService cola;
    private final ListaService lista = new ListaService();
    private final ArregloService arreglo = new ArregloService();


    public List<String> procesarCSV(MultipartFile file) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String linea;
        boolean skip = true;
        List<String> imagenes = new ArrayList<>();

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
                case "lista":
                    if ("insertar".equals(operacion)) lista.insertar(valor);
                    else lista.eliminar();
                    path = GraphvizUtil.generarImagen(lista.generarDot(), "lista");
                    break;
                case "arreglo":
                    if ("insertar".equals(operacion)) arreglo.insertar(valor);
                    else arreglo.eliminar();
                    path = GraphvizUtil.generarImagen(arreglo.generarDot(), "arreglo");
                    break;

            }

            if (path != null) {
                imagenes.add(path);
            }
        }

        return imagenes;
    }
}

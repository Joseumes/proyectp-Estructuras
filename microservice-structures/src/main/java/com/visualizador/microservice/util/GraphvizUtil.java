package com.visualizador.microservice.util;

import java.io.*;

import java.util.UUID;

public class GraphvizUtil {

    public static String generarImagen(String dot, String nombreBase) {
        try {
            // Verificar si el DOT es válido
            if (dot == null || dot.isBlank()) {
                System.out.println("⚠️ DOT vacío. No se genera imagen.");
                return null;
            }

            System.out.println("=== DOT GENERADO ===");
            System.out.println(dot);
            System.out.println("====================");

            // Crear carpeta imagenes/ si no existe
            String carpeta = "imagenes/";
            File dir = new File(carpeta);
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("🗂️ Carpeta 'imagenes/' creada.");
            }

            // Generar nombres únicos
            String uuid = UUID.randomUUID().toString();
            String dotPath = carpeta + nombreBase + "_" + uuid + ".dot";
            String imgPath = carpeta + nombreBase + "_" + uuid + ".png";

            // Escribir el archivo .dot
            try (FileWriter fw = new FileWriter(dotPath)) {
                fw.write(dot);
                System.out.println("✅ Archivo DOT guardado en: " + dotPath);
            }

            // Ejecutar Graphviz
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", dotPath, "-o", imgPath);
            pb.redirectErrorStream(true); // Unir salida de error y estándar
            Process process = pb.start();

            // Leer salida del proceso
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.out.println("Graphviz: " + errorLine);
            }

            int exitCode = process.waitFor();
            System.out.println("🔧 Graphviz terminó con código: " + exitCode);

            // Verificar si se generó la imagen
            File imagen = new File(imgPath);
            if (imagen.exists()) {
                System.out.println("✅ Imagen generada correctamente: " + imgPath);
                return imgPath; // Ruta relativa
            } else {
                System.out.println("❌ La imagen no fue generada.");
                return null;
            }

        } catch (Exception e) {
            System.out.println("❌ Error al generar imagen con Graphviz:");
            e.printStackTrace();
            return null;
        }
    }
}

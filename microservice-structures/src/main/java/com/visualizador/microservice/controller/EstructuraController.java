package com.visualizador.microservice.controller;

import com.visualizador.microservice.service.EstructuraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/estructuras")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class EstructuraController {

    private final EstructuraService service;

    @PostMapping("/procesar")
    public ResponseEntity<?> cargarCSV(@RequestParam("file") MultipartFile file) {
        try {
            List<String> imagenes = service.procesarCSV(file);
            return ResponseEntity.ok(imagenes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar archivo: " + e.getMessage());
        }
    }
}

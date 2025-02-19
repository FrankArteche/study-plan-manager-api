package com.lelox028.StudyPlanManagerApi.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lelox028.StudyPlanManagerApi.Models.Facultad;
import com.lelox028.StudyPlanManagerApi.Services.FacultadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/facultades")
public class FacultadController {

    @Autowired
    private FacultadService facultadService;

    @GetMapping
    public List<Facultad> getFacultades() {
        return facultadService.getAllFacultades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facultad> getFacultadById(@PathVariable int id) {
        try {
            Facultad facultad = facultadService.getFacultadById(id);
            return ResponseEntity.ok(facultad);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Obtener todas las facultades de una determinada universidad
    @GetMapping("/universidades/{idU}/facultades")
    public ResponseEntity<List<Facultad>> getFacultadesbyUniversidadId(@PathVariable int idU) {
        try {
            List<Facultad> facultades = facultadService.getFacultadesbyUniversidadId(idU);
            if (facultades.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(facultades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> createFacultad(@Valid @RequestBody Facultad facultad) {
        try {
            Facultad newFacultad = facultadService.createFacultad(facultad);
            return new ResponseEntity<>(newFacultad, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al crear la facultad: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facultad> updateFacultad(@PathVariable int id, @Valid @RequestBody Facultad updatedFacultad) {
        try {
            Facultad updated = facultadService.updateFacultad(id, updatedFacultad);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacultad(@PathVariable int id) {
        try {
            facultadService.deleteFacultad(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

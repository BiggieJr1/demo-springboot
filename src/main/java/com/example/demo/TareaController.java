package com.example.demo;

import java.util.Map;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;


@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaRepository repositorio;

    public TareaController(TareaRepository repositorio) {
        this.repositorio = repositorio;
    }

    // --- LEER (Read) ---

    // 1. Obtener todas las tareas
    @GetMapping
    public List<Tarea> obtenerTodas() {
        return repositorio.findAll();
    }

    // 2. Obtener una sola tarea por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(tarea -> ResponseEntity.ok(tarea))
                .orElse(ResponseEntity.notFound().build());
    }

    // --- ESCRIBIR (Create) ---

    // 3. Guardar una nueva tarea
    @PostMapping
    public Tarea crearTarea(@RequestBody Tarea tarea) {
        return repositorio.save(tarea);
    }

    // --- ACTUALIZAR (Update) ---

    // 4. Actualizar una tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea detallesTarea) {
        return repositorio.findById(id)
                .map(tareaExistente -> {
                    // Actualizamos los campos
                    tareaExistente.setTitulo(detallesTarea.getTitulo());
                    tareaExistente.setCompletada(detallesTarea.isCompletada());
                    // Guardamos los cambios
                    Tarea tareaActualizada = repositorio.save(tareaExistente);
                    return ResponseEntity.ok(tareaActualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- ACTUALIZAR PARCIAL (Patch) ---
    @PatchMapping("/{id}")
    public ResponseEntity<Tarea> actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        // 1. Buscamos la tarea original
        Tarea tarea = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        // 2. Iteramos sobre los campos que nos enviaron en el JSON
        campos.forEach((nombreCampo, valor) -> {
            // 3. Buscamos si existe ese campo en la clase Tarea
            Field campo = ReflectionUtils.findField(Tarea.class, nombreCampo);
            
            if (campo != null) {
                // 4. Hacemos el campo accesible (por si es private)
                campo.setAccessible(true); 
                
                // 5. Establecemos el nuevo valor
                ReflectionUtils.setField(campo, tarea, valor);
            }
        });

        // 6. Guardamos y retornamos
        return ResponseEntity.ok(repositorio.save(tarea));
    }

    // --- ELIMINAR (Delete) ---

    // 5. Borrar una tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarTarea(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(tarea -> {
                    repositorio.delete(tarea);
                    return ResponseEntity.noContent().build(); // Retorna 204 (Éxito sin contenido)
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
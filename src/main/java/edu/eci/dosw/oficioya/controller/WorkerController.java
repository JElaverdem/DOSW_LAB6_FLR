package edu.eci.dosw.oficioya.controller;

import edu.eci.dosw.oficioya.dto.LoginDTO;
import edu.eci.dosw.oficioya.service.Disponibilidad;
import edu.eci.dosw.oficioya.service.Worker;
import edu.eci.dosw.oficioya.service.WorkerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trabajadores")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    /**
     * GET /api/trabajadores
     */
    @GetMapping
    public ResponseEntity<List<Worker>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(workerService.findAll());
    }

    /**
     * GET /api/trabajadores/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Worker> worker = workerService.findById(id);
        if (worker.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(worker.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabajador no encontrado");
    }

    /**
     * POST /api/trabajadores
     * Reglas: Campos obligatorios y creación con estado por defecto (ACTIVO).
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Worker worker) {
        if (worker.getNombre() == null || worker.getNombre().isBlank() ||
            worker.getCorreo() == null || worker.getCorreo().isBlank() ||
            worker.getTelefono() == null || worker.getTelefono().isBlank() ||
            worker.getOficioPrincipal() == null || worker.getOficioPrincipal().isBlank() ||
            worker.getContrasena() == null || worker.getContrasena().isBlank()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Los campos Nombre, Correo, Teléfono, Oficio Principal y Contraseña son obligatorios.");
        }

        Worker nuevo = workerService.create(worker);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    /**
     * PUT /api/trabajadores/{id}
     * Regla: No modificar si su estado actual es DESACTIVADO (Inactivo).
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Worker workerData) {
        Optional<Worker> existente = workerService.findById(id);

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabajador no encontrado");
        }

        if (existente.get().getDisponibilidad() == Disponibilidad.DESACTIVADO) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("No se puede modificar la información de un trabajador inactivo.");
        }

        Optional<Worker> actualizado = workerService.update(id, workerData);
        return ResponseEntity.status(HttpStatus.OK).body(actualizado.get());
    }

    /**
     * PATCH /api/trabajadores/{id}/inactivar
     * Regla: Sustituye al DELETE (Desactivación lógica).
     */
    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable Long id) {
        Optional<Worker> inactivado = workerService.inactivar(id);

        if (inactivado.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(inactivado.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabajador no encontrado");
    }

    /**
     * POST /api/trabajadores/login
     * Autenticación por correo y contraseña.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        if (loginDTO.getCorreo() == null || loginDTO.getContrasena() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Correo y contraseña son obligatorios");
        }

        Optional<Worker> worker = workerService.autenticar(loginDTO.getCorreo(), loginDTO.getContrasena());

        if (worker.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(worker.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}

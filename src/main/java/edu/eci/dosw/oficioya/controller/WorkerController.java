package edu.eci.dosw.oficioya.controller;

import edu.eci.dosw.oficioya.dto.LoginDTO;
import edu.eci.dosw.oficioya.model.Disponibilidad;
import edu.eci.dosw.oficioya.model.User;
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
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(workerService.findAll());
    }

    /**
     * GET /api/trabajadores/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<User> user = workerService.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabajador no encontrado");
    }

    /**
     * POST /api/trabajadores
     * Reglas: Campos obligatorios y creación con estado por defecto (ACTIVO).
     * La validación de campos obligatorios ya la hace el WorkerService.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        Optional<User> nuevo = workerService.create(user);

        if (nuevo.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Los campos Nombre, Correo, Teléfono, Oficio Principal y Contraseña son obligatorios.");
    }

    /**
     * PUT /api/trabajadores/{id}
     * Regla: No modificar si su estado actual es DESACTIVADO (Inactivo).
     * El WorkerService ya valida esto y los campos obligatorios.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody User userData) {
        Optional<User> existente = workerService.findById(id);

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabajador no encontrado");
        }

        if (existente.get().getWorker().getDisponibilidad() == Disponibilidad.DESACTIVADO) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("No se puede modificar la información de un trabajador inactivo.");
        }

        Optional<User> actualizado = workerService.update(id, userData);

        if (actualizado.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(actualizado.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Los campos Nombre, Correo, Teléfono, Oficio Principal y Contraseña son obligatorios.");
    }

    /**
     * PATCH /api/trabajadores/{id}/inactivar
     * Regla: Sustituye al DELETE (Desactivación lógica).
     */
    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable String id) {
        Optional<User> inactivado = workerService.inactivar(id);

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

        Optional<User> user = workerService.autenticar(loginDTO.getCorreo(), loginDTO.getContrasena());

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}
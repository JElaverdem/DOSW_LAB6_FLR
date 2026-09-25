package edu.eci.dosw.oficioya.controller;

import edu.eci.dosw.oficioya.dto.LoginDTO;
import edu.eci.dosw.oficioya.model.Disponibilidad;
import edu.eci.dosw.oficioya.model.User;
import edu.eci.dosw.oficioya.service.WorkerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trabajadores")
@Tag(name = "Trabajadores", description = "API para la gestión del CRUD de trabajadores y su autenticación en Oficio Ya")
public class WorkerController {

    // aca esta la inyección de dependencias para conectar el controlador con la lógica de negocio
    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    /**
     * GET /api/trabajadores
     */
    // aca esta el endpoint GET que devuelve la lista completa de usuarios con rol de trabajador
    @GetMapping
    @Operation(summary = "Listar todos los trabajadores", description = "Obtiene una lista de todos los usuarios registrados con rol de trabajador en el sistema.")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(workerService.findAll());
    }

    /**
     * GET /api/trabajadores/{id}
     */
    // aca esta el endpoint GET que busca y retorna un único trabajador usando el ID de la URL
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un trabajador por ID", description = "Busca y retorna los detalles de un trabajador específico mediante su identificador único.")
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
    // aca esta el endpoint POST que recibe un JSON para crear un trabajador nuevo y le asigna el estado ACTIVO
    @PostMapping
    @Operation(summary = "Crear un nuevo trabajador", description = "Registra un trabajador. Valida campos obligatorios y asigna el estado ACTIVO por defecto.")
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
    // aca esta el endpoint PUT que actualiza los datos del trabajador validando primero que no esté inactivo
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un trabajador", description = "Modifica los datos de un trabajador existente, siempre y cuando no se encuentre en estado DESACTIVADO.")
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
    // aca esta el endpoint PATCH que hace el borrado lógico cambiando el estado a DESACTIVADO en lugar de hacer un DELETE real
    @PatchMapping("/{id}/inactivar")
    @Operation(summary = "Inactivar un trabajador (Soft Delete)", description = "Cambia el estado de disponibilidad de un trabajador a DESACTIVADO en lugar de eliminarlo de la base de datos.")
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
    // aca esta el endpoint POST de autenticación que valida si el correo y la contraseña coinciden con los guardados
    @PostMapping("/login")
    @Operation(summary = "Autenticar trabajador", description = "Valida las credenciales (correo y contraseña) de un trabajador para permitir el inicio de sesión.")
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
package edu.eci.dosw.oficioya.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class WorkerService {

    private final List<Worker> workers = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public WorkerService() {
        // Datos dummy de prueba iniciales
        Worker demo1 = new Worker();
        demo1.setId(idGenerator.getAndIncrement());
        demo1.setNombre("Carlos Mendoza");
        demo1.setCorreo("carlos@ejemplo.com");
        demo1.setTelefono("3001234567");
        demo1.setOficioPrincipal("Plomero");
        demo1.setContrasena("Pass1234");
        demo1.setDisponibilidad(Disponibilidad.ACTIVO);

        Worker demo2 = new Worker();
        demo2.setId(idGenerator.getAndIncrement());
        demo2.setNombre("Ana Gómez");
        demo2.setCorreo("ana@ejemplo.com");
        demo2.setTelefono("3109876543");
        demo2.setOficioPrincipal("Electricista");
        demo2.setContrasena("Pass5678");
        demo2.setDisponibilidad(Disponibilidad.ACTIVO);

        workers.add(demo1);
        workers.add(demo2);
    }

    public List<Worker> findAll() {
        return new ArrayList<>(workers);
    }

    public Optional<Worker> findById(Long id) {
        return workers.stream()
                .filter(w -> w.getId().equals(id))
                .findFirst();
    }

    public Worker create(Worker worker) {
        worker.setId(idGenerator.getAndIncrement());
        // Regla: Se crean por defecto con estado Activo
        worker.setDisponibilidad(Disponibilidad.ACTIVO);
        workers.add(worker);
        return worker;
    }

    public Optional<Worker> update(Long id, Worker workerData) {
        for (int i = 0; i < workers.size(); i++) {
            Worker current = workers.get(i);
            if (current.getId().equals(id)) {
                current.setNombre(workerData.getNombre());
                current.setCorreo(workerData.getCorreo());
                current.setTelefono(workerData.getTelefono());
                current.setOficioPrincipal(workerData.getOficioPrincipal());
                if (workerData.getContrasena() != null && !workerData.getContrasena().isBlank()) {
                    current.setContrasena(workerData.getContrasena());
                }
                return Optional.of(current);
            }
        }
        return Optional.empty();
    }

    public Optional<Worker> inactivar(Long id) {
        for (Worker worker : workers) {
            if (worker.getId().equals(id)) {
                // Regla: Inactivación en lugar de eliminación física
                worker.setDisponibilidad(Disponibilidad.DESACTIVADO);
                return Optional.of(worker);
            }
        }
        return Optional.empty();
    }

    public Optional<Worker> autenticar(String correo, String contrasena) {
        return workers.stream()
                .filter(w -> w.getCorreo().equalsIgnoreCase(correo) && w.getPassword().equals(contrasena))
                .findFirst();
    }
}

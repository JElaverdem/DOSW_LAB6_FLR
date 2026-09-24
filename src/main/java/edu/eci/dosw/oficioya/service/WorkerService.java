package edu.eci.dosw.oficioya.service;
 
import org.springframework.stereotype.Service;
 
import edu.eci.dosw.oficioya.model.User;
import edu.eci.dosw.oficioya.model.Worker;
import edu.eci.dosw.oficioya.model.Disponibilidad;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
 
/**
* Servicio encargado de la gestión en memoria de los usuarios con rol de Trabajador.
*/
@Service
public class WorkerService {
 
    private final List<User> users = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
 
    public WorkerService() {
        User demo1 = new User();
        demo1.setId(String.valueOf(idGenerator.getAndIncrement()));
        demo1.setNombre("Carlos Mendoza");
        demo1.setCorreo("carlos@ejemplo.com");
        demo1.setTelefono("3001234567");
        demo1.setContrasena("Pass1234");
 
        Worker worker1 = new Worker();
        worker1.setOficioPrincipal("Plomero");
        worker1.setDisponibilidad(Disponibilidad.ACTIVO);
        demo1.setWorker(worker1);
 
        User demo2 = new User();
        demo2.setId(String.valueOf(idGenerator.getAndIncrement()));
        demo2.setNombre("Ana Gómez");
        demo2.setCorreo("ana@ejemplo.com");
        demo2.setTelefono("3109876543");
        demo2.setContrasena("Pass5678");
 
        Worker worker2 = new Worker();
        worker2.setOficioPrincipal("Electricista");
        worker2.setDisponibilidad(Disponibilidad.ACTIVO);
        demo2.setWorker(worker2);
 
        users.add(demo1);
        users.add(demo2);
    }
 
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
 
    public Optional<User> findById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }
 
    /**
     * Crea un nuevo trabajador. Devuelve Optional.empty() si faltan
     * campos obligatorios (nombre, correo, telefono, oficio principal o contrasena).
     */
    public Optional<User> create(User user) {
        if (!tieneDatosObligatorios(user)) {
            return Optional.empty();
        }
        user.setId(String.valueOf(idGenerator.getAndIncrement()));
        user.getWorker().setDisponibilidad(Disponibilidad.ACTIVO);
        users.add(user);
        return Optional.of(user);
    }
 
    /**
     * Actualiza un trabajador existente. Devuelve Optional.empty() si:
     * - no existe el id,
     * - el trabajador esta DESACTIVADO,
     * - o faltan campos obligatorios en los datos nuevos.
     */
    public Optional<User> update(String id, User userData) {
        if (!tieneDatosObligatorios(userData)) {
            return Optional.empty();
        }
        for (User current : users) {
            if (current.getId().equals(id)) {
                if (current.getWorker().getDisponibilidad() == Disponibilidad.DESACTIVADO) {
                    return Optional.empty();
                }
                current.setNombre(userData.getNombre());
                current.setCorreo(userData.getCorreo());
                current.setTelefono(userData.getTelefono());
                current.setContrasena(userData.getContrasena());
                current.getWorker().setOficioPrincipal(userData.getWorker().getOficioPrincipal());
                return Optional.of(current);
            }
        }
        return Optional.empty();
    }
 
    public Optional<User> inactivar(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.getWorker().setDisponibilidad(Disponibilidad.DESACTIVADO);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
 
    public Optional<User> autenticar(String correo, String contrasena) {
        return users.stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo) && u.getContrasena().equals(contrasena))
                .findFirst();
    }
 
    private boolean tieneDatosObligatorios(User user) {
        if (user == null) {
            return false;
        }
        if (isBlank(user.getNombre()) || isBlank(user.getCorreo()) ||
            isBlank(user.getTelefono()) || isBlank(user.getContrasena())) {
            return false;
        }
        if (user.getWorker() == null || isBlank(user.getWorker().getOficioPrincipal())) {
            return false;
        }
        return true;
    }
 
    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
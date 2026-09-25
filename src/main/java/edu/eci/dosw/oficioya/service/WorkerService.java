package edu.eci.dosw.oficioya.service;
 
import org.springframework.stereotype.Service;
 
import edu.eci.dosw.oficioya.model.User;
import edu.eci.dosw.oficioya.model.Worker;
import edu.eci.dosw.oficioya.model.Disponibilidad;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
* Servicio encargado de la gestión en memoria de los usuarios con rol de Trabajador.
*/
@Service
public class WorkerService {
 
    private final List<User> users = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private static final Logger log = LoggerFactory.getLogger(WorkerService.class);
 
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
        log.debug("Encontrando todos los usuarios");
        return new ArrayList<>(users);
    }
 
    public Optional<User> findById(String id) {
        log.debug("Encontrando al usuario con id: {}",id);
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }
 
    /**
     * Crea un nuevo trabajador. Devuelve Optional.empty() si faltan
     * campos obligatorios (nombre, correo, telefono, oficio principal o contrasena).
     */
    public Optional<User> create(User user) {
        log.debug("Creando un nuevo trabajador con nombre: {}",user.getNombre());
        if (!tieneDatosObligatorios(user)) {
            log.info("El usuario no tiene los datos obligatorios");
            return Optional.empty();
        }
        log.info("El usuario tiene los datos obligatorios");
        user.setId(String.valueOf(idGenerator.getAndIncrement()));
        user.getWorker().setDisponibilidad(Disponibilidad.ACTIVO);
        users.add(user);
        log.info("Se ha creado el nuevo worker con id: {}",user.getId());
        return Optional.of(user);
    }
 
    /**
     * Actualiza un trabajador existente. Devuelve Optional.empty() si:
     * - no existe el id,
     * - el trabajador esta DESACTIVADO,
     * - o faltan campos obligatorios en los datos nuevos.
     */
    public Optional<User> update(String id, User userData) {
        log.debug("Actualizar un usuario con id {} a los datos de un usuario con id {} y nombre {}",id,userData.getId(),userData.getNombre());
        if (!tieneDatosObligatorios(userData)) {
            log.info("Los nuevos datos no están completos");
            return Optional.empty();
        }
        for (User current : users) {
            if (current.getId().equals(id)) {
                log.info("El usuario con el id: {} fue encontrado",id);
                if (current.getWorker().getDisponibilidad() == Disponibilidad.DESACTIVADO) {
                    log.info("El usuario tiene una disponibilidad que le impide actualizar sus datos");
                    return Optional.empty();
                }
                log.info("Se puede realizar la actualización de los datos");
                current.setNombre(userData.getNombre());
                current.setCorreo(userData.getCorreo());
                current.setTelefono(userData.getTelefono());
                current.setContrasena(userData.getContrasena());
                current.getWorker().setOficioPrincipal(userData.getWorker().getOficioPrincipal());
                log.info("Se realizó la actualización completa de los datos");
                return Optional.of(current);
            }
        }
        log.info("No fue encontrado el usuario con id: {}",id);
        return Optional.empty();
    }
 
    public Optional<User> inactivar(String id) {
        log.debug("Desactivar el usuario con id: {}",id);
        for (User user : users) {
            if (user.getId().equals(id)) {
                log.info("El usuario con id: {} fue encontrado",id);
                user.getWorker().setDisponibilidad(Disponibilidad.DESACTIVADO);
                log.info("Se actualizó correctamente la disponibilidad del usuario");
                return Optional.of(user);
            }
        }
        log.info("No se encontró el usuario con id: {}",id);
        return Optional.empty();
    }
 
    public Optional<User> autenticar(String correo, String contrasena) {
        log.debug("Se busca autenticar a un usuario con correo: {}",correo);
        return users.stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo) && u.getContrasena().equals(contrasena))
                .findFirst();
    }
 
    private boolean tieneDatosObligatorios(User user) {
        log.debug("Identificar si el usuario ingresado tiene los datos requeridos para continuar el proceso.");
        if (user == null) {
            log.info("No se envió un usuario");
            return false;
        }
        if (isBlank(user.getNombre()) || isBlank(user.getCorreo()) ||
            isBlank(user.getTelefono()) || isBlank(user.getContrasena())) {
            log.info("Tiene nombre: {}, Tiene correo: {}, Tiene telefono: {}, Tiene contraseña: {}",!isBlank(user.getNombre()),!isBlank(user.getCorreo()),!isBlank(user.getTelefono()),!isBlank(user.getContrasena()));
            return false;
        }
        if (user.getWorker() == null || isBlank(user.getWorker().getOficioPrincipal())) {
            log.info("Es trabajador: {}, Tiene trabajo principal: {}",!(user.getWorker() == null),!isBlank(user.getWorker().getOficioPrincipal()));
            return false;
        }
        log.debug("Tiene los datos obligatorios");
        return true;
    }
 
    private boolean isBlank(String value) {
        log.debug("Se revisa si los datos son nulos.");
        return value == null || value.isBlank();
    }
}
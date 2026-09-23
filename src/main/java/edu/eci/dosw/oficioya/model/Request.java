package edu.eci.dosw.oficioya.model;

import java.time.LocalDateTime;

/**
 * Representa una solicitud de servicio realizada por un usuario.
 */
public class Request {

    private LocalDateTime horaPedida;
    private LocalDateTime horaServicio;
    private RequestStates estado;
    private String descripcion;
    private String foto;
    private String ubicacion;

    public Request() {
    }

    public LocalDateTime getHoraPedida() {
        return horaPedida;
    }

    public void setHoraPedida(LocalDateTime horaPedida) {
        this.horaPedida = horaPedida;
    }

    public LocalDateTime getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(LocalDateTime horaServicio) {
        this.horaServicio = horaServicio;
    }

    public RequestStates getEstado() {
        return estado;
    }

    public void setEstado(RequestStates estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
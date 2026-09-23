package edu.eci.dosw.oficioya.service;

import java.util.ArrayList;

/**
 * Representa un usuario del sistema OficioYa.
 * Un usuario puede tener asociado un rol de Worker, Contractor y/o Administrator.
 */
public class User {

    private String id;
    private String name;
    private String foto;
    private String correo;
    private String telefono;
    private String contrasena;
    private double calificacion;
    private Suscripcion suscripcion;
    private ArrayList<Review> resenasObtenidas;
    private ArrayList<Request> requests;
    private Contractor contractor;
    private Worker worker;
    private Administrator admin;

    public User() {
        this.resenasObtenidas = new ArrayList<>();
        this.requests = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public ArrayList<Review> getResenasObtenidas() {
        return resenasObtenidas;
    }

    public void setResenasObtenidas(ArrayList<Review> resenasObtenidas) {
        this.resenasObtenidas = resenasObtenidas;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }
}
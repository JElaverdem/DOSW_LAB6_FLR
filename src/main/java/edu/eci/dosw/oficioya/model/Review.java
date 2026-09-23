package edu.eci.dosw.oficioya.model;

/**
 * Representa una reseña que un usuario deja sobre otro.
 */
public class Review {

    private User owner;
    private String comentario;
    private double calificacionDada;

    public Review() {
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getCalificacionDada() {
        return calificacionDada;
    }

    public void setCalificacionDada(double calificacionDada) {
        this.calificacionDada = calificacionDada;
    }
}
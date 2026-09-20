package edu.eci.dosw.oficioya.service;
import java.util.ArrayList;

public abstract class User{
  private String id;
  private String name;
  private String foto;
  private String correo;
  private String telefono;
  private double calificacion;
  private Suscripcion suscripcion;
  private ArrayList<Review> resenasObtenidas;
  private ArrayList<Request> requests;

  public User(){
    this.resenasObtenidas = new ArrayList<>();
    this.requests = new ArrayList<>();
  }
}

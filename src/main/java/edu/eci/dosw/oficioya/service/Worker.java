package edu.eci.dosw.oficioya.service;
import java.util.ArrayList;

public class Worker{
  private String oficioPrincipal;
  private ArrayList<String> oficiosSecundarios;
  private ArrayList<String> cobertura;
  private int tarifa;
  private Disponibilidad disponibilidad;
  private int trabajosCompletados;

  public Worker(){
    this.oficiosSecundarios = new ArrayList<>();
    this.cobertura = new ArrayList<>();
    this.disponibilidad = Disponibilidad.ACTIVO;
    this.trabajosCompletados = 0;
  }
}

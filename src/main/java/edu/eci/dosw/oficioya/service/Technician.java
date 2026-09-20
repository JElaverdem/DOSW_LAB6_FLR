package edu.eci.dosw.oficioya.service;
import java.util.ArrayList;

public class Technician extends Worker{
  private ArrayList<String> marcas;
  private ArrayList<String> equipos;

  public Technician(){
    this.marcas = new ArrayList<>();
    this.equipos = new ArrayList<>();
  }
}

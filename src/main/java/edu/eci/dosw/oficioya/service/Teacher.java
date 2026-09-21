package edu.eci.dosw.oficioya.service;
import java.util.ArrayList;

public class Teacher extends Worker{
  private ArrayList<String> materias;
  private String nivel;

  public Teacher(){
    this.materias = new ArrayList<>();
  }
}

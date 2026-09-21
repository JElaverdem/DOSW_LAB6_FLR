package edu.eci.dosw.oficioya.service;
import java.time.LocalDateTime;

public class Request{
  private LocalDateTime horaPedida;
  private LocalDateTime horaServicio;
  private RequestStates estado;
  private String descripcion;
  private String foto;
  private String ubicacion;

  public Request(){

  }
}

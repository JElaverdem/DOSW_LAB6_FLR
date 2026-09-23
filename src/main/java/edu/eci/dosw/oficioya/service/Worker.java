package edu.eci.dosw.oficioya.service;

import java.util.ArrayList;

/**
 * Representa el rol de trabajador de un usuario dentro del sistema OficioYa.
 * Contiene la información relacionada con el oficio que ofrece.
 */
public class Worker {

    private String oficioPrincipal;
    private ArrayList<String> oficiosSecundarios;
    private ArrayList<String> cobertura;
    private int tarifa;
    private Disponibilidad disponibilidad;
    private int trabajosCompletados;
    private ArrayList<String> galeriaTrabajos;

    public Worker() {
        this.oficiosSecundarios = new ArrayList<>();
        this.cobertura = new ArrayList<>();
        this.galeriaTrabajos = new ArrayList<>();
        this.disponibilidad = Disponibilidad.ACTIVO;
        this.trabajosCompletados = 0;
    }

    public String getOficioPrincipal() {
        return oficioPrincipal;
    }

    public void setOficioPrincipal(String oficioPrincipal) {
        this.oficioPrincipal = oficioPrincipal;
    }

    public ArrayList<String> getOficiosSecundarios() {
        return oficiosSecundarios;
    }

    public void setOficiosSecundarios(ArrayList<String> oficiosSecundarios) {
        this.oficiosSecundarios = oficiosSecundarios;
    }

    public ArrayList<String> getCobertura() {
        return cobertura;
    }

    public void setCobertura(ArrayList<String> cobertura) {
        this.cobertura = cobertura;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getTrabajosCompletados() {
        return trabajosCompletados;
    }

    public void setTrabajosCompletados(int trabajosCompletados) {
        this.trabajosCompletados = trabajosCompletados;
    }

    public ArrayList<String> getGaleriaTrabajos() {
        return galeriaTrabajos;
    }

    public void setGaleriaTrabajos(ArrayList<String> galeriaTrabajos) {
        this.galeriaTrabajos = galeriaTrabajos;
    }
}
package edu.eci.dosw.oficioya.model;

import java.util.ArrayList;

/**
 * Representa a un trabajador especializado en reparación técnica de equipos.
 */
public class Technician extends Worker {

    private ArrayList<String> marcas;
    private ArrayList<String> equipos;

    public Technician() {
        this.marcas = new ArrayList<>();
        this.equipos = new ArrayList<>();
    }

    public ArrayList<String> getMarcas() {
        return marcas;
    }

    public void setMarcas(ArrayList<String> marcas) {
        this.marcas = marcas;
    }

    public ArrayList<String> getEquipos() {
        return equipos;
    }

    public void setEquipos(ArrayList<String> equipos) {
        this.equipos = equipos;
    }
}
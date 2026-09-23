package edu.eci.dosw.oficioya.service;

import java.util.ArrayList;

/**
 * Representa a un trabajador especializado en la enseñanza de materias.
 */
public class Teacher extends Worker {

    private ArrayList<String> materias;
    private String nivel;

    public Teacher() {
        this.materias = new ArrayList<>();
    }

    public ArrayList<String> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<String> materias) {
        this.materias = materias;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
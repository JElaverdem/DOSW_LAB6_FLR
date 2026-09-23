package edu.eci.dosw.oficioya.service;

import java.util.ArrayList;

/**
 * Representa a un trabajador especializado en el cuidado de mascotas.
 */
public class PetCareTaker extends Worker {

    private ArrayList<String> atiende;

    public PetCareTaker() {
        this.atiende = new ArrayList<>();
    }

    public ArrayList<String> getAtiende() {
        return atiende;
    }

    public void setAtiende(ArrayList<String> atiende) {
        this.atiende = atiende;
    }
}
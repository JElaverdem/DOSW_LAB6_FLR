package edu.eci.dosw.oficioya.dto;

public class LoginDTO {
    private String email;
    private String password;

    public String getCorreo() { return email; }
    public void setCorreo(String email) { this.email = email; }

    public String getContrasena() { return password; }
    public void setContrasena(String contrasena) { this.password = password; }
}

package gunner.gunner.chat;

import java.sql.Time;

public class Mensaje {
    public String email;
    static String nombre2;
    public String mensaje;
    public Time time;
    public Mensaje(String mensaje, String nombre, Time time){
        this.mensaje=mensaje;
        this.email =nombre;
        this.time=time;
    }
}

package gunner.gunner;

import java.sql.Date;
import java.sql.Time;

public class Mensaje {
    public String nombre;
    static String nombre2;
    public String mensaje;
    public Time time;
    public Mensaje(String mensaje, String nombre, Time time){
        this.mensaje=mensaje;
        this.nombre=nombre;
        this.time=time;
    }
}

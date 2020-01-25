package gunner.gunner;

import android.graphics.Bitmap;

import java.sql.Blob;

public class ConversacionesUsuarioListaTipo {

     String nombre;
     String mensaje;
     Bitmap bitmap;
    public ConversacionesUsuarioListaTipo(String mensaje, String nombre, Bitmap image){
        this.mensaje=mensaje;
        this.nombre=nombre;
        this.bitmap=image;
    }
}

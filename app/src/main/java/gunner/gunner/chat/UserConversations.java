package gunner.gunner.chat;

import android.graphics.Bitmap;

import java.sql.Blob;

public class UserConversations {

     public String nombre;
     String mensaje;
     public Bitmap bitmap;
    public UserConversations(String mensaje, String nombre, Bitmap image){
        this.mensaje=mensaje;
        this.nombre=nombre;
        this.bitmap=image;
    }
}

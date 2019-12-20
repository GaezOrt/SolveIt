package gunner.gunner;

import android.graphics.Bitmap;

public class Comentarios {
    String comentario;
    float puntaje;
    String deQuien;
    String emailDeQuien;
    Bitmap bmp;

    public Comentarios(String comentario, float puntaje,String deQuien){
        this.comentario=comentario;
        this.puntaje=puntaje;
        this.deQuien=deQuien;
    }
}

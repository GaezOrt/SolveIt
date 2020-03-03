package gunner.gunner.reviews;

import android.graphics.Bitmap;

public class Comentarios {
    String comentario;
    float puntaje;
    String deQuien;
    String emailDeQuien;
    Bitmap bmp;
    String name;

    public Comentarios(String comentario, float puntaje,String deQuien,Bitmap bmp, String name){
        this.comentario=comentario;
        this.puntaje=puntaje;
        this.deQuien=deQuien;
        this.bmp=bmp;
        this.name=name;
    }
}

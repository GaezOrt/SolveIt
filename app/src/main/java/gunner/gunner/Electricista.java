package gunner.gunner;

import android.graphics.Bitmap;

public class Electricista {
    static int cantidadElectricistas;
     public Bitmap  photo;
     public String name;
    public  String email;
    public int rating;


    public Electricista(Bitmap photo, String name, String email, int rating){
        this.photo=photo;
        this.name=name;
        this.email=email;
        this.rating=rating;
    }
}

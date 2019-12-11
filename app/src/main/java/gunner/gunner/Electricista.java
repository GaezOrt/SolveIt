package gunner.gunner;

import android.graphics.Bitmap;

public class Electricista {
    static int cantidadElectricistas;
     public Bitmap  photo;
     public String name;
    public  String email;
    public int rating;
    public String location;
    String number;


    public Electricista(Bitmap photo, String name, String email, int rating,String location,String number){
        this.photo=photo;
        this.name=name;
        this.email=email;
        this.rating=rating;
        this.location=location;
        this.number=number;
    }
}

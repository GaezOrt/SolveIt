package gunner.gunner.rubros.electricistas;

import android.graphics.Bitmap;

public class Electricista {
    public static int cantidadElectricistas;
     public Bitmap  photo;
     public String name;
    public  String email;
    public int rating;
    public String location;
    public String number;
    float promedio;
    String lastName;
    String fechaDeNacimiento;
    float cantidadDeComentarios;

    public Electricista(Bitmap photo, String name, String email, int rating,String location,String number,float promedio,float cantidadDeComentarios,String fechaDeNacimiento,String lastName){
        this.photo=photo;
        this.name=name;
        this.email=email;
        this.rating=rating;
        this.location=location;
        this.number=number;
        this.promedio=promedio;
        this.cantidadDeComentarios=cantidadDeComentarios;
        this.fechaDeNacimiento=fechaDeNacimiento;
        this.lastName=lastName;
    }
}

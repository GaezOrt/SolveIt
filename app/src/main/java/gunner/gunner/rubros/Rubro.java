package gunner.gunner.rubros;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.target.DrawableImageViewTarget;

import java.sql.Time;

public class Rubro {

    public String rubro;
    Drawable drawableImg;
    public Rubro(String rubro, Drawable drawableImage){
        this.rubro=rubro;
        this.drawableImg=drawableImage;
    }
}

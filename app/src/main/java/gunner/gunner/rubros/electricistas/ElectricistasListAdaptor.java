package gunner.gunner.rubros.electricistas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import gunner.gunner.FindInDatabase;
import gunner.gunner.R;
import gunner.gunner.rubros.electricistas.Electricista;

public class ElectricistasListAdaptor extends ArrayAdapter<Electricista> {
   ArrayList<Electricista> electricistas;
   int resource;
   Context context;
   FindInDatabase find= new FindInDatabase();
    public ElectricistasListAdaptor(Context context, int resource, ArrayList <Electricista> electricista){
        super(context, R.layout.list_view,electricista);
        this.resource=resource;
        this.context=context;
        this.electricistas=electricista;

        }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        ViewHolder viewHolder=new ViewHolder();
         convertView= null;
        Electricista electricista= electricistas.get(position);
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_view,null,false);

            viewHolder.image=(CircleImageView) convertView.findViewById(R.id.greenCircle);
            viewHolder.name=(TextView)convertView.findViewById(R.id.textView);
            viewHolder.image.setImageBitmap(electricista.photo);
            viewHolder.name.setText(electricista.name+" "+ electricista.lastName);
            viewHolder.rating=(RatingBar)convertView.findViewById(R.id.myRating);
            viewHolder.rating.setRating(electricista.promedio);
            LayerDrawable stars = (LayerDrawable) viewHolder.rating.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);

            int x=(int)electricista.cantidadDeComentarios;

            convertView.setTag(viewHolder);
            if(x<5){
                viewHolder.rango=(TextView)convertView.findViewById(R.id.textView43);
                viewHolder.rango.setText("Servy nuevo");
                viewHolder.rangoRating=(RatingBar)convertView.findViewById(R.id.MyRating);
                viewHolder.rangoRating.setRating(1);
                LayerDrawable stars2 = (LayerDrawable) viewHolder.rangoRating.getProgressDrawable();
                stars2.getDrawable(2).setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
            }
            if(x>=5 && x<25){
                viewHolder.rango=(TextView)convertView.findViewById(R.id.textView43);
                viewHolder.rango.setText("Servy plata");
                viewHolder.rangoRating=(RatingBar)convertView.findViewById(R.id.MyRating);
                viewHolder.rangoRating.setRating(1);
                LayerDrawable stars2 = (LayerDrawable) viewHolder.rangoRating.getProgressDrawable();
                stars2.getDrawable(2).setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
            }
            if(x>=25 && x<50){
                viewHolder.rango=(TextView)convertView.findViewById(R.id.textView43);
                viewHolder.rango.setText("Servy oro");
                viewHolder.rangoRating=(RatingBar)convertView.findViewById(R.id.MyRating);
                viewHolder.rangoRating.setRating(1);
                LayerDrawable stars2 = (LayerDrawable) viewHolder.rangoRating.getProgressDrawable();
                stars2.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            }
            if(x>=50){
                viewHolder.rango=(TextView)convertView.findViewById(R.id.textView43);
                viewHolder.rango.setText("Servy Platinium");
                viewHolder.rangoRating=(RatingBar)convertView.findViewById(R.id.MyRating);
                viewHolder.rangoRating.setRating(1);
                LayerDrawable stars2 = (LayerDrawable) viewHolder.rating.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
            }
        }else{
        viewHolder  =(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder{
        CircleImageView image;
        TextView name;
        RatingBar rating;
        RatingBar rangoRating;
        TextView amount;
        TextView ubicacion;
        TextView nacimiento;
        TextView email;
        TextView rango;

    }
}

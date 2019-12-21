package gunner.gunner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyListAdaptor extends ArrayAdapter<Electricista> {
   ArrayList<Electricista> electricistas;
   int resource;
   Context context;
   FindInDatabase find= new FindInDatabase();
    public MyListAdaptor(Context context,int resource, ArrayList <Electricista> electricista){
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

            viewHolder.image=(CircleImageView) convertView.findViewById(R.id.profile_image);
            viewHolder.name=(TextView)convertView.findViewById(R.id.editText4);
            viewHolder.image.setImageBitmap(electricista.photo);
            viewHolder.name.setText(electricista.name);
            viewHolder.rating=(RatingBar)convertView.findViewById(R.id.MyRating);
            viewHolder.rating.setRating(electricista.promedio);
            LayerDrawable stars = (LayerDrawable) viewHolder.rating.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
            viewHolder.amount=(TextView)convertView.findViewById(R.id.textView42);
            int x=(int)electricista.cantidadDeComentarios;
            viewHolder.amount.setText("Reviews: "+(x));
            viewHolder.ubicacion=(TextView)convertView.findViewById(R.id.textView39);
            viewHolder.ubicacion.setText(electricista.location);
            viewHolder.nacimiento=(TextView)convertView.findViewById(R.id.textView41);
            viewHolder.nacimiento.setText(electricista.fechaDeNacimiento);
            viewHolder.email=(TextView)convertView.findViewById(R.id.textView28);
            viewHolder.email.setText(electricista.email);
            convertView.setTag(viewHolder);
        }else{
        viewHolder  =(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder{
        CircleImageView image;
        TextView name;
        RatingBar rating;
        TextView amount;
        TextView ubicacion;
        TextView nacimiento;
        TextView email;

    }
}

package gunner.gunner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentsListAdaptor extends ArrayAdapter<Comentarios> {
    ArrayList<Comentarios> comentarios;
    int resource;
    Context context;
    public CommentsListAdaptor(Context context,int resource, ArrayList <Comentarios> comentarios){
        super(context, R.layout.list_view,comentarios);
        this.resource=resource;
        this.context=context;
        this.comentarios=comentarios;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder = new ViewHolder();
       ViewHolder viewHolder=new ViewHolder();
        convertView= null;
        Comentarios comentarios= this.comentarios.get(position);
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.comentarios,null,false);
            viewHolder.image=(ImageView)convertView.findViewById(R.id.profile_image);
            viewHolder.image.setImageBitmap(comentarios.bmp);
            viewHolder.email =(TextView)convertView.findViewById(R.id.textView28);
            viewHolder.rating=(RatingBar)convertView.findViewById(R.id.MyRating);
            viewHolder.rating.setRating(comentarios.puntaje);
            viewHolder.email.setText(comentarios.deQuien);
            viewHolder.comentario=(TextView)convertView.findViewById(R.id.textView27);
            viewHolder.comentario.setText(comentarios.comentario);

            viewHolder.name=(TextView) convertView.findViewById(R.id.editText4);
            viewHolder.name.setText(comentarios.name);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder{
        ImageView image;
        TextView email;
        RatingBar rating;
        TextView comentario;
        TextView name;

    }
}

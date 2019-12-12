package gunner.gunner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        ViewHolder mainViewHolder= null;
        Comentarios comentarios= this.comentarios.get(position);
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.comentarios,null,false);
            ViewHolder viewHolder=new ViewHolder();
            viewHolder.image=(ImageView)convertView.findViewById(R.id.imageView7);
            viewHolder.name=(TextView)convertView.findViewById(R.id.editText4);
            viewHolder.rating=(RatingBar)convertView.findViewById(R.id.MyRating);
            viewHolder.rating.setRating(comentarios.puntaje);
            viewHolder.name.setText(comentarios.deQuien);
            viewHolder.comentario=(TextView)convertView.findViewById(R.id.textView28);
            viewHolder.comentario.setText(comentarios.comentario);
            convertView.setTag(viewHolder);
        }else{
            mainViewHolder=(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder{
        ImageView image;
        TextView name;
        RatingBar rating;
        TextView comentario;

    }
}

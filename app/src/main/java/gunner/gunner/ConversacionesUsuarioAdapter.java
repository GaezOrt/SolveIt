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

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversacionesUsuarioAdapter extends ArrayAdapter<ConversacionesUsuarioListaTipo> {
    ArrayList<ConversacionesUsuarioListaTipo> conversaciones;
    int resource;
    Context context;

    public ConversacionesUsuarioAdapter(Context context,int resource, ArrayList <ConversacionesUsuarioListaTipo> conversaciones){
        super(context, R.layout.list_conversaciones_usuario_view,conversaciones);
        this.resource=resource;
        this.context=context;
        this.conversaciones=conversaciones;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        ViewHolder viewHolder=new ViewHolder();
        convertView= null;
        ConversacionesUsuarioListaTipo comentarios= this.conversaciones.get(position);
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_conversaciones_usuario_view,null,false);

            viewHolder.name=(TextView) convertView.findViewById(R.id.textView43);
            viewHolder.name.setText(comentarios.nombre);

            viewHolder.image=(CircleImageView)convertView.findViewById(R.id.greenCircle);
            viewHolder.image.setImageBitmap(comentarios.bitmap);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder{
     TextView name;
     CircleImageView image;
    }
}
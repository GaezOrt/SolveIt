package gunner.gunner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends ArrayAdapter<Mensaje> {

    ArrayList<Mensaje> mensajes;
    int resource;
    Context context;
    public MessageAdapter(Context context,int resource, ArrayList <Mensaje> mensajes){
        super(context, R.layout.my_message,mensajes);
        this.resource=resource;
        this.context=context;
        this.mensajes=mensajes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder = new ViewHolder();
        ViewHolder viewHolder=new ViewHolder();
        convertView= null;

        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());

            if(!mensajes.get(position).nombre.equals(LogInService.email)) {
                convertView = inflater.inflate(R.layout.their_message, null, false);
                viewHolder.mensajeAgeno=(TextView)convertView.findViewById(R.id.name);
                viewHolder.mensajeAgeno.setText(mensajes.get(position).nombre);
                System.out.println("Nombre del usuario que lo pone adentro del que no es mi usuario:" + mensajes.get(position).nombre+ "Numero que pone"+ mensajes.get(position).mensaje);
            }else{
                System.out.println("Nombre del usuario que lo pone:" + mensajes.get(position).nombre+ "Numero que pone"+ mensajes.get(position).mensaje);
                convertView = inflater.inflate(R.layout.my_message, null, false);
            }
            viewHolder.mensaje =(TextView)convertView.findViewById(R.id.message);
            viewHolder.mensaje.setText(mensajes.get(position).mensaje);

            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder{
      TextView mensaje;
      TextView mensajeAgeno;

    }
}
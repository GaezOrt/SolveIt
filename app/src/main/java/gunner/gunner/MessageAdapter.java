package gunner.gunner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends ArrayAdapter<Mensaje> {

    ArrayList<Mensaje> mensajes;
    int resource;
    Context context;
    static boolean readPhoto;
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
        FindInDatabase find= new FindInDatabase();
        if(convertView==null ){
            LayoutInflater inflater= LayoutInflater.from(getContext());

            if(!mensajes.get(position).email.equals(LogInService.email)) {
                convertView = inflater.inflate(R.layout.their_message, null, false);
                viewHolder.nombreAgeno =(TextView)convertView.findViewById(R.id.name);
                viewHolder.nombreAgeno.setText(mensajes.get(position).email);
                viewHolder.mensajeAgeno=(TextView)convertView.findViewById(R.id.message);
                viewHolder.mensajeAgeno.setText(mensajes.get(position).mensaje);

                //if(Chat.downloadPhoto) {
                    viewHolder.image=(CircleImageView) convertView.findViewById(R.id.greenCircle);
                    viewHolder.image.setImageBitmap(find.findPictureFromUser(mensajes.get(position).email));
                    System.out.println("Downloading photo" + find.findPictureFromUser(mensajes.get(position).email));
                    Chat.downloadPhoto=false;
               // }
                    }else{
               convertView = inflater.inflate(R.layout.my_message, null, false);
                viewHolder.mensaje =(TextView)convertView.findViewById(R.id.message);
                viewHolder.mensaje.setText(mensajes.get(position).mensaje);

            }


            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder{
      TextView mensaje;
      TextView nombreAgeno;
      TextView mensajeAgeno;
      CircleImageView image;
    }
}
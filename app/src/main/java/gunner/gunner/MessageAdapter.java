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

public class MessageAdapter extends ArrayAdapter<String> {

    ArrayList<String> mensajes;
    int resource;
    Context context;
    public MessageAdapter(Context context,int resource, ArrayList <String> mensajes){
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
        String mensaje= this.mensajes.get(position);
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.my_message,null,false);

            viewHolder.mensaje =(TextView)convertView.findViewById(R.id.message);
            viewHolder.mensaje.setText(mensaje);

            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder{
      TextView mensaje;

    }
}
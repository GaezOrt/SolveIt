package gunner.gunner;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RubrosListAdapter extends ArrayAdapter<Rubro> {
    ArrayList<Rubro> rubros= new ArrayList<Rubro>();
    int resource;
    Context context;
    public RubrosListAdapter(Context context, int resource, ArrayList<Rubro> rubro){
        super(context, R.layout.rubross,rubro);
        this.resource=resource;
        this.context=context;
        this.rubros=rubro;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RubrosListAdapter.ViewHolder holder = new RubrosListAdapter.ViewHolder();
        RubrosListAdapter.ViewHolder viewHolder=new RubrosListAdapter.ViewHolder();
        convertView= null;
        Rubro rubro= rubros.get(position);
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.rubross,null,false);

            viewHolder.image=(CircleImageView) convertView.findViewById(R.id.image);
            viewHolder.image.setImageDrawable(rubro.drawableImg);
            viewHolder.name=(TextView)convertView.findViewById(R.id.textView3);
            viewHolder.name.setText(rubro.rubro);

            convertView.setTag(viewHolder);
        }else{
            viewHolder  =(RubrosListAdapter.ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder{
        CircleImageView image;
        TextView name;
        RatingBar rating;


    }


}

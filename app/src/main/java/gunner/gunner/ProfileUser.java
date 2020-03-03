package gunner.gunner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import gunner.gunner.reviews.AddComment;
import gunner.gunner.reviews.Comentarios;
import gunner.gunner.reviews.CommentsListAdaptor;

import static gunner.gunner.R.id.MyRating;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;
import static gunner.gunner.R.id.imageView15;
import static gunner.gunner.R.id.imageView2;
import static gunner.gunner.R.id.list;



public class ProfileUser extends AppCompatActivity {

    static ArrayList<Comentarios> comentarios =new ArrayList<Comentarios>();
    static CommentsListAdaptor adapter ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        FindInDatabase find= new FindInDatabase();
        find.findComments(MainActivity.loggedEmail,comentarios);
        //find.findAmountOfCommentsEachProvider(MainActivity.loggedEmail);
        //Boton para atras
        final Button atrasBut=(Button) findViewById(button2) ;
        atrasBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ProfileUser.this, MainActivity.class));
                setContentView(R.layout.activity_main);
            }
        });

        //Mostrar email
        final TextView emailButt=(TextView) findViewById(editText3) ;
        emailButt.setText(MainActivity.loggedEmail);

        //Mostrar username
        final TextView usernameButt=(TextView) findViewById(editText) ;
        usernameButt.setText(MainActivity.loggedUsername);

        //Mostrar telefono
        final TextView phoneButt=(TextView) findViewById(editText5) ;
        phoneButt.setText(MainActivity.loggedPhone);

        //Mostrar ubicacion
        TextView locatText=(TextView) findViewById(editText2) ;
        locatText.setText(MainActivity.loggedLocation);

        Log.w(" Activity"," Array in database" + MainActivity.loggedImageInDatabaseArray);

        //Settear imagen con los bytes extraidos de la base de datos
        ImageView image=(ImageView)findViewById(imageView2);
        Bitmap bitmap = BitmapFactory.decodeByteArray(MainActivity.loggedImageInDatabaseArray, 0, MainActivity.loggedImageInDatabaseArray .length);
        image.setImageBitmap(bitmap);
        MainActivity.profileImage=bitmap;
        ImageView add=(ImageView)findViewById(imageView15);


        RatingBar rating= (RatingBar)findViewById(MyRating);
        float x=find.obtenerPromedio(MainActivity.loggedEmail);
        rating.setRating(x);
        System.out.println("aaaa "+find.obtenerPromedio(MainActivity.loggedEmail));
        adapter = new CommentsListAdaptor(this,R.layout.comentarios,comentarios);
        final ListView listView= (ListView) findViewById(list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileUser.this.startActivity(new Intent(ProfileUser.this, AddComment.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileUser.this, MainActivity.class));

    }
}

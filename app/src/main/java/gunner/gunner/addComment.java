package gunner.gunner;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Rating;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.sql.PreparedStatement;

import static android.view.View.VISIBLE;
import static gunner.gunner.R.id.MyRating;
import static gunner.gunner.R.id.PasswordInc;
import static gunner.gunner.R.id.imageView17;
import static gunner.gunner.R.id.imageView5;

public class addComment extends AppCompatActivity {

    static String email;
    static String emailOfSender;
    String comment;
    boolean senderExist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment_page);


        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setMessage("Comment added.");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert12 = builder.create();




        Button add= (Button)findViewById(R.id.comment);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder1.setTitle("Error en comentario");
        //builder1.setIcon(R.drawable.usercorrect);

        builder1.setMessage("Debe estar loggeado para poder realizar el review");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingBar rating = (RatingBar) findViewById(MyRating);
                MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.cli);
                mp.start();
                EditText edit = (EditText) findViewById(R.id.editText8);
                comment = edit.getText().toString();
                if (LogInService.email == null) {
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    try {

                        alert12.show();


                        DatabaseConnection database = new DatabaseConnection();
                        database.connect();
                        String updateSQL = "INSERT INTO Comentarios VALUES (?,?,?,?)";
                        PreparedStatement pstmt = database.conn.prepareStatement(updateSQL);
                        pstmt.setString(1, email);
                        pstmt.setString(2, comment);
                        pstmt.setString(3, LogInService.email);
                        pstmt.setFloat(4, rating.getRating());
                        pstmt.executeUpdate();
                        System.out.println("Doing update on data");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });


        final ImageView atrasBut=(ImageView) findViewById(imageView17) ;
        atrasBut.setOnClickListener((v)-> {
            FindInDatabase.comentarios.clear();
            startActivity(new Intent(addComment.this, FindInDatabase.class));
        });
    }
    @Override
    public void onBackPressed() {
        FindInDatabase.comentarios.clear();
        startActivity(new Intent(addComment.this, FindInDatabase.class));


    }



}
package gunner.gunner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static gunner.gunner.R.id.PasswordInc;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.button7;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;

public class LogIn extends AppCompatActivity {
    String userName="9QFW2Os9pV",passwordDatabase="dKObZerUnf",url="jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV",driver,driver1="com.mysql.jdbc.Driver";
        String username;
        String password;
        String usernameCorrect;
        String passwordCorrect;
        DatabaseConnection database= new DatabaseConnection();
        Connection con;
        Statement st;
    int rowNumberPassword;
    int rowNumberUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        //Apretar boton para ir para atras
        final Button atrasBut=(Button) findViewById(button2) ;
        atrasBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, MainActivity.class));
                setContentView(R.layout.activity_main);
            }
        });
//Apretar boton para loggear
        final Button logInButt=(Button) findViewById(button7) ;
        logInButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText usernameText=(EditText) findViewById(editText);
                username=usernameText.getText().toString();
                EditText passwordText=(EditText) findViewById(editText2);
                password=passwordText.getText().toString();

               try {
                  Class.forName(driver1);
                   con= DriverManager.getConnection(url, userName, passwordDatabase);
                   st=con.createStatement();
                   System.out.println("Connection is successful");
                   st=con.createStatement();
                  // PreparedStatement upd = con.prepareStatement("SELECT User FROM Users WHERE '"+username+"'=User AND Password='"+password+"'");
                   PreparedStatement updN = con.prepareStatement("SELECT User FROM Users");
                   PreparedStatement updP = con.prepareStatement("SELECT Password FROM Users");

                   ResultSet rs = updN.executeQuery();
                   ResultSet rsP=updP.executeQuery();
                  // for (int x=1;x<=rs.getMetaData().getColumnCount();x++)
                  // System.out.print(rs.getMetaData().getColumnName(x)+ "\t");


                   while(rs.next()) {
                       String userName = rs.getString("User");

                       if (userName.equals(username)&& username!=null ) {
                           rowNumberUsername=rs.getRow();
                           usernameCorrect=username;
                           MainActivity.loggedUsername=username;
                           System.out.println("Username correct");
                       } else {
                           final TextView passwordIncText=(TextView) findViewById(PasswordInc) ;
                           passwordIncText.setText("Username or password incorrect.");

                           System.out.println("Username or password wrong");
                       }

                   }
                   while(rsP.next()){
                       String passWord=rsP.getString("Password");
                       if (passWord.equals(password) && password!=null) {
                           passwordCorrect=password;
                           rowNumberPassword=rsP.getRow();
                           System.out.println("Password succesfull");
                       } else {
                           System.out.println("Username or Password wrong");
                       }
                   }
                   if(rowNumberPassword==rowNumberUsername && usernameCorrect!=null && passwordCorrect!=null){
                       System.out.println("Log in correct");
                       MainActivity.loggedIn=true;
                       startActivity(new Intent(LogIn.this, MainActivity.class));
                       setContentView(R.layout.activity_main);

                   }
               }catch (Exception e){
                   e.printStackTrace();
               }

               // startActivity(new Intent(LogIn.this, MainActivity.class));
               // setContentView(R.layout.activity_main);
            }
        });

    }
}

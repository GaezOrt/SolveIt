package gunner.gunner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    Connection con;

    private EditText editText;
    static MessageAdapter messageAdapter;
    private ListView messagesView;
    static ArrayList<String> mensajes =new ArrayList<String>();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate  (savedInstanceState);
        setContentView(R.layout.message_layour);

        Intent u = new Intent(this, ChatInteraction.class);

        startService(u);

        editText = (EditText) findViewById(R.id.editText);
            ImageButton image= (ImageButton)findViewById(R.id.sender);
            image.setOnClickListener(new View.OnClickListener() {

                                         @Override
                                         public void onClick(View view) {
                                             Chat.runOnUI(new Runnable() {
                                                 public void run() {
                                                     try {
                                                         FindInDatabase find= new FindInDatabase();
                                                         if(FindInDatabase.emailPassed !=null) {
                                                             find.findMensajesBetween2Persons(LogInService.email, FindInDatabase.emailPassed);
                                                         }else{
                                                             find.findMensajesBetween2Persons(LogInService.email, DescargarConversacionesDeUsuario.email);

                                                         }
                                                         System.out.println("Image clicked");
                                                         String updateSQL = "INSERT INTO Conversaciones VALUES (?,?,?)";
                                                         final DatabaseConnection data = new DatabaseConnection();
                                                         try {
                                                             con = data.connect();
                                                             PreparedStatement pstmt = con.prepareStatement(updateSQL);
                                                             pstmt.setString(1, LogInService.email);
                                                             pstmt.setString(2, editText.getText().toString());
                                                             if(FindInDatabase.emailPassed !=null) {
                                                                 pstmt.setString(3, FindInDatabase.emailPassed);
                                                             }else{
                                                                 pstmt.setString(3, DescargarConversacionesDeUsuario.email);
                                                             }
                                                             pstmt.executeUpdate();
                                                             editText.setText("");
                                                             messagesView.invalidateViews();
                                                         } catch (Exception e) {
                                                             e.printStackTrace();
                                                         }
                                                     } catch (Exception e) {
                                                         e.printStackTrace();
                                                     }
                                                 }
                                             });
                                         }
                                     });


        messageAdapter = new MessageAdapter(this, R.layout.my_message, mensajes);
        final ListView listView = (ListView) findViewById(R.id.messages_view);
        listView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
        listView.invalidateViews();
    }
    public static Handler UIHandler;

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

}


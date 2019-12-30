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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;

public class Chat extends AppCompatActivity {

    Connection con;

    private EditText editText;
    static MessageAdapter messageAdapter;
    public ListView listView;
    static ArrayList<Mensaje> mensajes =new ArrayList<Mensaje>();
    static boolean vieneDeBusqueda=false;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate  (savedInstanceState);
        setContentView(R.layout.message_layour);
        mensajes.clear();
        Intent u = new Intent(this, ChatInteraction.class);
        DescargarConversacionesDeUsuario descargar= new DescargarConversacionesDeUsuario();
        descargar.seguirDescargandoChat=true;
        startService(u);
        ChatInteraction.descargarDeLaListaDeChats=true;
        editText = (EditText) findViewById(R.id.editText);
        ImageButton image= (ImageButton)findViewById(R.id.sender);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Chat.runOnUI(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("Image clicked");
                            String updateSQL = "INSERT INTO Conversaciones VALUES (?,?,?,?)";
                             java.util.Date utilStartDate = Calendar.getInstance().getTime();
                            java.sql.Time sqlStartDate = new java.sql.Time(utilStartDate.getTime());

                            final DatabaseConnection data = new DatabaseConnection();
                            try {
                                con = data.connect();
                                PreparedStatement pstmt = con.prepareStatement(updateSQL);
                                pstmt.setString(1, LogInService.email);
                                pstmt.setString(2, editText.getText().toString());
                                pstmt.setString(3, DescargarConversacionesDeUsuario.email);
                                pstmt.setTime(4,sqlStartDate);
                                messageAdapter.notifyDataSetChanged();
                                pstmt.executeUpdate();
                                editText.setText("");
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
        listView = (ListView) findViewById(R.id.messages_view);
        listView.setAdapter(messageAdapter);
        Chat.messageAdapter.notifyDataSetChanged();

    }
    public static Handler UIHandler;

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

}



package gunner.gunner;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.Message;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Random;

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
        editText = (EditText) findViewById(R.id.editText);
            ImageButton image= (ImageButton)findViewById(R.id.sender);
            image.setOnClickListener(new View.OnClickListener() {

                                         @Override
                                         public void onClick(View view) {
                                             Chat.runOnUI(new Runnable() {
                                                 public void run() {
                                                     try {
                                                         System.out.println("Image clicked");
                                                         String updateSQL = "INSERT INTO Conversaciones VALUES (?,?,?)";
                                                         final DatabaseConnection data = new DatabaseConnection();
                                                         try {
                                                             con = data.connect();
                                                             PreparedStatement pstmt = con.prepareStatement(updateSQL);
                                                             pstmt.setString(1, LogInService.email);
                                                             pstmt.setString(2, editText.getText().toString());
                                                             pstmt.setString(3, FindInDatabase.namePassedViaParam);
                                                             pstmt.executeUpdate();
                                                             editText.setText("");
                                                             FindInDatabase find= new FindInDatabase();

                                                             find.findMensajesBetween2Persons(LogInService.email,FindInDatabase.namePassedViaParam);
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


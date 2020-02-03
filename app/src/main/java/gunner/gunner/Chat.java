package gunner.gunner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Chat extends AppCompatActivity {

    Connection con;
    private Context contexte;
    private EditText editText;
    static MessageAdapter messageAdapter;
    public ListView listView;
    static boolean downloadPhoto=true;
    static ArrayList<Mensaje> mensajes =new ArrayList<Mensaje>();
    static boolean vieneDeBusqueda=false;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate  (savedInstanceState);
        setContentView(R.layout.message_layour);
        contexte=this;
        mensajes.clear();
        downloadPhoto=true;
        ChatInteraction.lookForEmail2=true;
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
                                MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.sent);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
                                messageAdapter.notifyDataSetChanged();
                                pstmt.executeUpdate();
                                mp.start();
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
    public void notification(String email,String nombre, String message, Context context) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = createID();
        String channelId = "channel-id";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.wechat_icon)//R.mipmap.ic_launcher
                .setContentTitle(nombre+"("+email+")")
                .setContentText(message)
                .setVibrate(new long[]{100, 250})
                .setLights(Color.YELLOW, 500, 5000)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary));

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(new Intent(context, MainActivity.class));
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }

    public int createID() {
        java.util.Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.FRENCH).format(now));
        return id;
    }


}
package gunner.gunner;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChatInteraction extends IntentService {

    FindInDatabase find = new FindInDatabase();
    Chat chat= new Chat();
    String emailFromList;
    boolean seguirDescargandoChat = true;
    static boolean descargarDeLaListaDeChats = true;
    static int numeroDeRS;
    static boolean addNotification=true;
    static String emailFromOtherUser;
    static boolean lookForEmail2=true;
    Connection con;
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    public static final String TRANSITION_INTENT_SERVICE = "ReceiveTransitionsIntentService";


    public ChatInteraction() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        while (find.chequearConstantemente(LogInService.email,DescargarConversacionesDeUsuario.email)+1>Chat.mensajes.size()) {
            //find.insertAllTheTime(LogInService.email, DescargarConversacionesDeUsuario.email);
            System.out.println("Hola gil");
            findMensajesBetween2Persons(LogInService.email, DescargarConversacionesDeUsuario.email);
            if(addNotification){
                sendNotification("Hola");
                addNotification=false;
            }
        }

    }
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Chat.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("GCM Notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg).setSmallIcon(R.drawable.wechat_icon);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }
    public void findMensajesBetween2Persons(String persona1, String persona2) {
        try {


            final DatabaseConnection data = new DatabaseConnection();

            con = data.connect();
            PreparedStatement updN = con.prepareStatement("SELECT * FROM Conversaciones WHERE (primerIntegrante=? AND segundoIntegrante=?) OR (primerIntegrante=? AND segundoIntegrante=?)",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            updN.setFetchSize(1);
            updN.setString(1,persona1);
            updN.setString(2,persona2);
            updN.setString(3,persona2);
            updN.setString(4,persona1);
            ResultSet rs = updN.executeQuery();


            while (rs.next()) {
                String g = rs.getString("mensaje");

                if (Chat.mensajes.size() + 1 < rs.getRow()) {
                    Mensaje mensaje = new Mensaje(g, rs.getString("primerIntegrante"), rs.getTime("horario"));
                    if(lookForEmail2){
                        if(!mensaje.email.equals(LogInService.email)) {
                            emailFromOtherUser = find.findNameFromuser(mensaje.email);
                            lookForEmail2 = false;
                        }
                    }
                    Chat.runOnUI(new Runnable() {
                        public void run() {
                            try {

                                System.out.println("Mensaje" + mensaje.mensaje);
                                Chat.mensajes.add(mensaje);
                                Chat chat= new Chat();
                                if(!mensaje.email.equals(LogInService.email)) {
                                    chat.notification(mensaje.email,emailFromOtherUser, mensaje.mensaje, ChatInteraction.this);
                                    ChatInteraction.addNotification = true;
                                }
                                Chat.messageAdapter.notifyDataSetChanged();
                                ChatInteraction.numeroDeRS = rs.getRow() - 1;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            System.out.println("Ultimo registro "+ChatInteraction.numeroDeRS);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "" + e.getMessage() + "Tomatela loro");


        }
    }

}






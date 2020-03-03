package gunner.gunner.background;

import android.app.IntentService;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;

import java.sql.Connection;
import java.sql.SQLException;

import gunner.gunner.DatabaseConnection;

public class KeepEverythingUpdated extends IntentService {
    DatabaseConnection data = new DatabaseConnection();
    static Connection con;
    public KeepEverythingUpdated() {
        super("Keep");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
       try {
           if (!DatabaseConnection.conn.isValid(0)) {
               data.connect();
               System.out.println("We goot dissconected");
           }
       }catch (Exception e){

       }
        if(DatabaseConnection.conn==null) {

           try {
               data.connect();
           }catch ( Exception e){

           }
       }
    }
}

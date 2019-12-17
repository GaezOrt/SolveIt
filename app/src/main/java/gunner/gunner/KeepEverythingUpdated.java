package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;

import java.sql.Connection;

public class KeepEverythingUpdated extends IntentService {

    static Connection con;
    public KeepEverythingUpdated() {
        super("Keep");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
       if(DatabaseConnection.conn==null) {
           DatabaseConnection data = new DatabaseConnection();
           try {


               data.connect();
           }catch ( Exception e){

           }
       }
    }
}

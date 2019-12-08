package gunner.gunner;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import java.sql.SQLException;

public class DownloadStuffInBackground extends IntentService {

    FindInDatabase find = new FindInDatabase();
    public DownloadStuffInBackground() {
        super("Download");
    }

    @Override
    protected void onHandleIntent( Intent intent) {

            if(DatabaseConnection.conn==null){
                DatabaseConnection database= new DatabaseConnection();
                try {
                    database.connect();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            find.findElectricistas();
            if(Electricista.cantidadElectricistas==Electricidad.electricistas.size()){
                stopSelf();
            }

    }
}

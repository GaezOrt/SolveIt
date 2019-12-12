package gunner.gunner;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.SQLException;

public class DownloadStuffInBackground extends IntentService {

    FindInDatabase find = new FindInDatabase();
    static boolean keepLooking=true;
    public DownloadStuffInBackground() {
        super("Download");
    }

    @Override
    protected void onHandleIntent( Intent intent) {


        if (keepLooking)
        {
            Log.w("E","Downloading electricistas");
            find.findElectricistas();
            keepLooking=false;
        }

        if(Electricista.cantidadElectricistas>Electricidad.electricistas.size()){
            Electricidad.showLoad=true;
        }else{
            Electricidad.showLoad=false;
        }

    }
}

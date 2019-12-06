package gunner.gunner;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class DownloadStuffInBackground extends IntentService {

    FindInDatabase find = new FindInDatabase();
    public DownloadStuffInBackground() {
        super("Download");
    }

    @Override
    protected void onHandleIntent( Intent intent) {

            find.findElectricistas();
            if(Electricista.cantidadElectricistas==Electricidad.electricistas.size()){
                stopSelf();
            }

    }
}

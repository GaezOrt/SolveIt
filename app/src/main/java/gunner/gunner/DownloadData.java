package gunner.gunner;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DownloadData extends Service {
    @Override
    public void onCreate() {


        Log.w("Service", "onCreate()");
        System.out.println("SEEEEEEEEEEEEEEEEEEE");
        android.os.Debug.waitForDebugger();
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("Service","OnCommand");

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class KeepEverythingUpdated extends IntentService {


    public KeepEverythingUpdated() {
        super("Keep");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w("E","Keep everything posted");

    }
}

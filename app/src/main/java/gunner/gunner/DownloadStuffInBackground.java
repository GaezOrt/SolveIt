package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class DownloadStuffInBackground extends IntentService {
    public static boolean isRunning;
    FindInDatabase find = new FindInDatabase();
   static String LookingAfterLocation;
    static boolean keepLooking=true;
    static boolean lookForLocation=false;
    static boolean searchComments=false;

    public DownloadStuffInBackground() {
        super("Download");
    }

    @Override
    protected void onHandleIntent( Intent intent) {


        if (keepLooking &&!lookForLocation)
        {
            Log.w("E","Downloading electricistas");
            find.findElectricistas();
            keepLooking=false;
            isRunning=false;
        }
        if(lookForLocation){
            find.findInDatabaseByLocation(LookingAfterLocation);
            lookForLocation=false;
        }
        if(searchComments){
            find.findComments(FindInDatabase.emailPassed,FindInDatabase.comentarios);
            searchComments=false;
        }


        if(Electricista.cantidadElectricistas>Electricidad.electricistas.size()){
            Electricidad.showLoad=true;
        }else{
            Electricidad.showLoad=false;
        }

    }
}

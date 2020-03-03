package gunner.gunner.background;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import gunner.gunner.FindInDatabase;
import gunner.gunner.rubros.electricistas.Electricidad;
import gunner.gunner.rubros.electricistas.Electricista;

public class DownloadStuffInBackground extends IntentService {
    public static boolean isRunning;
    public static boolean lookForLocation;
    FindInDatabase find = new FindInDatabase();
   public static String LookingAfterLocation;
    public static boolean keepLooking=true;

    public static boolean searchComments=false;

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


        if(Electricista.cantidadElectricistas> Electricidad.electricistas.size()){
            Electricidad.showLoad=true;
        }else{
            Electricidad.showLoad=false;
        }

    }
}

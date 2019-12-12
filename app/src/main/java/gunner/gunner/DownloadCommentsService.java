package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;

public class DownloadCommentsService  extends IntentService {

    FindInDatabase find= new FindInDatabase();

    public DownloadCommentsService() {
        super("Log in service");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
       // find.findComments();

    }


}

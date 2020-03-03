package gunner.gunner.reviews;

import android.app.IntentService;
import android.content.Intent;

import gunner.gunner.FindInDatabase;

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

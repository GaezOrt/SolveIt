package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataHolderService extends IntentService {

     Connection con;
    public DataHolderService() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            final String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            final String userName = "9QFW2Os9pV";
            final String passwordDatabase = "dKObZerUnf";
            final String url = "jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV";
            con = DriverManager.getConnection(url, userName, passwordDatabase);
        }catch (Exception e){

        }
    }
}

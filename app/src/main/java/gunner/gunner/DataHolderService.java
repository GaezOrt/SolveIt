package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHolderService extends IntentService {

     Connection con;
    public DataHolderService() {
        super("");
    }

    void connect() throws SQLException, ClassNotFoundException {
        final String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String userName = "9QFW2Os9pV";
            final String passwordDatabase = "dKObZerUnf";
        final String url = "jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV";
        con = DriverManager.getConnection(url, userName, passwordDatabase);

    }

    public Connection getCon() {
        try {
            connect();
        }catch (Exception e)
        {

        }
        return con;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
           connect();
        }catch (Exception e){

        }
    }
}

package gunner.gunner;


import android.os.StrictMode;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
        String userName="9QFW2Os9pV",passwordDatabase="dKObZerUnf",url="jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV",driver,driver1;
        Connection con;
        Statement st;
        static String claveAccesoAmazonID="AKIAJXVQEBNY6MESKVDA";
        static  String claveAccesoSecreta="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";

    //Conectar a Database

        public void DatabaseConnection(String email, String username, String password, String phoneNumber, String location,byte[] image) {
            userName="9QFW2Os9pV";
            passwordDatabase="dKObZerUnf";
            url="jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV";
            //driver="org.mariadb.jdbc.Driver";
            driver1="com.mysql.jdbc.Driver";
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                Class.forName(driver1);
                con= DriverManager.getConnection(url, userName, passwordDatabase);
                st=con.createStatement();
                System.out.println("Connection is successful");
                st=con.createStatement();
                st.executeUpdate("INSERT INTO Users " + "VALUES ('"+email+"', '"+username+"', '"+password+"','"+phoneNumber+"','"+location+"','"+image+"')");
                SignUp.creadConExito=true;

            } catch (Exception e) {
              e.printStackTrace();
            }


        }
}

package gunner.gunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
        String userName="9QFW2Os9pV",passwordDatabase="dKObZerUnf",url="jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV",driver,driver1;
        Connection con;
        Statement st;


    private static final String CREATE_TABLE_SQL="CREATE TABLE boraji.users ("
            + "UID INT NOT NULL,"
            + "NAME VARCHAR(45) NOT NULL,"
            + "DOB DATE NOT NULL,"
            + "EMAIL VARCHAR(45) NOT NULL,"
            + "PRIMARY KEY (UID))";

        public void DatabaseConnection(String email, String username, String password) {
            userName="9QFW2Os9pV";
            passwordDatabase="dKObZerUnf";
            url="jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV";
            driver="org.mariadb.jdbc.Driver";
            driver1="com.mysql.jdbc.Driver";
            try {
                Class.forName(driver1);
                con= DriverManager.getConnection(url, userName, passwordDatabase);
                st=con.createStatement();
                System.out.println("Connection is successful");
                st=con.createStatement();
                st.executeUpdate("INSERT INTO Users " + "VALUES ('"+email+"', '"+username+"', '"+password+"')");

                System.out.println("Table created");

            } catch (Exception e) {
              e.printStackTrace();
            }


        }
}

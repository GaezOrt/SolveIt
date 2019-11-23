package gunner.gunner;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
        String userName="9QFW2Os9pV",passwordDatabase="dKObZerUnf",url="jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV",driver,driver1;
        Connection con;
        Statement st;
    //Conectar a Database
        public void DatabaseConnection(String email, String username, String password,String phoneNumber,String location) {
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
                st.executeUpdate("INSERT INTO Users " + "VALUES ('"+email+"', '"+username+"', '"+password+"','"+phoneNumber+"','"+location+"')");
                SignUp.creadConExito=true;

            } catch (Exception e) {
              e.printStackTrace();
            }


        }
}

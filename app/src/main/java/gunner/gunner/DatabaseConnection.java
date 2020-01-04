package gunner.gunner;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection conn;


    Connection connect() throws SQLException, ClassNotFoundException {
        if(conn==null) {
            System.out.println("Establishing connection");
            final String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            final String userName = "9QFW2Os9pV";
            final String passwordDatabase = "dKObZerUnf";
            final String url = "jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV";
            conn = DriverManager.getConnection(url, userName, passwordDatabase);
        }
        return conn;
    }

    void createUser(
            String email,
            String username,
            String password,
            String phoneNumber,
            String location,
            byte[] pathForImage,
            int verificationNumber,
            boolean electricista,
            boolean plomero,
            boolean gasista,
            boolean computacion,
            boolean pintor,
            boolean albanil,
            boolean cerrajero,
            boolean carpintero,boolean esProveedor,String dni, String androidID) throws SQLException, FileNotFoundException {

        String updateSQL = "INSERT INTO Users VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        String updateRubro= " INSERT INTO Rubro VALUES (?,?,?,?,?,?,?,?,?)";
        String androidUuid=" INSERT INTO androidID VALUES(?,?,?)";


        PreparedStatement pstmt = conn.prepareStatement(updateSQL);
        PreparedStatement pstmtRubro= conn.prepareStatement(updateRubro);
        PreparedStatement pstmtAndroidUID=conn.prepareStatement(androidUuid);
        pstmt.setString(1, email);
        pstmt.setString(2, username);
        pstmt.setString(3, password);
        pstmt.setString(4, phoneNumber);
        pstmt.setString(5, location);
        pstmt.setBytes(6, pathForImage);
        pstmt.setString(7,SignUp.dateString);
        pstmt.setString(8,dni);
        pstmt.setInt(9,verificationNumber);
        pstmt.setBoolean(10,false);
        pstmt.setBoolean(11,esProveedor);
        pstmt.setString(12,androidID);
        pstmtRubro.setString(1,email );
        pstmtRubro.setBoolean(2,electricista);
        pstmtRubro.setBoolean(3,plomero );
        pstmtRubro.setBoolean(4,gasista);
        pstmtRubro.setBoolean(5,computacion);
        pstmtRubro.setBoolean(6,pintor);
        pstmtRubro.setBoolean(7,albanil);
        pstmtRubro.setBoolean(8,cerrajero);
        pstmtRubro.setBoolean(9,carpintero);

        pstmtAndroidUID.setString(1,email);
        pstmtAndroidUID.setString(2,password);
        pstmtAndroidUID.setString(3,androidID);

        pstmt.executeUpdate();
        pstmtRubro.executeUpdate();
        pstmtAndroidUID.executeUpdate();
        SignUp.dateString="";
    }
}

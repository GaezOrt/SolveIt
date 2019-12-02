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

    private Connection conn;

    DatabaseConnection() {
        conn = null;
    }

    void connect() throws SQLException, ClassNotFoundException {
        final String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String userName = "9QFW2Os9pV";
        final String passwordDatabase = "dKObZerUnf";
        final String url = "jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV";
        conn = DriverManager.getConnection(url, userName, passwordDatabase);
    }

    void createUser(
            String email,
            String username,
            String password,
            String phoneNumber,
            String location,
            byte[] pathForImage) throws SQLException, FileNotFoundException {

        String updateSQL = "INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?)";



        PreparedStatement pstmt = conn.prepareStatement(updateSQL);
        pstmt.setString(1, email);
        pstmt.setString(2, username);
        pstmt.setString(3, password);
        pstmt.setString(4, phoneNumber);
        pstmt.setString(5, location);
        pstmt.setBytes(6, pathForImage);

        pstmt.executeUpdate();
    }
}

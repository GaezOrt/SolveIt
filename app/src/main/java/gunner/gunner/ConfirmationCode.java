package gunner.gunner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class ConfirmationCode extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_layout);


        if(SignUpService.datosOk){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this,R.style.MyDialogTheme);
            builder1.setTitle("Sign up correcto");
            builder1.setIcon(R.drawable.usercorrect);

            builder1.setMessage("Se creo la cuenta correctamente");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
           AlertDialog alert11 = builder1.create();
            alert11.show();

            SignUpService.datosOk=false;
        }




        Button confirmation= (Button)findViewById(R.id.button7);
        confirmation.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edit = (EditText) ConfirmationCode.this.findViewById(R.id.editText2);
                        String confirmationCodeOnApp = edit.getText().toString();
                        try {
                            PreparedStatement pt = DatabaseConnection.conn.prepareStatement("SELECT * FROM Users WHERE verificationNumber= ?");

                            pt.setString(1, confirmationCodeOnApp);
                            ResultSet rs = pt.executeQuery();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ConfirmationCode.this, R.style.MyDialogTheme);
                            if (rs.next() == false) {
                                try {

                                    builder1.setMessage("Codigo de verficicación incorrecto");
                                    builder1.setCancelable(true);
                                    builder1.setPositiveButton(
                                            "Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                if (SignUpService.email.equals(rs.getString("email")) || LogInService.email.equals(rs.getString("email"))) {
                                    builder1.setMessage("Verificacion de cuenta correcta.");
                                    builder1.setCancelable(true);
                                    builder1.setPositiveButton(
                                            "Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                    startActivity(new Intent(ConfirmationCode.this, MainActivity.class));
                                                }
                                            });
                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();
                                    String updateSQL = "UPDATE Users SET cuentaVerificada = ? WHERE email= ? ";
                                    PreparedStatement pstmt = DatabaseConnection.conn.prepareStatement(updateSQL);
                                    if (LogInService.email != null) {
                                        pstmt.setBoolean(1, true);
                                        pstmt.setString(2, LogInService.email);

                                    } else {
                                        pstmt.setBoolean(1, true);
                                        pstmt.setString(2, SignUpService.email);
                                    }

                                    pstmt.executeUpdate();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
    }
    public static Handler UIHandler;

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

}


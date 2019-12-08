package gunner.gunner;

import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

public class SignUpService extends IntentService {

    static String username;
    static String password;
    static String email;
    static String phoneNumber;
    static String location;
    static byte[] pathForImage;
    static boolean datosOk;
    static int estado;
    SignUp signUp= new SignUp();
    static boolean imagenUsada=false;
    static boolean cuentaYaUtilizada;

    public SignUpService() {
        super("Log in service");
    }

    @Override
    protected void onHandleIntent( Intent intent) {


        signUp.signUp();
        if(cuentaYaUtilizada){
            startActivity(new Intent(SignUpService.this, SignUp.class));
        }
        if(datosOk){
            startActivity(new Intent(SignUpService.this, SignUp.class));
        }else{
            startActivity(new Intent(SignUpService.this, SignUp.class));
        }

    }
}

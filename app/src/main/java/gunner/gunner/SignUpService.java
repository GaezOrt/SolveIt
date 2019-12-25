package gunner.gunner;

import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

public class SignUpService extends IntentService {
    static int verificationNumber;
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

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        signUp.signUp();
        if(cuentaYaUtilizada){
            Intent intentt = new Intent (this, SignUp.class);
            intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentt);


        }
        if(datosOk){
            Intent intentt = new Intent (this, ConfirmationCode.class);
            intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentt);
            stopSelf();


        }else{
            Intent intentt = new Intent (this, SignUp.class);
            intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentt);
        }

    }
}

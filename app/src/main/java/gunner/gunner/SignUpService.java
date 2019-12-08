package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;

public class SignUpService extends IntentService {

    static String username;
    static String password;
    static String email;
    static String phoneNumber;
    static String location;
    static byte[] pathForImage;
    static boolean datosOk;

    SignUp signUp= new SignUp();


    public SignUpService() {
        super("Log in service");
    }

    @Override
    protected void onHandleIntent( Intent intent) {


        signUp.signUp();
        if(datosOk){
            startActivity(new Intent(SignUpService.this, SignUp.class));
        }else{
            startActivity(new Intent(SignUpService.this, SignUp.class));
        }

    }
}

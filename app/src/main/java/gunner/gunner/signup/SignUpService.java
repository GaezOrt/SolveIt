package gunner.gunner.signup;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import gunner.gunner.accountconfirmation.ConfirmationCode;
import gunner.gunner.DatabaseConnection;
import gunner.gunner.accountconfirmation.GMailSender;
import gunner.gunner.MainActivity;
import gunner.gunner.WelcomeWindow;

public class SignUpService extends IntentService {
    static int verificationNumber;
    static String dni;
    static String username;
    static String password;
    public static String email;
    static String phoneNumber;
    static String location;
    static String lastName;
    static byte[] pathForImage;
    public static boolean datosOk;
    static int estado;
    SignUp signUp= new SignUp();
    static boolean imagenUsada=false;
    static boolean cuentaYaUtilizada;
    static boolean signUpClient;
    static boolean signUpProveedor;
    static boolean weAreInProveedor;
    public SignUpService() {
        super("Log in service");
    }

    @Override
    protected void onHandleIntent( Intent intent) {

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(signUpClient) {
            signUp();
            signUpClient=false;
        }
        if(signUpProveedor){
            signUp.signUpProveedor();
            signUpProveedor=false;
        }
        if(cuentaYaUtilizada){
            if(!weAreInProveedor) {
                Intent intentt = new Intent(this, SignUp.class);
                intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentt);

            }else{
                Intent intentt = new Intent(this, SignUpProveedor.class);
                intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentt);
                weAreInProveedor=false;
            }

        }
        if(datosOk){
            Intent intentt = new Intent (this, ConfirmationCode.class);
            intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentt);
            stopSelf();


        }else{
            if(!weAreInProveedor) {
                Intent intentt = new Intent(this, SignUp.class);
                intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentt);
            }else{
                Intent intentt = new Intent(this, SignUpProveedor.class);
                intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentt);
                weAreInProveedor=false;
            }
        }

    }
    public void signUp() {
        try {

            PreparedStatement pt = DatabaseConnection.conn.prepareStatement("SELECT * FROM Users WHERE email = ?");
            pt.setString(1, SignUpService.email);
            pt.setFetchSize(1);
            ResultSet rs = pt.executeQuery();
            if (rs.next() == false) {
                SignUpService.cuentaYaUtilizada = false;
            } else {
                SignUpService.cuentaYaUtilizada = true;
                weAreInProveedor=false;
            }

        } catch (Exception e) {

        }
        if (SignUpService.email.length() == 0 || SignUpService.username.length() == 0 ||
                SignUpService.password.length() == 0 ||
                SignUpService.phoneNumber.length() == 0 || SignUpService.imagenUsada == false || SignUpService.cuentaYaUtilizada) {
            SignUpService.datosOk = false;
            weAreInProveedor=false;
            SignUpService.estado = 1;
            if (SignUpService.imagenUsada == false) {
                SignUpService.imagenUsada = false;

            }
        } else {
            SignUpService.datosOk = true;
            SignUpService.estado = 2;
        }

        if (!SignUpService.datosOk) {
            Log.e("", "errores en los datos");
            weAreInProveedor=false;

        } else {
            try {

                Random random= new Random();
                SignUpService.verificationNumber=random.nextInt(5000);
                MainActivity.esProveedor=false;
                final DatabaseConnection databaseConnection = new DatabaseConnection();
                databaseConnection.connect();
                SignUpService.dni="";
                SignUpService sign=new SignUpService();
                if(MainActivity.uniqueGoogleId==null){
                    MainActivity.uniqueGoogleId="0";
                }
                databaseConnection.createUser(
                        SignUpService.email, SignUpService.username, SignUpService.password,
                        SignUpService.phoneNumber, SignUpService.location, SignUpService.pathForImage,SignUpService.verificationNumber,
                        MainActivity.electricista,
                        MainActivity.carpintero,
                        MainActivity.computacion,
                        MainActivity.plomero,
                        MainActivity.gasista,
                        MainActivity.albanil,
                        MainActivity.pintor,
                        MainActivity.cerrajero,MainActivity.esProveedor,SignUpService.dni, WelcomeWindow.uuid,MainActivity.uniqueGoogleId,SignUpService.lastName);
                try {
                    GMailSender sender = new GMailSender("servyargentina@gmail.com",
                            "servy2019");


                    sender.sendMail("Servy Argentina (Codigo de verificacion)", "Querido "+SignUpService.username+" bienvenido a Servy! Este es su codigo de verificacion" +
                                    " "
                                    +SignUpService.verificationNumber,
                            "servyargentina@gmail.com", SignUpService.email);
                    System.out.println("Holaa");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                SignUpService.datosOk=false;
                e.printStackTrace();
                SignUpService.estado=1;
            }

        }
    }
}

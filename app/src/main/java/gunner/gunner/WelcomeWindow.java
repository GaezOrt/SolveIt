package gunner.gunner;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class WelcomeWindow  extends AppCompatActivity {
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_window);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }


            Intent i = new Intent(this, DataHolderService.class);
            startService(i);


        //Load electricistas list in background
        if (Electricidad.electricistas.isEmpty()) {
            Intent u = new Intent(this, DownloadStuffInBackground.class);
            startService(u);
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeWindow.this, MainActivity.class);
                startActivity(intent);
            }
        }, 6000); // 4 seconds
    //   startActivity(new Intent(WelcomeWindow.this, MainActivity.class));
    }
}

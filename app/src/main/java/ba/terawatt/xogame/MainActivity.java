package ba.terawatt.xogame;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newGameButtonVsAndroid= (Button) findViewById(R.id.newGameButtonVsAndroid);
        newGameButtonVsAndroid.setOnClickListener(this);

        Button newGameButtonVsPlayer= (Button) findViewById(R.id.newGameButtonVsPlayer);
        newGameButtonVsPlayer.setOnClickListener(this);

        Button aboutAppButon = (Button) findViewById(R.id.aboutAppButton);
        aboutAppButon.setOnClickListener(this);

        Button button= (Button) findViewById(R.id.exitGameButton);
        button.setOnClickListener(this);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newGameButtonVsAndroid:
                startActivity(new Intent(MainActivity.this,
                        XOGameActivity.class));
            break;
            case  R.id.newGameButtonVsPlayer:
                Intent myIntent = new Intent(MainActivity.this, XOGameVsPlayer.class);
                startActivity(myIntent);
            break;
            case R.id.aboutAppButton:
                startActivity(new Intent(MainActivity.this, AboutApp.class));
                break;
            case R.id.exitGameButton:
                MainActivity.this.finish();
            break;




        }
    }

}
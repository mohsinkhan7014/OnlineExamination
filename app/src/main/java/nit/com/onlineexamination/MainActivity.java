package nit.com.onlineexamination;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Thread mSplashThread;
    final MainActivity sPlashScreen = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(3000);
                    }
                } catch(InterruptedException ex){
                }
                finish();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,CandidateDetailesActivity.class);
                startActivity(intent);
            }
        };
        mSplashThread.start();
    }
}

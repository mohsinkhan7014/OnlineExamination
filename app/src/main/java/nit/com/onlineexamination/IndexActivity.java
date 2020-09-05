package nit.com.onlineexamination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class IndexActivity extends AppCompatActivity {
    Button signUp;
    Button admin;
    Button signIn;
    Button forgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        signUp = (Button) findViewById(R.id.signUp);
        admin = (Button) findViewById(R.id.admin);
        signIn = (Button) findViewById(R.id.signIn);
        forgetPassword = (Button) findViewById(R.id.forgot_password);

        admin.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                if (v.getId() == R.id.admin) {

                    Intent indexIntent = new Intent(IndexActivity.this, AdminMainActivity.class);
                    startActivity(indexIntent);
                }
            }
        });
        signUp.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                if (v.getId() == R.id.signUp) {
                    Intent indexIntent = new Intent(IndexActivity.this, UserRegistrationMainActivity.class);
                    startActivity(indexIntent);
                }

            }
        });
        signIn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                if (v.getId() == R.id.signIn) {
                    Intent indexIntent = new Intent(IndexActivity.this, CandidateMainActivity.class);
                    startActivity(indexIntent);
                }

            }
        });

    }
}


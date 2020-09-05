package nit.com.onlineexamination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TestMainActivity extends AppCompatActivity {
    Button testButton;
    RadioButton rbAndroid, rbJava, rbIphone, rbCcpp;
    RadioGroup rgTechnology;
    private String sTechnology;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        testButton 	=(Button)findViewById(R.id.test_button);
        rgTechnology = (RadioGroup) findViewById(R.id.question_view_type);
        rbAndroid = (RadioButton) findViewById(R.id.radio_view_question_android);
        rbJava = (RadioButton) findViewById(R.id.radio_view_question_java);
        rbIphone = (RadioButton) findViewById(R.id.radio_view_question_iPhone);
        rbCcpp = (RadioButton) findViewById(R.id.radio_view_question_ccpp);

        testButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                if(v.getId()==R.id.test_button)
                {
                    switch(rgTechnology.getCheckedRadioButtonId()){
                        case R.id.radio_view_question_android:
                            sTechnology = "Android";
                            break;

                        case R.id.radio_view_question_java:
                            sTechnology = "Java";
                            break;

                        case R.id.radio_view_question_iPhone:
                            sTechnology = "iPhone";
                            break;

                        case R.id.radio_view_question_ccpp:
                            sTechnology = "CCPP";
                            break;
                    }
                    Intent indexIntent=new Intent(TestMainActivity.this,TestStartActivity.class);
                    Bundle testTechnology	= new Bundle();
                    testTechnology.putString("TestTechnology",sTechnology);
                    indexIntent.putExtras(testTechnology);
                    startActivity(indexIntent);
                }
            }
        });
    }

}


package nit.com.onlineexamination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ViewQuestionMain extends AppCompatActivity {
    RadioButton rbAndroid , rbJava , rbIphone , rbCcpp;
    RadioGroup rgTechnology;
    private String sTechnology;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question_main);
        Button questionView = (Button)findViewById(R.id.question_view_button);

        rgTechnology = (RadioGroup) findViewById(R.id.question_view_type);
        rbAndroid = (RadioButton) findViewById(R.id.radio_view_question_android);
        rbJava = (RadioButton) findViewById(R.id.radio_view_question_java);
        rbIphone = (RadioButton) findViewById(R.id.radio_view_question_iPhone);
        rbCcpp = (RadioButton) findViewById(R.id.radio_view_question_ccpp);

        questionView.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

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

                Intent indexIntent=new Intent(ViewQuestionMain.this,ViewQuestion.class);
                Bundle viewTechnology	= new Bundle();
                viewTechnology.putString("Technology",sTechnology);
                indexIntent.putExtras(viewTechnology);
                startActivity(indexIntent);
                finish();
            }
        });


    }

}


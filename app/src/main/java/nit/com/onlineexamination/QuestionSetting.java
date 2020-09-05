package nit.com.onlineexamination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class QuestionSetting extends AppCompatActivity {
    RadioButton rbAndroid , rbJava , rbIphone , rbCcpp;
    RadioGroup rgTechnology;
    Button submit;
    private String sTechnology , sQuestion , sOption1 , sOption2 , sOption3 , sOption4 , iAnswer;

    MultiAutoCompleteTextView mtvOption1 , mtvOption2 , mtvOption3 , mtvOption4;
    EditText etQuestion , etAnswer;
    SQLiteDatabase dh;
    boolean isAllEnteryFilled = true ;
    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_setting);
        AndroidContext.setContext(this);
        dh = OLECDatabase.getInstance().getDb();

        rgTechnology = (RadioGroup) findViewById(R.id.question_set_type);
        rbAndroid = (RadioButton) findViewById(R.id.radio_setting_question_android);
        rbJava = (RadioButton) findViewById(R.id.radio_setting_question_java);
        rbIphone = (RadioButton) findViewById(R.id.radio_setting_question_iPhone);
        rbCcpp = (RadioButton) findViewById(R.id.radio_setting_question_ccpp);

        submit = (Button) findViewById(R.id.go);
        etQuestion = (EditText) findViewById(R.id.question_content);
        etAnswer = (EditText) findViewById(R.id.answer);
        mtvOption1 = (MultiAutoCompleteTextView) findViewById(R.id.option1);
        mtvOption2 = (MultiAutoCompleteTextView) findViewById(R.id.option2);
        mtvOption3 = (MultiAutoCompleteTextView) findViewById(R.id.option3);
        mtvOption4 = (MultiAutoCompleteTextView) findViewById(R.id.option4);

        submit.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                switch(rgTechnology.getCheckedRadioButtonId()){
                    case R.id.radio_setting_question_android:
                        sTechnology = "Android";
                        break;

                    case R.id.radio_setting_question_java:
                        sTechnology = "Java";
                        break;

                    case R.id.radio_setting_question_iPhone:
                        sTechnology = "iPhone";
                        break;

                    case R.id.radio_setting_question_ccpp:
                        sTechnology = "CCPP";
                        break;
                }

                sQuestion 	= etQuestion.getText().toString();
                sOption1 	= mtvOption1.getText().toString();
                sOption2 	= mtvOption2.getText().toString();
                sOption3 	= mtvOption3.getText().toString();
                sOption4 	= mtvOption4.getText().toString();
                iAnswer 	= etAnswer.getText().toString();



                if(sQuestion != null && !sQuestion.trim().equals("")){
                    sQuestion = sQuestion.trim();
                }else{
                    sQuestion = "";
                }
                if(sOption1 != null && !sOption1.trim().equals("")){
                    sOption1 = sOption1.trim();
                }else{
                    sOption1 = "";
                }
                if(sOption2 != null && !sOption2.trim().equals("")){
                    sOption2 = sOption2.trim();
                }else{
                    sOption2 = "";
                }
                if(sOption3 != null && !sOption3.trim().equals("")){
                    sOption3 = sOption3.trim();
                }else{
                    sOption3 = "";
                }
                if(sOption4 != null && !sOption4.trim().equals("")){
                    sOption4 = sOption4.trim();
                }else{
                    sOption4 = "";
                }
                if(iAnswer != null && !iAnswer.trim().equals("")){
                    iAnswer = iAnswer.trim();
                }else{
                    iAnswer = "";
                }
                if(!sQuestion.trim().equals("") && !sOption1.trim().equals("") && !sOption2.trim().equals("") && !sOption3.trim().equals("") && !sOption4.trim().equals("") && !iAnswer.trim().equals(""))
                {
                    isAllEnteryFilled = true;
                }
                else
                {
                    isAllEnteryFilled = false;
                }
                if(isAllEnteryFilled)
                {
                    ContentValues questionValues = new ContentValues();
                    questionValues.put("technology",sTechnology);
                    questionValues.put("question", sQuestion);
                    questionValues.put("option1",sOption1);
                    questionValues.put("option2",sOption2);
                    questionValues.put("option3",sOption3);
                    questionValues.put("option4",sOption4);
                    questionValues.put("answer",Integer.valueOf(iAnswer));
                    questionValues.put("ischecked",0);
                    dh.insert(OLECDatabase.QUESTIONS_TABLE, null, questionValues);
                    Intent intent = new Intent(QuestionSetting.this,QuestionSetting.class);
                    startActivity(intent);
                }
                else
                {
                    showAlert("Empty Field","Empty field must be filled ");
                }


            }
        });


    }
    protected void showAlert(String title, String mymessage)
    {
        new AlertDialog.Builder(this)
                .setMessage(mymessage) //.setPositiveButton( R.string.main_connection_button_ok_button,
                .setTitle(title)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                dialog.dismiss();
                            }
                        })
                .show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QuestionSetting.this , IndexActivity.class);
        startActivity(intent);

    }
}

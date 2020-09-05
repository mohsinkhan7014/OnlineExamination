package nit.com.onlineexamination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewQuestion extends AppCompatActivity {
    Bundle getTechnology;
    String viewTechnology;

    SQLiteDatabase dh;
    private String rowId = null;
    private String sQuestion, sOption1, sOption2, sOption3, sOption4, sAnswer;
    private TextView tvQuestion, tvOption1, tvOption2, tvOption3, tvOption4, tvAnswer;
    ImageView nextQuestion, previousQuestion;
    Drawable previousDisable, previousEnable, nextDisable, nextEnable;
    Resources res;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
        AndroidContext.setContext(this);
        dh = OLECDatabase.getInstance().getDb();
        res = getResources();
        previousDisable = res.getDrawable(R.drawable.leftarrow_disable);
        previousEnable = res.getDrawable(R.drawable.left_arrow);
        nextDisable = res.getDrawable(R.drawable.rightarrow_disable);
        nextEnable = res.getDrawable(R.drawable.right_arrow);
        getTechnology = getIntent().getExtras();
        viewTechnology = getTechnology.getString("Technology");

        cursor = dh.query(OLECDatabase.QUESTIONS_TABLE, new String[]{"_id", "technology", "question", "option1", "option2", "option3", "option4", "answer"}, "technology =?", new String[]{viewTechnology}, null, null, null);
        startManagingCursor(cursor);
        if (cursor != null && cursor.moveToFirst()) {
            rowId = cursor.getString(0);
            System.out.println("Row ID in Initial ->" + rowId);
            sQuestion = cursor.getString(cursor.getColumnIndex("question"));
            sOption1 = cursor.getString(cursor.getColumnIndex("option1"));
            sOption2 = cursor.getString(cursor.getColumnIndex("option2"));
            sOption3 = cursor.getString(cursor.getColumnIndex("option3"));
            sOption4 = cursor.getString(cursor.getColumnIndex("option4"));
            sAnswer = cursor.getString(cursor.getColumnIndex("answer"));
        }

        System.out.println("Viewing Technology ->" + viewTechnology);
        System.out.println("Viewing sQuestion ->" + sQuestion);
        System.out.println("Viewing sOption1 ->" + sOption1);
        System.out.println("Viewing sOption2 ->" + sOption2);
        System.out.println("Viewing sOption3 ->" + sOption3);
        System.out.println("Viewing sOption4 ->" + sOption4);
        System.out.println("Viewing sAnswer ->" + sAnswer);

        tvQuestion = (TextView) findViewById(R.id.view_question_content);
        tvOption1 = (TextView) findViewById(R.id.view_option1);
        tvOption2 = (TextView) findViewById(R.id.view_option2);
        tvOption3 = (TextView) findViewById(R.id.view_option3);
        tvOption4 = (TextView) findViewById(R.id.view_option4);
        tvAnswer = (TextView) findViewById(R.id.view_answer);
        ;

        if (sQuestion != null && !sQuestion.trim().equals("")) {
            tvQuestion.setText("Question   : " + sQuestion.trim());
        }
        if (sOption1 != null && !sOption1.trim().equals("")) {
            tvOption1.setText("Option 1    : " + sOption1.trim());
        }
        if (sOption2 != null && !sOption2.trim().equals("")) {
            tvOption2.setText("Option 2    : " + sOption2.trim());
        }
        if (sOption3 != null && !sOption3.trim().equals("")) {
            tvOption3.setText("Option 3    : " + sOption3.trim());
        }
        if (sOption4 != null && !sOption4.trim().equals("")) {
            tvOption4.setText("Option 4    : " + sOption4.trim());
        }
        if (sAnswer != null && !sAnswer.trim().equals("")) {
            tvAnswer.setText("Answer      : " + sAnswer.trim());
        }
        previousQuestion = (ImageView) findViewById(R.id.previousQuestionButton);
        nextQuestion = (ImageView) findViewById(R.id.nextQuestionButton);

        Cursor c = null;
        if (rowId != null) {
            c = dh.query(OLECDatabase.QUESTIONS_TABLE, new String[]{"_id"}, "_id < ?", new String[]{rowId}, null, null, null);
            System.out.println("corsor count for prev ->" + c.getColumnCount());
            startManagingCursor(c);
            if (c.getCount() == 0) {
                previousQuestion.setImageDrawable(previousEnable);
                previousQuestion.setEnabled(true);
                previousQuestion.setClickable(true);
            } else {
                previousQuestion.setImageDrawable(previousEnable);
                previousQuestion.setVisibility(View.VISIBLE);
            }

            c = dh.query(OLECDatabase.QUESTIONS_TABLE, new String[]{"_id"}, "_id > ?", new String[]{rowId}, null, null, null);
            System.out.println("corsor count for next ->" + c.getColumnCount());
            startManagingCursor(c);
            if (c.getCount() == 0) {
                nextQuestion.setImageDrawable(nextDisable);
                nextQuestion.setEnabled(false);
                nextQuestion.setClickable(false);
            } else {
                nextQuestion.setImageDrawable(nextEnable);
                nextQuestion.setVisibility(View.VISIBLE);
            }
        } else {
            previousQuestion.setImageDrawable(previousDisable);
            previousQuestion.setEnabled(false);
            previousQuestion.setClickable(false);
            nextQuestion.setImageDrawable(nextDisable);
            nextQuestion.setEnabled(false);
            nextQuestion.setClickable(false);
        }
    }

    public void clickHandler(View v) {
        if (v.getId() == R.id.previousQuestionButton) {
            moveToPreviousQuestion();

        }
        if (v.getId() == R.id.nextQuestionButton) {
            moveToNextQuestion();

        }
    }

    private void moveToPreviousQuestion() {
        String previousRowId = "";
        //Cursor cursor = dh.query(OLECDatabase.QUESTIONS_TABLE, new String[]{"_id","technology","question","option1","option2","option3","option4","answer"}, "technology =?",new String[]{viewTechnology} ,null, null, "_id desc");
        //	startManagingCursor(cursor);
        if (cursor != null && cursor.moveToPrevious()) {
            previousRowId = cursor.getString(0);
            sQuestion = cursor.getString(cursor.getColumnIndex("question"));
            sOption1 = cursor.getString(cursor.getColumnIndex("option1"));
            sOption2 = cursor.getString(cursor.getColumnIndex("option2"));
            sOption3 = cursor.getString(cursor.getColumnIndex("option3"));
            sOption4 = cursor.getString(cursor.getColumnIndex("option4"));
            sAnswer = cursor.getString(cursor.getColumnIndex("answer"));

            System.out.println("Viewing Technology 1->" + viewTechnology);
            System.out.println("Viewing sQuestion ->" + sQuestion);
            System.out.println("Viewing sOption1 ->" + sOption1);
            System.out.println("Viewing sOption2 ->" + sOption2);
            System.out.println("Viewing sOption3 ->" + sOption3);
            System.out.println("Viewing sOption4 ->" + sOption4);
            System.out.println("Viewing sAnswer ->" + sAnswer);

            if (sQuestion != null && !sQuestion.trim().equals("")) {
                tvQuestion.setText("Question   : " + sQuestion.trim());
            }
            if (sOption1 != null && !sOption1.trim().equals("")) {
                tvOption1.setText("Option 1    : " + sOption1.trim());
            }
            if (sOption2 != null && !sOption2.trim().equals("")) {
                tvOption2.setText("Option 2    : " + sOption2.trim());
            }
            if (sOption3 != null && !sOption3.trim().equals("")) {
                tvOption3.setText("Option 3    : " + sOption3.trim());
            }
            if (sOption4 != null && !sOption4.trim().equals("")) {
                tvOption4.setText("Option 4    : " + sOption4.trim());
            }
            if (sAnswer != null && !sAnswer.trim().equals("")) {
                tvAnswer.setText("Answer      : " + sAnswer.trim());
            }
        }


    }

    private void moveToNextQuestion() {
        String nextRowId = "";
        //Cursor cursor = dh.query(OLECDatabase.QUESTIONS_TABLE, new String[]{"_id","technology","question","option1","option2","option3","option4","answer"}, "technology =?",new String[]{viewTechnology} ,null, null, "_id asc");
        //startManagingCursor(cursor);
        if (cursor != null && cursor.moveToNext()) {
            nextRowId = cursor.getString(0);
            sQuestion = cursor.getString(cursor.getColumnIndex("question"));
            sOption1 = cursor.getString(cursor.getColumnIndex("option1"));
            sOption2 = cursor.getString(cursor.getColumnIndex("option2"));
            sOption3 = cursor.getString(cursor.getColumnIndex("option3"));
            sOption4 = cursor.getString(cursor.getColumnIndex("option4"));
            sAnswer = cursor.getString(cursor.getColumnIndex("answer"));

            System.out.println("Viewing Technology 1->" + viewTechnology);
            System.out.println("Viewing sQuestion ->" + sQuestion);
            System.out.println("Viewing sOption1 ->" + sOption1);
            System.out.println("Viewing sOption2 ->" + sOption2);
            System.out.println("Viewing sOption3 ->" + sOption3);
            System.out.println("Viewing sOption4 ->" + sOption4);
            System.out.println("Viewing sAnswer ->" + sAnswer);

            if (sQuestion != null && !sQuestion.trim().equals("")) {
                tvQuestion.setText("Question   : " + sQuestion.trim());
            }
            if (sOption1 != null && !sOption1.trim().equals("")) {
                tvOption1.setText("Option 1    : " + sOption1.trim());
            }
            if (sOption2 != null && !sOption2.trim().equals("")) {
                tvOption2.setText("Option 2    : " + sOption2.trim());
            }
            if (sOption3 != null && !sOption3.trim().equals("")) {
                tvOption3.setText("Option 3    : " + sOption3.trim());
            }
            if (sOption4 != null && !sOption4.trim().equals("")) {
                tvOption4.setText("Option 4    : " + sOption4.trim());
            }
            if (sAnswer != null && !sAnswer.trim().equals("")) {
                tvAnswer.setText("Answer      : " + sAnswer.trim());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewQuestion.this, ViewQuestionMain.class);
        startActivity(intent);
        finish();

    }
}


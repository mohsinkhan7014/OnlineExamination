package nit.com.onlineexamination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TestStartActivity extends AppCompatActivity {
    Bundle getTestTechnology;
    String viewTestTechnology;

    SQLiteDatabase dh;
    private String rowId = null;
    private String sQuestion, sOption1, sOption2, sOption3, sOption4;
    private TextView tvQuestion;
    RadioGroup rgAns;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private boolean ischeck = false;
    int check = 0;
    ImageView nextQuestion, previousQuestion;
    Drawable previousDisable, previousEnable, nextDisable, nextEnable;
    Resources res;
    Cursor cursor;
    int candidateAns = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start);
        AndroidContext.setContext(this);
        dh = OLECDatabase.getInstance().getDb();
        res = getResources();
        previousDisable = res.getDrawable(R.drawable.leftarrow_disable);
        previousEnable = res.getDrawable(R.drawable.left_arrow);
        nextDisable = res.getDrawable(R.drawable.rightarrow_disable);
        nextEnable = res.getDrawable(R.drawable.right_arrow);
        getTestTechnology = getIntent().getExtras();
        viewTestTechnology = getTestTechnology.getString("TestTechnology");
        rgAns = (RadioGroup) findViewById(R.id.test_question_ans);
        tvQuestion = (TextView) findViewById(R.id.test_question_content);
        rbOption1 = (RadioButton) findViewById(R.id.test_option1);
        rbOption2 = (RadioButton) findViewById(R.id.test_option2);
        rbOption3 = (RadioButton) findViewById(R.id.test_option3);
        rbOption4 = (RadioButton) findViewById(R.id.test_option4);

        System.out.println("View Test Technology ->" + viewTestTechnology);

        cursor = dh.query(OLECDatabase.QUESTIONS_TABLE, new String[] { "_id",
                        "technology", "question", "option1", "option2", "option3",
                        "option4", "answer", "isChecked" }, "technology =?",
                new String[] { viewTestTechnology }, null, null, null);
        startManagingCursor(cursor);

        if ((cursor != null) && cursor.moveToFirst()) {
            rowId = cursor.getString(0);
            System.out.println("Row ID in Initial ->" + rowId);
            sQuestion = cursor.getString(cursor.getColumnIndex("question"));
            sOption1 = cursor.getString(cursor.getColumnIndex("option1"));
            sOption2 = cursor.getString(cursor.getColumnIndex("option2"));
            sOption3 = cursor.getString(cursor.getColumnIndex("option3"));
            sOption4 = cursor.getString(cursor.getColumnIndex("option4"));
            check = cursor.getInt(cursor.getColumnIndex("isChecked"));
            /*
             * if (check == 0) { ischeck = false; } else { ischeck = true; }
             */
        }
        /*
         * tvQuestion = (TextView)findViewById(R.id.test_question_content);
         * tvOption1 = (RadioButton)findViewById(R.id.test_option1); tvOption2 =
         * (RadioButton)findViewById(R.id.test_option2); tvOption3 =
         * (RadioButton)findViewById(R.id.test_option3); tvOption4 =
         * (RadioButton)findViewById(R.id.test_option4);
         */
        System.out.println("Viewing sQuestion ->" + sQuestion);
        System.out.println("Viewing sOption1 ->" + sOption1);
        System.out.println("Viewing sOption2 ->" + sOption2);
        System.out.println("Viewing sOption3 ->" + sOption3);
        System.out.println("Viewing sOption4 ->" + sOption4);
        System.out.println("is checked       ->" + check);

        if ((sQuestion != null) && !sQuestion.trim().equals("")) {
            tvQuestion.setText("Question   : " + sQuestion.trim());
        }
        if ((sOption1 != null) && !sOption1.trim().equals("")) {
            if (check == 1) {
                rbOption1.setChecked(true);
            } else {
                rbOption1.setChecked(false);
            }
            rbOption1.setText("Option 1    : " + sOption1.trim());
        }
        if ((sOption2 != null) && !sOption2.trim().equals("")) {
            if (check == 2) {
                rbOption2.setChecked(true);
            } else {
                rbOption2.setChecked(false);
            }
            rbOption2.setText("Option 2    : " + sOption2.trim());
        }
        if ((sOption3 != null) && !sOption3.trim().equals("")) {
            if (check == 3) {
                rbOption3.setChecked(true);
            } else {
                rbOption3.setChecked(false);
            }
            rbOption3.setText("Option 3    : " + sOption3.trim());
        }
        if ((sOption4 != null) && !sOption4.trim().equals("")) {

            if (check == 4) {
                rbOption4.setChecked(true);
            } else {
                rbOption4.setChecked(false);
            }
            rbOption4.setText("Option 4    : " + sOption4.trim());
        }

        previousQuestion = (ImageView) findViewById(R.id.previousQuestionButton);
        nextQuestion = (ImageView) findViewById(R.id.nextQuestionButton);
        Cursor c = null;
        if (rowId != null) {
            c = dh.query(OLECDatabase.QUESTIONS_TABLE, new String[] { "_id" },
                    "_id < ?", new String[] { rowId }, null, null, null);
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

            c = dh.query(OLECDatabase.QUESTIONS_TABLE, new String[] { "_id" },
                    "_id > ?", new String[] { rowId }, null, null, null);
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
        ContentValues args = null;
        args = new ContentValues();
        if (v.getId() == R.id.previousQuestionButton) {

            switch (rgAns.getCheckedRadioButtonId()) {
                case R.id.test_option1:
                    candidateAns = 1;
                    break;

                case R.id.test_option2:
                    candidateAns = 2;
                    break;

                case R.id.test_option3:
                    candidateAns = 3;
                    break;

                case R.id.test_option4:
                    candidateAns = 4;
                    break;
            }
            args.put("isChecked", candidateAns);
            dh.update(OLECDatabase.QUESTIONS_TABLE, args, "_id=?",
                    new String[] { String.valueOf(rowId) });
            moveToPreviousQuestion();

        }
        if (v.getId() == R.id.nextQuestionButton) {
            switch (rgAns.getCheckedRadioButtonId()) {
                case R.id.test_option1:
                    candidateAns = 1;
                    break;

                case R.id.test_option2:
                    candidateAns = 2;
                    break;

                case R.id.test_option3:
                    candidateAns = 3;
                    break;

                case R.id.test_option4:
                    candidateAns = 4;
                    break;
            }
            args.put("isChecked", candidateAns);
            dh.update(OLECDatabase.QUESTIONS_TABLE, args, "_id=?",
                    new String[] { String.valueOf(rowId) });
            moveToNextQuestion();
        }
    }

    private void moveToPreviousQuestion() {
        String previousRowId = "";
        // Cursor cursor = dh.query(OLECDatabase.QUESTIONS_TABLE, new
        // String[]{"_id","technology","question","option1","option2","option3","option4","answer"},
        // "technology =?",new String[]{viewTechnology} ,null, null,
        // "_id desc");
        // startManagingCursor(cursor);
        ContentValues args = null;
        if ((cursor != null) && cursor.moveToPrevious()) {
            rowId = cursor.getString(0);
            sQuestion = cursor.getString(cursor.getColumnIndex("question"));
            sOption1 = cursor.getString(cursor.getColumnIndex("option1"));
            sOption2 = cursor.getString(cursor.getColumnIndex("option2"));
            sOption3 = cursor.getString(cursor.getColumnIndex("option3"));
            sOption4 = cursor.getString(cursor.getColumnIndex("option4"));
            int check = cursor.getInt(cursor.getColumnIndex("isChecked"));
            /*
             * if (check == 0) { ischeck = false; } else { ischeck = true; }
             */
            args = new ContentValues();
            args.put("isChecked", candidateAns);

            System.out.println("Viewing Technology 1->" + viewTestTechnology);
            System.out.println("Viewing sQuestion ->" + sQuestion);
            System.out.println("Viewing sOption1 ->" + sOption1);
            System.out.println("Viewing sOption2 ->" + sOption2);
            System.out.println("Viewing sOption3 ->" + sOption3);
            System.out.println("Viewing sOption4 ->" + sOption4);
            System.out.println("is checked       ->" + check);

            if ((sQuestion != null) && !sQuestion.trim().equals("")) {
                tvQuestion.setText("Question   : " + sQuestion.trim());
            }
            if ((sOption1 != null) && !sOption1.trim().equals("")) {
                rbOption1.setText("Option 1    : " + sOption1.trim());
                if (check == 1) {
                    rbOption1.setChecked(true);
                } else {
                    rbOption1.setChecked(false);
                }
            }
            if ((sOption2 != null) && !sOption2.trim().equals("")) {
                rbOption2.setText("Option 2    : " + sOption2.trim());
                if (check == 2) {
                    rbOption2.setChecked(true);
                } else {
                    rbOption2.setChecked(false);
                }
            }
            if ((sOption3 != null) && !sOption3.trim().equals("")) {
                rbOption3.setText("Option 3    : " + sOption3.trim());
                if (check == 3) {
                    rbOption3.setChecked(true);
                } else {
                    rbOption3.setChecked(false);
                }
            }
            if ((sOption4 != null) && !sOption4.trim().equals("")) {
                rbOption4.setText("Option 4    : " + sOption4.trim());
                if (check == 4) {
                    rbOption4.setChecked(true);
                } else {
                    rbOption4.setChecked(false);
                }
            }
            /*
             * tvOption1.setChecked(false); tvOption2.setChecked(false);
             * tvOption3.setChecked(false); tvOption4.setChecked(false);
             */

        }
        /*
         * dh.update(OLECDatabase.QUESTIONS_TABLE, args, "_id=?", new String[] {
         * String.valueOf(previousRowId) });
         */

    }

    private void moveToNextQuestion() {
        String nextRowId = "";
        ContentValues args = null;
        // Cursor cursor = dh.query(OLECDatabase.QUESTIONS_TABLE, new
        // String[]{"_id","technology","question","option1","option2","option3","option4","answer"},
        // "technology =?",new String[]{viewTechnology} ,null, null, "_id asc");
        // startManagingCursor(cursor);
        if ((cursor != null) && cursor.moveToNext()) {
            rowId = cursor.getString(0);
            sQuestion = cursor.getString(cursor.getColumnIndex("question"));
            sOption1 = cursor.getString(cursor.getColumnIndex("option1"));
            sOption2 = cursor.getString(cursor.getColumnIndex("option2"));
            sOption3 = cursor.getString(cursor.getColumnIndex("option3"));
            sOption4 = cursor.getString(cursor.getColumnIndex("option4"));
            int check = cursor.getInt(cursor.getColumnIndex("isChecked"));
            /*
             * if (check == 0) { ischeck = false; } else { ischeck = true; }
             */
            args = new ContentValues();
            args.put("isChecked", candidateAns);
            System.out.println("Viewing Technology 1->" + viewTestTechnology);
            System.out.println("Viewing sQuestion ->" + sQuestion);
            System.out.println("Viewing sOption1 ->" + sOption1);
            System.out.println("Viewing sOption2 ->" + sOption2);
            System.out.println("Viewing sOption3 ->" + sOption3);
            System.out.println("Viewing sOption4 ->" + sOption4);
            System.out.println("is checked       ->" + ischeck);

            if ((sQuestion != null) && !sQuestion.trim().equals("")) {
                tvQuestion.setText("Question   : " + sQuestion.trim());
            }
            if ((sOption1 != null) && !sOption1.trim().equals("")) {
                rbOption1.setText("Option 1    : " + sOption1.trim());
                if (check == 1) {
                    rbOption1.setChecked(true);
                } else {
                    rbOption1.setChecked(false);
                }
            }
            if ((sOption2 != null) && !sOption2.trim().equals("")) {
                rbOption2.setText("Option 2    : " + sOption2.trim());
                if (check == 2) {
                    rbOption2.setChecked(true);
                } else {
                    rbOption2.setChecked(false);
                }
            }
            if ((sOption3 != null) && !sOption3.trim().equals("")) {
                rbOption3.setText("Option 3    : " + sOption3.trim());
                if (check == 3) {
                    rbOption3.setChecked(true);
                } else {
                    rbOption3.setChecked(false);
                }
            }
            if ((sOption4 != null) && !sOption4.trim().equals("")) {
                rbOption4.setText("Option 4    : " + sOption4.trim());
                if (check == 4) {
                    rbOption4.setChecked(true);
                } else {
                    rbOption4.setChecked(false);
                }
            }

            /*
             * dh.update(OLECDatabase.QUESTIONS_TABLE, args, "_id=?", new
             * String[] { String.valueOf(rowId) });
             */

        }

    }

}


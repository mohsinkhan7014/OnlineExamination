package nit.com.onlineexamination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AdminRegistrationMainActivity extends AppCompatActivity {
    Button dob;
    Button submit;
    Button reset;
    static final int DATE_DIALOG_ID = 0;
    private EditText mDateDisplay;
    private Button mPickDate;
    private EditText mDateDisplayAnnivarsary;
    private Button mPickDateAnnivarsary;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration_main);
        dob = (Button) findViewById(R.id.admin_dob_button);
        submit = (Button) findViewById(R.id.admin_submit_button);
        reset = (Button) findViewById(R.id.admin_reset_button);

        // mDateDisplay 			= (EditText) findViewById(R.id.dob_user);
        mPickDate = (Button) findViewById(R.id.admin_dob_button);


        mPickDate.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        submit.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(AdminRegistrationMainActivity.this, "Submit is click", Toast.LENGTH_LONG).show();

            }
        });
        reset.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(AdminRegistrationMainActivity.this, "Reset is click", Toast.LENGTH_LONG).show();
            }
        });
        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mPickDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append("Date of Birth : ")
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }

    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;

    }

    private void updateDisplay() {
        mPickDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append("Date of Birth : ")
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }
}


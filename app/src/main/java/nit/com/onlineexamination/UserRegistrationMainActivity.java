package nit.com.onlineexamination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.Date;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegistrationMainActivity extends AppCompatActivity {
    Button dob;
    Button submit;
    Button reset;
    static final int DATE_DIALOG_ID = 0;
    private Button mPickDate;
    private EditText mDateDisplayAnnivarsary;
    private int mYear;
    private int mMonth;
    private int mDay;

    private EditText etFirstName, etLastName, etEmailId, etUserName, etPassword, etConfirmPassword;
    private String sFirstName, sLastName, sEmailId, sUserName, sPassword, sConfirmPassword, sBirthday;

    SQLiteDatabase dh;
    boolean isAllEnteryFilled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration_main);
        AndroidContext.setContext(this);
        dh = OLECDatabase.getInstance().getDb();

        etFirstName = (EditText) findViewById(R.id.first_name);
        etLastName = (EditText) findViewById(R.id.last_name);
        etEmailId = (EditText) findViewById(R.id.email_id);
        etUserName = (EditText) findViewById(R.id.user_name);
        etPassword = (EditText) findViewById(R.id.password);
        etConfirmPassword = (EditText) findViewById(R.id.repassword);

        dob = (Button) findViewById(R.id.dob_button);
        submit = (Button) findViewById(R.id.submit_button);
        reset = (Button) findViewById(R.id.reset_button);

        // mDateDisplay 			= (EditText) findViewById(R.id.dob_user);
        mPickDate = (Button) findViewById(R.id.dob_button);


        mPickDate.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        submit.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                sFirstName = etFirstName.getText().toString();

                if (sFirstName != null && !sFirstName.equals("")) {
                    sFirstName = sFirstName.trim();
                } else {
                    sFirstName = "";
                    //showAlert("First Name!","Please Enter the First Name");
                }

                sLastName = etLastName.getText().toString();
                if (sLastName != null && !sLastName.equals("")) {
                    sLastName = sLastName.trim();
                } else {
                    sLastName = "";
                    //showAlert("Last Name!","Please Enter the Last Name");
                }

                sUserName = etUserName.getText().toString();
                if (sUserName != null && !sUserName.equals("")) {
                    sUserName = sUserName.trim();
                } else {
                    sUserName = "";
                    //showAlert("User Name!","Please Enter the User Name");
                }

                sEmailId = etEmailId.getText().toString();
                if (sEmailId != null && !sEmailId.equals("")) {
                    sEmailId = sEmailId.trim();
                } else {
                    sEmailId = "";
                    //showAlert("Email ID!","Please Enter the Email ID");
                }

                sPassword = etPassword.getText().toString();


                if (sPassword != null && !sPassword.equals("")) {
                    sPassword = sPassword.trim();
                } else {
                    sPassword = "";
                }

                sConfirmPassword = etConfirmPassword.getText().toString();

                if (sConfirmPassword != null && !sConfirmPassword.equals("")) {
                    sConfirmPassword = sConfirmPassword.trim();
                } else {
                    sConfirmPassword = "";
                }

                sBirthday = (new Date(mYear - 1900, mMonth, mDay)).toString();

				/*if(sPassword.equals(""))
				{
					showAlert("Password !","Please Enter the Password");
				}

				else if(sConfirmPassword.equals(""))
				{
					showAlert("Password !","Please Enter the Confirm Password");
				}
				else if(!sPassword.equals(sConfirmPassword)){
					showAlert("Password not matched!","Please Enter the correct Password & Confirm Password");
				}*/
                if (!sFirstName.trim().equals("") && !sLastName.trim().equals("") && !sEmailId.trim().equals("") && !sUserName.trim().equals("") && !sPassword.trim().equals("") && !sConfirmPassword.trim().equals("") && !sBirthday.trim().equals("")) {
                    isAllEnteryFilled = true;
                } else {
                    isAllEnteryFilled = false;
                }
                if (isAllEnteryFilled) {
                    if (!sPassword.equals(sConfirmPassword)) {
                        showAlert("Password not matched!", "Please Enter the correct Password & Confirm Password");
                    } else {

                        ContentValues userdetailValues = new ContentValues();
                        userdetailValues.put("firstname", sFirstName);
                        userdetailValues.put("lastname", sLastName);
                        userdetailValues.put("emailid", sEmailId);
                        userdetailValues.put("username", sUserName);
                        userdetailValues.put("password", sPassword);
                        userdetailValues.put("dateofbirth", sBirthday);
                        dh.insert(OLECDatabase.USER_DETAIL_TABLE, null, userdetailValues);
                        Intent indexIntent = new Intent(UserRegistrationMainActivity.this, IndexActivity.class);
                        startActivity(indexIntent);
                    }
                } else {
                    showAlert("Empty Field", "Empty field must be filled ");
                }
            }
        });
        reset.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                etFirstName.setText("");
                etLastName.setText("");
                etEmailId.setText("");
                etUserName.setText("");
                etPassword.setText("");
                etConfirmPassword.setText("");

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
        System.out.println("year " + mYear);
        System.out.println("month " + mMonth);
        System.out.println("date " + mDay);
        mPickDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append("Date of Birth : ")
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }

    protected void showAlert(String title, String mymessage) {
        new AlertDialog.Builder(this)
                .setMessage(mymessage) //.setPositiveButton( R.string.main_connection_button_ok_button,
                .setTitle(title)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        })
                .show();
    }

}


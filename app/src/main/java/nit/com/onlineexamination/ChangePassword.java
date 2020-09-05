package nit.com.onlineexamination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ChangePassword extends AppCompatActivity {
    private Button change_password_ok_button;
    View viewParam;
    public static String password = "";
    public static String new_password = "";
    public static String re_new_password;

    ProgressDialog myProgressDialog;
    public EditText et_old_password;
    public EditText et_new_password;
    public EditText et_re_new_password;

    SQLiteDatabase dh ;
    Cursor cursor;

    String userName;
    String oldPasswordOld;
    String oldPassword;
    String newPassword;
    String reNewPassword;

    Bundle getUserNameBundle;
    public static final String USER_PASSWORD_TABLE = "USER_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //dh = MoDatabase.getInstance().getDb();
        //getUserNameBundle = getIntent().getExtras();
        //userName = setpassword.userName;
        //userName = getUserNameBundle.getString("userName");

		/*cursor = dh.query(USER_PASSWORD_TABLE, new String[] { "user_id",
				"user_name", "password" }, null, null, null, null, null);
		startManagingCursor(cursor);
		if(cursor.moveToLast())
		{

			userName 		= cursor.getString(1);
		}
		System.out.println("old password ->"+oldPasswordOld);*/
		/*if (cursor.moveToFirst()) {
			do {
				oldPasswordOld = new PasswordUtil().getPassword(userName);
				userName		= cursor.getString(1);
			} while (cursor.moveToNext());
		}*/
		/*// System.out.println("saved password  @ change Password file ="
				+ oldPasswordOld);*/

        /*
         * myProgressDialog =
         * ProgressDialog.show(change_password.this,"Please wait......"
         * ,"Checking setting", true);
         *
         * new Thread() { public void run() { try{ // Do some Fake-Work
         * sleep(5000); } catch (Exception e) { } // Dismiss the Dialog
         * myProgressDialog.dismiss(); } }.start();
         */
// et_old_password =
        // (EditText)findViewById(R.id.t_change_password_old_password);
        et_new_password = (EditText) findViewById(R.id.t_change_password_new_password);
        et_re_new_password = (EditText) findViewById(R.id.t_change_password_re_new_password);


        change_password_ok_button = (Button) findViewById(R.id.ok_change_password_button);

        /*
         * change_password_change_password_button.setOnClickListener( new
         * View.OnClickListener() { public void onClick(View viewParam) {
         *
         *
         * // System.out.println("tchangepassword in change_password - 63 ="+
         * tchangepassword); if(tchangepassword.getText().toString().equals(""))
         * { Toast.makeText(ChangePassword.this,
         * "Change Password must not be blank", Toast.LENGTH_LONG).show(); }
         *
         * else
         * if(tchangepassword.getText().toString().equals(setpassword.password))
         * {// System.out.println("tchangepassword.getText().toString()   ="+
         * tchangepassword.getText().toString());
         * Toast.makeText(ChangePassword.this,
         * "Change Password should be different", Toast.LENGTH_LONG).show(); }
         * else { ischanged=true; changepassword =
         * tchangepassword.getText().toString();
         * Toast.makeText(ChangePassword.this, "Password has been changed",
         * Toast.LENGTH_LONG).show(); } } });
         */
        change_password_ok_button
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View viewParam) {

                        // oldPassword = et_old_password.getText().toString();
                        newPassword = et_new_password.getText().toString();
                        reNewPassword = et_re_new_password.getText().toString();

                        if(newPassword==null){
                            newPassword="";
                        }

                        if(reNewPassword==null){
                            reNewPassword="";
                        }

                        // Change Password logic
                        if(newPassword.equals("")&&reNewPassword.equals("")){

                            Toast.makeText(ChangePassword.this, "Please enter Password", 4000).show();

                        }else if(!newPassword.equals(reNewPassword)){

                            Toast.makeText(ChangePassword.this, "Password & Confirm Password not matched",4000).show();
                            et_new_password.setText("");
                            et_re_new_password.setText("");

                        }else{

                            if(newPassword.equals(oldPasswordOld))
                            {
                                Toast.makeText(ChangePassword.this, "New password should not be old password",4000).show();
                                et_new_password.setText("");
                                et_re_new_password.setText("");

                            }else{

                                ContentValues passwordValue = new ContentValues();

                                passwordValue.put("user_name", userName);
                                passwordValue.put("password", newPassword);
                                // System.out.println("user name =" + userName + " password =" + newPassword);*/
                                //dh.insert("USER_PASSWORD", null, passwordValue);

                                Intent myIntent = new Intent(ChangePassword.this, IndexActivity.class);
                                startActivity(myIntent);

                            }

                        }


                    }

                });

        this.getWindow().setSoftInputMode(	WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
    }


}


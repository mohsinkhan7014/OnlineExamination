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

public class CandidateDetails extends AppCompatActivity {
    SQLiteDatabase dh;
    private String rowId = null;
    private String sFirstName , sLastName , sEmailId , sUserName ,sBirthday;
    private TextView tvFirstName , tvLastName , tvEmailId , tvUserName , tvBirthday;
    Bundle bundle;
    ImageView nextUser , previousUser ;
    Drawable previousDisable,previousEnable,nextDisable,nextEnable;
    Resources res ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_details);
        AndroidContext.setContext(this);
        dh = OLECDatabase.getInstance().getDb();

        res = getResources();
        previousDisable=res.getDrawable(R.drawable.leftarrow_disable);
        previousEnable=res.getDrawable(R.drawable.left_arrow);
        nextDisable=res.getDrawable(R.drawable.rightarrow_disable);
        nextEnable=res.getDrawable(R.drawable.right_arrow);
        Cursor cursor = dh.query(OLECDatabase.USER_DETAIL_TABLE, new String[]{"_id","firstname","lastname","emailid","username","dateofbirth"}, null,null, null, null, "_id asc");
        startManagingCursor(cursor);
        if(cursor != null && cursor.moveToFirst()){
            rowId = cursor.getString(0);
            sFirstName = cursor.getString(cursor.getColumnIndex("firstname"));
            sLastName =  cursor.getString(cursor.getColumnIndex("lastname"));
            sEmailId =  cursor.getString(cursor.getColumnIndex("emailid"));
            sUserName =  cursor.getString(cursor.getColumnIndex("username"));
            sBirthday =  cursor.getString(cursor.getColumnIndex("dateofbirth"));
        }

        bundle = getIntent().getExtras();

        if(bundle != null){
            rowId = bundle.getString("RowId");
            Cursor cur = dh.query(OLECDatabase.USER_DETAIL_TABLE, new String[]{"_id","firstname","lastname","emailid","username","dateofbirth"}, "_id =?",new String[]{rowId}, null, null, null);
            startManagingCursor(cur);
            if(cur != null && cur.moveToFirst()){
                rowId = cur.getString(0);
                sFirstName = cur.getString(cur.getColumnIndex("firstname"));
                sLastName =  cur.getString(cur.getColumnIndex("lastname"));
                sEmailId =  cur.getString(cur.getColumnIndex("emailid"));
                sUserName =  cur.getString(cur.getColumnIndex("username"));
                sBirthday =  cur.getString(cur.getColumnIndex("dateofbirth"));
            }
        }

        tvFirstName = (TextView) findViewById(R.id.view_first_name);
        tvLastName = (TextView) findViewById(R.id.view_last_name);
        tvUserName = (TextView) findViewById(R.id.view_user_id);
        tvEmailId = (TextView) findViewById(R.id.view_email);
        tvBirthday = (TextView) findViewById(R.id.view_dob);

        if(sFirstName != null && !sFirstName.trim().equals("")){
            tvFirstName.setText("First Name  : "+sFirstName.trim());
        }
        if(sLastName != null && !sLastName.trim().equals("")){
            tvLastName.setText("Last Name   : "+sLastName.trim());
        }
        if(sUserName != null && !sUserName.trim().equals("")){
            tvUserName.setText("Login ID    : "+sUserName.trim());
        }
        if(sEmailId!= null && !sEmailId.trim().equals("")){
            tvEmailId.setText("Email ID    : "+sEmailId.trim());
        }
        if(sBirthday != null && !sBirthday.trim().equals("")){
            tvBirthday.setText("DOB         : "+sBirthday.trim());
        }

        previousUser = (ImageView) findViewById(R.id.previousCandidateDetailsButton);
        nextUser = (ImageView) findViewById(R.id.nextCandidateDetailsButton);

        Cursor c = null;
        if(rowId != null){
            c = dh.query(OLECDatabase.USER_DETAIL_TABLE, new String[]{"_id"}, "_id < ?", new String[]{rowId}, null, null, null);
            startManagingCursor(c);
            if(c.getCount() == 0){
                previousUser.setImageDrawable(previousDisable);
                previousUser.setEnabled(false);
                previousUser.setClickable(false);
            }else{
                previousUser.setImageDrawable(previousEnable);
                previousUser.setVisibility(View.VISIBLE);
            }

            c = dh.query(OLECDatabase.USER_DETAIL_TABLE, new String[]{"_id"}, "_id > ?", new String[]{rowId}, null, null, null);
            startManagingCursor(c);
            if(c.getCount() == 0){
                nextUser.setImageDrawable(nextDisable);
                nextUser.setEnabled(false);
                nextUser.setClickable(false);
            }else{
                nextUser.setImageDrawable(nextEnable);
                nextUser.setVisibility(View.VISIBLE);
            }
        }else{
            previousUser.setImageDrawable(previousDisable);
            previousUser.setEnabled(false);
            previousUser.setClickable(false);
            nextUser.setImageDrawable(nextDisable);
            nextUser.setEnabled(false);
            nextUser.setClickable(false);
        }
    }
    public void clickHandler(View v){
        if (v.getId() == R.id.previousCandidateDetailsButton)
        {
            moveToPreviousUser();

        }
        if (v.getId() == R.id.nextCandidateDetailsButton)
        {
            moveToNextUser();

        }
    }

    private void moveToPreviousUser() {
        String previousRowId ="";
        Cursor previousUserCursor = dh.query(OLECDatabase.USER_DETAIL_TABLE, new String[]{"_id"}, "_id < ?", new String[]{rowId}, null, null, "_id desc");
        startManagingCursor(previousUserCursor);
        if(previousUserCursor != null && previousUserCursor.moveToFirst()){
            previousRowId = previousUserCursor.getString(0);
        }
        Bundle bun = new Bundle();
        bun.putString("RowId", previousRowId);
        bun.putBoolean("CandidateDetails", true);
        Intent intent = new Intent(CandidateDetails.this , AdminMainActivity.class);
        intent.putExtras(bun);
        startActivity(intent);

    }
    private void moveToNextUser() {
        String nextRowId ="" ;
        Cursor nextUserCursor = dh.query(OLECDatabase.USER_DETAIL_TABLE, new String[]{"_id"}, "_id > ?", new String[]{rowId}, null, null, "_id asc");
        startManagingCursor(nextUserCursor);
        if(nextUserCursor != null && nextUserCursor.moveToFirst()){
            nextRowId = nextUserCursor.getString(0);
        }
        Bundle bun = new Bundle();
        bun.putString("RowId", nextRowId);
        bun.putBoolean("CandidateDetails", true);
        Intent intent = new Intent(CandidateDetails.this , AdminMainActivity.class);
        intent.putExtras(bun);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CandidateDetails.this , IndexActivity.class);
        startActivity(intent);

    }
}

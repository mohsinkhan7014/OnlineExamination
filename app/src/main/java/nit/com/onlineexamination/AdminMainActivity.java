package nit.com.onlineexamination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TabHost;


public class AdminMainActivity extends TabActivity  {
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
            Resources res = getResources(); // Resource object to get Drawables
            TabHost tabHost = getTabHost(); // The activity TabHost
            TabHost.TabSpec spec; // Resusable TabSpec for each tab
            Intent intent; // Reusable Intent for each tab

            // Create an Intent to launch an Activity for the tab (to be reused)
            intent = new Intent().setClass(this, DummyAdminMainActivity.class);

            // Initialize a TabSpec for each tab and add it to the TabHost
            spec = tabHost.newTabSpec("timesheet").setIndicator("Dashboard")
                    .setContent(intent);
            tabHost.addTab(spec);

            // Do the same for the other tabs

            intent = new Intent().setClass(this, CandidateDetails.class);
            bundle= getIntent().getExtras();
            boolean showCandiateDetails = false;
            if(bundle != null){
                intent.putExtras(bundle);
                showCandiateDetails = bundle.getBoolean("CandidateDetails");
            }
            spec = tabHost.newTabSpec("summary").setIndicator("Candidate Details")
                    .setContent(intent);
            tabHost.addTab(spec);

            intent = new Intent().setClass(this, DummyAdminMainActivity.class);
            spec = tabHost.newTabSpec("logs").setIndicator("Delete Candidate")
                    .setContent(intent);
            tabHost.addTab(spec);

            intent = new Intent().setClass(this, QuestionSetting.class);
            spec = tabHost.newTabSpec("timesheet").setIndicator("Set Question")
                    .setContent(intent);
            tabHost.addTab(spec);

            // Do the same for the other tabs
            intent = new Intent().setClass(this, ViewQuestionMain.class);
            spec = tabHost.newTabSpec("summary").setIndicator("View Question")
                    .setContent(intent);
            tabHost.addTab(spec);

            intent = new Intent().setClass(this, AdminRegistrationMainActivity.class);
            spec = tabHost.newTabSpec("logs").setIndicator("Edit Profile")
                    .setContent(intent);
            tabHost.addTab(spec);

            intent = new Intent().setClass(this, ChangePassword.class);
            spec = tabHost.newTabSpec("logs").setIndicator("Change Password")
                    .setContent(intent);
            tabHost.addTab(spec);

            if(showCandiateDetails)
                tabHost.setCurrentTab(1);
            else
                tabHost.setCurrentTab(0);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    	/*intent = new Intent().setClass(this, MainActivity.class);
    	spec = tabHost.newTabSpec("songs").setIndicator("Songs",
    	res.getDrawable(R.drawable.ic_tab_songs))
    	.setContent(intent);
    	tabHost.addTab(spec);*/


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            Intent intent = new Intent(this,DummyAdminMainActivity.class);
            startActivityForResult(intent, 0);

        }

        return super.onKeyDown(keyCode, event);
    }

}

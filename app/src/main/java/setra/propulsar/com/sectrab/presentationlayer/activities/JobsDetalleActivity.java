package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.ws.WS;

public class JobsDetalleActivity extends AppCompatActivity implements WS.OnWSRequested {

    private ImageView imageViewLogo;
    private ScrollView mScrollView;
    private TextView textViewCompany;
    private TextView textViewSector;
    private TextView textViewDatetime;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewUbicacion;
    private TextView textViewPhone;
    private TextView textViewEmail;

    int jobsID=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_detalle);

        //Asignacion del toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarJobsDetalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        jobsID=getIntent().getIntExtra("Id",-1);
        Log.d("DEB-JOBS","jobsID="+jobsID);

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
        int userID = sharedPreferences.getInt("userID",0);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("id",jobsID);
        WS.getJobsDetail(params,this);

        mScrollView = findViewById(R.id.scrollViewJobsDetalle);

        textViewCompany = (TextView) findViewById(R.id.jobsdetalle_textView_company);
        textViewSector = (TextView)findViewById(R.id.jobsdetalle_textView_sector);
        textViewDatetime = (TextView)findViewById(R.id.jobsdetalle_textView_datetime);
        textViewTitle = (TextView)findViewById(R.id.jobsdetalle_textView_title);
        textViewDescription = (TextView)findViewById(R.id.jobsdetalle_textView_description);
        textViewUbicacion = (TextView)findViewById(R.id.jobsdetalle_textView_ubicacion);
        textViewPhone = (TextView)findViewById(R.id.jobsdetalle_textView_phone);
        textViewEmail = (TextView)findViewById(R.id.jobsdetalle_textView_email);
        imageViewLogo = (ImageView)findViewById(R.id.jobsdetalle_imageView_logo);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
        int userID = sharedPreferences.getInt("userID",0);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("id",jobsID);
        WS.getJobsDetail(params,this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("debugSaved","WILLSAVE="+jobsID);
        outState.putInt("Id", jobsID);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("debugSaved","WILLRESTORE="+jobsID);
        jobsID = savedInstanceState.getInt("Id");
        Log.d("debugSaved","DIDRESTORE="+jobsID);
    }

    // ---------------------------------------------------------- //
    // -------------- WEB SERVICES IMPLEMENTATION --------------- //
    // ---------------------------------------------------------- //

    @Override
    public void wsAnswered(JSONObject json) {

        Log.d("GETJobsDetails",json.toString());
        int ws=0; int status=-1;
        try{status=json.getInt("status");}catch(Exception e){e.printStackTrace();}
        if(status!=0){/*ERRRRRRROOOOOOOORRRRRRR*/}

        try{
            ws = json.getInt("ws");
            switch (ws){
                case WS.WS_getJobsDetail: {
                    JSONObject data = json.getJSONObject("data");

                    Picasso.with(JobsDetalleActivity.this).load(data.getString("Logo")).into((ImageView)findViewById(R.id.jobsdetalle_imageView_logo));
                    ((ImageView)findViewById(R.id.jobsdetalle_imageView_logo)).setScaleType(ImageView.ScaleType.FIT_XY);

                    textViewCompany.setText(data.getString("Company"));
                    textViewSector.setText(data.getString("Sector"));
                    textViewDatetime.setText(data.getString("Published"));
                    textViewTitle.setText(data.getString("Title"));
                    textViewDescription.setText(data.getString("Description"));
                    textViewPhone.setText(data.getString("Phone"));
                    textViewEmail.setText(data.getString("Email"));
                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
}

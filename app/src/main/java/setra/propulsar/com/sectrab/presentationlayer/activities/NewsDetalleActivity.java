package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.ws.WS;



public class NewsDetalleActivity extends AppCompatActivity implements WS.OnWSRequested {

    private ScrollView mScrollView;
    private Button buttonNext;
    private TextView textViewTitulo;
    private TextView textViewInformacion;

    int newsID=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detalle);

        //Asignacion del toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNewsDetalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        newsID=getIntent().getIntExtra("Id",-1);
        Log.d("DEB-NEWS","newsID="+newsID);

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
        int userID = sharedPreferences.getInt("userID",0);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("id",newsID);
        WS.getNewsDetails(params,this);

        mScrollView = findViewById(R.id.scrollviewNewsDetalle);

        textViewTitulo = (TextView) findViewById(R.id.newsdetalle_textView_titulo);
        textViewInformacion = (TextView) findViewById(R.id.newsdetalle_textView_informacion);
        buttonNext = (Button)findViewById(R.id.newsdetalle_button_next);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
        int userID = sharedPreferences.getInt("userID",0);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("id",newsID);
        WS.getNewsDetails(params,this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("debugSaved","WILLSAVE="+newsID);
        outState.putInt("Id", newsID);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("debugSaved","WILLRESTORE="+newsID);
        newsID = savedInstanceState.getInt("Id");
        Log.d("debugSaved","DIDRESTORE="+newsID);
    }

    // ---------------------------------------------------------- //
    // -------------- WEB SERVICES IMPLEMENTATION --------------- //
    // ---------------------------------------------------------- //

    @Override
    public void wsAnswered(JSONObject json) {
        Log.d("GETNewsDetails",json.toString());
        int ws=0; int status=-1;
        try{status=json.getInt("status");}catch(Exception e){e.printStackTrace();}
        if(status!=0){/*ERRRRRRROOOOOOOORRRRRRR*/}

        try{
            ws = json.getInt("ws");
            switch (ws){
                case WS.WS_getNewsDetails: {
                    JSONObject data = json.getJSONObject("data");

                    Picasso.with(NewsDetalleActivity.this).load(data.getString("Image")).into((ImageView)findViewById(R.id.newsdetalle_imageView_portada));
                    ((ImageView)findViewById(R.id.newsdetalle_imageView_portada)).setScaleType(ImageView.ScaleType.FIT_XY);
                    textViewTitulo.setText(data.getString("Title"));
                    textViewInformacion.setText(data.getString("Description"));
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }
}

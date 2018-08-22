package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.presentationlayer.fragments.AppInfoFragment;
import setra.propulsar.com.sectrab.presentationlayer.fragments.ChatFragment;
import setra.propulsar.com.sectrab.presentationlayer.fragments.ContactsFragment;
import setra.propulsar.com.sectrab.presentationlayer.fragments.JobVacanciesFragment;
import setra.propulsar.com.sectrab.presentationlayer.fragments.NewsFragment;

public class MainNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ContactsFragment contactsFrag;
    JobVacanciesFragment jobVacanciesFrag;
    NewsFragment newsFrag;
    ChatFragment chatFrag;
    AppInfoFragment appInfoFrag;
    FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_app_bar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
        int userID = sharedPreferences.getInt("userID",0);

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.main_drawer_navigation_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer_container);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        contactsFrag = new ContactsFragment();
        jobVacanciesFrag = new JobVacanciesFragment();
        newsFrag = new NewsFragment();
        chatFrag = new ChatFragment();
        appInfoFrag = new AppInfoFragment();

        //Fragment inicial
        fragmentManager.beginTransaction().replace(R.id.contenedorrr, newsFrag).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_navigation_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else { super.onBackPressed();}

    }

    private void logOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());

        builder.setTitle("¿Deseas cerrar sesion?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getResources().getString(R.string.sharedPrefName),0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("userID",0);
                editor.putString("email","");
                editor.putBoolean("loggedIn",false);
                editor.commit();
                sendToLogin();


            }
        });
    }
    private void sendToLogin(){
        Intent intent = new Intent(MainNavigationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        return super.onOptionsItemSelected(item);
    }


    //Esta es la implementacion del listener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_noticias: {
                Log.d("NavigationDrawerDebug","Noticias");
                fragmentManager.beginTransaction().replace(R.id.contenedorrr, newsFrag).commit();
                break;
            }
            case R.id.nav_ofertas_empleo: {
                Log.d("NavigationDrawerDebug","Ofertas de empleo");
                fragmentManager.beginTransaction().replace(R.id.contenedorrr, jobVacanciesFrag).commit();
                break;
            }
            case R.id.nav_resuelve: {
                Log.d("NavigationDrawerDebug","Resuelve");
                fragmentManager.beginTransaction().replace(R.id.contenedorrr, chatFrag).commit();
                break;
            }
            case R.id.nav_contacto: {
                Log.d("NavigationDrawerDebug","Contacto");
                fragmentManager.beginTransaction().replace(R.id.contenedorrr, contactsFrag).commit();
                break;
            }
            case R.id.nav_settings: {
                Log.d("NavigationDrawerDebug","Informacion de la App");
                fragmentManager.beginTransaction().replace(R.id.contenedorrr, appInfoFrag).commit();
                break;
            }
            case R.id.nav_cerrar_sesion:{
                Log.d("NavigationDrawerDebug","Cerrar sesión");
                logOut();
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_navigation_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

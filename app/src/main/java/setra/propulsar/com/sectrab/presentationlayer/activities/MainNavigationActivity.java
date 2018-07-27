package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.presentationlayer.fragments.AppInfoFragment;
import setra.propulsar.com.sectrab.presentationlayer.fragments.ContactsFragment;
import setra.propulsar.com.sectrab.presentationlayer.fragments.JobVacanciesFragment;
import setra.propulsar.com.sectrab.presentationlayer.fragments.NoticesFragment;

public class MainNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navDrawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        //modifique tu menu checalo por fa
        //tambien agregue un layout para el header

        //El NavigationView es el que gestiona los escuchas
        navDrawerView = findViewById(R.id.navigation_drawer_container);
        navDrawerView.setNavigationItemSelectedListener(this);

        //Agregamos las instancias del Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_navigation_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //Si asignas un listener... reescribe el listener porque supongo lo haces para hacer algo con los comportamientos, en este caso del drawerlayout (no sé para que)
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_navigation_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else { super.onBackPressed();}
    }

    //Esta es la implementacion del listener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (item.getItemId()){
            case R.id.nav_noticias: {
                Log.d("NavigationDrawerDebug","Noticias");
                fragmentManager.beginTransaction().replace(R.id.main_drawer_navigation_layout, new NoticesFragment()).commit();
                break;
            }
            case R.id.nav_ofertas_empleo: {
                Log.d("NavigationDrawerDebug","Ofertas de empleo");
                fragmentManager.beginTransaction().replace(R.id.main_drawer_navigation_layout, new JobVacanciesFragment()).commit();
                break;
            }
            case R.id.nav_resuelve: {
                Log.d("NavigationDrawerDebug","Resuelve");
                break;
            }
            case R.id.nav_contacto: {
                Log.d("NavigationDrawerDebug","Contacto");
                fragmentManager.beginTransaction().replace(R.id.main_drawer_navigation_layout, new ContactsFragment()).commit();
                break;
            }
            case R.id.nav_settings: {
                Log.d("NavigationDrawerDebug","Informacion de la App");
                fragmentManager.beginTransaction().replace(R.id.main_drawer_navigation_layout, new AppInfoFragment()).commit();
                break;
            }
        }
        return false;
    }
}

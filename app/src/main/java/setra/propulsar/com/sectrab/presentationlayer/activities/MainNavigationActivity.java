package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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

        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();


        if (mToggle.onOptionsItemSelected(item)){
            return true;
        } else if (id == R.id.nav_noticias){
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_noticias: {
                Log.d("NavigationDrawerDebug","Noticias");
                break;
            }
        }
        return false;
    }
}

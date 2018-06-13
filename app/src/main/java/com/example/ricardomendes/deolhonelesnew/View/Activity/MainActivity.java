package com.example.ricardomendes.deolhonelesnew.View.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.ricardomendes.deolhonelesnew.View.Fragments.InicioFragment;
import com.example.ricardomendes.deolhonelesnew.View.Fragments.ParlamentaresFragment;
import com.example.ricardomendes.deolhonelesnew.View.Fragments.PartidoFragment;
import com.example.ricardomendes.deolhonelesnew.R;

import static android.app.FragmentTransaction.TRANSIT_ENTER_MASK;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    double lattitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            InicioFragment mFrag = new InicioFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, mFrag).addToBackStack(null).commit();
            navigationView.getMenu().getItem(0).setChecked(true);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_inicio) {
            getSupportFragmentManager().beginTransaction().setTransition(TRANSIT_ENTER_MASK).replace(R.id.frame_container, new InicioFragment()).addToBackStack(null).commit();
            setTitle("Inicio");
        } else if (id == R.id.nav_parlamentares) {
            getSupportFragmentManager().beginTransaction().setTransition(TRANSIT_ENTER_MASK).replace(R.id.frame_container, new ParlamentaresFragment()).addToBackStack(null).commit();
            setTitle("Parlamentares");
        } else if (id == R.id.nav_partidos) {
            getSupportFragmentManager().beginTransaction().setTransition(TRANSIT_ENTER_MASK).replace(R.id.frame_container, new PartidoFragment()).addToBackStack(null).commit();
            setTitle("Partidos");
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Script", "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Script", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Script", "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Script", "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Script", "onResume()");
    }



}

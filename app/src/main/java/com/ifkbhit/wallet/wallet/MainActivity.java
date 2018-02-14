package com.ifkbhit.wallet.wallet;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ifkbhit.wallet.wallet.fragments.CalendarFragment;
import com.ifkbhit.wallet.wallet.fragments.GroupsFragment;
import com.ifkbhit.wallet.wallet.fragments.HistoryFragment;
import com.ifkbhit.wallet.wallet.fragments.InfoFragment;
import com.ifkbhit.wallet.wallet.fragments.ToolsFragment;
import com.ifkbhit.wallet.wallet.user.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private InfoFragment infoFragment;
    private GroupsFragment groupsFragment;
    private HistoryFragment historyFragment;
    private ToolsFragment toolsFragment;
    private CalendarFragment calendarFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.setContext(this);
        User.init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.headerTextView);
        name.setText("Roman");


        calendarFragment = new CalendarFragment();
        infoFragment = new InfoFragment();
        historyFragment = new HistoryFragment();
        toolsFragment = new ToolsFragment();
        groupsFragment = new GroupsFragment();

        infoFragment.setAppContext(this);

        getFragmentManager().beginTransaction().replace(R.id.container, infoFragment).commit();

    }

    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (!infoFragment.onBackPressed()) {
                super.onBackPressed();
            }
        }

    }

    private void pressLog(String s) {
        Log.d("PRESS_LOG", s);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        int container = R.id.container;
        switch (id) {
            case R.id.nav_groups:
                transaction.replace(container, groupsFragment);
                break;
            case R.id.nav_history:
                transaction.replace(container, historyFragment);
                break;
            case R.id.nav_calendar:
                transaction.replace(container, calendarFragment);
                break;
            case R.id.nav_settings:
                transaction.replace(container, toolsFragment);
                break;
            default:
                transaction.replace(container, infoFragment);
                break;
        }

        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return infoFragment.onMenuItemSelected(item.getItemId());
    }
}

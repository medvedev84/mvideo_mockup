package com.terralink.android.mvideo.mockup;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;

public class MainDrawerActivity extends ActionBarMutableActivity {

    private final int SHOW_ADDRESS_DETAILS = -1;
    private final int SHOW_ADDRESS_LIST = 0;
    private final int SHOW_MAP = 1;
    private final int SHOW_PROFILE = 2;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mMenuTitles;

    private int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        showDatePickerDialog();

        mMenuTitles = getResources().getStringArray(R.array.menu_titles_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mMenuTitles));
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    protected int getActionBarLayout() {
        int actionBarId;
        switch (selectedPosition) {
            case SHOW_ADDRESS_LIST:
                actionBarId = R.layout.action_bar_main;
                break;
            case SHOW_MAP:
                actionBarId = R.layout.action_bar_map;
                break;
            case SHOW_PROFILE:
                actionBarId = R.layout.action_bar_map;
                break;
            default:
                actionBarId = R.layout.action_bar_details;
                break;
        }

        return actionBarId;
    }

    @Override
    public void customizeActionBar() {
        super.customizeActionBar();

        ImageView drawerBtn = (ImageView) findViewById(R.id.drawer_btn);
        if (drawerBtn != null)
            drawerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
                    if (!drawerOpen)
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                    else
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                }
            });

        ImageView backBtn = (ImageView) findViewById(R.id.back_btn);
        if (backBtn != null)
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItem(0);
                }
            });

        ImageView showDatePickerButton = (ImageView) findViewById(R.id.reload_btn);
        if (showDatePickerButton != null)
            showDatePickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {
        this.selectedPosition = position;
        customizeActionBar();
        switch (position) {
            case SHOW_ADDRESS_LIST:
                showRoutesList();
                break;
            case SHOW_MAP:
                showMap();
                break;
            case SHOW_PROFILE:
                showProfile();
                break;
            default:
                break;
        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void showRoutesList(){
        Fragment fragment = new RoutesListFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private void showMap(){
        Fragment fragment = new RoutesMapFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private void showProfile(){
        Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private void showDatePickerDialog(){
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dateDlg = new DatePickerDialog(MainDrawerActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(MainDrawerActivity.this, String.format("Date selected: %d-%d-%d", dayOfMonth, monthOfYear, year), Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);

        dateDlg.show();
    }

    public void showAddressCard(int id) {
        this.selectedPosition = SHOW_ADDRESS_DETAILS;
        customizeActionBar();

        // update the main content by replacing fragments
        Fragment fragment = new AddressCardDetailFragment();
        Bundle args = new Bundle();
        args.putInt(AddressCardDetailFragment.ARG_ADDRESS_NUMBER, id);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

}

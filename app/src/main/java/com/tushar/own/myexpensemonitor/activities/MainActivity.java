package com.tushar.own.myexpensemonitor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tushar.own.myexpensemonitor.R;
import com.tushar.own.myexpensemonitor.adapters.ViewPagerAdapter;
import com.tushar.own.myexpensemonitor.fragments.AddExpenseFragment;
import com.tushar.own.myexpensemonitor.fragments.ChartFragment;
import com.tushar.own.myexpensemonitor.fragments.HistoryFragment;
import com.tushar.own.myexpensemonitor.listeners.SettingsButtonClickListener;
import com.tushar.own.myexpensemonitor.services.SettingsChangedServices;
import com.tushar.own.myexpensemonitor.services.SharedPreferenceServices;
import com.tushar.own.myexpensemonitor.services.ViewPagerPageChangedServices;
import com.tushar.own.myexpensemonitor.utils.MyAlertDialog;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferenceServices.getInstance().setActivity(this);

        //Initializing all the UI element
        initializeUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_about){
            MyAlertDialog.showAboutAlertDialog(this, this);
        }
        else if (id == R.id.item_settings){
            MyAlertDialog.showSettingsAlertDialog(
                    SharedPreferenceServices.getInstance().getExpenseLimit(),
                    SharedPreferenceServices.getInstance().getExpenseCurrency(),
                    this,
                    this,
                    new SettingsButtonClickListener() {
                        @Override
                        public void onSettingsButtonClicked(String dailyLimit, String currency) {
                            SharedPreferenceServices.getInstance().setLimitAndCurrency(dailyLimit, currency);
                            SettingsChangedServices.getInstance().changeExpenseTextColor();
                        }
                    }
            );
        }
        else if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is used to initialize all the view element and event listener.
     */
    private void initializeUI() {
        initBottomNavigationView();

        initToolbar();

        initViewPager();
    }

    /**
     * This method is used to initialize the bottom navigation view
     * and it's on click listener.
     */
    private void initBottomNavigationView(){
        //Initializing the bottom navigation bar
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Handling bottom navigation icon select
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_charts:
                        viewPager.setCurrentItem(0);
                        toolbar.setTitle(getResources().getString(R.string.charts));
                        return true;
                    case R.id.menu_add_expense:
                        viewPager.setCurrentItem(1);
                        toolbar.setTitle(getResources().getString(R.string.add_expense));
                        return true;
                    case R.id.menu_history:
                        viewPager.setCurrentItem(2);
                        toolbar.setTitle(getResources().getString(R.string.history));
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * This method is used to initialize the toolbar with title and back button.
     */
    private void initToolbar(){
        //Initializing the toolbar
        toolbar = findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle(getResources().getString(R.string.add_expense));

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * This method is used to initialize the viewpager with the created fragments object
     * and set the @{AddExpenseFragment} as default page.
     */
    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);

        AddExpenseFragment addExpenseFragment = new AddExpenseFragment();
        HistoryFragment historyFragment = new HistoryFragment();
        ChartFragment chartFragment = new ChartFragment();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(chartFragment);

        adapter.addFragment(addExpenseFragment);

        adapter.addFragment(historyFragment);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        //Setting the local fragment as default
        bottomNavigationView.setSelectedItemId(R.id.menu_add_expense);

        //Manipulating the fragments with view pager
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

                if (position == 0){
                    toolbar.setTitle(getResources().getString(R.string.charts));
                    ViewPagerPageChangedServices.getInstance().changeViewPagerPageElement();
                }
                else if (position == 1){
                    toolbar.setTitle(getResources().getString(R.string.add_expense));
                }
                else if (position == 2){
                    toolbar.setTitle(getResources().getString(R.string.history));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

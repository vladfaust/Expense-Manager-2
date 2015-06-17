package ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import bl.models.DatabaseInstrument;
import ui.helpers.HomeExpListAdapter;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    // Views
    private Toolbar toolbar;
    Drawer drawer;
    TextView money,balance,percentage,budget;
    RelativeLayout spaceBelowToolbar;
    ExpandableListView listView;
    RelativeLayout toolbarOverlay;
    FloatingActionMenu fabMenu;

    // Main color
    int primaryColor;

    // Fonts
    public static Typeface robotoLight;
    public static Typeface robotoRegular;

    // Variables
    private boolean runInvisibility = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startUI();

        // transact test
        DatabaseInstrument dbi = new DatabaseInstrument(this);

    }


    @Override
    protected void startUI() {
        // UI blocks
        getFonts();
        getColors();
        initToolbar();
        initDrawer();
        initExpandableListView();
        initFAB();
    }


    // Setting Fonts
    private void getFonts() {
        robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        robotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
    }
    // Setting Fonts
    private void getColors() {
        primaryColor = getResources().getColor(R.color.HomeColorPrimary);
    }

    // Add Toolbar
    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(null);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        initToolbarText();
    }


    private void initToolbarText() {
        // TextViews of Toolbar
        money = (TextView)findViewById(R.id.MoneyValue);
        balance = (TextView)findViewById(R.id.Balance);
        percentage = (TextView)findViewById(R.id.PercentageValue);
        budget = (TextView)findViewById(R.id.Budget);

        // Setting fonts
        money.setTypeface(robotoLight);
        balance.setTypeface(robotoRegular);
        percentage.setTypeface(robotoLight);
        budget.setTypeface(robotoRegular);

        money.setText("asd");
        percentage.setText("789");
    }

    // Add Drawer
    private void initDrawer() {
        ColorDrawable drawable = new ColorDrawable(primaryColor);
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTextColor(Color.BLACK)
                .withHeaderBackground(drawable)

                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggleAnimated(true)
                .withDisplayBelowStatusBar(true)
                // Elements
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Summary June")
                                .withIcon(getResources().getDrawable(R.drawable.home))
                                .withSelectedTextColor(primaryColor),
                        new PrimaryDrawerItem().withName("History")
                                .withIcon(getResources().getDrawable(R.drawable.history))
                                .withSelectedTextColor(primaryColor),
                        new PrimaryDrawerItem().withName("Statistics")
                                .withIcon(getResources().getDrawable(R.drawable.statistics))
                                .withSelectedTextColor(primaryColor)
                )
                .build();
    }

    // Add ExpandableListView
    private void initExpandableListView() {
        listView = (ExpandableListView)findViewById(R.id.list);

        // Filling ListView
        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        ArrayList<String> children1 = new ArrayList<String>();
        ArrayList<String> children2 = new ArrayList<String>();
        children1.add("Child_1");
        children1.add("Child_2");
        groups.add(children1);
        children2.add("Child_1");
        children2.add("Child_2");
        children2.add("Child_3");
        groups.add(children2);
        groups.add(children2);
        groups.add(children2);

        // Setting adapter
        HomeExpListAdapter adapter = new HomeExpListAdapter(getApplicationContext(), groups);
        listView.setAdapter(adapter);

        //todo why 0?
//        int right = listView.getRight();
//        int width = listView.getWidth();
//        listView.setIndicatorBounds(right - 40, width);

    }

    // Add floating action bar
    private void initFAB() {
        // Fab-menu
        fabMenu = (FloatingActionMenu) findViewById(R.id.fab);
        // Income FAB
        FloatingActionButton incomeFab = new FloatingActionButton(this);
        incomeFab.setButtonSize(FloatingActionButton.SIZE_MINI);
        incomeFab.setLabelText(getResources().getString(R.string.addIncome));
        incomeFab.setColorNormal(getResources().getColor(R.color.FabColor));

        // Expense FAB
        FloatingActionButton expenseFab = new FloatingActionButton(this);
        expenseFab.setButtonSize(FloatingActionButton.SIZE_MINI);
        expenseFab.setLabelText(getResources().getString(R.string.addExpense));
        expenseFab.setColorNormal(getResources().getColor(R.color.FabColor));

        // Setting OnClickListener
        expenseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFab();
                startActivity(new Intent(MainActivity.this, ExpenseActivity.class));
            }
        });

        // Adding to Fab-menu
        fabMenu.addMenuButton(incomeFab);
        fabMenu.addMenuButton(expenseFab);

        fabMenu.setMenuButtonColorNormal(getResources().getColor(R.color.FabColor));
        fabMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean b) {
                setInvisibility(!fabMenu.isOpened());
            }
        });

        // Showing Fab-menu
        fabMenu.showMenuButton(true);
    }

    private void closeFab() {
        runInvisibility = false;
        fabMenu.close(true);
    }

    private void setInvisibility(boolean Invisible) {
        spaceBelowToolbar = (RelativeLayout)findViewById(R.id.spaceBelowToolbar);

        if(!Invisible) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean sleeping = false;
                    int animationDuration = 150;
                    int currentTime = 0;
                    float alphaWhite = 0.3f;
                    while(currentTime<animationDuration) {
                        setAlphaToViews(alphaWhite);
                        while (!sleeping && runInvisibility) {
                            try {
                                Thread.sleep(50);
                                sleeping = true;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        sleeping = false;
                        currentTime += 50;
                        alphaWhite -= 0.1f;
                    }
                }
            });
            thread.start();
        }
        else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean sleeping = false;
                    int animationDuration = 300;
                    int currentTime = 0;
                    float alphaNormal = 0.1f;
                    while(currentTime < animationDuration) {
                        setAlphaToViews(alphaNormal);
                        while (!sleeping && runInvisibility) {
                            try {
                                Thread.sleep(50);
                                sleeping = true;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        sleeping = false;
                        currentTime += 50;
                        alphaNormal += 0.3f;
                        if(currentTime == animationDuration)
                            setAlphaToViews(100);
                    }
                }
            });
            thread.start();
        }
    }

    private void setAlphaToViews(float alpha) {
        toolbar.setAlpha(alpha);
        spaceBelowToolbar.setAlpha(alpha);
        listView.setAlpha(alpha);
    }


    /*
       BackPressed Button Handler
     */
    @Override
    public void onBackPressed() {

        if(drawer!=null && !drawer.isDrawerOpen())
            if(fabMenu!=null && !fabMenu.isOpened())
                super.onBackPressed();
            else
            // Close fabMenu if it's open.
                closeFab();
        else
            // Close drawer if it's open.
            drawer.closeDrawer();

    }
    /*
       Menu methods
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}

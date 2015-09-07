package ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import bl.models.DatabaseInstrument;
import bl.models.User;
import ui.adapters.HomeExpListAdapter;
import ui.helpers.MyDrawer;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    // Views
    private Toolbar toolbar;
    private TextView money,balance,percentage,budget;
    private static TextView toolbarText;
    private RelativeLayout spaceBelowToolbar;
    private ExpandableListView listView;
    private RelativeLayout toolbarOverlay;
    private FloatingActionMenu fabMenu;
    private MyDrawer drawerBuilder;

    // Main color
    int primaryColor;

    // Fonts
    public static Typeface robotoLight;
    public static Typeface robotoRegular;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DatabaseInstrument dbi = new DatabaseInstrument(this);
        startUI();
    }

    @Override
    protected void onResume() {
        // For proper display
        drawerBuilder.getDrawer().setSelection(0);
        super.onResume();
    }

    @Override
    protected void startUI() {
        // UI blocks
        getFonts();
        getColors();
        initFAB();
        initToolbar();
        initDrawer();
        initExpandableListView();
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
        // Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        // SpaceBelowToolbar
        spaceBelowToolbar = (RelativeLayout)findViewById(R.id.spaceBelowToolbar);
        initToolbarText();

        // SettingOnclickListener to close fab is it's open
        toolbar.setOnClickListener(closeFabWithoutAnim);
        spaceBelowToolbar.setOnClickListener(closeFabWithoutAnim);
    }


    private void initToolbarText() {
        // TextViews of Toolbar
        money = (TextView)findViewById(R.id.MoneyValue);
        balance = (TextView)findViewById(R.id.Balance);
        percentage = (TextView)findViewById(R.id.PercentageValue);
        budget = (TextView)findViewById(R.id.Budget);
        toolbarText = (TextView)findViewById(R.id.toolbarText);

        // Setting fonts
        money.setTypeface(robotoLight);
        balance.setTypeface(robotoRegular);
        percentage.setTypeface(robotoLight);
        budget.setTypeface(robotoRegular);
        toolbarText.setTypeface(robotoRegular);

        toolbarText.setTextSize(25);
        toolbarText.setVisibility(View.GONE);

//        money.setText("$850");
        money.setText("$" + String.valueOf(User.getBalance()));
    }
    public static void setToolbarText(String text) {
        toolbarText.setVisibility(View.VISIBLE);
        toolbarText.setText(text);
    }
    public static void makeToolbarInvisible() {
        toolbarText.setText("");
        toolbarText.setVisibility(View.GONE);
    }

    // Add Drawer
    private void initDrawer() {
        drawerBuilder = new MyDrawer(this, toolbar,primaryColor, MyDrawer.Activities.Home);
        drawerBuilder.setFabMenu(fabMenu);
        drawerBuilder.create();
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


        // Setting adapter
        HomeExpListAdapter adapter = new HomeExpListAdapter(getApplicationContext(), groups, toolbar,spaceBelowToolbar,fabMenu);
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
        FloatingActionButton incomeFab = (FloatingActionButton)findViewById(R.id.incomeFab);
        incomeFab.setLabelText(getResources().getString(R.string.addIncome));
        incomeFab.setLabelVisibility(View.VISIBLE);

        // Expense FAB
        FloatingActionButton expenseFab = new FloatingActionButton(this);
        expenseFab.setButtonSize(FloatingActionButton.SIZE_MINI);
        expenseFab.setLabelText(getResources().getString(R.string.addExpense));
        expenseFab.setColorNormal(getResources().getColor(R.color.FabColor));
        expenseFab.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);


        // Setting OnClickListener
        expenseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFab();
                startActivity(new Intent(MainActivity.this, ExpenseActivity.class));
            }

        });

        // Adding to Fab-menu
        fabMenu.addMenuButton(expenseFab);

        fabMenu.setMenuButtonColorNormal(getResources().getColor(R.color.FabColor));

        fabMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean b) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(50);
                setInvisibility(fabMenu.isOpened());
            }
        });


        fabMenu.setClosedOnTouchOutside(true);
        // Showing Fab-menu
        fabMenu.showMenuButton(true);
    }

    private void closeFab() {
        setInvisibility(true);
        fabMenu.close(true);
    }

    public void setInvisibility(boolean isFabOpened) {
        AlphaAnimation alpha;
        final int durationOfAnimation = 150;

        if(isFabOpened) {
            alpha = new AlphaAnimation(1F, 0.1F);
        }
        else {
            alpha = new AlphaAnimation(0.1F, 1F);
        }

        alpha.setDuration(durationOfAnimation); // 250ms
        alpha.setFillAfter(true); // Tell it to persist after the animation ends

        RelativeLayout listViewLayout = (RelativeLayout)findViewById(R.id.listViewLayout);

        // And then on your layout
        toolbar.startAnimation(alpha);
        spaceBelowToolbar.startAnimation(alpha);
        listViewLayout.startAnimation(alpha);
    }

    /*
        BackPressed Button Handler
     */
    @Override
    public void onBackPressed() {

        if(drawerBuilder.getDrawer() !=null && !drawerBuilder.getDrawer().isDrawerOpen()) {
            if (fabMenu != null && !fabMenu.isOpened()) {
                super.onBackPressed();
            }
            // Close fabMenu if it's open.
            else {
                closeFab();
            }
        }
        // Close drawer if it's open.
        else {
            drawerBuilder.getDrawer().closeDrawer();
        }
    }

    View.OnClickListener closeFabWithoutAnim = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (fabMenu != null && fabMenu.isOpened()) {
                fabMenu.close(false);
            }
        }
    };
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

package ui.helpers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.cheesehole.expencemanager.R;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import ui.activities.MainActivity;
import ui.fragments.HistoryFragment;
import ui.fragments.StatisticsFragment;

/**
 * Created by Жамбыл on 21.06.2015.
 */
public class MyDrawer {

    private com.mikepenz.materialdrawer.Drawer drawer;

    Activity activity;
    Toolbar toolbar;
    FloatingActionMenu fabMenu;
    int primaryColor;
    boolean isFabMenuSet = false;

    FragmentManager fragmentManager;


    /*
        Constructor
     */
    public MyDrawer(FragmentActivity activity, Toolbar toolbar, int primaryColor) {
        this.activity = activity;
        this.toolbar = toolbar;
        this.primaryColor = primaryColor;
        fragmentManager = activity.getSupportFragmentManager();
    }

    /*
        Main method, which creates drawer
     */
    public void create(){

        drawer = new com.mikepenz.materialdrawer.DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(getHeader())
                .withActionBarDrawerToggleAnimated(true)
                .withDisplayBelowStatusBar(true)
                .withOnDrawerListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View view) {
                        if (isFabMenuSet) {
                            if (fabMenu != null && fabMenu.isOpened()) {
                                fabMenu.close(false);
                            }
                        } else {
                            Log.e("Drawer", "FabMenu is not set!");
                        }
                    }

                    @Override
                    public void onDrawerClosed(View view) {
                    }

                    @Override
                    public void onDrawerSlide(View view, float v) {

                    }
                })
                        // Elements
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("This Month")
                                .withIcon(activity.getResources().getDrawable(R.drawable.home))
                                .withSelectedTextColor(primaryColor),
                        new PrimaryDrawerItem().withName("History")
                                .withIcon(activity.getResources().getDrawable(R.drawable.history))
                                .withSelectedTextColor(primaryColor),
                        new PrimaryDrawerItem().withName("Budget")
                                .withIcon(activity.getResources().getDrawable(R.drawable.statistics))
                                .withSelectedTextColor(primaryColor),
                        new PrimaryDrawerItem().withName("Statistics")
                                .withIcon(activity.getResources().getDrawable(R.drawable.statistics))
                                .withSelectedTextColor(primaryColor)
                        ,
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Settings")
                                .withIcon(activity.getResources().getDrawable(R.drawable.statistics))
                                .withSelectedTextColor(primaryColor)
                )
                .withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        switch (i) {
                            // This Month
                            case 0:
                                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                MainActivity.makeToolbarInvisible();
                                break;

                            // History
                            case 1:
                                startFragment(new HistoryFragment(), "History");
                                break;

                            // Budget
                            case 2:
                                break;

                            // Statistics
                            case 3:
                                startFragment(new StatisticsFragment(), "Statistics");
                                break;

                            // Settings
                            case 4:
                                break;
                        }

                        return false;
                    }
                })
                .build();
    }

    private void startFragment(Fragment fragment, String text) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        MainActivity.setToolbarText(text);
    }

    private AccountHeader getHeader() {
        ColorDrawable drawable = new ColorDrawable(primaryColor);
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withTextColor(Color.BLACK)
                .withHeaderBackground(drawable)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        return false;
                    }
                })
                .build();

        return headerResult;
    }
    /*
        Getters and Setters
     */
    public void setFabMenu(FloatingActionMenu fabMenu) {
        this.fabMenu = fabMenu;
        isFabMenuSet = true;
    }
    public Drawer getDrawer(){
        return drawer;
    }
}

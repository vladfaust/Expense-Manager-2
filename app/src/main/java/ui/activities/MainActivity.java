package ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.cheesehole.expencemanager.R;

import ui.helpers.ExpListAdapter;
import ui.helpers.FlexibleSpace;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        startUI();
    }


    @Override
    protected void startUI() {
        initToolbar();

        // Create Flexible Toolbar
        new FlexibleSpace(this,this).create();
        initDrawer();
        initListView();
        initFAB();
    }

    // Add Toolbar
    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
    }

    // Add Drawer
    private void initDrawer() {
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("1"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("2"),
                        new SecondaryDrawerItem().withName("3"),
                        new SecondaryDrawerItem().withName("4")
                )
                .build();
    }

    // Add ExpandableListView
    private void initListView() {
        ExpandableListView listView = (ExpandableListView)findViewById(R.id.list);

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

        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), groups);
        listView.setAdapter(adapter);
        int right = listView.getRight();
        int width = listView.getWidth();
        listView.setIndicatorBounds(right - 40, width);

    }

    // Add Floating Action Bar
    private void initFAB() {
        final FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);

        FloatingActionButton incomeFab = new FloatingActionButton(this);
        incomeFab.setButtonSize(FloatingActionButton.SIZE_MINI);
        incomeFab.setLabelText(getResources().getString(R.string.addIncome));
        incomeFab.setColorNormal(getResources().getColor(R.color.FabColor));

        FloatingActionButton expenseFab = new FloatingActionButton(this);
        expenseFab.setButtonSize(FloatingActionButton.SIZE_MINI);
        expenseFab.setLabelText(getResources().getString(R.string.addExpence));
        expenseFab.setColorNormal(getResources().getColor(R.color.FabColor));

        fab.addMenuButton(incomeFab);
        fab.addMenuButton(expenseFab);

        fab.setMenuButtonColorNormal(getResources().getColor(R.color.FabColor));
        fab.showMenuButton(true);
    }





    /*
       BackPressed Button Handler
     */
    @Override
    public void onBackPressed() {
        // close drawer if it's open.
        if(drawer!=null && !drawer.isDrawerOpen())
            super.onBackPressed();
        else
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

package com.app.expencemanager;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bll.FlexibleSpace;
import bll.ListViewAdapter;


public class MainActivity extends BaseActivity {

    private static final String ATTRIBUTE_NAME_TEXT_NAME = "Text";
    private Toolbar toolbar;
    ListView listView;
    Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        startUI();
    }


    @Override
    protected void startUI() {
        // Create Flexible Toolbar
        new FlexibleSpace(this).create();

        initDrawer();
        initListView();
    }

    private void initListView() {
        listView = (ListView)findViewById(R.id.ListView);

        List<String> Texts =  new ArrayList<String>();
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> m;


        String[] from = {ATTRIBUTE_NAME_TEXT_NAME };
        int[] to = { R.id.MyText };

        for(int i = 0; i<10;i++) {
            Texts.add("asdsad");
        }
        for(int i=0;i<10; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NAME_TEXT_NAME,Texts.get(i));
            data.add(m);
        }

        ListViewAdapter listViewAdapter = new ListViewAdapter(this, data, R.layout.elements,
                from, to);
        listView.setAdapter(listViewAdapter);

        //initFAB();
    }

    /*private void initFAB() {
        ActionButton fab = (ActionButton) findViewById(R.id.fab);
        fab.show();
    }*/

    private void initDrawer() {
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("1"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("2")
                )
                .build();
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Expense Manager");
        toolbar.setTitleTextColor(Color.WHITE);
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

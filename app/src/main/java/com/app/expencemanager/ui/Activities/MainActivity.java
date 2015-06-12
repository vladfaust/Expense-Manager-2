package com.app.expencemanager.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.app.expencemanager.R;
import com.app.expencemanager.ui.uiHelper.FlexibleSpace;
import com.app.expencemanager.ui.uiHelper.ListViewAdapter;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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
        initToolbar();
        new FlexibleSpace(this,this).create();

        initDrawer();
        initListView();
    }

    private void initListView() {
        listView = (ListView)findViewById(R.id.list);

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

        initFAB();
    }

    private void initFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,onFAB.class));
            }
        });
    }


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

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
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

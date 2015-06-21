package ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import com.cheesehole.expencemanager.R;

import ui.helpers.MyDrawer;
import ui.helpers.HistoryFirstLevelAdapter;

/**
 * Created by Жамбыл on 21.06.2015.
 */
public class History extends BaseActivity {

    ExpandableListView listView;
    Toolbar toolbar;
    MyDrawer drawer;
    int colorOfDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        startUI();
    }

    @Override
    protected void startUI() {
        getColors();
        initToolbar();
        initDrawer();
        initExpListView();
    }

    private void getColors() {
        colorOfDrawer = getResources().getColor(R.color.HomeColorPrimary);
    }

    private void initDrawer() {
        drawer = new MyDrawer(this, toolbar,colorOfDrawer);
        drawer.create();
    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbarHistory);
    }

    private void initExpListView() {
        listView = (ExpandableListView)findViewById(R.id.history_list);

        HistoryFirstLevelAdapter adapter = new HistoryFirstLevelAdapter(this);
        listView.setAdapter(adapter);
    }
}

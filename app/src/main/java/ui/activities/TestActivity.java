package ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.cheesehole.expencemanager.R;

/**
 * Created by Жамбыл on 22.06.2015.
 */
public class TestActivity extends BaseActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

    }

    @Override
    protected void startUI() {
        initListView();
    }

    private void initListView() {
        listView = (ListView)findViewById(R.id.listViewTest);

    }
}

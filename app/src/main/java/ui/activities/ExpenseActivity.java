package ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

import ui.helpers.FinancesExpListViewAdapter;
import ui.helpers.HomeExpListAdapter;

/**
 * Created by Жамбыл on 13.06.2015.
 */
public class ExpenseActivity extends Activity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        startUI();
    }

    private void startUI() {
        initToolbar();
        initExpandableListView();
    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbarExpense);

        // Toolbar's views
        ImageButton back = (ImageButton)findViewById(R.id.finances_back);
        ImageButton ok = (ImageButton)findViewById(R.id.finances_ok);
        TextView label = (TextView)findViewById(R.id.finances_label);

        // Setting onClickListener
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void initExpandableListView() {
        ExpandableListView listView = (ExpandableListView)findViewById(R.id.finances_list);

        // Filling listView
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

        // Adding Views below ExpandedListView
        LinearLayout footer = (LinearLayout) getLayoutInflater().inflate(R.layout.finances_list_footer,null);
        listView.addFooterView(footer);

        // Setting adapter. It's important to set adapter after adding header/footer
        FinancesExpListViewAdapter adapter = new FinancesExpListViewAdapter(getApplicationContext(), groups);
        listView.setAdapter(adapter);
    }

}

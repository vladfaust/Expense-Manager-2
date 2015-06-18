package ui.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;

import ui.helpers.FinancesExpListViewAdapter;
import ui.helpers.MoneyExpListAdapter;

/**
 * Created by Жамбыл on 13.06.2015.
 */
public class ExpenseActivity extends Activity {

    // Views
    Toolbar toolbar;
    LinearLayout footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        startUI();
    }

    private void startUI() {
        initToolbar();
        initExpandableListView();
        initEditTexts(footer);
        initMoneyView();
        setStatusBarColor();
    }

    private void setStatusBarColor() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.FinancesColorPrimaryDark));
        }
    }

    private void initMoneyView() {
        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        ArrayList<String> children1 = new ArrayList<String>();
        children1.add("Child_1");
        groups.add(children1);

        ExpandableListView moneyList = (ExpandableListView)findViewById(R.id.moneyList);
        MoneyExpListAdapter adapter = new MoneyExpListAdapter(getApplicationContext(), groups);
        moneyList.setAdapter(adapter);
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
        footer = (LinearLayout) getLayoutInflater().inflate(R.layout.finances_list_footer, null);
        listView.addFooterView(footer);

        // Setting adapter. It's important to set adapter AFTER adding header/footer
        FinancesExpListViewAdapter adapter = new FinancesExpListViewAdapter(getApplicationContext(), groups);
        listView.setAdapter(adapter);

    }

    private void initEditTexts(LinearLayout footer) {
        // EditTexts
//        final EditText addCategory = (EditText)footer.findViewById(R.id.addCategory);
        final EditText addComment = (EditText)footer.findViewById(R.id.addComment);

        // Setting auto-hint
//        initEditTextHint(addCategory, getResources().getString(R.string.addCategory));
        initEditTextHint(addComment, getResources().getString(R.string.addComment));
    }

    private void initEditTextHint(final EditText editText, final String hint) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    editText.setHint("");

                if(!hasFocus)
                    if(editText.getHint().equals(""))
                    editText.setHint(hint);
            }
        });
    }

}

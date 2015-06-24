package ui.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import ui.adapters.FinancesExpListViewAdapter;
import ui.adapters.MoneyExpListAdapter;
import ui.helpers.FinancesExpListBundle;

/**
 * Created by Жамбыл on 13.06.2015.
 */
public class ExpenseActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    // Views
    private Toolbar toolbar;
    private LinearLayout footer;
    private ExpandableListView listView;
    private MoneyExpListAdapter moneyAdapter;
    private EditText addComment;
    private Button datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        startUI();
    }

    protected void startUI() {
        // UI blocks
        initToolbar();
        initFooter();
        initEditText();
        initDatePicker();
        initMoneyView();
        initExpandableListView();
        setBodyHeight();
        setStatusBarColor();
    }

    private void setBodyHeight() {
        LinearLayout body = (LinearLayout)findViewById(R.id.finances_body);
        body.setMinimumHeight(2000);
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

        ExpandableListView moneyList = (ExpandableListView)findViewById(R.id.moneyList);
        moneyAdapter = new MoneyExpListAdapter(getApplicationContext(), addComment);
        moneyList.setAdapter(moneyAdapter);

        moneyList.expandGroup(0);
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
        listView = (ExpandableListView)findViewById(R.id.finances_list);

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
        listView.addFooterView(footer);

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyAdapter.close();
            }
        });

        // Data container
        ArrayList<FinancesExpListBundle> bundles = new ArrayList<>();

        // Filling container
        for(int i = 0; i < groups.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View groupView = inflater.inflate(R.layout.finances_group_view, null);

            ArrayList<View> childViews = new ArrayList<>();
            for(int k = 0; k < groups.get(i).size(); k++) {
                View childView = inflater.inflate(R.layout.finances_child_view, null);
                childViews.add(childView);
            }
            FinancesExpListBundle bundle = new FinancesExpListBundle(groupView,childViews,groups.get(i));
            bundles.add(bundle);
        }

        // Setting adapter. It's important to set adapter AFTER adding header/footer
        final FinancesExpListViewAdapter adapter = new FinancesExpListViewAdapter(getApplicationContext(),bundles,moneyAdapter);
        listView.setAdapter(adapter);

//        if (android.os.Build.VERSION.SDK_INT <
//                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            listView.setIndicatorBounds(170, 200);
//        } else {
//            listView.setIndicatorBoundsRelative(170, 200);
//        }
    }

    private void initFooter() {
        footer = (LinearLayout) getLayoutInflater().inflate(R.layout.finances_list_footer, null);
    }

    @Override
    public void onBackPressed() {
        // Close calc if it's open
        if(MoneyExpListAdapter.isListExpanded){
            moneyAdapter.close();
        }
        else {
            super.onBackPressed();
        }
    }

    private void initEditText() {
        // EditText
        addComment = (EditText)footer.findViewById(R.id.addComment);

        // Setting auto-hint
        addComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    addComment.setHint("");
                    addComment.setFocusableInTouchMode(true);
                    // Close calc if it's open
                    moneyAdapter.close();
                }

                if (!hasFocus) {
                    if (addComment.getHint().equals(""))
                        addComment.setHint(getResources().getString(R.string.addComment));
                }
            }
        });
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment.setFocusableInTouchMode(true);
                moneyAdapter.close();
            }
        });
    }


    private void initDatePicker() {
        datePicker = (Button)footer.findViewById(R.id.datePicker);
        datePicker.setText("Today");
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ExpenseActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }
    /*
        OnDateSetListener method
     */
    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
        // todo Handle
    }
}

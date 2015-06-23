package ui.activities;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import ui.helpers.FinancesExpListViewAdapter;
import ui.helpers.MoneyExpListAdapter;

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
        moneyAdapter = new MoneyExpListAdapter(getApplicationContext(), groups,addComment);
        moneyList.setAdapter(moneyAdapter);
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

        // Setting adapter. It's important to set adapter AFTER adding header/footer
        final FinancesExpListViewAdapter adapter = new FinancesExpListViewAdapter(getApplicationContext(), groups, moneyAdapter);
        listView.setAdapter(adapter);

//        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//
//                ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.FinancesColorPrimary));
//                for(int i = 0; i < adapter.getGroupCount();i++) {
//
//                    for(int k = 0; k < adapter.getChildrenCount(i); k++) {
//
//                        if(i != groupPosition || k != childPosition) {
//                            if (adapter.getChildView(i, k, false, null, listView).getBackground() != cd) {
////                                adapter.getChildView(i, k, false, null, listView).setBackground(cd);
//                            }
//                        }
//                    }
//                }
//                v.setBackgroundColor(getResources().getColor(R.color.FinancesColorPrimary));
//
//                return false;
//            }
//        });

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

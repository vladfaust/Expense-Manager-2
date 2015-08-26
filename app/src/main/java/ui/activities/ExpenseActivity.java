package ui.activities;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.cheesehole.expencemanager.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import bl.models.Calculator;
import bl.models.Category;
import bl.models.DatabaseInstrument;
import bl.models.Selection;
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

        // for proper work moneyText should be initialised separately
        moneyAdapter.initMoneyText();
    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbarExpense);

        // Toolbar's views
        ImageButton back = (ImageButton)findViewById(R.id.finances_back);
        ImageButton ok = (ImageButton)findViewById(R.id.finances_ok);
        TextView label = (TextView)findViewById(R.id.finances_label);

        // Link to activity
        final ExpenseActivity context = this;

        // Setting onClickListener
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = "'"+((EditText)findViewById(R.id.addComment)).getText().toString()+"'";
                float sum = Calculator.eval(((TextView) findViewById(R.id.moneyText)).getText().toString());

                DatabaseInstrument.instance.addTransaction(comment, sum, Selection.selectedCategory,
                        "'"+Selection.selectedSubCategory+"'", "'"+Selection.selectedDate+"'");
                Toast.makeText(getApplicationContext(), "Expence added", Toast.LENGTH_SHORT).show();
                context.finish();
            }
        });

    }
    private void initExpandableListView() {
        listView = (ExpandableListView)findViewById(R.id.finances_list);

        // Filling listView
        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        ArrayList<Category> allCategories = DatabaseInstrument.instance.getAllCategories();
        for (Category category : allCategories){
            ArrayList<String> children = new ArrayList<>();
            for (String subCategory : category.subCategoryList){
                children.add(subCategory);
            }
            groups.add(children);
        }

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

            final int catId = i;
            for(int k = 0; k < groups.get(i).size(); k++) {
                final String subCat = groups.get(i).get(k);
                final View childView = inflater.inflate(R.layout.finances_child_view, null);

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
                dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
                        Selection.selectedDate = i + "-" + i1 + "-" + i2;
                    }
                });
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

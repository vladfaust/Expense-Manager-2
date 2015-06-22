package ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

/**
 * Created by Жамбыл on 22.06.2015.
 */
public class ExpenseViewActivity extends BaseActivity {

    // Views
    Toolbar toolbar;
    TextView dateValueText,dateText,categoryValueText,categoryText,
             subcategoryValueText,subcategoryText, commentValueText, commentText, moneyValueText,moneyText;

    // Variables
    String date,category,subcategory,comment,money;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_view);

        getStoredData();
        startUI();
    }

    private void getStoredData() {
        Bundle bundle = getIntent().getExtras();
        date = bundle.getString(HistoryActivity.DATE);
        category = bundle.getString(HistoryActivity.CATEGORY);
        subcategory = bundle.getString(HistoryActivity.SUBCATEGORY);
        comment = bundle.getString(HistoryActivity.COMMENT);
        money = bundle.getString(HistoryActivity.MONEY);
    }

    @Override
    protected void startUI() {
        // UI blocks
        initToolbar();
        initExpenseData();
    }

    private void initExpenseData() {

        // Initialization
        dateValueText = (TextView)findViewById(R.id.expense_view_date_value);
        dateText = (TextView)findViewById(R.id.expense_view_date);
        categoryValueText = (TextView)findViewById(R.id.expense_view_category_value);
        categoryText = (TextView)findViewById(R.id.expense_view_category);
        subcategoryValueText = (TextView)findViewById(R.id.expense_view_subcategory_value);
        subcategoryText = (TextView)findViewById(R.id.expense_view_subcategory);
        commentValueText = (TextView)findViewById(R.id.expense_view_comment_value);
        commentText = (TextView)findViewById(R.id.expense_view_comment);
        moneyValueText = (TextView)findViewById(R.id.expense_view_money_value);
        moneyText = (TextView)findViewById(R.id.expense_view_money);

        // Setting value
        dateValueText.setText(date);
        categoryValueText.setText(category);
        subcategoryValueText.setText(subcategory);
        commentValueText.setText(comment);
        moneyValueText.setText(money);


    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbarExpenseView);
    }
}

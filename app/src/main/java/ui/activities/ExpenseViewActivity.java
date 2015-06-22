package ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.cheesehole.expencemanager.R;

/**
 * Created by Жамбыл on 22.06.2015.
 */
public class ExpenseViewActivity extends BaseActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_view);

        startUI();
    }

    @Override
    protected void startUI() {
        // UI blocks
        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbarExpenseView);
    }
}

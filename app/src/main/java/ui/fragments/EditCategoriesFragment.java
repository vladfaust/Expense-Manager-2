package ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheesehole.expencemanager.R;

/**
 * Created by Жамбыл on 13.09.2015.
 */
public class EditCategoriesFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_edit_categories,null);

        startUI(v);

        return v;
    }

    @Override
    protected void startUI(View v) {

    }
}

package ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import android.os.Bundle;

import com.cheesehole.expencemanager.R;

import ui.adapters.DragNDropAdapter;
import ui.views.DragNDropExpListView;

/**
 * Created by Жамбыл on 13.09.2015.
 */
public class EditCategoriesFragment extends BaseFragment {

    /**children items with a key and value list */
    private Map<String, ArrayList<String>> children;
    DragNDropExpListView dndListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_edit_categories,null);

        getData();
        startUI(v);

        return v;
    }

    @Override
    protected void startUI(View v) {
        dndListView = (DragNDropExpListView) v.findViewById(R.id.list_view_customizer);
        dndListView.setDragOnLongPress(true);
        dndListView.setAdapter(new DragNDropAdapter(v.getContext(), new int[] {R.layout.edit_categ_row_item}, new int[]{R.id.txt__customizer_item}, children));
    }

    private void getData()
    {
        ArrayList<String> groups = new ArrayList<String>();
        children = Collections
                .synchronizedMap(new LinkedHashMap<String, ArrayList<String>>());
        for(int i = 0;i<4 ;i++)
        {
            groups.add("group "+i);

        }
        for(String s : groups)
        {
            ArrayList<String> child = new ArrayList<String>();


            for(int i = 0;i<4 ;i++)
            {

                child.add(s+" -value"+i);


            }
            children.put(s, child);
        }
    }

}

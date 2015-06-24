package ui.helpers;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by Жамбыл on 24.06.2015.
 */
public class FinancesExpListBundle {

    public View groupView;

    public ArrayList<View> childViews;

    public ArrayList<String> groupData;

    public FinancesExpListBundle(View groupView, ArrayList<View> childViews, ArrayList<String> groupData) {
        this.childViews = childViews;
        this.groupData = groupData;
        this.groupView = groupView;
    }
}

package ui.views;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by Жамбыл on 21.06.2015.
 */
public class CustExpListview extends ExpandableListView {

    int intGroupPosition, intChildPosition, intGroupid;

    public CustExpListview(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(1500,
                MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(6000,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

package ui.helpers;

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
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(960,
                MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(600,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

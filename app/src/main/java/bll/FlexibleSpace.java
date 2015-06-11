package bll;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.app.expencemanager.BaseActivity;
import com.app.expencemanager.R;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;


/**
 * Created by Жамбыл on 11.06.2015.
 */
public class FlexibleSpace implements ObservableScrollViewCallbacks {

    private View mFlexibleSpaceView;
    private View mToolbarView;
    private TextView mTitleView;
    private int mFlexibleSpaceHeight;

    BaseActivity activity;

    public FlexibleSpace(BaseActivity activity) {
        this.activity = activity;
    }

    public void create(){
        activity.setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
        ActionBar ab = activity.getSupportActionBar();
        if (ab != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFlexibleSpaceView = activity.findViewById(R.id.flexible_space);
        mFlexibleSpaceView.setBackgroundColor(activity.getResources().getColor(R.color.ColorPrimary));

        mTitleView = (TextView) activity.findViewById(R.id.title);
        mTitleView.setText("Expense Manager");
        activity.setTitle(null);
        mToolbarView = activity.findViewById(R.id.toolbar);
        //mToolbarView.setElevation(20);

        final ObservableScrollView scrollView = (ObservableScrollView) activity.findViewById(R.id.scroll);
        scrollView.setScrollViewCallbacks(this);

        mFlexibleSpaceHeight = activity.getResources().getDimensionPixelSize(R.dimen.flexible_space_height);
        int flexibleSpaceAndToolbarHeight = mFlexibleSpaceHeight + getActionBarSize();

        activity.findViewById(R.id.body).setPadding(0, flexibleSpaceAndToolbarHeight, 0, 0);
        mFlexibleSpaceView.getLayoutParams().height = flexibleSpaceAndToolbarHeight;

        ScrollUtils.addOnGlobalLayoutListener(mTitleView, new Runnable() {
            @Override
            public void run() {
                updateFlexibleSpaceText(scrollView.getCurrentScrollY());
            }
        });
    }

    /*
        ObservableScrollViewCallbacks methods
     */
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        updateFlexibleSpaceText(scrollY);

        if(mFlexibleSpaceHeight == mToolbarView.getHeight())
            Log.e("","woking");
    }

    @Override
    public void onDownMotionEvent() {
        //mToolbarView.setElevation(0);
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        //mToolbarView.setElevation(0);
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = activity.obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    private void updateFlexibleSpaceText(final int scrollY) {
        ViewHelper.setTranslationY(mFlexibleSpaceView, -scrollY);
        int adjustedScrollY = (int) ScrollUtils.getFloat(scrollY, 0, mFlexibleSpaceHeight);
        float maxScale = (float) (mFlexibleSpaceHeight - mToolbarView.getHeight()) / mToolbarView.getHeight();
        float scale = (maxScale * ((float) mFlexibleSpaceHeight - adjustedScrollY) / mFlexibleSpaceHeight)/5;

        ViewHelper.setPivotX(mTitleView, 0);
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, 1 + scale);
        ViewHelper.setScaleY(mTitleView, 1 + scale);
        int maxTitleTranslationY = mToolbarView.getHeight() + mFlexibleSpaceHeight - (int) (mTitleView.getHeight() * (1 + scale));
        int titleTranslationY = (int) (maxTitleTranslationY * ((float) mFlexibleSpaceHeight - adjustedScrollY) / mFlexibleSpaceHeight);
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);
    }
}

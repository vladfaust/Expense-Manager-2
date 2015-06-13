package ui.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;
import ui.activities.BaseActivity;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;


/**
 * Created by Жамбыл on 11.06.2015.
 */

public class FlexibleSpace implements ObservableScrollViewCallbacks {


    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private Toolbar mToolbarView;
    private View mOverlayView;
    private View mListBackgroundView;
    private TextView mTitleView;
    private int mActionBarSize;
    private int mFlexibleSpaceImageHeight;

    BaseActivity activity;                       // Main UI instance
    Context context;

    public FlexibleSpace (BaseActivity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    /*
        Main method which creates FlexibleSpace
     */
    public void create() {
        activity.setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
        ActionBar ab = activity.getSupportActionBar();
        if (ab != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbarView = (Toolbar) activity.findViewById(R.id.toolbar);

        mFlexibleSpaceImageHeight = activity.getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mActionBarSize = getActionBarSize();
        mOverlayView = activity.findViewById(R.id.overlay);
        ExpandableListView listView = (ExpandableListView) activity.findViewById(R.id.list);
        //listView.setScrollViewCallbacks(this);

        // Set padding view for ListView. This is the flexible space.
        View paddingView = new View(context);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                mFlexibleSpaceImageHeight);
        paddingView.setLayoutParams(lp);

        // This is required to disable header's list selector effect
        paddingView.setClickable(true);

        listView.addHeaderView(paddingView);
        mTitleView = (TextView) activity.findViewById(R.id.title);
        mTitleView.setText("Expense Manager");
        activity.setTitle(null);

        // mListBackgroundView makes ListView's background except header view.
        mListBackgroundView = activity.findViewById(R.id.list_background);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        //ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        //ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Translate list background
        ViewHelper.setTranslationY(mListBackgroundView, Math.max(0, -scrollY + mFlexibleSpaceImageHeight));

        // Change alpha of overlay
        //ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        //setPivotXToTitle();
        //ViewHelper.setPivotY(mTitleView, 0);
        //ViewHelper.setScaleX(mTitleView, scale);
        //ViewHelper.setScaleY(mTitleView, scale);

        //
        int adjustedScrollY = (int) ScrollUtils.getFloat(scrollY, 0, mFlexibleSpaceImageHeight);
        float maxScale = (float) (mFlexibleSpaceImageHeight - mToolbarView.getHeight()) / mToolbarView.getHeight();
        float mscale = maxScale * ((float) mFlexibleSpaceImageHeight - adjustedScrollY) / mFlexibleSpaceImageHeight;


        //ViewHelper.setPivotX(mTitleView, 0);
        // ViewHelper.setPivotY(mTitleView, 0);
        // ViewHelper.setScaleX(mTitleView, 1 + mscale);
        // ViewHelper.setScaleY(mTitleView, 1 + mscale);
        int maxTitleTranslationY = mToolbarView.getHeight() + mFlexibleSpaceImageHeight - (int) (mTitleView.getHeight() * (1 + mscale));
        int titleTranslationY = (int) (maxTitleTranslationY * ((float) mFlexibleSpaceImageHeight - adjustedScrollY) / mFlexibleSpaceImageHeight);
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);
        //
/*
        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);*/

    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle() {
        Configuration config = activity.getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(mTitleView, activity.findViewById(android.R.id.content).getWidth());
        } else {
            ViewHelper.setPivotX(mTitleView, 0);
        }
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


}
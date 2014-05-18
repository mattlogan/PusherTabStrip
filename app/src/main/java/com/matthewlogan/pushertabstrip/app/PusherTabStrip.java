package com.matthewlogan.pushertabstrip.app;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PusherTabStrip extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private Context mContext;

    private ViewPager mViewPager;

    private TextView[] mTextViews;

    private int mCurrentPosition;
    private double mCurrentPositionOffset;

    public PusherTabStrip(Context context) {
        this(context, null);
    }

    public PusherTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PusherTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;

        setWillNotDraw(false);
    }

    public void bindViewPager(ViewPager viewPager, String[] titles) {
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);

        mTextViews = new TextView[titles.length];

        for (int i = 0; i < titles.length; i++) {
            TextView textView = new TextView(mContext);

            textView.setText(titles[i]);

            addView(textView);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) textView.getLayoutParams();
            if (lp != null) {
                lp.addRule(CENTER_VERTICAL);
                textView.setLayoutParams(lp);
            }

            mTextViews[i] = textView;
        }

        layoutTextViews();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPosition = position;
        mCurrentPositionOffset = positionOffset;

        layoutTextViews();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void layoutTextViews() {
        for (int i = 0; i < mTextViews.length; i++) {
            if (i < mCurrentPosition - 1) {
                TextView behindTextView = mTextViews[i];
                behindTextView.setX(0.f - behindTextView.getMeasuredWidth());

            } else if (i == mCurrentPosition - 1) {
                TextView prevTextView = mTextViews[i];
                prevTextView.setX(0.f);

            } else if (i == mCurrentPosition) {
                TextView currentTextView = mTextViews[i];
                currentTextView.setX(getWidth() / 2.f - currentTextView.getMeasuredWidth() / 2.f);

            } else if (i == mCurrentPosition + 1) {
                TextView nextTextView = mTextViews[i];
                nextTextView.setX(getWidth() - nextTextView.getMeasuredWidth());

            } else if (i > mCurrentPosition + 1) {
                TextView aheadTextView = mTextViews[i];
                aheadTextView.setX(getWidth());

            }
        }
    }
}

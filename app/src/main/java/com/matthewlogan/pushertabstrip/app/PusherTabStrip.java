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
    private int mCurrentOffsetPixels;

    private boolean mDidInitialLayout;

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

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);

        mTextViews = new TextView[mViewPager.getAdapter().getCount()];

        for (int i = 0; i < mTextViews.length; i++) {
            TextView textView = new TextView(mContext);

            textView.setText(mViewPager.getAdapter().getPageTitle(i));

            addView(textView);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) textView.getLayoutParams();
            if (lp != null) {
                lp.addRule(CENTER_VERTICAL);
                textView.setLayoutParams(lp);
            }

            mTextViews[i] = textView;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPosition = position;
        mCurrentOffsetPixels = positionOffsetPixels;

        if (mDidInitialLayout) {
            layoutTextViews();
        } else {
            initialLayoutTextViews();
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initialLayoutTextViews() {
        for (int i = 0; i < mTextViews.length; i++) {
            if (i < mCurrentPosition - 1) {
                mTextViews[i].setX(-mTextViews[i].getMeasuredWidth());

            } else if (i == mCurrentPosition - 1) {
                mTextViews[i].setX(0.f);

            } else if (i == mCurrentPosition) {
                mTextViews[i].setX(getWidth() / 2.f - mTextViews[i].getMeasuredWidth() / 2.f);

            } else if (i == mCurrentPosition + 1) {
                mTextViews[i].setX(getWidth() - mTextViews[i].getMeasuredWidth());

            } else if (i > mCurrentPosition) {
                mTextViews[i].setX(getWidth());
            }
        }

        mDidInitialLayout = true;
    }

    private void layoutTextViews() {
        TextView curTextView = mTextViews[mCurrentPosition];

        float center = getWidth() / 2.f - curTextView.getMeasuredWidth() / 2.f;
        float curX = center - mCurrentOffsetPixels;
        if (curX < 0) {
            curX = 0;
        }

        curTextView.setX(curX);

        if (mCurrentPosition > 0) {
            TextView prevTextView = mTextViews[mCurrentPosition - 1];

            float prevX = 0.f;
            if (curX <= prevTextView.getWidth()) {
                prevX = curX - prevTextView.getWidth();
            }

            prevTextView.setX(prevX);
        }

        if (mCurrentPosition < mTextViews.length - 1) {
            TextView nextTextView = mTextViews[mCurrentPosition + 1];

            float nextRight = getWidth() - nextTextView.getMeasuredWidth();
            float nextCenter = getWidth() / 2.f - nextTextView.getMeasuredWidth() / 2.f;
            float distanceToMove = nextRight - nextCenter;

            float nextX = nextRight;
            if (getWidth() - mCurrentOffsetPixels <= distanceToMove) {
                nextX = nextRight - (distanceToMove - (getWidth() - mCurrentOffsetPixels));
            }

            nextTextView.setX(nextX);

            if (mCurrentPosition < mTextViews.length - 2) {
                TextView nextNextTextView = mTextViews[mCurrentPosition + 2];

                float nextNextX = nextX + nextTextView.getMeasuredWidth();
                if (nextNextX < getWidth() - nextNextTextView.getMeasuredWidth()) {
                    nextNextX = getWidth() - nextNextTextView.getMeasuredWidth();
                }

                nextNextTextView.setX(nextNextX);
            }
        }
    }
}

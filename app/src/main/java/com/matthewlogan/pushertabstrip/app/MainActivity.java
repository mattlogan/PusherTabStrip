package com.matthewlogan.pushertabstrip.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends ActionBarActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new ColorAdapter());

        PusherTabStrip pusherTabStrip = (PusherTabStrip) findViewById(R.id.pusher_tab_strip);
        pusherTabStrip.bindViewPager(viewPager, new String[] {
                "Blue", "Red", "Green"
        });
    }

    private class ColorAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem (ViewGroup container, int position) {
            View view = new View(mContext);

            int backgroundColor = 0;
            switch (position) {
                case 0:
                    backgroundColor = Color.BLUE;
                    break;
                case 1:
                    backgroundColor = Color.RED;
                    break;
                case 2:
                    backgroundColor = Color.GREEN;
                    break;
            }
            view.setBackgroundColor(backgroundColor);

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem (ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

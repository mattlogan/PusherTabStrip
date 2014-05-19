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
        pusherTabStrip.setViewPager(viewPager);
    }

    private class ColorAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 5;
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
                case 3:
                    backgroundColor = Color.YELLOW;
                    break;
                case 4:
                    backgroundColor = Color.MAGENTA;
                    break;
            }
            view.setBackgroundColor(backgroundColor);

            container.addView(view);

            return view;
        }

        public CharSequence getPageTitle (int position) {
            String title = null;
            switch (position) {
                case 0:
                    title = "Blue";
                    break;
                case 1:
                    title = "Red";
                    break;
                case 2:
                    title = "Green";
                    break;
                case 3:
                    title = "Yellow";
                    break;
                case 4:
                    title = "Magenta";
                    break;
            }
            return title;
        }

        @Override
        public void destroyItem (ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

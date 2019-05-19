package com.wzh.demo;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.tmall.ultraviewpager.UltraViewPager;
import com.wzh.viewpager.indicator.UIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager1, mViewPager2, mViewPager3;
    private DemoPagerAdapter mAdapter1, mAdapter2, mAdapter3,mAdapter4;

    private UltraViewPager mViewPager4;
    private UIndicator uIndicator1, uIndicator2, uIndicator3,uIndicator4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager1 = findViewById(R.id.viewPager1);
        mViewPager2 = findViewById(R.id.viewPager2);
        mViewPager3 = findViewById(R.id.viewPager3);
        mViewPager4 = findViewById(R.id.ultra_viewpager);

        uIndicator1 = findViewById(R.id.indicator1);
        uIndicator2 = findViewById(R.id.indicator2);
        uIndicator3 = findViewById(R.id.indicator3);
        uIndicator4 = findViewById(R.id.indicator4);


        mAdapter1 = new DemoPagerAdapter(getList());
        mViewPager1.setAdapter(mAdapter1);
        uIndicator1.attachToViewPager(mViewPager1);

        mAdapter2 = new DemoPagerAdapter(getList());
        mViewPager2.setAdapter(mAdapter2);
        uIndicator2.attachToViewPager(mViewPager2);

        mAdapter3 = new DemoPagerAdapter(getList());
        mViewPager3.setAdapter(mAdapter3);
        uIndicator3.attachToViewPager(mViewPager3);

        mAdapter4 = new DemoPagerAdapter(getList());
        mViewPager4.setAdapter(mAdapter4);
        uIndicator4.attachToViewPager(mViewPager4.getViewPager());
    }

    public List<View> getList() {
        List<View> list = new ArrayList<>();
        Random rm = new Random();
        for (int i = 0; i < 5; i++) {
            int ranColor = 0xff000000 | rm.nextInt(0x00ffffff);
            list.add(generateView(ranColor));
        }
        return list;
    }

    public View generateView(int color) {
        View tv = new View(this);
        ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
        lp.width = ViewPager.LayoutParams.MATCH_PARENT;
        lp.height = ViewPager.LayoutParams.MATCH_PARENT;
        tv.setBackgroundColor(color);
        tv.setLayoutParams(lp);

        return tv;
    }

    public class DemoPagerAdapter extends PagerAdapter {
        private List<View> views;

        public DemoPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }
}

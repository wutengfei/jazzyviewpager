package com.jfeinstein.jazzyviewpager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;

public class MainActivity extends Activity {

    private JazzyViewPager mJazzy;
    private int[] mImgIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgIds = new int[]{R.drawable.a1, R.drawable.a2, R.drawable.a3,
                R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7,
                R.drawable.a8, R.drawable.a9};
        setupJazziness(TransitionEffect.Tablet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Toggle Fade");
        String[] effects = this.getResources().getStringArray(
                R.array.jazzy_effects);
        for (String effect : effects)
            menu.add(effect);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Toggle Fade")) {
            mJazzy.setFadeEnabled(!mJazzy.getFadeEnabled());
        } else {
            TransitionEffect effect = TransitionEffect.valueOf(item.getTitle()
                    .toString());
            setupJazziness(effect);
        }
        return true;
    }

    private void setupJazziness(TransitionEffect effect) {
        mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
        mJazzy.setTransitionEffect(effect);
        mJazzy.setAdapter(new MainAdapter());
        mJazzy.setPageMargin(30);
//		mJazzy.setOutlineEnabled(true);
    }

    private class MainAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(mImgIds[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            container.addView(imageView);
            mJazzy.setObjectForPosition(imageView, position);
            return imageView;

//            TextView text = new TextView(MainActivity.this);
//            text.setGravity(Gravity.CENTER);
//            text.setTextSize(30);
//            text.setTextColor(Color.WHITE);
//            text.setText("Page " + position);
//            text.setPadding(30, 30, 30, 30);
//            int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
//                    (int) Math.floor(Math.random() * 128) + 64,
//                    (int) Math.floor(Math.random() * 128) + 64);
//            text.setBackgroundColor(bg);
//            container.addView(text, LayoutParams.MATCH_PARENT,
//                    LayoutParams.MATCH_PARENT);
//            mJazzy.setObjectForPosition(text, position);
//            return text;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(mJazzy.findViewFromObject(position));
        }

        @Override
        public int getCount() {
            return mImgIds.length;
            // return 9;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == obj;
            } else {
                return view == obj;
            }
        }
    }
}

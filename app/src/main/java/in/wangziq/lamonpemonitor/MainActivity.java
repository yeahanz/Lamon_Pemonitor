package in.wangziq.lamonpemonitor;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity implements heart_rate.OnFragmentInteractionListener, tracking.OnFragmentInteractionListener, activity.OnFragmentInteractionListener{


    float x1,x2,y1,y2;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TabLayout tabLayout = findViewById(R.id.tablayout);
        TabItem tabheart = findViewById(R.id.tabheart);
        TabItem tabgps = findViewById(R.id.tabgps);
        TabItem tabactivity = findViewById(R.id.tabactivity);
        ViewPager viewPager = findViewById(R.id.pager);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1>x2){
                    Intent i = new Intent(MainActivity.this, Aktifitas.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }





    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

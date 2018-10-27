package edu.cs4730.progject4;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.cs4730.progject4.db.mySQLiteHelper;
import edu.cs4730.progject4.db.mydb;

public class MainActivity extends AppCompatActivity implements  TransFrag.OnFragmentInteractionListener, catFrag.OnFragmentCatInteractionListener{
    String TAG = "MainActivity";


    private long passVal = 0;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new mydb(getApplicationContext()).open();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //mViewPager.setCurrentItem(7);// set to a specific page in the pager.
    }




    @Override
    public void onFragmentInteraction(long i) {
        passVal = i;
        mViewPager.setCurrentItem(2);



    }
    public long getPassVall(){
        return passVal;
    }

    @Override
    public void onFragmentCatInteraction(long i) {
        passVal = i;
        mViewPager.setCurrentItem(3);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TransFrag();
                case 1:
                   return new catFrag();
                case 2:
                    return new addUpdate();
                case 3:
                    return new catAdd();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}

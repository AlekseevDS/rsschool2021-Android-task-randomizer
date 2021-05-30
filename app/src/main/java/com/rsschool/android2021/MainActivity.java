package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.rsschool.android202.FirstFragment;

public class MainActivity extends AppCompatActivity implements FragmentDataListener, FragmentSecDataListener, BackPressListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }
    @Override
    public void openSecondFragment(@org.jetbrains.annotations.Nullable Integer min, @org.jetbrains.annotations.Nullable Integer max) {
        // TODO: implement it
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof FirstFragment) {
            Fragment fragmentReplace = SecondFragment.newInstance(min, max);

            Bundle bundle = new Bundle();
            bundle.putInt(SecondFragment.MIN_VALUE_KEY, min);
            bundle.putInt(SecondFragment.MAX_VALUE_KEY, max);
            fragmentReplace.setArguments(bundle);

            fm.beginTransaction()
                    .replace(R.id.container, fragmentReplace, SecondFragment.TAG)
                    //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    //.addToBackStack(SecondFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onSendDataFromFragment2(int res) {
        openFirstFragment(res);
    }

    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        BackPressListener backPressedListener = null;
        for (Fragment fragment : fm.getFragments()) {
            if (fragment instanceof BackPressListener) {
                backPressedListener = (BackPressListener) fragment;
                break;
            }
        }
        if (backPressedListener != null) {
            backPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}

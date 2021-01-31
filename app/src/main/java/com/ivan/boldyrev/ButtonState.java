package com.ivan.boldyrev;

import android.widget.Button;

import androidx.viewpager2.widget.ViewPager2;

class ButtonState {
    public void changeButtonState(Button button, ViewPager2 pager, int tab){
        ViewAdapter adapter = (ViewAdapter) pager.getAdapter();
        switch (tab){
            case 1:
                Fragment2 fragment2 = (Fragment2) adapter.hashMap.get(tab);
                if (fragment2.postNum == 0)
                    button.setClickable(false);
                else
                    button.setClickable(true);
                break;
            case 2:
                Fragment3 fragment3 = (Fragment3) adapter.hashMap.get(tab);
                if (fragment3.postNum == 0)
                    button.setClickable(false);
                else
                    button.setClickable(true);
                break;
            default:
                Fragment1 fragment1 = (Fragment1) adapter.hashMap.get(tab);
                if (fragment1.postNum == 0)
                    button.setClickable(false);
                else
                    button.setClickable(true);
        }
    }
}

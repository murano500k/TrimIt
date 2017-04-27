package com.trimit.android.utils;

import com.trimit.android.R;

/**
 * Created by artem on 4/27/17.
 */

public class ImageResUtils {
    public static int getStarsResId(String starsString){
        int stars = (int) Double.parseDouble(starsString);
        switch (stars){
            case 0:
                return R.drawable.img_stars_0;
            case 1:
                return R.drawable.img_stars_1;
            case 2:
                return R.drawable.img_stars_2;
            case 3:
                return R.drawable.img_stars_3;
            case 4:
                return R.drawable.img_stars_4;
            case 5:
                return R.drawable.img_stars_5;
            default:
                return R.drawable.img_stars_0;
        }
    }
}

package com.veryworks.android.airbnbsearch;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

import static com.veryworks.android.airbnbsearch.R.id.btnCheckin;

/**
 * Created by pc on 7/27/2017.
 */

public class StringUtil {
    public static void setHtmlText(TextView target, String text){
        target.setAllCaps(false);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            target.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        }else {
            target.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE);
        }
    }
}

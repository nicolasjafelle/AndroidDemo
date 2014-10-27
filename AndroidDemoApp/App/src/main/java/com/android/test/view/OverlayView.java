package com.android.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.test.R;

/**
 * Created by Nicolas Jafelle on 10/27/14.
 */
public class OverlayView extends View {

    public OverlayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public OverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public OverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OverlayView(Context context) {
        super(context);
        init();
    }

    private void init() {

        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });

        float height;
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            height = styledAttributes.getDimension(0, 0);
        }else {
            height = styledAttributes.getDimension(0, 0) + getContext().getResources().getDimension(R.dimen.main_separation);
        }

        this.setMinimumHeight((int)height);
        styledAttributes.recycle();


    }
}

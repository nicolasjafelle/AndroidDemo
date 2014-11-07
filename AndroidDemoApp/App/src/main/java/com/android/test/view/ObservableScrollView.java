package com.android.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Nicolas Jafelle on 11/7/14.
 */
public class ObservableScrollView extends ScrollView {

    public interface ObservableScrollViewListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private ObservableScrollViewListener listener;


    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setObservableScrollViewListener(ObservableScrollViewListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null) {
            this.listener.onScrollChanged(l, t, oldl, oldt);
        }
    }
}

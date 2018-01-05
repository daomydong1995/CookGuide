package com.example.daomy.foodguide.ultil;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


/**
 * Created by WIN7 on 05/22/2015.
 */
public class SelectionEnabledEditText extends AppCompatEditText {
    public SelectionEnabledEditText(Context context) {
        super(context);
    }

    public SelectionEnabledEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectionEnabledEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        if(onSelectionChangeListener != null)
            onSelectionChangeListener.onSelectionChanged(selStart, selEnd);
    }

    public static interface OnSelectionChangeListener{
        public void onSelectionChanged(int selStart, int selEnd);
    }

    private  OnSelectionChangeListener onSelectionChangeListener;

    public void setOnSelectionChangeListener(OnSelectionChangeListener onSelectionChangeListener) {
        this.onSelectionChangeListener = onSelectionChangeListener;
    }
}

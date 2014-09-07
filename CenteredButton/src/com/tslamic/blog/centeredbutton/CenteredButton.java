package com.tslamic.blog.centeredbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class CenteredButton extends Button {

    public CenteredButton(Context context) {
        super(context);
    }

    public CenteredButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenteredButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int wMeasureSpec, int hMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(wMeasureSpec);
        final int halfWidth = widthSize / 2;
        final int newMeasureSpec = MeasureSpec.makeMeasureSpec(halfWidth, MeasureSpec.EXACTLY);

        super.onMeasure(newMeasureSpec, hMeasureSpec);
    }

}
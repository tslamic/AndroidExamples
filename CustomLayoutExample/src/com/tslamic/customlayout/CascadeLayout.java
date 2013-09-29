package com.tslamic.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CascadeLayout extends ViewGroup {

    private int mHorizontalSpacing;
    private int mVerticalSpacing;

    public CascadeLayout(Context context) {
        super(context);
        init(null);
    }

    public CascadeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CascadeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mHorizontalSpacing = getDefaultHorizontalSpacing();
        mVerticalSpacing = getDefaultVerticalSpacing();

        if (null != attrs) {
            final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CascadeLayout);
            try {
                mHorizontalSpacing = array.getDimensionPixelSize(R.styleable.CascadeLayout_cascade_horizontal_spacing,
                                                                 mHorizontalSpacing);
                mVerticalSpacing = array.getDimensionPixelSize(R.styleable.CascadeLayout_cascade_vertical_spacing,
                                                               mVerticalSpacing);
            } finally {
                array.recycle();
            }
        }
    }

    protected int getDefaultHorizontalSpacing() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.cascade_horizontal_spacing);
    }

    protected int getDefaultVerticalSpacing() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.cascade_vertical_spacing);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = getPaddingTop();

        for (int i = 0, size = getChildCount(); i < size; i++) {
            final View child = getChildAt(i);
            if (View.GONE != child.getVisibility()) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);

                final LayoutParams params = (LayoutParams) child.getLayoutParams();
                width = getPaddingLeft() + mHorizontalSpacing * i;
                params.x = width;
                params.y = height;

                width += child.getMeasuredWidth();
                height += mVerticalSpacing;
            }
        }

        width += getPaddingRight();
        height += getPaddingBottom();

        final View last = getChildAt(getChildCount() - 1);
        if (null != last) {
            height += last.getMeasuredHeight();
        }

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0, size = getChildCount(); i < size; i++) {
            final View child = getChildAt(i);
            if (View.GONE != child.getVisibility()) {
                final LayoutParams params = (LayoutParams) child.getLayoutParams();
                child.layout(params.x, params.y, params.x + child.getMeasuredWidth(),
                             params.y + child.getMeasuredHeight());
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CascadeLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return (p instanceof LayoutParams);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        int x;
        int y;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

    }

}

package com.tslamic.dragdrop;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnLongClickListener, View.OnDragListener {

    private static final int COLOR_DEFAULT = Color.BLACK;
    private static final int COLOR_VALID_DROPS = Color.BLUE;
    private static final int COLOR_ACTIVE_DROP = Color.RED;

    private static final String TEXT_1_TAG = "text1";
    private static final String TEXT_2_TAG = "text2";
    private static final String TEXT_3_TAG = "text3";
    private static final String TEXT_4_TAG = "text4";
    private static final String TEXT_5_TAG = "text5";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        init(R.id.text1, TEXT_1_TAG);
        init(R.id.text2, TEXT_2_TAG);
        init(R.id.text3, TEXT_3_TAG);
        init(R.id.text4, TEXT_4_TAG);
        init(R.id.text5, TEXT_5_TAG);
    }

    @Override
    public boolean onLongClick(View view) {
        final TextView textView = (TextView) view;
        final ClipData data = ClipData.newPlainText(textView.getTag().toString(), textView.getText().toString());
        final TextViewDragShadowBuilder shadow = new TextViewDragShadowBuilder(textView);

        textView.startDrag(data, shadow, null, 0);
        return true;
    }

    @Override
    public boolean onDrag(View view, DragEvent event) {
        final TextView textView = (TextView) view;
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    setTextColor(textView, COLOR_VALID_DROPS);
                    return true;
                }
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                setTextColor(textView, COLOR_ACTIVE_DROP);
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                setTextColor(textView, COLOR_VALID_DROPS);
                return true;

            case DragEvent.ACTION_DROP:
                ClipData.Item item = event.getClipData().getItemAt(0);
                final CharSequence dragData = item.getText();

                textView.setText(dragData);
                setTextColor(textView, COLOR_DEFAULT);

                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                setTextColor(textView, COLOR_DEFAULT);
                return true;

            default:
                throw new IllegalStateException("unrecognized event action: " + event.getAction());
        }
    }

    private TextView init(final int resource, final String tag) {
        final TextView textView = (TextView) findViewById(resource);

        textView.setTag(tag);
        textView.setOnDragListener(this);
        textView.setOnLongClickListener(this);

        return textView;
    }

    private static class TextViewDragShadowBuilder extends View.DragShadowBuilder {
        private final Drawable mShadow;

        private TextViewDragShadowBuilder(View view) {
            super(view);
            mShadow = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            final int width = getView().getWidth();
            final int height = getView().getHeight();

            mShadow.setBounds(0, 0, width, height);
            shadowSize.set(width, height);
            shadowTouchPoint.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            mShadow.draw(canvas);
        }
    }

    private static void setTextColor(final TextView view, final int color) {
        view.setTextColor(color);
        view.invalidate();
    }

}
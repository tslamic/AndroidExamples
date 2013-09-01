package com.tslamic.unitcircle;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;


/** Displays the Unit circle. See http://en.wikipedia.org/wiki/Unit_circle for more information. */
public class UnitCircleView extends View implements UnitCircle {

    // Used when rounding decimal values.
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.####");

    // Padding and Scaling factors
    private static final float PADDING_RADIUS = 18f;
    private static final float PADDING_RECT = 15f;
    private static final float SCALE_SELECTOR = .06f;
    private static final float SCALE_TEXT_SIZE = .12f;

    // Sizes for random components.
    private static final float TEXT_SIZE = 18f;
    private static final int ANGLE_RECT_EDGE = 30;
    private static final String ANGLE_CHAR = "\u2220";

    // Paint values
    private static final Paint PAINT_UNIT_CIRCLE = Util.getUnitCirclePaint();
    private static final Paint PAINT_SELECTOR = Util.getSelectorPaint();
    private static final Paint PAINT_GRID = Util.getGridPaint();
    private static final Paint PAINT_SINE = Util.getSinePaint();
    private static final Paint PAINT_COSINE = Util.getCosinePaint();
    private static final Paint PAINT_HYPOTENUSE = Util.getHypotenusePaint();
    private static final Paint PAINT_TEXT = Util.getTextPaint(TEXT_SIZE);

    /** x coordinate of the unit circle origin. */
    private float mCircleOriginX;
    /** y coordinate of the unit circle origin. */
    private float mCircleOriginY;
    /** Unit circle radius. */
    private float mCircleRadius;
    /** Rectangle around origin. Used for drawing an arc displaying the current angle. */
    private RectF mCircleOriginRect;
    /** x coordinate of the selector button. */
    private float mSelectorOriginX;
    /** y coordinate of the selector button. */
    private float mSelectorOriginY;
    /** Selector radius. */
    private float mSelectorRadius;
    /** Rectangle around selector button, monitoring for motion events. */
    private Rect mSelectorRect;
    /** Selector gesture detector. */
    private GestureDetector mGestureDetector;
    /** Determines if sine function should be displayed. */
    private boolean mShowSine;
    /** Determines if cosine function should be displayed. */
    private boolean mShowCosine;
    /** Units to be used when displaying the angle. */
    private int mAngleUnit;
    /** The current angle. */
    private double mCurrentAngle;
    /** Current sine value. */
    private double mCurrentSine;
    /** Current cosine value. */
    private double mCurrentCosine;
    /** Size used when drawing text. */
    private float mTextSize;

    public UnitCircleView(Context context) {
        super(context);
        init();
    }

    public UnitCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UnitCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), new SelectorGestureDetector());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Determine the origin and radius of the unit circle.
        mCircleOriginX = getWidth() >>> 1;
        mCircleOriginY = getHeight() >>> 1;
        mCircleRadius = Math.min(mCircleOriginX, mCircleOriginY) - PADDING_RADIUS;

        // Determine the origin and radius of the selector - the "button" used to traverse the unit circle.
        mSelectorRadius = mCircleRadius * SCALE_SELECTOR;
        mSelectorOriginX = mCircleOriginX + mCircleRadius;
        mSelectorOriginY = mCircleOriginY;

        // Create a rect for displaying the arc which displays the current angle.
        final Rect originRect = getBorderingRect(mCircleOriginX, mCircleOriginY, ANGLE_RECT_EDGE);
        mCircleOriginRect = new RectF(originRect);

        // Create a rect around selector that detects motion events.
        mSelectorRect = getBorderingRect(mSelectorOriginX, mSelectorOriginY, mSelectorRadius);

        // Text size
        mTextSize = mCircleRadius * SCALE_TEXT_SIZE;
        PAINT_TEXT.setTextSize(mTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw basic x and y grid lines.
        canvas.drawLine(mCircleOriginX, 0, mCircleOriginX, getHeight(), PAINT_GRID);
        canvas.drawLine(0, mCircleOriginY, getWidth(), mCircleOriginY, PAINT_GRID);

        // Draw hypotenuse.
        canvas.drawLine(mCircleOriginX, mCircleOriginY, mSelectorOriginX, mSelectorOriginY, PAINT_HYPOTENUSE);

        // Calculate degrees from unit circle radians.
        final int degrees = getDegreesFrom(mCurrentAngle);

        // Draw angle.
        canvas.drawArc(mCircleOriginRect, 0, -degrees, true, PAINT_GRID);

        // Display current angle value as text.
        canvas.drawText(angleAsString(degrees), 5, mTextSize, PAINT_TEXT);

        if (mShowSine) {
            canvas.drawLine(mSelectorOriginX, mSelectorOriginY, mSelectorOriginX, mCircleOriginY, PAINT_SINE);
            canvas.drawText(asString("sin", -mCurrentSine), 5, mTextSize * 2, PAINT_TEXT);
        }

        if (mShowCosine) {
            canvas.drawLine(mCircleOriginX, mCircleOriginY, mSelectorOriginX, mCircleOriginY, PAINT_COSINE);
            final float position = mShowSine ? mTextSize * 3 : mTextSize * 2;
            canvas.drawText(asString("cos", mCurrentCosine), 5, position, PAINT_TEXT);
        }

        // Draw unit circle.
        canvas.drawCircle(mCircleOriginX, mCircleOriginY, mCircleRadius, PAINT_UNIT_CIRCLE);
        canvas.drawCircle(mSelectorOriginX, mSelectorOriginY, mSelectorRadius, PAINT_SELECTOR);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final boolean isDown = mGestureDetector.onTouchEvent(event);

        if (MotionEvent.ACTION_UP == event.getAction()) { PAINT_SELECTOR.setColor(Util.COLOR_GRANDMA_PILLOW); }
        if (isDown) { PAINT_SELECTOR.setColor(Util.COLOR_CHEERY_PINK); }

        final Point point = project(event.getX(), event.getY());
        mSelectorOriginX = point.x;
        mSelectorOriginY = point.y;
        updateSelectorRect();

        invalidate();
        return isDown;
    }

    private class SelectorGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            final int x = (int) e.getX();
            final int y = (int) e.getY();
            return mSelectorRect.contains(x, y);
        }
    }

    private Point project(float x, float y) {
        final float relativeX = x - mCircleOriginX;
        final float relativeY = y - mCircleOriginY;

        mCurrentAngle = Math.atan2(relativeY, relativeX);
        mCurrentCosine = Math.cos(mCurrentAngle);
        mCurrentSine = Math.sin(mCurrentAngle);

        final int newX = (int) (mCircleOriginX + (mCircleRadius * mCurrentCosine));
        final int newY = (int) (mCircleOriginY + (mCircleRadius * mCurrentSine));

        return new Point(newX, newY);
    }

    private void updateSelectorRect() {
        final float edge = mSelectorRadius + PADDING_RECT;

        final int left = (int) (mSelectorOriginX - edge);
        final int top = (int) (mSelectorOriginY - edge);

        mSelectorRect.offsetTo(left, top);
    }

    private static Rect getBorderingRect(float middleX, float middleY, float radius) {
        final float edge = radius + PADDING_RECT;

        final int left = Math.round(middleX - edge);
        final int top = Math.round(middleY - edge);
        final int right = Math.round(middleX + edge);
        final int bottom = Math.round(middleY + edge);

        return new Rect(left, top, right, bottom);
    }

    @Override
    public void showSine(boolean show) {
        mShowSine = show;
    }

    @Override
    public void showCosine(boolean show) {
        mShowCosine = show;
    }

    @Override
    public void setAngleUnit(int unit) {
        if (!(Util.ANGLE_UNIT_DEGREES == unit || Util.ANGLE_UNIT_RADIANS == unit)) {
            throw new IllegalArgumentException("no such unit: " + unit);
        }
        mAngleUnit = unit;
    }

    @Override
    public PointF getCurrentPoint() {
        return new PointF(mSelectorOriginX, mSelectorOriginY);
    }

    @Override
    public void setCurrentPoint(final PointF point) {
        mSelectorOriginX = point.x;
        mSelectorOriginY = point.y;
        invalidate();
    }

    private String angleAsString(int angle) {
        if (Util.ANGLE_UNIT_DEGREES == mAngleUnit) {
            return asString(ANGLE_CHAR, angle);
        } else {
            return asString(ANGLE_CHAR, Math.toRadians(angle));
        }
    }

    private static String asString(final String key, final int value) {
        StringBuilder builder = new StringBuilder();

        builder.append(key);
        builder.append(" = ");
        builder.append(value);

        return builder.toString();
    }

    private static String asString(final String key, final double value) {
        StringBuilder builder = new StringBuilder();

        builder.append(key);
        builder.append(" = ");
        builder.append(DECIMAL_FORMAT.format(value));

        return builder.toString();
    }

    // Argument is assumed to be the result returned by Math.atan2 method.
    // Because the xy grid is flipped over x axis, we must negate the degree values to obtain correct values.
    private static int getDegreesFrom(final double radians) {
        int degrees = (int) Math.toDegrees(radians);
        if (degrees > 0) { degrees -= 360; }
        return -degrees;
    }

}
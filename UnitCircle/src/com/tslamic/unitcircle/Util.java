package com.tslamic.unitcircle;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

final class Util {

    // Keys used for retrieving user preferences from SharedPreferences and Bundle.
    public static final String KEY_SHOW_SINE = "showSin";
    public static final String KEY_SHOW_COSINE = "showCos";
    public static final String KEY_ANGLE_UNIT = "angle";
    public static final String KEY_CURRENT_POINT_X = "pointX";
    public static final String KEY_CURRENT_POINT_Y = "pointY";

    // Available angle units.
    public static final int ANGLE_UNIT_RADIANS = 0;
    public static final int ANGLE_UNIT_DEGREES = 1;

    // Color template; stolen from http://www.colourlovers.com/palette/1930/cheer_up_emo_kid
    public static final int COLOR_MIGHTY_SLATE = Color.rgb(85, 98, 112);
    public static final int COLOR_PACIFICA = Color.rgb(78, 205, 196);
    public static final int COLOR_APPLE_CHIC = Color.rgb(199, 244, 100);
    public static final int COLOR_CHEERY_PINK = Color.rgb(255, 107, 107);
    public static final int COLOR_GRANDMA_PILLOW = Color.rgb(196, 77, 88);

    public static Paint getGridPaint() {
        return getPaint(Color.LTGRAY, Paint.Style.STROKE, .5f);
    }

    public static Paint getUnitCirclePaint() {
        return getPaint(COLOR_MIGHTY_SLATE, Paint.Style.STROKE, 3.5f);
    }

    public static Paint getSelectorPaint() {
        return getPaint(COLOR_GRANDMA_PILLOW, Paint.Style.FILL, .125f);
    }

    public static Paint getSinePaint() {
        return getPaint(COLOR_APPLE_CHIC, Paint.Style.FILL, 3f);
    }

    public static Paint getCosinePaint() {
        return getPaint(COLOR_PACIFICA, Paint.Style.FILL, 3f);
    }

    public static Paint getHypotenusePaint() {
        final Paint paint = getPaint(Color.LTGRAY, Paint.Style.STROKE, 3f);
        paint.setPathEffect(new DashPathEffect(new float[]{5, 10}, 0));
        return paint;
    }

    public static Paint getTextPaint(final float size) {
        final Paint paint = getPaint(COLOR_MIGHTY_SLATE, Paint.Style.FILL, .75f);
        paint.setTextSize(size);
        return paint;
    }

    private static Paint getPaint(int color, Paint.Style style, float strokeWidth) {
        final Paint paint = new Paint();

        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);

        return paint;
    }

    private Util() {}
}

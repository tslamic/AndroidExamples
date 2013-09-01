package com.tslamic.unitcircle;

import android.graphics.PointF;

public interface UnitCircle {

    /** Determines if sine function should be displayed. */
    void showSine(boolean show);

    /** Determines if cosine function should be displayed. */
    void showCosine(boolean show);

    /** Sets the unit to display the current angle in - either radians or degrees. */
    void setAngleUnit(int unit);

    /** Returns the current point from the unit circle. */
    PointF getCurrentPoint();

    /** Sets the current point in the unit circle. */
    void setCurrentPoint(final PointF point);

}

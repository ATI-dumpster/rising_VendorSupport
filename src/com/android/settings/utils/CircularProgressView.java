package com.android.settings.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.android.settings.R;

public class CircularProgressView extends View {
    private final Paint backgroundPaint;
    private final Paint progressPaint;
    private final RectF circleRect;
    private float progress = 0;

    public CircularProgressView(Context context) {
        this(context, null);
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        circleRect = new RectF();
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // Retrieve theme colors
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.colorAccent, typedValue, true);
        int progressColor = typedValue.data;

        getContext().getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true);
        int backgroundColor = typedValue.data;

        float strokeWidth = getResources().getDimensionPixelSize(R.dimen.circular_progress_stroke_width);

        // Retrieve custom attributes
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CircularProgressView);
            progressColor = ta.getColor(R.styleable.CircularProgressView_progressColor, progressColor);
            backgroundColor = ta.getColor(R.styleable.CircularProgressView_backgroundColor, backgroundColor);
            strokeWidth = ta.getDimension(R.styleable.CircularProgressView_strokeWidth, strokeWidth);
            ta.recycle();
        }

        // Set up paints
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float stroke = Math.max(progressPaint.getStrokeWidth(), backgroundPaint.getStrokeWidth());
        circleRect.set(stroke, stroke, w - stroke, h - stroke);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw background and progress arcs
        canvas.drawArc(circleRect, 0, 360, false, backgroundPaint);
        float sweepAngle = 360 * (progress / 100);
        canvas.drawArc(circleRect, -90, sweepAngle, false, progressPaint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public float getProgress() {
        return progress;
    }
}

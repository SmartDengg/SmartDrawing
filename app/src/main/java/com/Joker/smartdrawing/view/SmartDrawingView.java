package com.Joker.smartdrawing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.Joker.smartdrawing.R;

public class SmartDrawingView extends View {

    private static String TAG = SmartDrawingView.class.getSimpleName();

    private static final int DEFAULT_FINGER_COLOR = Color.RED;
    private static final int DEFAULT_STROKE_WIDTH = 10;

    private Path path = new Path();
    private Paint paint;

    private int defaultColor;
    private int defaultStrokeWidth;

    private Bitmap smartBitmap;
    private Canvas smartCanvas;

    public SmartDrawingView(Context context) {
        this(context, null);
    }

    public SmartDrawingView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SmartDrawingViewStyle);
    }

    public SmartDrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!this.isInEditMode()) {
            SmartDrawingView.this.initTypeArray(context, attrs, defStyleAttr, R.style.DefaultSmartViewStyle);
            SmartDrawingView.this.initPaint();
        }
    }

    private void initTypeArray(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartDrawingView, defStyleAttr, defStyleRes);

        if (typedArray == null) return;

        try {
            defaultColor = typedArray.getColor(R.styleable.SmartDrawingView_smart_finger_color, DEFAULT_FINGER_COLOR);
            defaultStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.SmartDrawingView_smart_stroke_width, DEFAULT_STROKE_WIDTH);
        } finally {
            typedArray.recycle();
        }
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(defaultColor);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(defaultStrokeWidth);
        paint.setStrokeMiter(180.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        SmartDrawingView.this.setFocusable(true);
        SmartDrawingView.this.setFocusableInTouchMode(true);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

    /*http://stackoverflow.com/questions/3406910/efficient-2d-drawing-in-android/3408641#3408641*/
        if (width != 0 && height != 0) {
            smartBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            smartCanvas = new Canvas(smartBitmap);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;

            default:
                return false;
        }

        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        SmartDrawingView.this.drawPath(smartCanvas, path, paint);
        canvas.drawBitmap(smartBitmap, 0, 0, null);
    }

    /**
     * 简直不能更感谢
     * Thanks a lot for this answer
     * http://stackoverflow.com/questions/16090607/blurry-offset-paths-when-canvas-is-scaled-under-hardware-acceleration/16110530#16110530
     *
     * @param canvas
     * @param path
     * @param paint
     */
    void drawPath(Canvas canvas, Path path, final Paint paint) {
        canvas.save();

        // get the current matrix
        Matrix mat = canvas.getMatrix();

        // reverse the effects of the current matrix
        Matrix inv = new Matrix();
        mat.invert(inv);
        canvas.concat(inv);

        // transform the path
        path.transform(mat);

        // get the scale for transforming the Paint
        float[] pts = { 0, 0, 1, 0 }; // two points 1 unit away from each other
        mat.mapPoints(pts);
        float scale = (float) Math.sqrt(Math.pow(pts[0] - pts[2], 2) + Math.pow(pts[1] - pts[3], 2));

        // copy the existing Paint
        Paint pen2 = new Paint();
        pen2.set(paint);

        // scale the Paint
        pen2.setStrokeMiter(paint.getStrokeMiter() * scale);
        pen2.setStrokeWidth(paint.getStrokeWidth() * scale);

        // draw the path
        canvas.drawPath(path, pen2);

        canvas.restore();
    }
}

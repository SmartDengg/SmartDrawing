package com.Joker.smartdrawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * Created by SmartDengg on 2016/1/16.
 */
public class DrawingViewInflater {

  private Context context;
  private SmartDrawableCardView rootView;
  private WindowManager windowManager;
  private WindowManager.LayoutParams layoutParams;

  private SmartDrawableCardView.Callback listener = new SmartDrawableCardView.Callback() {
    @Override public void onCancel() {
      rootView.setCallback(null);
      if (rootView.getParent() != null) windowManager.removeViewImmediate(rootView);
    }
  };

  private DrawingViewInflater(Context context) {
    this.context = context;
  }

  public static DrawingViewInflater createdDrawingInflater(Context context) {
    return new DrawingViewInflater(context);
  }

  public void setDrawable(@NonNull final Bitmap bitmap) {

    Bitmap cacheBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(cacheBitmap);
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setDither(true);
    paint.setFlags(Paint.FILTER_BITMAP_FLAG);
    canvas.drawBitmap(bitmap, 0, 0, paint);

    if (rootView == null) DrawingViewInflater.this.installRoot();
    if (windowManager == null) DrawingViewInflater.this.installWindowManager();

    windowManager.addView(rootView, layoutParams);

    /*may cause memory leak*/
    rootView.post(new Runnable() {
      @Override public void run() {
        rootView.setDrawingDrawable(bitmap);
      }
    });
  }

  private void installRoot() {
    rootView = new SmartDrawableCardView(context);
    rootView.setCallback(listener);
  }

  private void installWindowManager() {

    windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    layoutParams = new WindowManager.LayoutParams();
    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
    layoutParams.format = PixelFormat.RGB_565;
    layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
    layoutParams.windowAnimations = android.R.style.Animation_Toast;
    layoutParams.gravity = Gravity.TOP;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
    } else {
      layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
    }
  }
}

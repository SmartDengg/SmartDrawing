package com.Joker.smartdrawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.Joker.smartdrawing.R;
import com.Joker.smartdrawing.Utils;

/**
 * Created by Joker on 2016/1/16.
 */
public class FingerViewInflater {

  public static FingerViewInflater createdFingerInflater() {
    return new FingerViewInflater();
  }

  public void setupFingerView(final Context context) {

    Bitmap source = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    Bitmap bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setDither(true);
    paint.setFlags(Paint.FILTER_BITMAP_FLAG);
    Matrix matrix = new Matrix();
    matrix.postScale(-1.0f, 1.0f);
    canvas.drawBitmap(source, matrix, paint);

    final ImageView fingerView = new ImageView(context);
    fingerView.setImageBitmap(source);

    ViewGroup rootView =
        (ViewGroup) ((AppCompatActivity) context).getWindow().getDecorView().findViewById(android.R.id.content);

    ViewGroup.LayoutParams params =
        new ViewGroup.LayoutParams(Utils.getActionBarSize(context), Utils.getActionBarSize(context));
    rootView.addView(fingerView, params);

    fingerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override public boolean onPreDraw() {
        fingerView.getViewTreeObserver().removeOnPreDrawListener(this);
        fingerView.setTranslationX(Utils.getScreenWidth(context) - Utils.getActionBarSize(context));
        fingerView.setTranslationY(Utils.getActionBarSize(context) / 2);
        return true;
      }
    });
  }
}

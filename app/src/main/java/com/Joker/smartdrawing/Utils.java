package com.Joker.smartdrawing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageButton;
import java.lang.reflect.Field;

/**
 * Created by Joker on 2016/1/8.
 */
public class Utils {

  private static final String TAG = Utils.class.getSimpleName();
  private static int actionBarSize;
  private static int screenWidth;

  /**
   * Toolbar的 NavButton
   *
   * @param toolbar
   * @return
   */
  public static ImageButton getNavButtonView(@NonNull Toolbar toolbar) {
    try {
      Class<?> toolbarClass = Toolbar.class;
      Field navButtonField = toolbarClass.getDeclaredField("mNavButtonView");
      navButtonField.setAccessible(true);
      return (ImageButton) navButtonField.get(toolbar);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Toolbar的 NavButton 的高度
   *
   * @param context
   * @return
   */
  public static int getActionBarSize(Context context) {

    if (actionBarSize == 0) {
      TypedArray actionbarSizeTypedArray = null;
      try {
        actionbarSizeTypedArray = context.obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        actionBarSize = (int) actionbarSizeTypedArray.getDimension(0, 0);
      } finally {
        actionbarSizeTypedArray.recycle();
      }
    }

    return actionBarSize;
  }

  /**
   * 获取屏幕宽度
   */
  public static int getScreenWidth(Context context) {

    if (screenWidth == 0) {
      WindowManager wm =
          (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
      Display display = wm.getDefaultDisplay();
      Point size = new Point();
      display.getSize(size);
      screenWidth = size.x;
    }

    return screenWidth;
  }
}

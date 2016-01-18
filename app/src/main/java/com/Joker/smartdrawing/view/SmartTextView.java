package com.Joker.smartdrawing.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Joker on 2016/1/3.
 */
public class SmartTextView extends TextView {

  public SmartTextView(Context context) {
    this(context, null);
  }

  public SmartTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SmartTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    if (!isInEditMode()) {
      SmartTextView.this.init(context);
    }
  }

  private void init(Context context) {

    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "Lobster-Regular.ttf");
    this.setTypeface(typeface);
  }
}

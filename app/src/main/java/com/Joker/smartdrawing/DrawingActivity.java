package com.Joker.smartdrawing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.Bind;
import com.Joker.smartdrawing.view.DrawingViewInflater;
import com.Joker.smartdrawing.view.FingerViewInflater;
import com.Joker.smartdrawing.view.SmartDrawingView;

public class DrawingActivity extends BaseActivity implements View.OnClickListener {

  private static final String TAG = DrawingActivity.class.getSimpleName();

  @Nullable @Bind(R.id.drawing_layout_smart_view) protected SmartDrawingView smartDrawingView;

  public static void navigateToDrawing(AppCompatActivity startingActivity) {
    Intent intent = new Intent(startingActivity, DrawingActivity.class);
    startingActivity.startActivity(intent);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    MenuItem menuItem = menu.findItem(R.id.action_inbox);
    menuItem.setActionView(R.layout.action_layout);

    FingerViewInflater.createdFingerInflater().setupFingerView(DrawingActivity.this);

    AppCompatImageButton actionView = (AppCompatImageButton) menuItem.getActionView();

    actionView.setOnClickListener(DrawingActivity.this);
    DrawingActivity.this.bindActionDrawable(actionView, R.drawable.ic_touch_icon2);
    DrawingActivity.this.polishDrawable(actionView.getDrawable(), android.R.color.white);

    return true;
  }

  @Override protected int getLayoutId() {
    return R.layout.drawing_layout;
  }

  @Override protected void setupToolBar() {
    DrawingActivity.this.setNavigationIcon(toolbar, R.drawable.ic_arrow_back);
    DrawingActivity.this.polishDrawable(toolbar.getNavigationIcon(), android.R.color.white);
  }

  @Override protected void exit() {

    DrawingActivity.this.finish();
    overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
  }

  @Override public void onClick(View v) {

    if (smartDrawingView.isDrawingCacheEnabled()) {
      smartDrawingView.destroyDrawingCache();
    }

    smartDrawingView.setDrawingCacheEnabled(true);
    Bitmap drawingCache = smartDrawingView.getDrawingCache();

    DrawingViewInflater.createdDrawingInflater(DrawingActivity.this).setDrawable(drawingCache);
  }
}

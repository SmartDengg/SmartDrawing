package com.Joker.smartdrawing;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

  @Nullable @Bind(R.id.toolbar_layout_root) protected Toolbar toolbar;
  @Nullable @Bind(R.id.toolbar_layout_title_tv) protected TextView titleTv;

  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    getWindow().setBackgroundDrawable(null);
    ButterKnife.bind(BaseActivity.this);
    BaseActivity.this.bindView();
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
  }

  private void bindView() {
    setSupportActionBar(toolbar);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) toolbar.setElevation(0.0f);
    BaseActivity.this.setupToolBar();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        BaseActivity.this.exit();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      BaseActivity.this.exit();
    }
    return false;
  }

  protected void bindActionDrawable(@NonNull AppCompatImageButton actionView, @DrawableRes int drawable) {
    actionView.setImageDrawable(SomeCompat.getImageDrawable(BaseActivity.this, drawable));
  }

  protected void polishDrawable(@NonNull Drawable drawable, @ColorRes int color) {
    SomeCompat.polishDrawable(BaseActivity.this, drawable, color);
  }

  protected void setNavigationIcon(@NonNull Toolbar toolbar, @DrawableRes int drawableId) {
    SomeCompat.setNavigationIcon(toolbar, BaseActivity.this, drawableId);
  }

  @CallSuper @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(BaseActivity.this);
  }

  protected abstract int getLayoutId();

  protected abstract void setupToolBar();

  protected abstract void exit();
}

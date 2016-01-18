package com.Joker.smartdrawing;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  @Nullable @OnClick(R.id.main_layout_btn) void onGoClick() {
    DrawingActivity.navigateToDrawing(MainActivity.this);
    overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    MenuItem menuItem = menu.findItem(R.id.action_inbox);
    menuItem.setActionView(R.layout.action_layout);

    AppCompatImageButton actionView = (AppCompatImageButton) menuItem.getActionView();

    MainActivity.this.bindActionDrawable(actionView, R.drawable.ic_github_icon2);
    MainActivity.this.polishDrawable(actionView.getDrawable(), android.R.color.white);

    return true;
  }

  @Override protected int getLayoutId() {
    return R.layout.main_layout;
  }

  @Override protected void setupToolBar() {

    toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));

    final ImageButton navButtonView = Utils.getNavButtonView(toolbar);
    if (navButtonView != null) {
      toolbar.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
        @Override public boolean onPreDraw() {

          toolbar.getViewTreeObserver().removeOnPreDrawListener(this);

          Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleTv.getLayoutParams();
          layoutParams.leftMargin = navButtonView.getWidth();

          toolbar.setNavigationIcon(null);
          return true;
        }
      });
    }
  }

  @Override protected void exit() {
    MainActivity.this.finish();
  }
}

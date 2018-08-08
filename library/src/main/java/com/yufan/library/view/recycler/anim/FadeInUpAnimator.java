package com.yufan.library.view.recycler.anim;


import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;

public class FadeInUpAnimator extends BaseItemAnimator {

  public FadeInUpAnimator() {
  }

  public FadeInUpAnimator(Interpolator interpolator) {
    mInterpolator = interpolator;
  }

  @Override
  protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
    ViewCompat.animate(holder.itemView)
        .translationY(holder.itemView.getHeight() * 1.7f)
        .alpha(0)
         .scaleX(0)
         .scaleY(0)
        .setDuration(getRemoveDuration())
        .setInterpolator(mInterpolator)
        .setListener(new DefaultRemoveVpaListener(holder))
        .setStartDelay(getRemoveDelay(holder))
        .start();
  }

  @Override
  protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
    ViewCompat.setTranslationY(holder.itemView, holder.itemView.getHeight() * 1.7f);
    ViewCompat.setAlpha(holder.itemView, 0.5f);
    ViewCompat.setScaleX(holder.itemView, 0f);
    ViewCompat.setScaleY(holder.itemView, 0f);
  }

  @Override
  protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
    ViewCompat.animate(holder.itemView)
        .translationY(0)
        .alpha(1)
        .scaleX(1)
        .scaleY(1)
        .setDuration(getAddDuration())
        .setInterpolator(mInterpolator)
        .setListener(new DefaultAddVpaListener(holder))
        .setStartDelay(getAddDelay(holder))
        .start();
  }
}

package com.example.movieapp.common.binding;

import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void setViewVisibility(View view, Boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("roundedCorner")
    public static void setViewVisibility(View view, Float roundedCorner) {
        view.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(
                        0,
                        0,
                        view.getWidth(),
                        view.getHeight(),
                        roundedCorner
                );
            }
        });
        view.setClipToOutline(true);
    }

    @BindingAdapter("imageUrl")
    public static void setImage(ImageView view, String imageUrl) {
        Picasso.get().cancelRequest(view);
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .into(view);
    }

    @BindingAdapter(value = {"drawable", "imageTint"})
    public static void setImageTint(ImageView view, Drawable drawable, int imageTint) {
        int color = view.getResources().getColor(imageTint);
        ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);
        drawable.setColorFilter(colorFilter);
        view.setImageDrawable(drawable);
    }

}

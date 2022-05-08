package com.example.movieapp.common.customview;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EndDividerItemDecorator extends RecyclerView.ItemDecoration {

    Integer offset;

    public EndDividerItemDecorator(Integer offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.right = offset;
    }
}

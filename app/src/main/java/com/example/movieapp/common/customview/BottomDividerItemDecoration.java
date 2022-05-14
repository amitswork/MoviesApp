package com.example.movieapp.common.customview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BottomDividerItemDecoration extends EndDividerItemDecorator {

    public BottomDividerItemDecoration(Integer offset) {
        super(offset);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = offset;
    }
}

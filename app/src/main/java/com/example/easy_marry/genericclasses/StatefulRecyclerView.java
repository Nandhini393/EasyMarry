package com.example.easy_marry.genericclasses;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by android2 on 24/1/17.
 */
public class StatefulRecyclerView extends RecyclerView {

    private static final String SAVED_SUPER_STATE = "super-state";
    private static final String SAVED_LAYOUT_MANAGER = "layout-manager-state";

    private Parcelable mLayoutManagerSavedState;

    public StatefulRecyclerView(Context context) {
        super(context);
    }

    public StatefulRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatefulRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SAVED_SUPER_STATE, super.onSaveInstanceState());
        bundle.putParcelable(SAVED_LAYOUT_MANAGER, this.getLayoutManager().onSaveInstanceState());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mLayoutManagerSavedState = bundle.getParcelable(SAVED_LAYOUT_MANAGER);
            state = bundle.getParcelable(SAVED_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * Restores scroll position after configuration change.
     * <p>
     * <b>NOTE:</b> Must be called after adapter has been set.
     */
    private void restorePosition() {
        if (mLayoutManagerSavedState != null) {
            this.getLayoutManager().onRestoreInstanceState(mLayoutManagerSavedState);
            mLayoutManagerSavedState = null;
        }
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        restorePosition();
    }
}
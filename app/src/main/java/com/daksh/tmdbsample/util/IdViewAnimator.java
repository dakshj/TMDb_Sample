package com.daksh.tmdbsample.util;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewAnimator;

/**
 * ViewAnimator helper which allows setting the selected child by ID rather than index.
 */
public class IdViewAnimator extends ViewAnimator {

    public IdViewAnimator(Context context) {
        super(context);
    }

    public IdViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setInAnimation(getContext(), android.R.anim.fade_in);
        setOutAnimation(getContext(), android.R.anim.fade_out);
    }

    /**
     * Get the view ID of the currently displayed child.
     */
    @IdRes
    public int getDisplayedChildId() {
        return getChildAt(getDisplayedChild()).getId();
    }

    /**
     * Set the currently displayed child by its ID.
     *
     * @param id ID of child.
     * @throws IllegalArgumentException If child with specified ID does not exist.
     */
    public void setDisplayedChildId(@IdRes int id) {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getId() == id) {
                if (i != getDisplayedChild()) {
                    setDisplayedChild(i);
                }
                return;
            }
        }
        throw new IllegalArgumentException("View with ID " + id + " not found.");
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.mDisplayedChild = getDisplayedChild();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setDisplayedChild(ss.mDisplayedChild);
    }

    static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int mDisplayedChild;

        public SavedState(Parcel source) {
            super(source);
            mDisplayedChild = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mDisplayedChild);
        }
    }
}

/*
 * Copyright 2017 Mike Penz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mikepenz.iconics.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.internal.CheckableIconBundle;
import com.mikepenz.iconics.internal.IconicsView;
import com.mikepenz.iconics.internal.IconicsViewsAttrsReader;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * @author pa.gulko zTrap (06.07.2017)
 */
public class IconicsCompoundButton extends CompoundButton implements IconicsView {
    private final CheckableIconBundle mIconsBundle = new CheckableIconBundle();

    public IconicsCompoundButton(Context context) {
        super(context);
    }

    public IconicsCompoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconicsCompoundButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            initialize(context, attrs, defStyle);
        }
    }

    @Override
    @RestrictTo(LIBRARY_GROUP)
    public void initialize(Context context, AttributeSet attrs, int defStyle) {
        mIconsBundle.createIcons(context);
        applyAttr(context, attrs, defStyle);
        setButtonDrawable(mIconsBundle.createStates(context));
    }

    @Override
    @RestrictTo(LIBRARY_GROUP)
    @SuppressLint("CustomViewStyleable")
    public void applyAttr(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconicsCompoundButton, defStyle, 0);
        IconicsViewsAttrsReader.readIconicsCompoundButton(context, a, mIconsBundle);
        a.recycle();
        a = context.obtainStyledAttributes(attrs, R.styleable.IconicsAnimateChanges, defStyle, 0);
        mIconsBundle.mAnimateChanges = a.getBoolean(R.styleable.IconicsAnimateChanges_iiv_animate_icon_changes, true);
        a.recycle();
    }

    public void setCheckedIcon(@Nullable IconicsDrawable icon) {
        mIconsBundle.mCheckedIconBundle = icon;
        setButtonDrawable(mIconsBundle.createStates(getContext()));
    }

    public void setUncheckedIcon(@Nullable IconicsDrawable icon) {
        mIconsBundle.mUncheckedIconBundle = icon;
        setButtonDrawable(mIconsBundle.createStates(getContext()));
    }

    public IconicsDrawable getCheckedIcon() {
        if (mIconsBundle.mCheckedIconBundle != null) {
            return mIconsBundle.mCheckedIconBundle;
        } else {
            return null;
        }
    }

    public IconicsDrawable getUncheckedIcon() {
        if (mIconsBundle.mUncheckedIconBundle != null) {
            return mIconsBundle.mUncheckedIconBundle;
        } else {
            return null;
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!isInEditMode()) {
            super.setText(new Iconics.IconicsBuilder().ctx(getContext()).on(text).build(), type);
        } else {
            super.setText(text, type);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return IconicsCompoundButton.class.getName();
    }
}

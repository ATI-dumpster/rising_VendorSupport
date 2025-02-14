/*
 * Copyright (C) 2023-2024 The risingOS Android Project
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

package com.android.settings.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.android.internal.widget.PreferenceImageView;

import java.util.Random;

public class ColoredPreferenceImageView extends PreferenceImageView {

    private static final String[] COLOR_MAP = {
        "#007aff", "#2fb151", "#fb7c47", "#fa7d4d", "#fbb404", "#e13e39"
    };

    public ColoredPreferenceImageView(Context context) {
        this(context, null);
    }

    public ColoredPreferenceImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColoredPreferenceImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ColoredPreferenceImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setRandomBgTint();
    }

    private void setRandomBgTint() {
        Random random = new Random();
        String randomColor = COLOR_MAP[random.nextInt(COLOR_MAP.length)];
        int colorInt = Color.parseColor(randomColor);
        setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorInt));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            final int maxWidth = getMaxWidth();
            if (maxWidth != Integer.MAX_VALUE
                    && (maxWidth < widthSize || widthMode == MeasureSpec.UNSPECIFIED)) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST);
            }
        }
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            final int maxHeight = getMaxHeight();
            if (maxHeight != Integer.MAX_VALUE
                    && (maxHeight < heightSize || heightMode == MeasureSpec.UNSPECIFIED)) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

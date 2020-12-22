package com.sotaiga.apps.gimcanace20.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.sotaiga.apps.gimcanace20.R;

public class RadioButtonCaption extends androidx.appcompat.widget.AppCompatRadioButton {

    public RadioButtonCaption(Context context_) {
        super(context_);
        this.init(context_, null);
    }

    public RadioButtonCaption(Context context_, AttributeSet attrs_) {
        super(context_, attrs_);
        this.init(context_, attrs_);
    }

    public RadioButtonCaption(Context context_, AttributeSet attrs_, int defStyle_) {
        super(context_, attrs_, defStyle_);
        this.init(context_, attrs_);
    }

    protected void onDraw(Canvas canvas_) {
        super.onDraw(canvas_);
    }

    private void init(Context context_, AttributeSet attrs_) {
        if (!isInEditMode()) {

            int[][] states = new int[][] {
                    new int[] {-android.R.attr.state_enabled},
                    new int[] { android.R.attr.state_checked},
                    new int[] { android.R.attr.state_pressed},
                    new int[] { android.R.attr.state_enabled},
            };

            int[] colors = new int[] {
                    Color.BLACK,
                    Color.BLACK,
                    Color.BLACK,
                    Color.WHITE
            };

            this.setTextColor(new ColorStateList(states, colors));
            this.setBackgroundResource(R.drawable.selector_radio_button);
            this.setButtonDrawable(null);

            String font_family = "capriola.ttf";

            if (this.getTypeface() != null) {
                if (this.getTypeface().isBold()) {
                    Typeface font = Typeface.createFromAsset(context_.getAssets(), font_family);
                    this.setTypeface(font);
                } else {
                    Typeface font = Typeface.createFromAsset(context_.getAssets(), font_family);
                    this.setTypeface(font);
                }
            } else {
                Typeface font = Typeface.createFromAsset(context_.getAssets(), font_family);
                this.setTypeface(font);
            }

            int paddingPixel = (int)(10 * this.getContext().getResources().getDisplayMetrics().density);
            this.setPadding(paddingPixel,paddingPixel,paddingPixel,paddingPixel);
        }
    }
}
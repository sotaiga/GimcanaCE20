package com.sotaiga.apps.gimcanace20.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class ButtonCaption extends androidx.appcompat.widget.AppCompatButton {

    // ---------------------------------------------------------------------------------------------

    public ButtonCaption(Context context_) {
        super(context_);
        this.init(context_, null);
    }

    public ButtonCaption(Context context_, AttributeSet attrs_) {
        super(context_, attrs_);
        this.init(context_, attrs_);
    }

    public ButtonCaption(Context context_, AttributeSet attrs_, int defStyle_) {
        super(context_, attrs_, defStyle_);
        this.init(context_, attrs_);
    }

    // ---------------------------------------------------------------------------------------------

    protected void onDraw(Canvas canvas_) {
        super.onDraw(canvas_);
    }

    // ---------------------------------------------------------------------------------------------

    private void init(Context context_, AttributeSet attrs_) {
        if (!isInEditMode()) {

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
        }
    }
}

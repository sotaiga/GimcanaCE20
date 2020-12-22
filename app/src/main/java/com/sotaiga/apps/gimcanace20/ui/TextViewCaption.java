package com.sotaiga.apps.gimcanace20.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Enric on 01/04/2016.
 */
public class TextViewCaption extends AppCompatTextView {

    public TextViewCaption(Context context_) {
        super(context_);
        this.init(context_, null);
    }

    public TextViewCaption(Context context_, AttributeSet attrs_) {
        super(context_, attrs_);
        this.init(context_, attrs_);
    }

    public TextViewCaption(Context context_, AttributeSet attrs_, int defStyle_) {
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

        // this.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }
}
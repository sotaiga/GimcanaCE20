package com.sotaiga.apps.gimcanace20.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Enric on 01/04/2016.
 */
public class TextViewText extends AppCompatTextView {

    public TextViewText(Context context_) {
        super(context_);
        this.init(context_, null);
    }

    public TextViewText(Context context_, AttributeSet attrs_) {
        super(context_, attrs_);
        this.init(context_, attrs_);
    }

    public TextViewText(Context context_, AttributeSet attrs_, int defStyle_) {
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
            Typeface font = Typeface.createFromAsset(context_.getAssets(), "abhaya_libre.ttf");
            this.setTypeface(font);
        }
    }
}
package com.sotaiga.apps.gimcanace20.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Enric on 01/04/2016.
 */
public class TextViewMinion extends AppCompatTextView {

    public TextViewMinion(Context context_) {
        super(context_);
        this.init(context_);//, null);
    }

    public TextViewMinion(Context context_, AttributeSet attrs_) {
        super(context_, attrs_);
        this.init(context_);//, attrs_);
    }

    public TextViewMinion(Context context_, AttributeSet attrs_, int defStyle_) {
        super(context_, attrs_, defStyle_);
        this.init(context_);//, attrs_);
    }

    protected void onDraw(Canvas canvas_) {
        super.onDraw(canvas_);
    }

    private void init(Context context_) { //, AttributeSet attrs_) {
        //LayoutInflater inflater = (LayoutInflater) context_.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (!isInEditMode()) {
            if (this.getTypeface() != null) {
                if (this.getTypeface().isBold()) {
                    Typeface font = Typeface.createFromAsset(context_.getAssets(), "MinionPro-Bold.otf");
                    this.setTypeface(font);
                } else {
                    Typeface font = Typeface.createFromAsset(context_.getAssets(), "MinionPro-Medium.otf");
                    this.setTypeface(font);
                }
            } else {
                Typeface font = Typeface.createFromAsset(context_.getAssets(), "MinionPro-Medium.otf");
                this.setTypeface(font);
            }
        }

        this.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
    }
}
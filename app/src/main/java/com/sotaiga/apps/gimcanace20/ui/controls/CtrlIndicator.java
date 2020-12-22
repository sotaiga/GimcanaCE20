package com.sotaiga.apps.gimcanace20.ui.controls;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.sotaiga.apps.gimcanace20.R;

/**
 * Created by Enric on 04/07/2016.
 */
public class CtrlIndicator extends FrameLayout {
    private Context _context;

    public CtrlIndicator(Context context) {
        super(context);

        this._context = context;

        this.initInterface();
    }

    public CtrlIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        this._context = context;

        this.initInterface();
    }

    public CtrlIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this._context = context;

        this.initInterface();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initInterface() {
        LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctrl_indicator_button, this);
    }
}



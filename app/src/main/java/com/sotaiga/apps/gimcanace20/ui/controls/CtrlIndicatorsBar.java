package com.sotaiga.apps.gimcanace20.ui.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sotaiga.apps.gimcanace20.R;

/**
 * Created by Enric on 04/07/2016.
 */
public class CtrlIndicatorsBar extends LinearLayout {

    private Context _context;

    // controls

    private LinearLayout _linearLayout;

    // Propietats

    private Integer _selected_index;

    public CtrlIndicatorsBar(Context context) {
        super(context);

        this._context = context;

        this.initInterface();
    }

    public CtrlIndicatorsBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        this._context = context;

        this.initInterface();
    }

    public CtrlIndicatorsBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this._context = context;

        this.initInterface();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initInterface() {
        LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctrl_indicators_bar, this);

        this._linearLayout = this.findViewById(R.id.indicatorsbar_linearLayout);
        this._selected_index = 0;
    }

    public void add() {
        Button btn = new Button(this._context);
        btn.setClickable(false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.gallery_indicator_button_size), getResources().getDimensionPixelSize(R.dimen.gallery_indicator_button_size));
        layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.gallery_indicator_button_margin), 0, getResources().getDimensionPixelSize(R.dimen.gallery_indicator_button_margin), 0);
        btn.setLayoutParams(layoutParams);

        btn.setBackgroundResource(R.drawable.selector_indicatorbutton_background);

        this._linearLayout.addView(btn);
    }

    public void setSelectedIndicator(int position_) {

        this._linearLayout.getChildAt(this._selected_index).setSelected(false);
        this._linearLayout.getChildAt(position_).setSelected(true);
        this._selected_index = position_;
    }
}
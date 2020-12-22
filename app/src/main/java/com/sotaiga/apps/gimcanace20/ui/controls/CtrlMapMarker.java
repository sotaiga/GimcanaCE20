package com.sotaiga.apps.gimcanace20.ui.controls;

import android.content.Context;
import android.graphics.Canvas;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sotaiga.apps.gimcanace20.R;
import com.sotaiga.apps.gimcanace20.model.Punt;
import com.sotaiga.apps.gimcanace20.ui.TextViewCaption;

public class CtrlMapMarker extends LinearLayout {

    private Context context;

    // Controls

    private ImageView number;

    private TextViewCaption text;

    // Propietats

    private Integer punt_id;

    private ClickMapMarkerListener click_map_marker_listener;

    // ---------------------------------------------------------------------------------------------

    public CtrlMapMarker(Context context) {
        super(context);

        this.context = context;

        this.click_map_marker_listener = null;

        this.initInterface();
    }

    public CtrlMapMarker(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        this.click_map_marker_listener = null;

        this.initInterface();
    }

    public CtrlMapMarker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        this.click_map_marker_listener = null;

        this.initInterface();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initInterface() {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctrl_map_marker, this);

        this.setClickable(true);

        this.number = this.findViewById(R.id.ctrl_map_marker_number);
        this.text = this.findViewById(R.id.ctrl_map_marker_text);

        this.text.setVisibility(View.GONE);

        // Cada vegada que fem click sobre el número, expandim l'event.
        this.number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.isSelected()) {
                    if (click_map_marker_listener != null) {
                        click_map_marker_listener.onClickMapMarker(punt_id);
                    }
                }
            }
        });
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        this.number.setSelected(selected);

        if (this.text != null) {
            if (selected) {
                this.text.setVisibility(View.VISIBLE);
            } else {
                this.text.setVisibility(View.GONE);
            }
        }
    }

    public void setPunt(Punt punt_) {
        this.punt_id = punt_.getId();

        if (punt_.getNomMini() == null) {
            ((LinearLayout)this.findViewById(R.id.ctrl_map_marker_linearlayout)).removeView(this.text);
            this.text = null;
        } else {
            this.text.setText(punt_.getNomMini());
        }

        int drawableId = this.context.getResources().getIdentifier(punt_.getMarker(), "drawable", this.context.getPackageName());
        if (drawableId > 0) {
            this.number.setImageDrawable(ContextCompat.getDrawable(this.context, drawableId));
        }
    }

    public void setAnswered() {
        this.number.setBackgroundResource(R.drawable.selector_number_marker_gray_background);
        this.text.setBackgroundResource(R.drawable.selector_text_marker_gray_background);
    }

    // ---------------------------------------------------------------------------------------------

    public void setClickMapMarkerListener(ClickMapMarkerListener in_click_map_marker_listener) {
        this.click_map_marker_listener = in_click_map_marker_listener;
    }

    public interface ClickMapMarkerListener {
        void onClickMapMarker(int in_punt_id);
    }
}

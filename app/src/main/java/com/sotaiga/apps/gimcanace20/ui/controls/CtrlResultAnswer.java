package com.sotaiga.apps.gimcanace20.ui.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sotaiga.apps.gimcanace20.R;
import com.sotaiga.apps.gimcanace20.model.Pregunta;
import com.sotaiga.apps.gimcanace20.model.Punt;
import com.sotaiga.apps.gimcanace20.model.Resposta;

public class CtrlResultAnswer extends LinearLayout {

    private Context _context;

    // ---------------------------------------------------------------------------------------------
    // Constructors

    public CtrlResultAnswer(Context context) {
        super(context);

        this._context = context;

        this.initInterface();
    }

    public CtrlResultAnswer(Context context, AttributeSet attrs) {
        super(context, attrs);

        this._context = context;

        this.initInterface();
    }

    public CtrlResultAnswer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this._context = context;

        this.initInterface();
    }

    // ---------------------------------------------------------------------------------------------

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initInterface() {
        LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctrl_result_answer, this);
    }

    public void setPuntPreguntaResposta(int in_ordre, Punt in_punt, Pregunta in_pregunta, Resposta in_resposta) {
        ((TextView) this.findViewById(R.id.numero_text_view)).setText(String.valueOf(in_ordre));

        StringBuilder string_builder = new StringBuilder();
        string_builder.append(in_punt.getNom());

        if (!in_punt.getTema().trim().equals(""))
        {
            string_builder.append("\n"+in_punt.getTema());
        }

        if (!in_punt.getUbicacio().trim().equals(""))
        {
            string_builder.append("\n"+in_punt.getUbicacio());
        }

        ((TextView) this.findViewById(R.id.punt_text_view)).setText(string_builder.toString());
        ((TextView) this.findViewById(R.id.pregunta_text_view)).setText(in_pregunta.getText());
        ((TextView) this.findViewById(R.id.resposta_text_view)).setText(in_resposta.getText());
    }
}
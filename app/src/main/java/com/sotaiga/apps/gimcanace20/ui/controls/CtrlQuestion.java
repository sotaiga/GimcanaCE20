package com.sotaiga.apps.gimcanace20.ui.controls;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sotaiga.apps.gimcanace20.R;
import com.sotaiga.apps.gimcanace20.model.Pregunta;
import com.sotaiga.apps.gimcanace20.model.Resposta;
import com.sotaiga.apps.gimcanace20.ui.RadioButtonCaption;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;

public class CtrlQuestion extends LinearLayout {

    private Context context;

    // Controls

    private RadioGroup answers_radio_group;

    private AppCompatButton confirm_button;

    // --

    private Integer pregunta_id;
    private Integer resposta_id;

    // --

    private CtrlQuestion.QuestionListener question_listener;

    // ---------------------------------------------------------------------------------------------
    // Constructors

    public CtrlQuestion(Context context) {
        super(context);

        this.context = context;

        this.question_listener = null;

        this.initInterface();
    }

    public CtrlQuestion(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        this.question_listener = null;

        this.initInterface();
    }

    public CtrlQuestion(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        this.question_listener = null;

        this.initInterface();
    }

    // ---------------------------------------------------------------------------------------------

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initInterface() {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctrl_question, this);

        this.answers_radio_group = this.findViewById(R.id.answers_radio_group);

        this.confirm_button = this.findViewById(R.id.confirm_button);
        this.confirm_button.setEnabled(false);
        this.confirm_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question_listener != null) {
                    question_listener.onAnswerQuestion(pregunta_id, resposta_id);
                }
            }
        });
    }

    // ---------------------------------------------------------------------------------------------

    public void setQuestionAnswers(final Pregunta in_pregunta, Cursor in_respostes_cursor) {

        this.pregunta_id = in_pregunta.getId();
        this.resposta_id = null;

        // Text de la pregunta.

        ((TextView) this.findViewById(R.id.question_text_view)).setText(in_pregunta.getText());

        // Respostes.

        this.answers_radio_group.removeAllViews();

        while (in_respostes_cursor.moveToNext()) {
            Resposta resposta = new Resposta(in_respostes_cursor);

            RadioButtonCaption radio_button = new RadioButtonCaption(this.context);
            radio_button.setTag(resposta.getId());
            radio_button.setText(resposta.getText());

            if (resposta.getSeleccionada()) {
                this.resposta_id = resposta.getId();
                radio_button.setChecked(true);
            } else {
                radio_button.setChecked(false);
            }

            if (in_pregunta.getHoraResposta() == null) {
                radio_button.setEnabled(true);
            } else {
                radio_button.setEnabled(false);
            }

            radio_button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    resposta_id = (Integer) v.getTag();
                    confirm_button.setEnabled(true);
                }
            });

            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 10);
            radio_button.setLayoutParams(params);

            int paddingPixel = (int) (10 * context.getResources().getDisplayMetrics().density);
            radio_button.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);

            this.answers_radio_group.addView(radio_button);
        }

        // Pregunta ja resposta?

        if (in_pregunta.getHoraResposta() == null) {
            this.answers_radio_group.setEnabled(true);
            this.confirm_button.setVisibility(VISIBLE);
            confirm_button.setEnabled(false);
        } else {
            this.answers_radio_group.setEnabled(false);
            this.confirm_button.setVisibility(GONE);
        }
    }

    public void setAnswered() {
        for (int i = 0; i < answers_radio_group.getChildCount(); i++) {
            answers_radio_group.getChildAt(i).setEnabled(false);
        }

        answers_radio_group.setEnabled(false);
        confirm_button.setEnabled(false);
    }

    // ---------------------------------------------------------------------------------------------

    public void setQuestionListener(CtrlQuestion.QuestionListener in_question_listener) {
        this.question_listener = in_question_listener;
    }

    public interface QuestionListener {
        void onAnswerQuestion(int in_pregunta_id, int in_resposta_id);
    }
}
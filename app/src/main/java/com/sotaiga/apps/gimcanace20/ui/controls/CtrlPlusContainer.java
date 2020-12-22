package com.sotaiga.apps.gimcanace20.ui.controls;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sotaiga.apps.gimcanace20.R;
import com.sotaiga.apps.gimcanace20.model.Pregunta;
import com.sotaiga.apps.gimcanace20.model.Punt;

public class CtrlPlusContainer extends RelativeLayout {

    private Context context;

    // Controls

    private TextView nom_text_view;
    private TextView tema_text_view;
    private TextView ubicacio_text_view;
    private TextView text_text_view;
    private TextView nexe_text_view;

    private CtrlQuestion ctrl_question;

    // --

    private PlusQuestionListener plus_question_listener;

    // --

    private int punt_id;

    // ---------------------------------------------------------------------------------------------
    // Constructors

    public CtrlPlusContainer(Context context) {
        super(context);

        this.context = context;

        this.plus_question_listener = null;

        this.initInterface();
    }

    public CtrlPlusContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        this.plus_question_listener = null;

        this.initInterface();
    }

    public CtrlPlusContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        this.plus_question_listener = null;

        this.initInterface();
    }

    // ---------------------------------------------------------------------------------------------

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initInterface() {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctrl_plus_container, this);

        this.nom_text_view = this.findViewById(R.id.nom_text_view);
        this.tema_text_view = this.findViewById(R.id.tema_text_view);
        this.ubicacio_text_view = this.findViewById(R.id.ubicacio_text_view);
        this.text_text_view = this.findViewById(R.id.text_text_view);

        this.ctrl_question = this.findViewById(R.id.question_ctrl);
        this.ctrl_question.setQuestionListener(new CtrlQuestion.QuestionListener() {
            @Override
            public void onAnswerQuestion(int in_pregunta_id, int in_resposta_id) {
                if (plus_question_listener != null) {
                    plus_question_listener.onPlusAnswerQuestion(punt_id, in_pregunta_id, in_resposta_id);
                }
            }
        });

        this.nexe_text_view = this.findViewById(R.id.nexe_text_view);

        findViewById(R.id.line1).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        findViewById(R.id.line2).setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    // ---------------------------------------------------------------------------------------------

    public void setPuntQuestionAnswers(Punt in_punt, Pregunta in_pregunta, Cursor in_respostes_cursor) {
        if (this.punt_id != in_punt.getId()) {
            this.findViewById(R.id.scroll_view).scrollTo(0, 0);
        }

        this.punt_id = in_punt.getId();

        if (in_punt.getNom().length() == 0) {
            this.nom_text_view.setVisibility(GONE);
        } else {
            this.nom_text_view.setVisibility(VISIBLE);
            this.nom_text_view.setText(in_punt.getNom());
        }

        if (in_punt.getTema().length() == 0) {
            this.tema_text_view.setVisibility(GONE);
        } else {
            this.tema_text_view.setVisibility(VISIBLE);
            this.tema_text_view.setText(in_punt.getTema());
        }

        if (in_punt.getUbicacio().length() == 0) {
            this.ubicacio_text_view.setVisibility(GONE);
        } else {
            this.ubicacio_text_view.setVisibility(VISIBLE);
            this.ubicacio_text_view.setText(in_punt.getUbicacio());
        }

        // this.text_text_view.setText(Utils.getSpannableString(in_punt.getText()), TextView.BufferType.SPANNABLE);
        this.text_text_view.setText(Html.fromHtml(in_punt.getText()));

        if (in_punt.getNexe().length() == 0) {
            this.findViewById(R.id.nexe_label).setVisibility(GONE);
            this.nexe_text_view.setVisibility(GONE);
        } else {
            this.findViewById(R.id.nexe_label).setVisibility(VISIBLE);
            this.nexe_text_view.setVisibility(VISIBLE);
            this.nexe_text_view.setText(in_punt.getNexe());
        }

        if (in_pregunta == null) {
            this.findViewById(R.id.question_label).setVisibility(GONE);
            this.ctrl_question.setVisibility(GONE);
        } else {
            this.findViewById(R.id.question_label).setVisibility(VISIBLE);
            this.ctrl_question.setVisibility(VISIBLE);
            this.ctrl_question.setQuestionAnswers(in_pregunta, in_respostes_cursor);
        }
    }

    public void setAnswered()
    {
        this.ctrl_question.setAnswered();
    }

    // ---------------------------------------------------------------------------------------------

    public void setPlusQuestionListener(CtrlPlusContainer.PlusQuestionListener in_plus_question_listener) {
        this.plus_question_listener = in_plus_question_listener;
    }

    public interface PlusQuestionListener {
        void onPlusAnswerQuestion(int in_punt_id, int in_pregunta_id, int in_resposta_id);
    }
}
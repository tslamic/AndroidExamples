package com.tslamic.androidscreens;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AnswersFragment extends Fragment {

    private String answer = "Select a Question!";
    private TextView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            answer = savedInstanceState.getString(QuestionListener.BUNDLE_ANSWER_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QuestionListener.BUNDLE_ANSWER_KEY, answer);
    }

    public void setAnswer(final String answer) {
        if (null == answer) throw new IllegalArgumentException();
        this.answer = answer;
        view.setText(answer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = new TextView(getActivity());
        view.setText(answer);
        return view;
    }

}

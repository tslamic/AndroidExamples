package com.tslamic.swipableviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Random;

public class DemoFragment extends Fragment implements Validator {

    private static final Random RANDOM = new Random();
    private static final int CAP = 10;
    private EditText mEditText;
    private int fst;
    private int snd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.demo, container, false);

        mEditText = (EditText) view.findViewById(R.id.calc);
        fst = RANDOM.nextInt(CAP);
        snd = RANDOM.nextInt(CAP);

        final String hint = fst + " + " + snd;
        mEditText.setHint(hint);

        return view;
    }

    @Override
    public boolean isValid() {
        final String value = mEditText.getText().toString();

        if (TextUtils.isEmpty(value)) {
            mEditText.setError("What is the sum?");
            return false;
        }

        int input;
        try {
            input = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            mEditText.setText("");
            mEditText.setError("Numbers only, please.");
            return false;
        }

        final int sum = fst + snd;
        if (sum != input) {
            mEditText.setText("");
            mEditText.setError("Care to try that again?");
            return false;
        }

        return true;
    }

}

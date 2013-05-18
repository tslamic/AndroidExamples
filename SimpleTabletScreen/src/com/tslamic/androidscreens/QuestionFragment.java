package com.tslamic.androidscreens;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QuestionFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String[] questions = getActivity().getResources().getStringArray(R.array.questions);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                                                 android.R.layout.simple_list_item_1, questions);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final QuestionListener listener = (QuestionListener) getActivity();
        listener.onQuestionSelected(position);
    }

}

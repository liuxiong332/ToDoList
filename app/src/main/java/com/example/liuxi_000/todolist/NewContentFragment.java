package com.example.liuxi_000.todolist;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
/**
 * Created by liuxi_000 on 2014/7/14.
 */
public class NewContentFragment extends Fragment {

    public interface OnAddContentButtonClickListener {
        public void onAddContent(String item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_content, container);

        Button button = (Button)view.findViewById(R.id.add_content_button);
        final EditText addContentEdit = (EditText)view.findViewById(R.id.add_content_edit);
        final OnAddContentButtonClickListener listener = (OnAddContentButtonClickListener)getActivity();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddContent(addContentEdit.getText().toString());
                addContentEdit.setText("");
            }
        });
        return view;
    }
}

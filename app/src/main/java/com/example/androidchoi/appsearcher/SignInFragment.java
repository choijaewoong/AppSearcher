package com.example.androidchoi.appsearcher;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    TextView mTextSignUp;
    TextView mTextFailMessage;
    EditText mEditEmail;
    EditText mEditPassWord;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mTextFailMessage = (TextView) view.findViewById(R.id.text_login_fail_message);
        Button btn = (Button) view.findViewById(R.id.btn_sign_in);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                logIn();
            }
        });

        mTextSignUp = (TextView) view.findViewById(R.id.text_sign_up);
        mTextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.login_container, new WriteUserInputFragment()).addToBackStack(null).commit();
            }
        });
        mEditEmail = (EditText) view.findViewById(R.id.editText_login_email);
        mEditPassWord = (EditText) view.findViewById(R.id.editText_login_password);
        return view;
    }

    public void logIn() {

    }

    public void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }
}
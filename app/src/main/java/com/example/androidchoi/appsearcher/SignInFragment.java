package com.example.androidchoi.appsearcher;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.appsearcher.Manager.MyApplication;
import com.example.androidchoi.appsearcher.Manager.NetworkManager;
import com.example.androidchoi.appsearcher.Model.SignInData;
import com.example.androidchoi.appsearcher.Model.UserData;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    public static final String MESSAGE_SUCCESS = "success";

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
                signIn();
            }
        });

        mTextSignUp = (TextView) view.findViewById(R.id.text_sign_up);
        mTextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.login_container, new WriteUserInputFragment()).addToBackStack("write").commit();
            }
        });
        mEditEmail = (EditText) view.findViewById(R.id.editText_login_email);
        mEditPassWord = (EditText) view.findViewById(R.id.editText_login_password);
        return view;
    }

    public void signIn() {
        final String email = mEditEmail.getText().toString();
        final String password = mEditPassWord.getText().toString();
        NetworkManager.getInstance().signIn(email, password, new NetworkManager.OnResultListener<SignInData>() {
            @Override
            public void onSuccess(SignInData result) {
                if(result.getMessage().equals(MESSAGE_SUCCESS)){
                    UserData user = result.getUser();
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }else{
                    Toast.makeText(MyApplication.getContext(), "입력 정보가 잘못 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "로그인 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }
}

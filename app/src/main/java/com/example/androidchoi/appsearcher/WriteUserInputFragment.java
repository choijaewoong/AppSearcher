package com.example.androidchoi.appsearcher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class WriteUserInputFragment extends Fragment {

    EditText mEditName;
    EditText mEditEmail;
    EditText mEditPassword;
    EditText mEditPasswordCheck;

    public WriteUserInputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_user_input, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_input_user_info);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        mEditEmail = (EditText) view.findViewById(R.id.editText_signup_email);
        mEditEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValidEmail(s)) {
                    mEditEmail.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                } else {
                    mEditEmail.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEditName = (EditText) view.findViewById(R.id.editText_signup_name);
        mEditPassword = (EditText) view.findViewById(R.id.editText_signup_password);
        mEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValidPassword(s)) {
                    mEditPassword.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                } else {
                    mEditPassword.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditPasswordCheck = (EditText) view.findViewById(R.id.editText_signup_password_check);
        mEditPasswordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isSamePassword(mEditPassword.getText().toString(), s.toString())) {
                    mEditPasswordCheck.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                } else {
                    mEditPasswordCheck.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btn = (Button) view.findViewById(R.id.btn_input_complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        return view;
    }

    public void signUp(){
        if(TextUtils.isEmpty(mEditName.getText())){
            Toast.makeText(getActivity(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }else if(!isValidEmail(mEditEmail.getText())){
            Toast.makeText(getActivity(), "Email을 확인해 주세요.", Toast.LENGTH_SHORT).show();
        }else if(!isValidPassword(mEditPassword.getText())){
            Toast.makeText(getActivity(), "비밀번호는 8~20자의 영문, 숫자 조합으로 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }else if(!isSamePassword(mEditPassword.getText().toString(), mEditPasswordCheck.getText().toString())){
            Toast.makeText(getActivity(), "비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
        }else {
            // 회원 가입 서버 요청
        }
    }

    public boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean isValidPassword(CharSequence target) {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,20}$";
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return Pattern.compile(PASSWORD_PATTERN).matcher(target).matches();
        }
    }

    public boolean isSamePassword(String password, String passwordCheck ){
        if(password.equals(passwordCheck)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

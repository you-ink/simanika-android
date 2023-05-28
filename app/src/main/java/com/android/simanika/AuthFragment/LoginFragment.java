package com.android.simanika.AuthFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.simanika.MainActivity;
import com.android.simanika.R;
import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.LoginRequest;
import com.android.simanika.Services.HTTP.LoginResponse;
import com.android.simanika.Services.SharedPreference.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // My Code
    private View rootview;
    Button login_btn;
    EditText email, password;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_login, container, false);

        email = rootview.findViewById(R.id.login_input_email);
        password = rootview.findViewById(R.id.login_input_password);

        login_btn = rootview.findViewById(R.id.login_button);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        return rootview;
    }

    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        ProgressDialog progressDialog = new ProgressDialog(rootview.getContext());
        progressDialog.setMessage("Loading..."); // Set message untuk dialog
        progressDialog.setCancelable(false); // Set apakah dialog bisa di-cancel atau tidak

        progressDialog.show(); // Menampilkan dialog


        Call<LoginResponse> loginResponseCall = ApiClient.getUserService(rootview.getContext()).userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    if (!loginResponse.isError()) {
                        Toast.makeText(rootview.getContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        Preferences.setLoggedInToken(getActivity().getBaseContext(), loginResponse.getData().getToken());
                        Preferences.setLoggedInStatus(getActivity().getBaseContext(), true);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Activity thisActivity = getActivity();
                                thisActivity.finishAfterTransition();

                                Intent intent = new Intent(thisActivity, MainActivity.class);
                                startActivity(intent);
                            }
                        }, 700);
                    } else {
                        Toast.makeText(rootview.getContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(rootview.getContext(), "Login Gagal.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(rootview.getContext(), "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.android.simanika.AuthFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.simanika.R;
import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.GlobalResponse;
import com.android.simanika.Services.HTTP.RegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // My Code
    private View rootview;
    Button register_btn;
    EditText nama, email, nim, angkatan, telp, password, confirm_password;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        rootview = inflater.inflate(R.layout.fragment_register, container, false);

        nama = rootview.findViewById(R.id.register_nama);
        email = rootview.findViewById(R.id.register_email);
        nim = rootview.findViewById(R.id.register_nim);
        angkatan = rootview.findViewById(R.id.register_angkatan);
        telp = rootview.findViewById(R.id.register_telp);
        password = rootview.findViewById(R.id.register_password);
        confirm_password = rootview.findViewById(R.id.register_confirm_password);

        register_btn = rootview.findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register();

            }
        });

        return rootview;
    }

    public void register(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setNama(nama.getText().toString());
        registerRequest.setEmail(email.getText().toString());
        registerRequest.setNim(nim.getText().toString());
        registerRequest.setAngkatan(angkatan.getText().toString());
        registerRequest.setTelp(telp.getText().toString());
        registerRequest.setPassword(password.getText().toString());
        registerRequest.setConfirm_password(confirm_password.getText().toString());

        ProgressDialog progressDialog = new ProgressDialog(rootview.getContext());
        progressDialog.setMessage("Loading..."); // Set message untuk dialog
        progressDialog.setCancelable(false); // Set apakah dialog bisa di-cancel atau tidak

        progressDialog.show(); // Menampilkan dialog

        Call<GlobalResponse> globalResponseCall = ApiClient.getUserService(rootview.getContext()).userRegister(registerRequest);
        globalResponseCall.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()){
                    GlobalResponse globalResponse = response.body();

                    if (!globalResponse.isError()) {
                        Toast.makeText(rootview.getContext(), globalResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                // Pindah ViewPager
                                ViewPager2 viewPager2 = rootview.getRootView().findViewById(R.id.view_pager);
                                viewPager2.setCurrentItem(0);
                            }
                        }, 700);
                    } else {
                        Toast.makeText(rootview.getContext(), globalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(rootview.getContext(), "Register Gagal.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(rootview.getContext(), "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
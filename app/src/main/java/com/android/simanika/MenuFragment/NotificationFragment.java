package com.android.simanika.MenuFragment;

import static android.content.ContentValues.TAG;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.simanika.Adapter.NotificationAdapter;
import com.android.simanika.Adapter.NotificationData;
import com.android.simanika.R;
import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.NotifikasiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootview;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        rootview = inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView recyclerView = rootview.findViewById(R.id.notification_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootview.getContext()));

        int requestCode = 1; // Misalnya, kode permintaan 1
        Intent intent = new Intent(rootview.getContext(), NotificationFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(rootview.getContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        getNotifikasi(recyclerView);
//        getNewNotifikasi(recyclerView);

        return rootview;
    }

//    private void getNewNotifikasi(RecyclerView recyclerView){
//        Call<NotifikasiResponse> notifikasiResponseCall = ApiClient.getNotificationService(rootview.getContext()).getNewNotifikasi();
//        notifikasiResponseCall.enqueue(new Callback<NotifikasiResponse>() {
//            @Override
//            public void onResponse(Call<NotifikasiResponse> call, Response<NotifikasiResponse> response) {
//
//                if (response.isSuccessful()){
//                    NotifikasiResponse notifikasiResponse = response.body();
//                    if (notifikasiResponse != null) {
//                        List<NotifikasiResponse.Data> dataList = notifikasiResponse.getData();
//
//                        // Mengubah List menjadi array NotificationData[]
//                        NotificationData[] notificationData = new NotificationData[dataList.size()];
//
//                        for (int i = 0; i < dataList.size(); i++) {
//                            NotifikasiResponse.Data data = dataList.get(i);
//
//                            // Ambil data yang diperlukan dari objek data
//                            int id = data.getId();
//                            String judul = data.getJudul();
//                            String isi = data.getIsi();
//
//                            // Buat objek NotificationData dan tambahkan ke array
//                            notificationData[i] = new NotificationData(id, judul, isi);
//                        }
//
//                        // Tambahkan kode untuk melakukan sesuatu dengan notificationData, seperti mengatur adapter RecyclerView
//                        NotificationAdapter notificationAdapter = new NotificationAdapter(notificationData, rootview.getContext());
//                        recyclerView.setAdapter(notificationAdapter);
//
//                        if (dataList.size() == 0) {
//                            rootview.findViewById(R.id.article_list).setVisibility(View.GONE);
//                            rootview.findViewById(R.id.article_list_null).setVisibility(View.VISIBLE);
//                        } else {
//                            rootview.findViewById(R.id.article_list).setVisibility(View.VISIBLE);
//                            rootview.findViewById(R.id.article_list_null).setVisibility(View.GONE);
//                        }
//                    } else {
//                        Toast.makeText(rootview.getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(rootview.getContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NotifikasiResponse> call, Throwable t) {
//                Log.e(TAG, "onFailure: "+t.getMessage());
//            }
//        });
//    }

    private void getNotifikasi(RecyclerView recyclerView){
        ProgressDialog progressDialog = new ProgressDialog(rootview.getContext());
        progressDialog.setMessage("Loading..."); // Set message untuk dialog
        progressDialog.setCancelable(false); // Set apakah dialog bisa di-cancel atau tidak

        progressDialog.show(); // Menampilkan dialog

        Call<NotifikasiResponse> notifikasiResponseCall = ApiClient.getNotificationService(rootview.getContext()).getNotifikasi();

        notifikasiResponseCall.enqueue(new Callback<NotifikasiResponse>() {
            @Override
            public void onResponse(Call<NotifikasiResponse> call, Response<NotifikasiResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()){
                    NotifikasiResponse notifikasiResponse = response.body();
                    if (notifikasiResponse != null) {
                        List<NotifikasiResponse.Data> dataList = notifikasiResponse.getData();

                        // Mengubah List menjadi array NotifikasiData[]
                        NotificationData[] notificationData = new NotificationData[dataList.size()];

                        for (int i = 0; i < dataList.size(); i++) {
                            NotifikasiResponse.Data data = dataList.get(i);

                            // Ambil data yang diperlukan dari objek data
                            int id = data.getId();
                            String judul = data.getJudul();
                            String isi = data.getIsi();

                            // Buat objek NotificationData dan tambahkan ke array
                            notificationData[i] = new NotificationData(id, judul, isi);
                        }

                        // Tambahkan kode untuk melakukan sesuatu dengan notificationData, seperti mengatur adapter RecyclerView
                        NotificationAdapter notificationAdapter = new NotificationAdapter(notificationData, rootview.getContext());
                        recyclerView.setAdapter(notificationAdapter);

                        if (dataList.size() == 0) {
                            rootview.findViewById(R.id.notification_list).setVisibility(View.GONE);
                        } else {
                            rootview.findViewById(R.id.notification_list).setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(rootview.getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(rootview.getContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotifikasiResponse> call, Throwable t) {
                progressDialog.dismiss();

                String errorMessage = t.getMessage();
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
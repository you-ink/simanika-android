package com.android.simanika.MenuFragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.simanika.Adapter.ArticleData;
import com.android.simanika.Adapter.HomeArticleAdapter;
import com.android.simanika.Adapter.RapatAdapter;
import com.android.simanika.Adapter.RapatData;
import com.android.simanika.R;
import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.ArtikelResponse;
import com.android.simanika.Services.HTTP.RapatResponse;
import com.android.simanika.Services.SharedPreference.Preferences;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View rootView;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        if (!Preferences.getLoggedInUserStatus(rootView.getContext()).equalsIgnoreCase("1")) {
            TextView tv = rootView.findViewById(R.id.header_home_fragment);
            tv.setText("Halo calon pengurus baru, tunggu notifikasi seputar wawancaramu disini!\n\nJangan lupa untuk melengkapi datamu terlebih dahulu.\n");

            LinearLayout ll = rootView.findViewById(R.id.home_section_meeting);
            ll.setVisibility(View.GONE);
        }

        RecyclerView recyclerView = rootView.findViewById(R.id.article_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        getNewArtikel(recyclerView);


        RecyclerView recyclerView2 = rootView.findViewById(R.id.rapat_list);
        recyclerView2.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView2.setHasFixedSize(false);

        getNewRapat(recyclerView2);

        return rootView;
    }

    private void getNewArtikel(RecyclerView recyclerView){
        Call<ArtikelResponse> artikelResponseCall = ApiClient.getArtikelService(rootView.getContext()).getNewArtikel();
        artikelResponseCall.enqueue(new Callback<ArtikelResponse>() {
            @Override
            public void onResponse(Call<ArtikelResponse> call, Response<ArtikelResponse> response) {

                if (response.isSuccessful()){
                    ArtikelResponse artikelResponse = response.body();
                    if (artikelResponse != null) {
                        List<ArtikelResponse.Data> dataList = artikelResponse.getData();

                        // Mengubah List menjadi array ArticleData[]
                        ArticleData[] articleData = new ArticleData[dataList.size()];

                        for (int i = 0; i < dataList.size(); i++) {
                            ArtikelResponse.Data data = dataList.get(i);

                            // Ambil data yang diperlukan dari objek data
                            int id = data.getId();
                            String judul = data.getJudul();
                            String divisi = data.getDivisi().getNama();
                            String penulis = data.getPenulis().getNama();
                            String tanggal = data.getTanggal();
                            String sampul = data.getSampul();

                            // Buat objek ArticleData dan tambahkan ke array
                            articleData[i] = new ArticleData(id, judul, divisi, penulis, tanggal, ApiClient.getBaseUrl()+sampul);
                        }

                        // Tambahkan kode untuk melakukan sesuatu dengan articleData, seperti mengatur adapter RecyclerView
                        HomeArticleAdapter articleAdapter = new HomeArticleAdapter(articleData, rootView.getContext());
                        recyclerView.setAdapter(articleAdapter);

                        if (dataList.size() == 0) {
                            rootView.findViewById(R.id.article_list).setVisibility(View.GONE);
                            rootView.findViewById(R.id.article_list_null).setVisibility(View.VISIBLE);
                        } else {
                            rootView.findViewById(R.id.article_list).setVisibility(View.VISIBLE);
                            rootView.findViewById(R.id.article_list_null).setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(rootView.getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(rootView.getContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArtikelResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
    private void getNewRapat(RecyclerView recyclerView){
        Call<RapatResponse> rapatResponseCall = ApiClient.getRapatService(rootView.getContext()).getRapat();
        rapatResponseCall.enqueue(new Callback<RapatResponse>() {
            @Override
            public void onResponse(Call<RapatResponse> call, Response<RapatResponse> response) {

                if (response.isSuccessful()){
                    RapatResponse rapatResponse = response.body();
                    if (rapatResponse != null) {
                        List<RapatResponse.Data> dataList = rapatResponse.getData();

                        // Mengubah List menjadi array ArticleData[]
                        RapatData[] rapatData = new RapatData[dataList.size()];

                        for (int i = 0; i < dataList.size(); i++) {
                            RapatResponse.Data data = dataList.get(i);

                            // Ambil data yang diperlukan dari objek data
                            int id = data.getId();
                            String tanggal = data.getTanggal();
                            String waktu_mulai = data.getWaktu_mulai();
                            String notulensi = data.getNotulensi();
                            String nama = data.getNama();
                            String tipe= data.getTipe();
                            String deskripsi_tipe= data.getDeskripsi_tipe();
                            String divisi= data.getDivisi().getNama();

                            // Buat objek RapatData dan tambahkan ke array
                            rapatData[i] = new RapatData(id, nama, waktu_mulai, deskripsi_tipe, divisi, tanggal);
                        }
                        if (rapatData.length > 1) {
                            recyclerView.setMinimumHeight(180 * 5);
                        }
                        // Tambahkan kode untuk melakukan sesuatu dengan articleData, seperti mengatur adapter RecyclerView
                        RapatAdapter rapatAdapter = new RapatAdapter(rapatData, rootView.getContext());
                        recyclerView.setAdapter(rapatAdapter);

                        if (dataList.size() == 0) {
                            rootView.findViewById(R.id.rapat_list).setVisibility(View.GONE);
                            rootView.findViewById(R.id.rapat_list_null).setVisibility(View.VISIBLE);
                        } else {
                            rootView.findViewById(R.id.rapat_list).setVisibility(View.VISIBLE);
                            rootView.findViewById(R.id.rapat_list_null).setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(rootView.getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(rootView.getContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RapatResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
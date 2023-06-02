package com.android.simanika.MenuFragment;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.simanika.Adapter.ArticleAdapter;
import com.android.simanika.Adapter.ArticleData;
import com.android.simanika.R;
import com.android.simanika.Services.ApiClient;
import com.android.simanika.Services.HTTP.ArtikelResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootview;

    public ArticleFragment() {
        // Required empty public constructor
    }

    /**
     *
     *
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleFragment newInstance(String param1, String param2) {
        ArticleFragment fragment = new ArticleFragment();
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
        rootview = inflater.inflate(R.layout.fragment_article, container, false);

        RecyclerView recyclerView = rootview.findViewById(R.id.article_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootview.getContext()));

        getArtikel(recyclerView, null);

        EditText inputSearch = rootview.findViewById(R.id.cari_artikel);
        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchQuery = textView.getText().toString();
                    getArtikel(recyclerView, (searchQuery.isEmpty())? "" : searchQuery);
                    return true;
                }
                return false;
            }
        });

        return rootview;
    }
    private void getArtikel(RecyclerView recyclerView, String search){
        ProgressDialog progressDialog = new ProgressDialog(rootview.getContext());
        progressDialog.setMessage("Loading..."); // Set message untuk dialog
        progressDialog.setCancelable(false); // Set apakah dialog bisa di-cancel atau tidak

        progressDialog.show(); // Menampilkan dialog

        Call<ArtikelResponse> artikelResponseCall = null;
        if (search == null || search.isEmpty()) {
            artikelResponseCall = ApiClient.getArtikelService(rootview.getContext()).getArtikel();
        } else {
            artikelResponseCall = ApiClient.getArtikelService(rootview.getContext()).getArtikel(search);
        }

        artikelResponseCall.enqueue(new Callback<ArtikelResponse>() {
            @Override
            public void onResponse(Call<ArtikelResponse> call, Response<ArtikelResponse> response) {
                progressDialog.dismiss();

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
                        ArticleAdapter articleAdapter = new ArticleAdapter(articleData, rootview.getContext());
                        recyclerView.setAdapter(articleAdapter);
                    } else {
                        Toast.makeText(rootview.getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(rootview.getContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArtikelResponse> call, Throwable t) {
                progressDialog.dismiss();

                String errorMessage = t.getMessage();
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
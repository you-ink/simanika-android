package com.android.simanika.MenuFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.simanika.Adapter.ArticleAdapter;
import com.android.simanika.Adapter.ArticleData;
import com.android.simanika.R;

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

        ArticleData[] articleData = new ArticleData[]{
            new ArticleData(1, "Ini Adalah Judul Artikel 1 Yang Akan Ditampilkan.", "BPH", "Harry Santoso♦", "12-12-2023 00:00:00", "https://berita.99.co/wp-content/uploads/2023/02/poster-digital.jpg"),
            new ArticleData(2, "Ini Adalah Judul Artikel 2 Yang Akan Ditampilkan.", "Humas", "Ahmad Afandi", "12-12-2023 00:00:00", "https://berita.99.co/wp-content/uploads/2023/02/budaya-membaca.jpg"),
            new ArticleData(3, "Ini Adalah Judul Artikel 3 Yang Akan Ditampilkan.", "Kominfo", "Younki Vanesta Ramadhana Pecinta Alam", "12-12-2023 00:00:00", "https://berita.99.co/wp-content/uploads/2023/02/selamatkan-bumi.jpg"),
            new ArticleData(4, "Ini Adalah Judul Artikel 4 Yang Akan Ditampilkan.", "PSDM", "Budi Tarmizi", "12-12-2023 00:00:00", "https://berita.99.co/wp-content/uploads/2023/02/poster-produk.jpg"),
        };

        ArticleAdapter articleAdapter = new ArticleAdapter(articleData,this);
        recyclerView.setAdapter(articleAdapter);

        return rootview;
    }
}
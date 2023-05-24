package com.android.simanika.MenuFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.simanika.Adapter.ArticleAdapter;
import com.android.simanika.Adapter.ArticleData;
import com.android.simanika.Adapter.HomeArticleAdapter;
import com.android.simanika.Adapter.RapatAdapter;
import com.android.simanika.Adapter.RapatData;
import com.android.simanika.R;
import com.google.android.material.tabs.TabLayout;

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
    private View view;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.article_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        ArticleData[] articleData = new ArticleData[]{
                new ArticleData(1, "Ini Adalah Judul Artikel 1 Yang Akan Ditampilkan.", "BPH", "Harry Santoso♦", "12-12-2023 00:00:00", "https://berita.99.co/wp-content/uploads/2023/02/poster-digital.jpg"),
                new ArticleData(2, "Ini Adalah Judul Artikel 2 Yang Akan Ditampilkan.", "Humas", "Ahmad Afandi", "12-12-2023 00:00:00", "https://berita.99.co/wp-content/uploads/2023/02/budaya-membaca.jpg"),
                new ArticleData(3, "Ini Adalah Judul Artikel 3 Yang Akan Ditampilkan.", "Kominfo", "Younki Vanesta Ramadhana Pecinta Alam", "12-12-2023 00:00:00", "https://berita.99.co/wp-content/uploads/2023/02/selamatkan-bumi.jpg"),
                new ArticleData(4, "Ini Adalah Judul Artikel 4 Yang Akan Ditampilkan.", "PSDM", "Budi Tarmizi", "12-12-2023 00:00:00", "https://berita.99.co/wp-content/uploads/2023/02/poster-produk.jpg"),
        };

        HomeArticleAdapter articleAdapter = new HomeArticleAdapter(articleData,view.getContext());
        recyclerView.setAdapter(articleAdapter);


        RecyclerView recyclerView2 = view.findViewById(R.id.rapat_list);
        recyclerView2.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView2.setHasFixedSize(false);

        RapatData[] rapatData = new RapatData[]{
                new RapatData(1, "Rapat Membahas Anggota Baru", "19.00", "Rapat Himpunan", "SEMUA DIVISI", "02-06-2023"),
                new RapatData(2, "Rapat Kegiatan Bulan ini", "18.00", "Rapat Program Kerja", "HUMAS", "23-08-2023"),
                new RapatData(3, "Rapat Acara Olahraga", "13.00", "Rapat Program Kerja", "PSDM", "17-05-2023"),
                new RapatData(4, "Rapat Bulanan", "09.00", "Rapat Himpunan", "SEMUA DIVISI", "10-10-2023"),
        };

        if (rapatData.length > 1) {
            recyclerView2.setMinimumHeight(180 * 4);
        }

        RapatAdapter rapatAdapter = new RapatAdapter(rapatData, view.getContext());
        recyclerView2.setAdapter(rapatAdapter);

        return view;
    }

}
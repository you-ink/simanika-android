package com.android.simanika.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.simanika.DetailArticle;
import com.android.simanika.MenuFragment.HomeFragment;
import com.android.simanika.R;
import com.squareup.picasso.Picasso;

public class HomeArticleAdapter extends RecyclerView.Adapter<HomeArticleAdapter.ViewHolder> {
    ArticleData[] articleData;
    Context context;
    public HomeArticleAdapter(ArticleData[] articleData, Context context) {
        this.articleData = articleData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_article_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        final ArticleData articleDataList = articleData[position];
        holder.artikelDivisi.setText(articleDataList.getDivisi());
        holder.artikelJudul.setText(articleDataList.getJudul());
        holder.artikelPenulis.setText(articleDataList.getPenulis());
        holder.artikelWaktu.setText(articleDataList.getWaktu());
        holder.artikelId = articleDataList.getId();
        Picasso.get().load(articleDataList.getSampul()).into(holder.artikelSampul);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailArticle.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView artikelDivisi, artikelJudul, artikelPenulis, artikelWaktu;
        ImageView artikelSampul;
        int artikelId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artikelDivisi = itemView.findViewById(R.id.artikelDivisi);
            artikelJudul = itemView.findViewById(R.id.artikelJudul);
            artikelPenulis = itemView.findViewById(R.id.artikelPenulis);
            artikelWaktu = itemView.findViewById(R.id.artikelWaktu);
            artikelSampul = itemView.findViewById(R.id.artikelSampul);
        }
    }
}


package com.android.simanika.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.simanika.MenuFragment.ArticleFragment;
import com.android.simanika.R;
import com.squareup.picasso.Picasso;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    ArticleData[] articleData;
    Context context;
    public ArticleAdapter(ArticleData[] articleData, ArticleFragment activity) {
        this.articleData = articleData;
        this.context = activity.getContext();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.article_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
                Toast.makeText(context, articleDataList.getJudul(), Toast.LENGTH_SHORT).show();
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
        ImageButton artikelMenu;
        int artikelId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artikelDivisi = itemView.findViewById(R.id.artikelDivisi);
            artikelJudul = itemView.findViewById(R.id.artikelJudul);
            artikelPenulis = itemView.findViewById(R.id.artikelPenulis);
            artikelWaktu = itemView.findViewById(R.id.artikelWaktu);
            artikelSampul = itemView.findViewById(R.id.artikelSampul);
            artikelMenu = itemView.findViewById(R.id.artikelMenu);

            artikelMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence[] dialogitem = {"Edit Artikel", "Hapus Artikel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Kelola Artikel");
                    builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0:
                                    Toast.makeText(context, "Edit Artikel "+String.valueOf(artikelId), Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    Toast.makeText(context, "Hapus Artikel "+String.valueOf(artikelId), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                }
            });
        }
    }
}

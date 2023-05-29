package com.android.simanika.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.simanika.PresensiActivity;
import com.android.simanika.R;

public class RapatAdapter extends RecyclerView.Adapter<RapatAdapter.ViewHolder>{
    RapatData[] rapatData;
    Context context;

    public RapatAdapter(RapatData[] rapatData, Context context) {
        this.rapatData = rapatData;
        this.context = context;
    }

    @NonNull
    @Override
    public RapatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_rapat_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RapatAdapter.ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        final RapatData rapatDataList = rapatData[position];
        holder.rapatDivisi.setText(rapatDataList.getDivisi());
        holder.rapatNama.setText(rapatDataList.getNama());
        holder.rapatTanggal.setText(rapatDataList.getTanggal()+" "+rapatDataList.getWaktu_mulai());
        holder.rapatTipe.setText(rapatDataList.getTipe());
        holder.rapatId = rapatDataList.getId();

        holder.rapatAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PresensiActivity.class);
                intent.putExtra("idRapat", String.valueOf(rapatDataList.getId()));
                intent.putExtra("namaRapat", rapatDataList.getNama());
                context.startActivity(intent);
            }
        });
    }

    @Override

    public int getItemCount() {
        return rapatData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rapatDivisi, rapatNama, rapatTanggal, rapatTipe;
        Button rapatAbsen;
        int rapatId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rapatDivisi = itemView.findViewById(R.id.rapatDivisi);
            rapatNama = itemView.findViewById(R.id.rapatNama);
            rapatTanggal= itemView.findViewById(R.id.rapatTanggal);
            rapatTipe = itemView.findViewById(R.id.rapatTipe);
            rapatAbsen = itemView.findViewById(R.id.rapatAbsen);
        }
    }
}


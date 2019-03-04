package com.example.firestoreunderstanding;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

class CustomeRecylerAdapter extends RecyclerView.Adapter<CustomeRecylerAdapter.InsideKelas> {
    public CustomeRecylerAdapter(ArrayList<String> rateTry) {
    }

    @NonNull
    @Override
    public InsideKelas onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InsideKelas insideKelas, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class InsideKelas extends RecyclerView.ViewHolder {
        public InsideKelas(@NonNull View itemView) {
            super(itemView);
        }
    }
}

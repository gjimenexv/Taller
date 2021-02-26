package com.example.taller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taller.R;
import com.example.taller.gestion.EstudianteGestion;
import com.example.taller.model.Estudiante;

import java.util.List;

public class EstudianteAdapter
        extends RecyclerView.Adapter<EstudianteAdapter.EstudianteViewHolder>
    implements View.OnClickListener {

    private List<Estudiante> lista;
    private View.OnClickListener listener;

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null) {
            listener.onClick(v);
        }
    }

    public EstudianteAdapter(List<Estudiante> lista) {
        this.lista = lista;
    }

    public void refresh() {
        lista= EstudianteGestion.getEstudiante();
    }


    @NonNull
    @Override
    public EstudianteViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_estudiante,parent,false);
        vista.setOnClickListener(this);
        return new EstudianteViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull EstudianteAdapter.EstudianteViewHolder holder,
            int position) {
        holder.tvId.setText(""+lista.get(position).getId());
        holder.tvNombre.setText(lista.get(position).getNombre());
        holder.tvEdad.setText(""+lista.get(position).getEdad());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }



    public class EstudianteViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public TextView tvNombre;
        public TextView tvEdad;

        public EstudianteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvId = itemView.findViewById(R.id.tvId);
            this.tvNombre = itemView.findViewById(R.id.tvNombre);
            this.tvEdad = itemView.findViewById(R.id.tvEdad);
        }
    }
}

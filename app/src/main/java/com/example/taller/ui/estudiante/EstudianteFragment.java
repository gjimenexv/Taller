package com.example.taller.ui.estudiante;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taller.R;
import com.example.taller.adapter.EstudianteAdapter;
import com.example.taller.gestion.EstudianteGestion;
import com.example.taller.model.Estudiante;

import java.util.List;

public class EstudianteFragment extends Fragment {

    private RecyclerView recyclerView;
    private EstudianteAdapter estudianteAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_estudiante, container, false);

        List<Estudiante> lista= EstudianteGestion.getEstudiante();

        recyclerView = view.findViewById(R.id.reciclador);
        layoutManager=new LinearLayoutManager(getContext());
        estudianteAdapter=new EstudianteAdapter(lista);

        estudianteAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fila=recyclerView.getChildAdapterPosition(v);
                Estudiante estudiante=lista.get(fila);  //Obtengo el estudiante...

                Toast.makeText(getContext(),
                        "Es "+estudiante.getNombre(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(),EstudianteActivity.class);
                intent.putExtra("estudiante",estudiante);
                intent.putExtra("tipo",1); //1 es modificar/eliminar
                startActivity(intent); //presenta la pantalla...

            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(estudianteAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        estudianteAdapter.refresh();
        estudianteAdapter.notifyDataSetChanged();
    }
}
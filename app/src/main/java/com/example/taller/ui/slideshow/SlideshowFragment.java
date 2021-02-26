package com.example.taller.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.taller.R;
import com.example.taller.datos.AdminDB;
import com.example.taller.gestion.EstudianteGestion;
import com.example.taller.model.Estudiante;

public class SlideshowFragment extends Fragment {

    private EditText etId;
    private EditText etNombre;
    private EditText etEdad;

    private Button btInserta;
    private Button btConsulta;
    private Button btModifica;
    private Button btElimina;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        etId = root.findViewById(R.id.etId);
        etNombre = root.findViewById(R.id.etNombre);
        etEdad = root.findViewById(R.id.etEdad);

        btInserta = root.findViewById(R.id.btInserta);
        btConsulta = root.findViewById(R.id.btConsulta);
        btModifica = root.findViewById(R.id.btModifica);
        btElimina = root.findViewById(R.id.btElimina);

        AdminDB data = new AdminDB(getContext(),
                "base.db",
                null,
                1);

        EstudianteGestion.init(data);

        btInserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });
        btConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultar();
            }
        });
        btModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();
            }
        });
        btElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elimanar();
            }
        });


        return root;
    }
    //Limpia los contenidos de los EditText
    private void limpia(){
        etId.setText("");
        etNombre.setText("");
        etEdad.setText("");
    }
    // Presenta la informacion
    private void presenta(Estudiante estudiante){
        if (estudiante!=null){
            etId.setText(estudiante.getId());
            etNombre.setText(estudiante.getNombre());
            etEdad.setText(estudiante.getEdad());
        }else{
            limpia();
        }
    }
    //Toma la info de los edit text y crea un obj estudiante
    private Estudiante getEstudiante(){
        Estudiante estudiante = null;
        String id = etId.getText().toString();
        String nombre = etNombre.getText().toString();
        String edad = etEdad.getText().toString();
        if(!id.isEmpty()&&!nombre.isEmpty()&&!edad.isEmpty()){
            estudiante = new Estudiante(
                    Integer.parseInt(id),
                    nombre,
                    Integer.parseInt(edad)
            );
        }else{
            Toast.makeText(getContext(),"Debe llenar los campos",Toast.LENGTH_LONG);
        }
        return estudiante;
    }

    private void insertar() {
        if (EstudianteGestion.inserta(getEstudiante())){
            Toast.makeText(getContext(),"Se insertó el estudiante",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),"NO se insertó el estudiante",Toast.LENGTH_LONG).show();
        }
    }
    private void consultar() {
        String id = etId.getText().toString();
        boolean encontrado = false;
        if (!id.isEmpty()){
            int llave = Integer.parseInt(id);
            Estudiante estudiante = EstudianteGestion.busca(llave);
            if (estudiante!=null){
                presenta(estudiante);
                encontrado=true;
            }
        }
        if(!encontrado){
            Toast.makeText(getContext(),"NO se encontró el estudiante",Toast.LENGTH_LONG).show();
        }
    }
    private void modificar() {
        if (EstudianteGestion.actualizar(getEstudiante())){
            Toast.makeText(getContext(),"Se modificó el estudiante",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),"NO se modificó el estudiante",Toast.LENGTH_LONG).show();
        }
    }

    private void elimanar() {
        String id = etId.getText().toString();
        boolean eliminado = false;
        if (!id.isEmpty()){
            int llave = Integer.parseInt(id);
            eliminado = EstudianteGestion.elimina(llave);
        }
        if(!eliminado){
            Toast.makeText(getContext(),"NO se eliminó el estudiante",Toast.LENGTH_LONG).show();
        }
    }

}
package com.example.taller.ui.estudiante;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.taller.R;
import com.example.taller.model.Estudiante;

public class EstudianteActivity extends AppCompatActivity {
    private EditText etId;
    private EditText etNombre;
    private EditText etEdad;

    private Button boton1;
    private Button boton2;

    private int tipo;
    private Estudiante estudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        etId=findViewById(R.id.etId);
        etNombre=findViewById(R.id.etNombre);
        etEdad=findViewById(R.id.etEdad);
        boton1=findViewById(R.id.boton1);
        boton2=findViewById(R.id.boton2);
        tipo=getIntent().getIntExtra("tipo",0);
        if (tipo==1) {
            estudiante =
                    (Estudiante)
                            getIntent()
                                    .getSerializableExtra("estudiante");
            etId.setText(""+estudiante.getId());
            etNombre.setText(estudiante.getNombre());
            etEdad.setText(""+estudiante.getEdad());
            boton1.setText("Eliminar");
            boton2.setText("Modificar");
        }else{
            etId.setText("");
            etNombre.setText("");
            etEdad.setText("");
            boton1.setText("Crear");
            boton2.setText("Cancelar");
        }
    }
}
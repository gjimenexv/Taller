package com.example.taller.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.taller.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    //Defnir atributos locales para usar los controles visuales.
    private EditText etNumero;
    private Button btActualiza;
    private TextView tvActual;

    //Trabajo con Firebase DataBase
    private FirebaseDatabase database;  //Instancia a la base de datos
    private DatabaseReference myRef;   //Referencia a un elemento

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Mapeo los atributos con los controles
        etNumero = root.findViewById(R.id.etNumero);
        btActualiza = root.findViewById(R.id.btActualiza);
        tvActual = root.findViewById(R.id.tvNumeroActual);

        //Obtengo la instancia de la base de datos RT
        database = FirebaseDatabase.getInstance();

        //Defino el numbre del elemento a utilizar
        myRef = database.getReference("numero");

        //Agregamos un "listener" para ver cuando se cambian los datos en la referencia
        myRef.addValueEventListener(new ValueEventListener() {

            //Se programa lo que corresponde a cuando efectivamente se cambia algo en la referencia
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Convertir la instantanea en una String y asignarlo
                String valor = snapshot.getValue(String.class);
                tvActual.setText(valor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tvActual.setText("error");
            }
        });


        //Programo el evento "onClic"
        btActualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtener el valor del numero
                String strNumero = etNumero.getText().toString();
                myRef.setValue(strNumero);
            }
        });

        return root;
    }
}
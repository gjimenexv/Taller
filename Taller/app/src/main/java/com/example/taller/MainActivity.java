package com.example.taller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Variables locales para referenciar los controles visuales
    private EditText etCorreo;
    private EditText etClave;

    //Varible para crear la instancia de autenticación
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mapeo las varibles locales a los controles visuales
        etCorreo=findViewById(R.id.etCorreo);
        etClave=findViewById(R.id.etClave);

        //Obtengo la instancia desde Firebase para autenticar.
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {  //Se ejecuta cualer momento que se presenta el App en la pantalla
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }

    public void registrar(View view) {
        //Obtengo el correo que escribio el usuario
        String email = etCorreo.getText().toString();
        //Obtengo la contraseña que escribió el usuario
        String password=etClave.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(getApplicationContext(), "Fallo el registro",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user!=null) {
            //se crea y se presenta un nuevo activity...
            Intent intento = new Intent(this,Principal.class);
            startActivity(intento);
        } else {
            Toast.makeText(getApplicationContext(), "Intente de nuevo",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view) {
        //Obtengo el correo que escribio el usuario
        String email = etCorreo.getText().toString();
        //Obtengo la contraseña que escribió el usuario
        String password=etClave.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(getApplicationContext(), "Fallo el registro",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
}
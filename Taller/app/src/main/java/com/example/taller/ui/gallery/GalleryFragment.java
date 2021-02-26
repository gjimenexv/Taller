package com.example.taller.ui.gallery;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.taller.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class GalleryFragment extends Fragment {
    //Atributos para mapear los controles visuales
    private EditText etArchivo;
    private Button btDescargar;
    private Button btBuscar;
    private Button btCargar;
    private ImageView imagen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        //Mapeo los atributos a los controles visuales.
        etArchivo = root.findViewById(R.id.etArchivo);
        btDescargar=root.findViewById(R.id.btDescarga);
        btBuscar=root.findViewById(R.id.btBuscar);
        btCargar=root.findViewById(R.id.btCargar);
        imagen=root.findViewById(R.id.imagen);

        btDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargar();
            }
        });

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

        btCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargar();
            }
        });

   return root;
    }

    private void cargar() {
        //obtengo el nombre del archivo
        String archivo=etArchivo.getText().toString();
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child(archivo);
        storageReference.putFile(uri)
              .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      Toast.makeText(getContext(), "Se subió...", Toast.LENGTH_SHORT).show();
                  }
              })
              .addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(getContext(), "Error subiendo...", Toast.LENGTH_SHORT).show();
                  }
              });

    }

    private void buscar() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent,333);
    }

    private Uri uri;  //para "recordar" la ubicacion del archivo...
    public  void onActivityResult(int requestCode,int resultCode,Intent datos){
        uri = datos.getData();
        imagen.setImageURI(uri);
    }

    private void descargar() {
        //obtengo el nombre del archivo
        String archivo=etArchivo.getText().toString();
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child(archivo);

        File localFile=null;  //Archivo local y temporal

        try {
            localFile = File.createTempFile("images","jpg");
            String ubicacion=localFile.getAbsolutePath();  //Ruta del archivo temporal
            //Descargo la imagen...asincrona
            storageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            imagen.setImageBitmap(BitmapFactory.decodeFile(ubicacion));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error descargando...",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}









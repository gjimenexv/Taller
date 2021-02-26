package com.example.taller.gestion;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taller.datos.AdminDB;
import com.example.taller.model.Estudiante;

import java.util.ArrayList;

//Creamos los metodos del CRUD
public class EstudianteGestion {
    private static AdminDB data = null; //La base de datos fisica
    private static SQLiteDatabase conexion = null; //La conexion a la base datos

    /*Se inicializa la referencia de la base da datos para usar estudiantes*/
    public static void init(AdminDB data){
        EstudianteGestion.data = data;
    }

    //El inserta retorna verdadero si logra insertar un estudiante, falso si no lo logra.
    public static boolean inserta(Estudiante estudiante){
        long registro =-1;
        if (estudiante!=null){
            ContentValues info = new ContentValues(); //Repositorio generico para cualquier info
            info.put("id",estudiante.getId());
            info.put("nombre",estudiante.getNombre());
            info.put("edad",estudiante.getEdad());
            //Content values --> info --> Map -->{{"id",1},{"nombre",'Juan',{"edad",18}}

            conexion = data.getWritableDatabase();

            registro = conexion.insert("estudiante",null, info);
            conexion.close();
        }

        return registro!=-1; // Retorna verdadero si es un numero de registro valido, falso si no.
    }

    public static Estudiante busca (int id){
        Estudiante estudiante = null;
        conexion = data.getReadableDatabase();
        Cursor datos = conexion.rawQuery("select * from estudiante where id=?",
                new String[]{""+id+""});
        if (datos.moveToFirst()){
            estudiante = new Estudiante(
                    datos.getInt(0), //Id
                    datos.getString(1), //Nombre
                    datos.getInt(2) //Edad
            );
        }
        conexion.close();
        return estudiante; //Si estudiante es null no lo encontró.
    }
    //El actualiza retorna verdadero si logra actualizar un estudiante, falso si no lo logra.
    public static boolean actualizar (Estudiante estudiante){
        long registro = -1;
        if (estudiante!=null){
            ContentValues info = new ContentValues(); //Repositorio generico para cualquier info
            info.put("id",estudiante.getId());
            info.put("nombre",estudiante.getNombre());
            info.put("edad",estudiante.getEdad());
            //Content values --> info --> Map -->{{"id",1},{"nombre",'Juan',{"edad",18}}

            conexion = data.getWritableDatabase();

            registro = conexion.update("estudiante",
                    info,
                    "id=?",
                    new String[]{""+estudiante.getId()+""});
            conexion.close();
        }
        return registro==1; //Retorna true si actualizo un registro
    }
    //Retorna true si logra eliminar un estudiante
    public static boolean elimina (int id){
        conexion = data.getWritableDatabase();
        long valor = conexion.delete("estudiante","id=?",
                new String[]{""+id+""});
        conexion.close();
        return valor==1; //Si estudiante es null no lo encontró.
    }

    //Se retorna una ArrayList con todos los registros de estudiantes
    public static ArrayList<Estudiante> getEstudiantes(){
        ArrayList<Estudiante> lista = new ArrayList<>();
        conexion = data.getReadableDatabase();
        Cursor datos = conexion.rawQuery("select * from estudiante", null);
        while (datos.moveToNext()){
            lista.add(new Estudiante(
                    datos.getInt(0), //Id
                    datos.getString(1), //Nombre
                    datos.getInt(2) //Edad
            ));
        }
        conexion.close();
        return lista;
    }
}
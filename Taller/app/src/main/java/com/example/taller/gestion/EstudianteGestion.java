package com.example.taller.gestion;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taller.datos.AdminDB;
import com.example.taller.model.Estudiante;

import java.util.ArrayList;

//Esta clase tiene los métodos para implementar un CRUD de estudiante
// Create Read Update Delete
public class EstudianteGestion {
    private static AdminDB data=null; //La base de datos física
    private static SQLiteDatabase conexion = null; //La conexión a la base de datos

    //Se inicializa la referencia a la base de datos para usar estiantes
    public static void init(AdminDB data) {
        EstudianteGestion.data=data;
    }

    //El inserta retorna true si logra insertar un estudiante, false si no lo logra
    public static boolean inserta(Estudiante estudiante) {
        long registro=-1;
        if (estudiante!=null) {
            ContentValues info = new ContentValues();
            info.put("id",estudiante.getId());
            info.put("nombre",estudiante.getNombre());
            info.put("edad",estudiante.getEdad());
            //ContentValues --> info --> Map --> {{"id",1},{"nombre",'Juan'},{"edad",18}}
            conexion=data.getWritableDatabase();
            registro=conexion.insert("estudiante",
                    null,
                    info);
            conexion.close();
        }
        return registro!=-1;  //Retorna true si es un numero de registro válido, false si no lo es...
    }

    public static Estudiante busca(int id) {
        Estudiante estudiante=null;
        conexion = data.getReadableDatabase();
        Cursor datos=conexion.rawQuery("select * from estudiante where id=?",
                new String[]{""+id+""});
        //Cursor datos2=conexion.rawQuery("select * from estudiante where id="+id,null);

        if (datos.moveToFirst()) {  //Si logra ubicarse en el primer registro... lo encontró
            estudiante = new Estudiante(
                    datos.getInt(0),  //id del estudiante
                    datos.getString(1),  //nombre del estudiante
                    datos.getInt(2)   //Edad del estudiante
            );
        }
        conexion.close();
        return estudiante;  //Si estudiante es null no lo encontró.. sino entonces si lo encontró
    }

    //El actualiza retorna true si logra actualizar un estudiante, false si no lo logra
    public static boolean actualiza(Estudiante estudiante) {
        long registro=-1;
        if (estudiante!=null) {
            ContentValues info = new ContentValues();
            info.put("id",estudiante.getId());
            info.put("nombre",estudiante.getNombre());
            info.put("edad",estudiante.getEdad());
            //ContentValues --> info --> Map --> {{"id",1},{"nombre",'Juan'},{"edad",18}}
            conexion=data.getWritableDatabase();
            registro=conexion.update("estudiante",
                    info,
                    "id=?",
                    new String[]{""+estudiante.getId()+""});
            conexion.close();
        }
        return registro==1;  //Retorna true si actualizó un registro, false si no...
    }

    //retorna true si logra eliminar un estudiante... false si no lo logra
    public static boolean elimina(int id) {
        conexion=data.getWritableDatabase();
        long valor = conexion.delete("estudiante",
                "id=?",
                new String[]{""+id+""});
        conexion.close();
        return valor==1;
    }

    //Se retorna un ArrayList con todos los registros de estudiantes
    public static ArrayList<Estudiante> getEstudiante() {
        ArrayList<Estudiante> lista=new ArrayList<>();
        conexion=data.getReadableDatabase();
        Cursor datos = conexion.rawQuery("select * from estudiante",null);
        while (datos.moveToNext()) {
            lista.add(new Estudiante(
                    datos.getInt(0),  //id del estudiante
                    datos.getString(1),  //nombre del estudiante
                    datos.getInt(2)   //Edad del estudiante
            ));
        }
        conexion.close();
        return lista;
    }
}

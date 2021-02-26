package com.example.taller.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminDB extends SQLiteOpenHelper {
    public AdminDB(@Nullable Context context,
                   @Nullable String name,
                   @Nullable SQLiteDatabase.CursorFactory factory,
                   int version) {
        super(context, name, factory, version);
    }

    //El onCreate se ejecuta sólo 1 vez cuando se crea la Base de Datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table estudiante (id int primary key, nombre text, edad int)");
        db.execSQL("insert into estudiante values(1,'Juan',18)");
        db.execSQL("insert into estudiante values(2,'Pedro',19)");
        db.execSQL("insert into estudiante values(3,'Maria',20)");
        db.execSQL("insert into estudiante values(4,'Rebeca',21)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table estudiante (id int primary key, nombre text, edad int)");
        db.execSQL("insert into estudiante values(1,'Juan',18)");
        db.execSQL("insert into estudiante values(2,'Pedro',19)");
        db.execSQL("insert into estudiante values(3,'Maria',20)");
        db.execSQL("insert into estudiante values(4,'Rebeca',21)");
    }
}

package com.example.projetoapi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDHelper extends SQLiteOpenHelper {

    public static final String BANCO ="BD_Cine";
    public static final String TABELA_OBRAS="obras";
    public static final String OBRA_NOME="titulo";
    public static final String GENERO_FILME="genero";
    public static final String DIRETOR="diretor";
    public static final String CAST="elenco";
    public static final String SINOPSE="descricao";
    public static final String ID_IMBD ="id";
    public static int VERSAO = 1;

    public BDHelper(Context context) { super(context, BANCO, null, VERSAO); }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table obras" +
                        "(id string primary key, titulo text, genero text, diretor text, elenco text, descricao text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

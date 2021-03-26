package com.example.projetoapi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDHelper extends SQLiteOpenHelper {

    public static final String BANCO ="BD_Cine";
    public static final String TABELA_FILME="obras";
    public static final String NOME_FILME="titulo";
    public static final String CARTAZ_FILME="cartaz";
    public static final String ANO_FILME = "ano";
    public static final String GENERO_FILME="genero";
    public static final String DIRETOR_FILME="diretor";
    public static final String CAST_FILME="elenco";
    public static final String SINOPSE_FILME="descricao";
    public static final String ID_FILME ="_id";
    public static int VERSAO = 1;

    public BDHelper(Context context) { super(context, BANCO, null, VERSAO); }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table obras" +
                        "(_id text primary key, cartaz text, titulo text, ano int, genero text, diretor text, elenco text, descricao text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

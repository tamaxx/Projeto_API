package com.example.projetoapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BDController {

    private SQLiteDatabase bd;
    private BDHelper banco;


    public BDController(Context context) { banco = new BDHelper(context);}

    public String inserir(String titulo, String genero, String diretor, String elenco, String descricao) {
        ContentValues valores;
        long result;

        bd = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BDHelper.OBRA_NOME, titulo);
        valores.put(BDHelper.GENERO_FILME, genero);
        valores.put(BDHelper.DIRETOR, diretor);
        valores.put(BDHelper.CAST, elenco);
        valores.put(BDHelper.SINOPSE, descricao);

        result = bd.insert(BDHelper.TABELA_OBRAS, null, valores);

        if (result == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Sucesso na inserção";
        }
    }
        public Cursor carrega(){
            Cursor cursor;
            String [] dados = { banco.ID_IMBD, banco.OBRA_NOME, banco.GENERO_FILME, banco.DIRETOR, banco.CAST, banco.SINOPSE};
            bd = banco.getReadableDatabase();
            cursor = bd.query(banco.TABELA_OBRAS, dados, null ,null, null, null, null,null);
            return cursor;
        }






















}

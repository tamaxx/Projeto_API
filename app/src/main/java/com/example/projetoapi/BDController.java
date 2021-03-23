package com.example.projetoapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BDController {

    private SQLiteDatabase bd;
    private BDHelper banco;


    public BDController(Context context) { banco = new BDHelper(context);}

    public String inserir(String titulo, String cartaz, Integer ano, String genero, String diretor, String elenco, String descricao) {
        ContentValues valores;
        long result;

        bd = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BDHelper.NOME_FILME, titulo);
        valores.put(BDHelper.CARTAZ_FILME, cartaz);
        valores.put(BDHelper.ANO_FILME, ano);
        valores.put(BDHelper.GENERO_FILME, genero);
        valores.put(BDHelper.DIRETOR_FILME, diretor);
        valores.put(BDHelper.CAST_FILME, elenco);
        valores.put(BDHelper.SINOPSE_FILME, descricao);

        result = bd.insert(BDHelper.TABELA_FILME, null, valores);

        if (result == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Sucesso na inserção";
        }
    }
        public Cursor carrega(){
            Cursor cursor;
            String [] dados = { banco.ID_FILME, banco.NOME_FILME, banco.CARTAZ_FILME, banco.ANO_FILME, banco.GENERO_FILME, banco.DIRETOR_FILME, banco.CAST_FILME, banco.SINOPSE_FILME};
            bd = banco.getReadableDatabase();
            cursor = bd.query(banco.TABELA_FILME, dados, null ,null, null, null, null,null);
            return cursor;
        }






















}

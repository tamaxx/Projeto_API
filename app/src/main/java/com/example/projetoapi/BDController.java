package com.example.projetoapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public class BDController {

    private SQLiteDatabase bd;
    private BDHelper banco;

    public BDController(Context context) { banco = new BDHelper(context);}

    public String inserir(String _id, String titulo, String cartaz, String ano, String genero, String diretor, String elenco, String descricao) {
        ContentValues valores;
        long result;

        bd = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(BDHelper.ID_FILME, _id);
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

        public void deletar(String id){
            String where = BDHelper.ID_FILME + "=" + "'" + id + "'";
            bd = banco.getReadableDatabase();
            bd.delete(BDHelper.TABELA_FILME, where, null);
        }

        public boolean procuraID(String id) {
            bd = banco.getReadableDatabase();
            long queryRows = DatabaseUtils.queryNumEntries(bd, BDHelper.TABELA_FILME, BDHelper.ID_FILME + "=?", new String[] {id});
            if(queryRows > 0){
                return true;
            }
            else{
                return false;
            }
        }

        public Cursor carregaAleatorio(){
            Cursor cursor;
            bd = banco.getReadableDatabase();
            String[] poster = {banco.CARTAZ_FILME};
            cursor = bd.query(banco.TABELA_FILME, poster, null, null, null, null, "random()", "1");

            return cursor;
        }























}

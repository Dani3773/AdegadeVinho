package com.example.adegadevinho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class VinhoDAO {

    private DatabaseHelper dbHelper;

    public VinhoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long inserirVinho(Vinho vinho) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_VINHO_NOME, vinho.getNome());
        values.put(DatabaseHelper.COLUMN_VINHO_TIPO, vinho.getTipo());
        values.put(DatabaseHelper.COLUMN_VINHO_IMAGEM, vinho.getImagem());
        values.put(DatabaseHelper.COLUMN_VINHO_DESCRICAO, vinho.getDescricao());
        values.put(DatabaseHelper.COLUMN_VINHO_PRECO, vinho.getPreco());
        values.put(DatabaseHelper.COLUMN_VINHO_SAFRA, vinho.getSafra());
        values.put(DatabaseHelper.COLUMN_VINHO_HARMONIZACAO, vinho.getHarmonizacao());

        long id = db.insert(DatabaseHelper.TABLE_VINHOS, null, values);
        db.close();
        return id;
    }

    public List<Vinho> getTodosVinhos() {
        List<Vinho> listaVinhos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_VINHOS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VINHO_ID));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VINHO_NOME));
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VINHO_TIPO));
                int imagem = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VINHO_IMAGEM));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VINHO_DESCRICAO));
                double preco = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VINHO_PRECO));
                int safra = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VINHO_SAFRA));
                String harmonizacao = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VINHO_HARMONIZACAO));

                Vinho vinho = new Vinho(nome, tipo, imagem, descricao, preco, safra, harmonizacao);
                vinho.setId(id);
                listaVinhos.add(vinho);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();

        return listaVinhos;
    }
    public boolean atualizarVinho(Vinho vinho) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_VINHO_NOME, vinho.getNome());
        values.put(DatabaseHelper.COLUMN_VINHO_TIPO, vinho.getTipo());
        values.put(DatabaseHelper.COLUMN_VINHO_IMAGEM, vinho.getImagem());
        values.put(DatabaseHelper.COLUMN_VINHO_DESCRICAO, vinho.getDescricao());
        values.put(DatabaseHelper.COLUMN_VINHO_PRECO, vinho.getPreco());
        values.put(DatabaseHelper.COLUMN_VINHO_SAFRA, vinho.getSafra());
        values.put(DatabaseHelper.COLUMN_VINHO_HARMONIZACAO, vinho.getHarmonizacao());

        int linhasAfetadas = db.update(DatabaseHelper.TABLE_VINHOS, values,
                DatabaseHelper.COLUMN_VINHO_ID + " = ?", new String[]{String.valueOf(vinho.getId())});
        db.close();
        return linhasAfetadas > 0;
    }
}


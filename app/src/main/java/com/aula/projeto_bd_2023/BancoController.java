package com.aula.projeto_bd_2023;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class BancoController {
    private SQLiteDatabase db;
    private CriaBanco banco;


    public BancoController(Context context) {
        banco = new CriaBanco(context);
    }
    //--------------------------------------------------------------------------------------------------

    //inclusão de dados na tabela de Usuarios
    public String insereDadosUsuario(String txtNome, String txtEmail, String txtSenha) {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();


        valores = new ContentValues();
        valores.put("nome", txtNome);
        valores.put("email", txtEmail);
        valores.put("senha", txtSenha);


        resultado = db.insert("usuarios", null, valores);
        db.close();


        if (resultado == -1)
            return "Erro ao inserir os dados do usuário!";
        else
            return "Dados Cadastrados com sucesso!";
    }

    // consulta dados para login / senha
    public Cursor carregaDadosPeloEmailSenha(String email, String senha) {
        Cursor cursor;
        //SELECT idUser, nome, cpf, email, senha FROM usuarios WHERE email = 'digitado' and senha = 'digitada'
        String[] campos = {"idUser", "nome", "email", "senha"};
        String where = "email = '" + email + "' and senha = '" + senha + "' ";
        db = banco.getReadableDatabase();
        cursor = db.query("usuarios", campos, where, null, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void inserirTask(ToDoModel model) {
        db = banco.getWritableDatabase();  // abrir banco de dados para gravação
        ContentValues valores = new ContentValues();
        valores.put("tasktext", model.getTask());
        valores.put("status", 0);

        db.insert("tasks", null, valores);
        db.close();

    }

    public void updateTask(int id, String task) {
        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();

        valores.put("tasktext", task);
        db.update("tasks", valores, "idTask=?", new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status) {
        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();

        valores.put("status", status);
        db.update("tasks", valores, "idTask=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db = banco.getWritableDatabase();
        db.delete("tasks", "idTask=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTask() {

        db = banco.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query("tasks", null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex("idTask")));
                        task.setTask(cursor.getString(cursor.getColumnIndex("tasktext")));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                        modelList.add(task);

                    } while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            if (cursor != null) {
                cursor.close();
            }
        }
        return modelList;
    }
}





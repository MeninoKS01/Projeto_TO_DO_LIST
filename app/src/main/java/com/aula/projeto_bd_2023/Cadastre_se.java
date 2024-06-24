package com.aula.projeto_bd_2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Cadastre_se extends AppCompatActivity implements View.OnClickListener {
    EditText txtCADNome, txtCADEmail, txtCADSenha1, txtCADSenha2;
    Button btCADGravar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastre_se);

        txtCADNome = findViewById(R.id.txtCADNome);
        txtCADEmail = findViewById(R.id.txtCADEmail);
        txtCADSenha1 = findViewById(R.id.txtCADSenha1);
        txtCADSenha2 = findViewById(R.id.txtCADSenha2);
        btCADGravar = findViewById(R.id.btCADGravar);

        btCADGravar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (validaDados()) {
            // gravardados
            SalvarDados();
        }
    }

    public boolean validaDados() {
        boolean retorno = true;
        String msg;
        if (txtCADNome.getText().length() == 0) {
            msg = "O campo NOME deve ser preenchido";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }
        if (txtCADEmail.getText().length() == 0) {
            msg = "O campo EMAIL deve ser preenchido";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }
        if (txtCADSenha1.getText().length() == 0 || txtCADSenha2.getText().length() == 0) {
            msg = "Os campos de SENHA devem ser preenchidos";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }
        if (!txtCADSenha1.getText().toString().equals(txtCADSenha2.getText().toString())) {
            msg = "Os campos de SENHA e CONFIRMA SENHA devem ser iguais";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            retorno = false;
        }
        return retorno;
    }

    public void SalvarDados() {
        String msg = "";
        String nome = txtCADNome.getText().toString();
        String email = txtCADEmail.getText().toString();
        String senha = txtCADSenha1.getText().toString();

        BancoController bd = new BancoController(getBaseContext());
        String resultado;

        resultado = bd.insereDadosUsuario(nome, email, senha);

        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
        limpar();
    }

    public void limpar() {
        txtCADNome.setText("");
        txtCADEmail.setText("");
        txtCADSenha1.setText("");
        txtCADSenha2.setText("");
    }

}
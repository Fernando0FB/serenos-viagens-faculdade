package com.example.serenosviagens.activities.registrar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.LoginActivity;
import com.example.serenosviagens.database.DAO.UsuarioDAO;
import com.example.serenosviagens.database.Models.UsuarioModel;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    private EditText nome, email, usuario, cpf, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);

        nome = findViewById(R.id.edit_text_name);
        email = findViewById(R.id.edit_text_email);
        usuario = findViewById(R.id.edit_text_user);
        cpf = findViewById(R.id.edit_text_cpf);
        senha = findViewById(R.id.edit_text_password);

        Button registerButton = findViewById(R.id.loginButton);
        registerButton.setOnClickListener( v -> registrar());

        Button conectButton = findViewById(R.id.registerButton);
        conectButton.setOnClickListener( v -> {
            Intent intent = new Intent( RegistrarUsuarioActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }


    private void registrar() {
        String nomeText = nome.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String usuarioText = usuario.getText().toString().trim();
        String cpfText = cpf.getText().toString();
        String senhaText = senha.getText().toString();

        if (nomeText.isEmpty() || (emailText.isEmpty()) || usuarioText.isEmpty() || cpfText.isEmpty() || senhaText.isEmpty()) {
            Toast.makeText(RegistrarUsuarioActivity.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        } else if (!isValidEmail(emailText)) {
            Toast.makeText(RegistrarUsuarioActivity.this, "Por favor, insira um email válido!", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioModel usuarioModel = new UsuarioModel(
                nomeText,
                emailText,
                cpfText,
                usuarioText,
                senhaText
        );

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO(RegistrarUsuarioActivity.this);
            usuarioDAO.Insert(usuarioModel);

            Toast.makeText(RegistrarUsuarioActivity.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RegistrarUsuarioActivity.this, LoginActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(RegistrarUsuarioActivity.this, "Erro ao inserir usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}

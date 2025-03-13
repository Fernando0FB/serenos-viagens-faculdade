package com.example.serenosviagens.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarViagensActivity;
import com.example.serenosviagens.activities.registrar.RegistrarUsuarioActivity;
import com.example.serenosviagens.database.DAO.UsuarioDAO;
import com.example.serenosviagens.database.Models.UsuarioModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.edit_text_username);
        password = findViewById(R.id.edit_text_password);

        Button registerButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrarUsuarioActivity.class);
            startActivity(intent);
        });
        loginButton.setOnClickListener(v -> checkLogin());
    }

    private void checkLogin() {
        String loginText = username.getText().toString();
        String passwordText = password.getText().toString();

        if(loginText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha as informações de Usuário e Senha", Toast.LENGTH_SHORT).show();
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO(LoginActivity.this);
        UsuarioModel usuario = usuarioDAO.Select(loginText, passwordText);

        if(usuario == null) {
            Toast.makeText(this, "Usuário não encontrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!usuario.getSenha().equals(passwordText)) {
            Toast.makeText(this, "Senha inválida, tente novamente!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong("userLogado", usuario.getId());
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, ListarViagensActivity.class);
        startActivity(intent);
    }
}

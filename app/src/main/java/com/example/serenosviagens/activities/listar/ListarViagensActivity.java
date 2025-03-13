package com.example.serenosviagens.activities.listar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.LoginActivity;
import com.example.serenosviagens.activities.registrar.RegistrarViagemActivity;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Adapters.ViagemAdapter;

public class ListarViagensActivity extends AppCompatActivity {
    private ListView lista_viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_viagem);
        lista_viagem = findViewById(R.id.lista_viagem);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ListarViagensActivity.this);
        Long idUsuario = sharedPreferences.getLong("userLogado", -1);

        try {
            ViagemDAO viagemDAO = new ViagemDAO(ListarViagensActivity.this);
            ViagemAdapter adapter = new ViagemAdapter(ListarViagensActivity.this);
            adapter.setItens(viagemDAO.getByUserId(idUsuario));
            lista_viagem.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(ListarViagensActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        Button btnNovaViagem = findViewById(R.id.btnNovaViagem);
        btnNovaViagem.setOnClickListener(v -> {
                startActivity(new Intent(ListarViagensActivity.this, RegistrarViagemActivity.class));
        });

    }

}

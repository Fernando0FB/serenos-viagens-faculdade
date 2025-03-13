package com.example.serenosviagens.activities.visualizar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.editar.EditarViagemActivity;
import com.example.serenosviagens.activities.listar.ListarDespesasActivity;
import com.example.serenosviagens.activities.listar.ListarViagensActivity;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Models.ViagemModel;

public class VisualizarViagemActivity extends AppCompatActivity {

    ViagemDAO viagemDAO = new ViagemDAO(VisualizarViagemActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_viagem);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(VisualizarViagemActivity.this);
        Long idViagem = sharedPreferences.getLong("ViagemAtual", -1);
        ViagemModel model = viagemDAO.getById(idViagem);

        TextView titulo = findViewById(R.id.tituloViagem);
        titulo.setText(model.getDescricao());

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(VisualizarViagemActivity.this, ListarViagensActivity.class);
            startActivity(intent);
        });

        Button calcularTotal = findViewById(R.id.calcular);
        calcularTotal.setOnClickListener(v -> {
            Intent intent = new Intent(VisualizarViagemActivity.this, VisualizarResultadoActivity.class);
            intent.putExtra("idViagem", idViagem);
            startActivity(intent);
        });

        Button editarViagem = findViewById(R.id.editar);
        editarViagem.setOnClickListener(v -> {
            Intent intent = new Intent(VisualizarViagemActivity.this, EditarViagemActivity.class);
            intent.putExtra("idViagem", idViagem);
            startActivity(intent);
        });

        Button listarDespesasButton = findViewById(R.id.lista);
        listarDespesasButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Intent intent = new Intent(VisualizarViagemActivity.this, ListarDespesasActivity.class);
            startActivity(intent);
        });
    }



}

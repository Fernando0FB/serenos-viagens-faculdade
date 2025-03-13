package com.example.serenosviagens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarDespesasActivity;
import com.example.serenosviagens.activities.registrar.RegistrarAereoActivity;
import com.example.serenosviagens.activities.registrar.RegistrarDiversosActivity;
import com.example.serenosviagens.activities.registrar.RegistrarGasolinaActivity;
import com.example.serenosviagens.activities.registrar.RegistrarHospedagemActivity;
import com.example.serenosviagens.activities.registrar.RegistrarRefeicaoActivity;

public class SelecionarDespesaActivity extends AppCompatActivity {
    private EditText valor, descricao;
    private Button criarGasolina, criarAereo, criarRefeicoes, criarHospedagem, criarDiversos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_gasto);

        criarGasolina = findViewById(R.id.calcular_gasolina);
        criarAereo = findViewById(R.id.calcular_aereo);
        criarRefeicoes = findViewById(R.id.teste);
        criarHospedagem = findViewById(R.id.calcular_hospedagem);
        criarDiversos = findViewById(R.id.calcular_diversos);

        criarGasolina.setOnClickListener(v -> {
            Intent intent = new Intent(SelecionarDespesaActivity.this, RegistrarGasolinaActivity.class);
            startActivity(intent);
        });

        criarAereo.setOnClickListener(v -> {
            Intent intent = new Intent(SelecionarDespesaActivity.this, RegistrarAereoActivity.class);
            startActivity(intent);
        });

        criarRefeicoes.setOnClickListener(v -> {
            Intent intent = new Intent(SelecionarDespesaActivity.this, RegistrarRefeicaoActivity.class);
            startActivity(intent);
        });

        criarHospedagem.setOnClickListener(v -> {
            Intent intent = new Intent(SelecionarDespesaActivity.this, RegistrarHospedagemActivity.class);
            startActivity(intent);
        });

        criarDiversos.setOnClickListener(v -> {
            Intent intent = new Intent(SelecionarDespesaActivity.this, RegistrarDiversosActivity.class);
            startActivity(intent);
        });

        ImageButton botaoVolta = findViewById(R.id.buttonBack);
        botaoVolta.setOnClickListener(v -> {
            Intent intent = new Intent(SelecionarDespesaActivity.this, ListarDespesasActivity.class);
            startActivity(intent);
        });
    }
}

package com.example.serenosviagens.activities.visualizar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarDespesasActivity;
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.DAO.DiversosDAO;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.DiversosModel;

import java.util.Objects;

public class VisualizarDiversosActivity extends AppCompatActivity {

    private DespesasDAO despesasDAO = new DespesasDAO(VisualizarDiversosActivity.this);
    private DiversosDAO diversosDAO = new DiversosDAO(VisualizarDiversosActivity.this);

    private TextView descricao, valor, adicionarTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_diversos);

        descricao = findViewById(R.id.descricaoDiversosView);
        valor = findViewById(R.id.valorDiversosView);
        adicionarTotal = findViewById(R.id.adicionar_total_view);

        Long idDespesa = getIntent().getLongExtra("despesaId", -1L);
        DespesasModel despesasModel = despesasDAO.getById(Long.valueOf(idDespesa));
        DiversosModel diversosModel = diversosDAO.getByDespesaId(despesasModel.getId());
        String adicionarTotalText = Objects.equals(despesasModel.getAdcTotal(), "S") ? "Sim" : "Não";

        descricao.setText(diversosModel.getEntretenimento());
        valor.setText(diversosModel.getValor().toString());
        adicionarTotal.setText(adicionarTotalText);

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(VisualizarDiversosActivity.this, ListarDespesasActivity.class);
            startActivity(intent);
        });
    }
}

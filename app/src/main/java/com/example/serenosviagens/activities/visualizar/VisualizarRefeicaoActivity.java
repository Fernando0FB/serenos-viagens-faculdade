package com.example.serenosviagens.activities.visualizar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarDespesasActivity;
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.DAO.RefeicoesDAO;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.RefeicoesModel;
import com.example.serenosviagens.utils.Utils;

import java.util.Objects;

public class VisualizarRefeicaoActivity extends AppCompatActivity {
    DespesasDAO despesasDAO = new DespesasDAO(VisualizarRefeicaoActivity.this);
    RefeicoesDAO refeicoesDAO = new RefeicoesDAO(VisualizarRefeicaoActivity.this);
    Utils utils = new Utils();

    TextView custoRefeicao, quantRefeicoesDia, adicionarTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_refeicao);

        custoRefeicao = findViewById(R.id.custo_refeicao_view);
        quantRefeicoesDia = findViewById(R.id.refeicoes_dia_view);
        adicionarTotal = findViewById(R.id.adicionar_total_view);

        Long idDespesa = getIntent().getLongExtra("despesaId", -1L);
        DespesasModel despesasModel = despesasDAO.getById(Long.valueOf(idDespesa));
        RefeicoesModel refeicoesModel = refeicoesDAO.getByDespesaId(despesasModel.getId());
        String adicionarTotalText = Objects.equals(despesasModel.getAdcTotal(), "S") ? "Sim" : "Não";

        custoRefeicao.setText(utils.formataValor(refeicoesModel.getCustoRefeicao()));
        quantRefeicoesDia.setText(refeicoesModel.getRefeicoesDia().toString());
        adicionarTotal.setText(adicionarTotalText);

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(VisualizarRefeicaoActivity.this, ListarDespesasActivity.class);
            startActivity(intent);
        });
    }

}

package com.example.serenosviagens.activities.visualizar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarDespesasActivity;
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.DAO.HospedagemDAO;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.HospedagemModel;

import java.util.Objects;

public class VisualizarHospedagemActivity extends AppCompatActivity {

    private DespesasDAO despesasDAO = new DespesasDAO(VisualizarHospedagemActivity.this);
    private HospedagemDAO hospedagemDAO = new HospedagemDAO(VisualizarHospedagemActivity.this);

    private TextView custoMedioNoite, totalNoites, totalQuartos, adicionarTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_hospedagem);

        custoMedioNoite = findViewById(R.id.custoM_noite_view);
        totalNoites = findViewById(R.id.total_noites_view);
        totalQuartos = findViewById(R.id.total_quartos_view);
        adicionarTotal = findViewById(R.id.adicionar_total_view);

        Long idDespesa = getIntent().getLongExtra("despesaId", -1L);
        DespesasModel despesasModel = despesasDAO.getById(Long.valueOf(idDespesa));
        HospedagemModel hospedagemModel = hospedagemDAO.getByDespesaId(despesasModel.getId());
        String adicionarTotalText = Objects.equals(despesasModel.getAdcTotal(), "S") ? "Sim" : "Não";


        custoMedioNoite.setText(hospedagemModel.getCustoMedioNoite().toString());
        totalNoites.setText(hospedagemModel.getTotalNoite().toString());
        totalQuartos.setText(hospedagemModel.getTotalQuartos().toString());
        adicionarTotal.setText(adicionarTotalText);

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(VisualizarHospedagemActivity.this, ListarDespesasActivity.class);
            startActivity(intent);
        });
    }
}

package com.example.serenosviagens.activities.visualizar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarDespesasActivity;
import com.example.serenosviagens.database.DAO.AereoDAO;
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.Models.AereoModel;
import com.example.serenosviagens.database.Models.DespesasModel;

import java.util.Objects;

public class VisualizarAereoActivity extends AppCompatActivity {

    private DespesasDAO despesasDAO = new DespesasDAO(VisualizarAereoActivity.this);
    private AereoDAO aereoDAO = new AereoDAO(VisualizarAereoActivity.this);

    private TextView aluguelVeiculo, custoPessoa, adicionarTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_aereo);

        aluguelVeiculo = findViewById(R.id.aluguel_veiculo_view);
        custoPessoa = findViewById(R.id.custo_pessoa_view);
        adicionarTotal = findViewById(R.id.adicionar_total_view);

        Long idDespesa = getIntent().getLongExtra("despesaId", -1L);
        DespesasModel despesasModel = despesasDAO.getById(Long.valueOf(idDespesa));
        AereoModel aereoModel = aereoDAO.getByDespesaId(despesasModel.getId());
        String adicionarTotalText = Objects.equals(despesasModel.getAdcTotal(), "S") ? "Sim" : "Não";

        aluguelVeiculo.setText(aereoModel.getCustoAluguelVeiculo().toString());
        custoPessoa.setText(aereoModel.getCustoPessoa().toString());
        adicionarTotal.setText(adicionarTotalText);

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(VisualizarAereoActivity.this, ListarDespesasActivity.class);
            startActivity(intent);
        });
    }

}

package com.example.serenosviagens.activities.visualizar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarDespesasActivity;
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.DAO.GasolinaDAO;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.GasolinaModel;
import com.example.serenosviagens.utils.Utils;

import java.util.Objects;

public class VisualizarGasolinaActivity extends AppCompatActivity {

    DespesasDAO despesasDAO = new DespesasDAO(VisualizarGasolinaActivity.this);
    GasolinaDAO gasolinaDAO = new GasolinaDAO(VisualizarGasolinaActivity.this);
    Utils utils = new Utils();

    TextView totalKm, mediaKmL, custoLitroGasolina, totalVeiculos, adicionarTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_gasolina);
        totalKm = findViewById(R.id.total_km);
        mediaKmL = findViewById(R.id.media_km_l);
        custoLitroGasolina = findViewById(R.id.custo_l_gasolina);
        totalVeiculos = findViewById(R.id.total_veiculos);
        adicionarTotal = findViewById(R.id.adicionar_total_view);

        Long idDespesa = getIntent().getLongExtra("despesaId", -1L);
        DespesasModel despesasModel = despesasDAO.getById(Long.valueOf(idDespesa));
        GasolinaModel gasolinaModel = gasolinaDAO.getByDespesaId(despesasModel.getId());
        String adicionarTotalText = Objects.equals(despesasModel.getAdcTotal(), "S") ? "Sim" : "Não";

        totalKm.setText(gasolinaModel.getTotalEstimadoKM().toString() + " Km");
        mediaKmL.setText(gasolinaModel.getMediaKMLitro().toString() + " L");
        custoLitroGasolina.setText(utils.formataValor(gasolinaModel.getCustoMedioLitro()));
        totalVeiculos.setText(String.valueOf(gasolinaModel.getTotalVeiculos()));
        adicionarTotal.setText(adicionarTotalText);

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(VisualizarGasolinaActivity.this, ListarDespesasActivity.class);
            startActivity(intent);
        });
    }

}

package com.example.serenosviagens.activities.registrar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarDespesasActivity;
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.DAO.HospedagemDAO;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.HospedagemModel;
import com.example.serenosviagens.database.Models.ViagemModel;
import com.example.serenosviagens.utils.Formulas;
import com.example.serenosviagens.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;

public class RegistrarHospedagemActivity extends AppCompatActivity {

    Utils utils = new Utils();
    Formulas formulas = new Formulas();
    ViagemDAO viagemDAO = new ViagemDAO(RegistrarHospedagemActivity.this);

    private TextInputEditText custoMedioNoite, quantTotalNoites, totalQuartos;
    private Spinner adicionarViagem;

    @Override
    protected void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.criar_gasto_hospedagem);

        custoMedioNoite = findViewById(R.id.custoM_noite);
        quantTotalNoites = findViewById(R.id.total_noites);
        totalQuartos = findViewById(R.id.total_quartos);
        adicionarViagem = findViewById(R.id.adicionar_total);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_adicao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adicionarViagem.setAdapter(adapter);

        Button createButton = findViewById(R.id.button_new_hospedagem);
        createButton.setOnClickListener(v -> {
            if (!utils.campoIsValid(custoMedioNoite.getText()) || !utils.campoIsValid(quantTotalNoites.getText()) || !utils.campoIsValid(totalQuartos.getText()) || adicionarViagem.getSelectedItem().toString().isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegistrarHospedagemActivity.this);
            Long idViagem = sharedPreferences.getLong("ViagemAtual", -1);
            ViagemModel modelViagem = viagemDAO.getById(idViagem);

            BigDecimal custoMedioNoiteValue = new BigDecimal(custoMedioNoite.getText().toString());
            Integer quantTotalNoitesValue = Integer.valueOf(quantTotalNoites.getText().toString());
            Integer totalQuartosValue = Integer.valueOf(totalQuartos.getText().toString());
            String adicionarViagemValue = utils.getInicialSimNao(adicionarViagem.getSelectedItem().toString());

            BigDecimal valorCalculado = formulas.calculaHospedagem(custoMedioNoiteValue, quantTotalNoitesValue, totalQuartosValue);

            Long despesaId = null;

            try {
                DespesasModel despesasModel = new DespesasModel("Gasto com Hospedagem", "HOSPEDAGEM", valorCalculado, adicionarViagemValue, idViagem);
                DespesasDAO despesasDAO = new DespesasDAO(RegistrarHospedagemActivity.this);

                despesaId = despesasDAO.insert(despesasModel);
                viagemDAO.setSincronizado(idViagem, false);
            } catch (Exception ex) {
                Toast.makeText(this, "Erro ao criar a despesa! " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (despesaId != null) {
                try {
                    HospedagemModel hospedagemModel = new HospedagemModel(despesaId, custoMedioNoiteValue, quantTotalNoitesValue, totalQuartosValue);
                    HospedagemDAO hospedagemDAO = new HospedagemDAO(RegistrarHospedagemActivity.this);

                    hospedagemDAO.Insert(hospedagemModel);

                    Intent intent = new Intent(RegistrarHospedagemActivity.this, ListarDespesasActivity.class);
                    startActivity(intent);
                } catch (Exception ex) {
                    Toast.makeText(this, "Erro ao criar aereo!" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

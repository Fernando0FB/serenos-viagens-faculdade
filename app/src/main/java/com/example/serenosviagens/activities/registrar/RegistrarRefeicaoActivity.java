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
import com.example.serenosviagens.database.DAO.RefeicoesDAO;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.RefeicoesModel;
import com.example.serenosviagens.database.Models.ViagemModel;
import com.example.serenosviagens.utils.Formulas;
import com.example.serenosviagens.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;

public class RegistrarRefeicaoActivity extends AppCompatActivity {

    Utils utils = new Utils();
    Formulas formulas = new Formulas();
    ViagemDAO viagemDAO = new ViagemDAO(RegistrarRefeicaoActivity.this);


    private TextInputEditText custoRefeicao, refeicoesDia;
    private Spinner adicionarViagem;

    @Override
    protected void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.criar_gasto_refeicao);

        custoRefeicao = findViewById(R.id.custo_refeicao);
        refeicoesDia = findViewById(R.id.refeicoes_dia);
        adicionarViagem = findViewById(R.id.adicionar_total);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_adicao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adicionarViagem.setAdapter(adapter);

        Button createButton = findViewById(R.id.button_new_refeicao);
        createButton.setOnClickListener(v -> {
            if (!utils.campoIsValid(custoRefeicao.getText()) || !utils.campoIsValid(refeicoesDia.getText()) || adicionarViagem.getSelectedItem().toString().isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegistrarRefeicaoActivity.this);
            Long idViagem = sharedPreferences.getLong("ViagemAtual", -1);
            ViagemModel modelViagem = viagemDAO.getById(idViagem);

            BigDecimal custoRefeicaoValue = new BigDecimal(custoRefeicao.getText().toString());
            Integer refeicoesDiaValue = Integer.valueOf(refeicoesDia.getText().toString());
            String adicionarViagemValue = utils.getInicialSimNao(adicionarViagem.getSelectedItem().toString());
            Integer duracaoViagem = utils.getDuracaoViagem(modelViagem.getData(), modelViagem.getDataFim());

            BigDecimal valorCalculo = formulas.calculaRefeicao(refeicoesDiaValue, modelViagem.getQuantPessoas(), custoRefeicaoValue, duracaoViagem);

            Long despesaId = null;

            try {
                DespesasModel despesasModel = new DespesasModel("Gasto com refeição", "REFEICAO", valorCalculo, adicionarViagemValue, idViagem);
                DespesasDAO despesasDAO = new DespesasDAO(RegistrarRefeicaoActivity.this);

                despesaId = despesasDAO.insert(despesasModel);
                viagemDAO.setSincronizado(idViagem, false);
            } catch (Exception ex) {
                Toast.makeText(this, "Erro ao criar a despesa! " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (despesaId != null) {
                try {
                    RefeicoesModel refeicoesModel = new RefeicoesModel(despesaId, custoRefeicaoValue, refeicoesDiaValue);
                    RefeicoesDAO refeicoesDAO = new RefeicoesDAO(RegistrarRefeicaoActivity.this);

                    refeicoesDAO.Insert(refeicoesModel);

                    Intent intent = new Intent(RegistrarRefeicaoActivity.this, ListarDespesasActivity.class);
                    startActivity(intent);
                } catch (Exception ex) {
                    Toast.makeText(this, "Erro ao criar aereo!" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

}

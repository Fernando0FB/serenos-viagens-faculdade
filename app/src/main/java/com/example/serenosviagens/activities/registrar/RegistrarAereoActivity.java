package com.example.serenosviagens.activities.registrar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarDespesasActivity;
import com.example.serenosviagens.activities.SelecionarDespesaActivity;
import com.example.serenosviagens.database.DAO.AereoDAO;
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Models.AereoModel;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.ViagemModel;
import com.example.serenosviagens.utils.Formulas;
import com.example.serenosviagens.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;

public class RegistrarAereoActivity extends AppCompatActivity {

    Utils utils = new Utils();
    Formulas formulas = new Formulas();
    ViagemDAO viagemDAO = new ViagemDAO(RegistrarAereoActivity.this);

    private TextInputEditText custoPorPessoa, aluguelVeiculo;

    private Spinner adicionarViagem;

    @Override
    protected void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.criar_gasto_aereo);

        custoPorPessoa = findViewById(R.id.custo_p_pessoa);
        aluguelVeiculo = findViewById(R.id.aluguel_veiculo);
        adicionarViagem = findViewById(R.id.adicionar_total);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_adicao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adicionarViagem.setAdapter(adapter);

        Button createButton = findViewById(R.id.button_new_aereo);
        createButton.setOnClickListener(v -> {
            if(!utils.campoIsValid(custoPorPessoa.getText()) || !utils.campoIsValid(aluguelVeiculo.getText()) || adicionarViagem.getSelectedItem().toString().isEmpty()){
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegistrarAereoActivity.this);
            Long idViagem = sharedPreferences.getLong("ViagemAtual", -1);
            ViagemModel modelViagem = viagemDAO.getById(idViagem);

            BigDecimal custoPorPessoaValue = new BigDecimal(custoPorPessoa.getText().toString());
            BigDecimal aluguelVeiculoValue = new BigDecimal(aluguelVeiculo.getText().toString());
            Integer totalViajantes = modelViagem.getQuantPessoas();
            String adicionarViagemValue = utils.getInicialSimNao(adicionarViagem.getSelectedItem().toString());

            BigDecimal valorCalculo = formulas.calculaAereo(custoPorPessoaValue, aluguelVeiculoValue, totalViajantes);

            Long despesaId = null;

            try {
                DespesasModel despesasModel = new DespesasModel("Gasto com tarífa aérea", "AEREO", valorCalculo, adicionarViagemValue, idViagem);
                DespesasDAO despesasDAO = new DespesasDAO(RegistrarAereoActivity.this);

                despesaId = despesasDAO.insert(despesasModel);
                Toast.makeText(this, "DespesaId: "+ despesaId, Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "Erro ao criar a despesa! " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (despesaId != null) {
                try {
                    AereoModel aereoModel = new AereoModel(despesaId, custoPorPessoaValue, aluguelVeiculoValue);
                    AereoDAO aereoDAO = new AereoDAO(RegistrarAereoActivity.this);

                    aereoDAO.Insert(aereoModel);
                    viagemDAO.setSincronizado(idViagem, false);

                    Intent intent = new Intent(RegistrarAereoActivity.this, ListarDespesasActivity.class);
                    startActivity(intent);
                } catch (Exception ex) {
                    Toast.makeText(this, "Erro ao criar aereo!" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton botaoVolta = findViewById(R.id.buttonBack);
        botaoVolta.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarAereoActivity.this, SelecionarDespesaActivity.class);
            startActivity(intent);
        });
    }
}

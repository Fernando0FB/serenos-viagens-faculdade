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
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.DAO.DiversosDAO;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.DiversosModel;
import com.example.serenosviagens.database.Models.ViagemModel;
import com.example.serenosviagens.utils.Formulas;
import com.example.serenosviagens.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;

public class RegistrarDiversosActivity extends AppCompatActivity {

    Utils utils = new Utils();
    Formulas formulas = new Formulas();
    ViagemDAO viagemDAO = new ViagemDAO(RegistrarDiversosActivity.this);

    private TextInputEditText descricao, valor;
    private Spinner adicionarViagem;

    @Override
    protected void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.criar_gasto_diverso);

        adicionarViagem = findViewById(R.id.adicionar_total);

        descricao = findViewById(R.id.descricaoDiversos);
        valor = findViewById(R.id.valorDiversos);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_adicao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adicionarViagem.setAdapter(adapter);

        Button createButton = findViewById(R.id.button_new_diversos);
        createButton.setOnClickListener(v -> {

            if (!utils.campoIsValid(descricao.getText()) || !utils.campoIsValid(valor.getText()) || adicionarViagem.getSelectedItem().toString().isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegistrarDiversosActivity.this);
            Long idViagem = sharedPreferences.getLong("ViagemAtual", -1);
            ViagemModel modelViagem = viagemDAO.getById(idViagem);

            String descricaoValue = descricao.getText().toString();
            BigDecimal valorValue = new BigDecimal(valor.getText().toString());
            String adicionarViagemValue = utils.getInicialSimNao(adicionarViagem.getSelectedItem().toString());

            Long despesaId = null;

            try {
                DespesasModel despesasModel = new DespesasModel(descricaoValue, "DIVERSOS", valorValue, adicionarViagemValue, idViagem);
                DespesasDAO despesasDAO = new DespesasDAO(RegistrarDiversosActivity.this);

                despesaId = despesasDAO.insert(despesasModel);
                viagemDAO.setSincronizado(idViagem, false);
            } catch (Exception ex) {
                Toast.makeText(this, "Erro ao criar a despesa! " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (despesaId != null) {
                try {

                    DiversosModel diversosModel = new DiversosModel(despesaId, valorValue, descricaoValue);
                    DiversosDAO diversosDAO = new DiversosDAO(RegistrarDiversosActivity.this);

                    diversosDAO.Insert(diversosModel);

                    Intent intent = new Intent(RegistrarDiversosActivity.this, ListarDespesasActivity.class);
                    startActivity(intent);
                } catch (Exception ex) {
                    Toast.makeText(this, "Erro ao criar despesas!" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton botaoVolta = findViewById(R.id.buttonBack);
        botaoVolta.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarDiversosActivity.this, SelecionarDespesaActivity.class);
            startActivity(intent);
        });
    }

}

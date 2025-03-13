package com.example.serenosviagens.activities.registrar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarDespesasActivity;
import com.example.serenosviagens.activities.SelecionarDespesaActivity;
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.DAO.GasolinaDAO;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.GasolinaModel;
import com.example.serenosviagens.utils.Formulas;
import com.example.serenosviagens.utils.Utils;

import java.math.BigDecimal;

public class RegistrarGasolinaActivity extends AppCompatActivity {

    Utils utils = new Utils();
    Formulas formulas = new Formulas();

    private EditText totalKm, mediaKmL, custoLitroGasolina, totalVeiculos;
    private Spinner adicionarViagem;

    ViagemDAO viagemDAO = new ViagemDAO(RegistrarGasolinaActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_gasto_gasolina);

        totalKm = findViewById(R.id.total_km);
        mediaKmL = findViewById(R.id.media_km_l);
        custoLitroGasolina= findViewById(R.id.custo_l_gasolina);
        totalVeiculos = findViewById(R.id.total_veiculos);
        adicionarViagem = findViewById(R.id.adicionar_total);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_adicao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adicionarViagem.setAdapter(adapter);

        Button createButton = findViewById(R.id.button_new_gasolina);
        createButton.setOnClickListener(v -> {
            if (!utils.campoIsValid(totalKm.getText()) || !utils.campoIsValid(mediaKmL.getText()) || !utils.campoIsValid(custoLitroGasolina.getText()) || !utils.campoIsValid(totalVeiculos.getText()) || adicionarViagem.getSelectedItem().toString().isEmpty()){
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
                return;
            }

            BigDecimal totalKmValue = new BigDecimal(totalKm.getText().toString());
            BigDecimal mediaKmLValue = new BigDecimal(mediaKmL.getText().toString());
            BigDecimal custoLitroGasolinaValue = new BigDecimal(custoLitroGasolina.getText().toString());
            Integer totalVeiculosValue = Integer.valueOf(totalVeiculos.getText().toString());
            String adicionarViagemValue = utils.getInicialSimNao(adicionarViagem.getSelectedItem().toString());


            BigDecimal valorCalculo = formulas.calculaGasolina(totalKmValue, mediaKmLValue, custoLitroGasolinaValue, totalVeiculosValue);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegistrarGasolinaActivity.this);
            Long idViagem = sharedPreferences.getLong("ViagemAtual", -1);

            Long despesaId = null;

            try{
                DespesasModel despesasModel = new DespesasModel("Gasto com Gasolina", "GASOLINA", valorCalculo, adicionarViagemValue, idViagem);
                DespesasDAO despesasDAO = new DespesasDAO(RegistrarGasolinaActivity.this);

                despesaId = despesasDAO.insert(despesasModel);
                viagemDAO.setSincronizado(idViagem, false);
            } catch (Exception ex) {
                Toast.makeText(this, "Erro ao criar a despesa!" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            if(despesaId != null) {
                try{
                    GasolinaModel gasolinaModel = new GasolinaModel(totalVeiculosValue, despesaId, totalKmValue, mediaKmLValue, custoLitroGasolinaValue);
                    GasolinaDAO gasolinaDAO = new GasolinaDAO(RegistrarGasolinaActivity.this);

                    gasolinaDAO.insert(gasolinaModel);

                    Intent intent = new Intent(RegistrarGasolinaActivity.this, ListarDespesasActivity.class);
                    startActivity(intent);
                } catch (Exception ex) {
                    Toast.makeText(this, "Erro ao criar gasolina!" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton botaoVolta = findViewById(R.id.buttonBack);
        botaoVolta.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarGasolinaActivity.this, SelecionarDespesaActivity.class);
            startActivity(intent);
        });
    }

}

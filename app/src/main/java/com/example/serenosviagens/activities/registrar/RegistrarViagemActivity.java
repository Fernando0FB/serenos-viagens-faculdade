package com.example.serenosviagens.activities.registrar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarViagensActivity;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Models.ViagemModel;
import com.example.serenosviagens.utils.Utils;

public class RegistrarViagemActivity extends AppCompatActivity {
    private EditText descricao, destino, data, dataVolta, quantPessoas;
    Utils utils = new Utils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_viagens);

        descricao = findViewById(R.id.descricao);
        destino = findViewById(R.id.destino);
        data = findViewById(R.id.data);
        dataVolta = findViewById(R.id.dataVolta);
        quantPessoas = findViewById(R.id.quantPessoas);

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarViagemActivity.this, ListarViagensActivity.class);
            startActivity(intent);
        });

        Button btnNovaViagem = findViewById(R.id.button_new_refeicao);
        btnNovaViagem.setOnClickListener(v -> {
                registrar();
        });
    }

    private void registrar() {
        String descricaoTxt = descricao.getText().toString();
        String destinoTxt = destino.getText().toString();
        String dataTxt = data.getText().toString();
        String dataFimTxt = dataVolta.getText().toString();
        String quantPessoasTxt = quantPessoas.getText().toString();

        if(!utils.campoIsValid(descricao.getText()) || !utils.campoIsValid(destino.getText()) || !utils.campoIsValid(data.getText()) || !utils.campoIsValid(dataVolta.getText()) || !utils.campoIsValid(quantPessoas.getText())) {
            Toast.makeText(this, "Por favor, preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
            return;
        } else if(!utils.validaData(dataTxt) || !utils.validaData(dataFimTxt)) {
            Toast.makeText(this, "Data em formato inválido!", Toast.LENGTH_SHORT).show();
            return;
        }


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegistrarViagemActivity.this);
        Long idUsuario = sharedPreferences.getLong("userLogado", -1);

        String data = utils.formatToLocalDate(dataTxt);
        String dataFim = utils.formatToLocalDate(dataFimTxt);
        ViagemModel viagemModel = new ViagemModel(
                descricaoTxt,
                destinoTxt,
                Integer.valueOf(quantPessoasTxt),
                data,
                dataFim,
                idUsuario
        );

        try {
            ViagemDAO viagemDAO = new ViagemDAO(RegistrarViagemActivity.this);
            viagemDAO.Insert(viagemModel);

            Toast.makeText(this, "Viagem criada com sucesso!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RegistrarViagemActivity.this, ListarViagensActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao inserir usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

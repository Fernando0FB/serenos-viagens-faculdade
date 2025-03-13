package com.example.serenosviagens.activities.editar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.listar.ListarViagensActivity;
import com.example.serenosviagens.activities.registrar.RegistrarViagemActivity;
import com.example.serenosviagens.activities.visualizar.VisualizarViagemActivity;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Models.ViagemModel;
import com.example.serenosviagens.utils.Utils;

public class EditarViagemActivity extends AppCompatActivity {

    Utils utils = new Utils();
    ViagemDAO viagemDAO = new ViagemDAO(EditarViagemActivity.this);

    private EditText descricao, destino, quantPessoas, data, dataVolta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_viagens);

        descricao = findViewById(R.id.descricao);
        destino = findViewById(R.id.destino);
        quantPessoas = findViewById(R.id.quantPessoas);
        data = findViewById(R.id.data);
        dataVolta = findViewById(R.id.dataVolta);

        Long idViagem = getIntent().getLongExtra("idViagem", -1L);
        ViagemModel viagem = viagemDAO.getById(idViagem);

        descricao.setText(viagem.getDescricao());
        destino.setText(viagem.getDestino());
        quantPessoas.setText(viagem.getQuantPessoas().toString());
        data.setText(utils.formatData(viagem.getData().toString()));
        dataVolta.setText(utils.formatData(viagem.getDataFim().toString()));

        Button botaoEditar = findViewById(R.id.button_editar);
        botaoEditar.setOnClickListener(v -> {
            if(!utils.campoIsValid(descricao.getText()) || !utils.campoIsValid(destino.getText()) || !utils.campoIsValid(data.getText()) || !utils.campoIsValid(dataVolta.getText()) || !utils.campoIsValid(quantPessoas.getText())) {
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
                return;
            } else if(!utils.validaData(data.getText().toString()) || !utils.validaData(dataVolta.getText().toString())) {
                Toast.makeText(this, "Data em formato inválido!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                String descricaoTxt = descricao.getText().toString();
                String destinoTxt = destino.getText().toString();
                Integer quantPessoasInt = Integer.valueOf(quantPessoas.getText().toString());
                String dataTxt = utils.formatToLocalDate(data.getText().toString());
                String dataVoltaTxt = utils.formatToLocalDate(dataVolta.getText().toString());

                viagemDAO.update(idViagem, descricaoTxt, destinoTxt, quantPessoasInt, dataTxt, dataVoltaTxt);
                viagemDAO.setSincronizado(idViagem, false);
                Toast.makeText(this, "Viagem atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditarViagemActivity.this, VisualizarViagemActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Erro ao atualizar a viagem: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(EditarViagemActivity.this, VisualizarViagemActivity.class);
            startActivity(intent);
        });

    }
}

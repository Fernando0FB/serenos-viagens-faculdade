package com.example.serenosviagens.activities.listar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.SelecionarDespesaActivity;
import com.example.serenosviagens.activities.visualizar.VisualizarViagemActivity;
import com.example.serenosviagens.database.Adapters.DespesasAdapter;
import com.example.serenosviagens.database.DAO.DespesasDAO;

public class ListarDespesasActivity extends AppCompatActivity {

    private ListView lista_despesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_despesas);
        try {
            lista_despesa = findViewById(R.id.lista_despesa);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ListarDespesasActivity.this);
            Long idViagem = sharedPreferences.getLong("ViagemAtual", -1);

            DespesasDAO despesasDAO = new DespesasDAO(ListarDespesasActivity.this);
            DespesasAdapter adapter = new DespesasAdapter(ListarDespesasActivity.this);
            adapter.setItens(despesasDAO.getByViagemId(idViagem));
            lista_despesa.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Button btnNovaDespesa = findViewById(R.id.btnNovaDespesa);
        btnNovaDespesa.setOnClickListener(v -> {
            Intent intent = new Intent(ListarDespesasActivity.this, SelecionarDespesaActivity.class);
            startActivity(intent);
        });

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(ListarDespesasActivity.this, VisualizarViagemActivity.class);
            startActivity(intent);
        });
    }

}

package com.example.serenosviagens.database.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.visualizar.VisualizarAereoActivity;
import com.example.serenosviagens.activities.visualizar.VisualizarDiversosActivity;
import com.example.serenosviagens.activities.visualizar.VisualizarGasolinaActivity;
import com.example.serenosviagens.activities.visualizar.VisualizarHospedagemActivity;
import com.example.serenosviagens.activities.visualizar.VisualizarRefeicaoActivity;
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.utils.Utils;

import java.util.List;

public class DespesasAdapter extends BaseAdapter {

    Utils utils = new Utils();

    private Activity activity;

    private List<DespesasModel> lista;

    private LayoutInflater inflater;

    public DespesasAdapter(Activity activity) {
        this.activity = activity;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return this.lista != null ? this.lista.size() : 0;
    }

    public void setItens(List<DespesasModel> list) {
        this.lista = list;
    }

    @Override
    public Object getItem(int position) {
        return this.lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View rootView = inflater.inflate(R.layout.item_list_despesa,null);

        TextView titulo = (TextView) rootView.findViewById(R.id.tituloListaDespesa);
        TextView preco = (TextView) rootView.findViewById(R.id.valorDespesa);
        titulo.setText(lista.get(position).getDescricao());
        String valorString = utils.formataValor(lista.get(position).getValor());
        preco.setText(valorString);

        ImageButton buttonVisualizarListaDespesa = rootView.findViewById(R.id.buttonVisualizarListaDespesa);
        buttonVisualizarListaDespesa.setTag(position);
        buttonVisualizarListaDespesa.setOnClickListener(v -> {
            int clickedPosition = (int) v.getTag();
            Long despesaId = lista.get(clickedPosition).getId();
            String tipoDespesa = lista.get(clickedPosition).getTipo();
            Intent intent = new Intent(String.valueOf(activity));
            switch (tipoDespesa){
                case "GASOLINA":
                    intent = new Intent(activity, VisualizarGasolinaActivity.class);
                    intent.putExtra("despesaId", despesaId);
                    activity.startActivity(intent);
                    break;
                case "AEREO":
                    intent = new Intent(activity, VisualizarAereoActivity.class);
                    intent.putExtra("despesaId", despesaId);
                    activity.startActivity(intent);
                    break;
                case "REFEICAO":
                    intent = new Intent(activity, VisualizarRefeicaoActivity.class);
                    intent.putExtra("despesaId", despesaId);
                    activity.startActivity(intent);
                    break;
                case "HOSPEDAGEM":
                    intent = new Intent(activity, VisualizarHospedagemActivity.class);
                    intent.putExtra("despesaId", despesaId);
                    activity.startActivity(intent);
                    break;
                case "DIVERSOS":
                    intent = new Intent(activity, VisualizarDiversosActivity.class);
                    intent.putExtra("despesaId", despesaId);
                    activity.startActivity(intent);
                    break;
                default:
                    Toast.makeText(activity, "Tipo de despesa não reconhecido! " + tipoDespesa, Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        ImageButton buttonDelete = rootView.findViewById(R.id.buttonDeleteListaDespesa);
        buttonDelete.setTag(position);
        buttonDelete.setOnClickListener(v ->{
            int clickedPosition = (int) v.getTag();
            confirmaDelete(clickedPosition);
        });

        return rootView;
    }

    private void confirmaDelete(int positionDelete) {
        View rootView = inflater.inflate(R.layout.item_list_viagem,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
        builder.setTitle("Exclusão");
        builder.setMessage("Você tem certeza que deseja deletar?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DespesasDAO(activity).Delete(lista.get(positionDelete).getId());
                new ViagemDAO(activity).setSincronizado(lista.get(positionDelete).getViagemId(), false);
                lista.remove(positionDelete);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

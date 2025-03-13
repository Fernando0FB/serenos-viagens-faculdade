package com.example.serenosviagens.database.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.serenosviagens.R;
import com.example.serenosviagens.activities.visualizar.VisualizarViagemActivity;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.Models.ViagemModel;

import java.util.List;

public class ViagemAdapter extends BaseAdapter {

    private Activity activity;

    private List<ViagemModel> lista;

    private LayoutInflater inflater;

    public ViagemAdapter(Activity activity) {
        this.activity = activity;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return this.lista != null ? this.lista.size() : 0;
    }

    public void setItens(List<ViagemModel> list) {
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
        View rootView = inflater.inflate(R.layout.item_list_viagem,null);
        try {
            TextView titulo = (TextView) rootView.findViewById(R.id.tituloListaViagem);
            titulo.setText(lista.get(position).getDescricao());
        } catch (Exception ex) {

        }

        ImageButton buttonVisualizar = rootView.findViewById(R.id.buttonVisualizarListaViagem);
        buttonVisualizar.setOnClickListener(v -> {
                SharedPreferences sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("ViagemAtual", lista.get(position).getId());
                editor.apply();

                Intent intent = new Intent(activity, VisualizarViagemActivity.class);
                activity.startActivity(intent);
        });

        ImageButton buttonDelete = rootView.findViewById(R.id.buttonDeleteListaViagem);
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
                new ViagemDAO(activity).Delete(lista.get(positionDelete).getId());
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
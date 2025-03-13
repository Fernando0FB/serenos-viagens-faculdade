package com.example.serenosviagens.activities.visualizar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.serenosviagens.R;
import com.example.serenosviagens.database.DAO.DespesasDAO;
import com.example.serenosviagens.database.DAO.GasolinaDAO;
import com.example.serenosviagens.database.DAO.DiversosDAO;
import com.example.serenosviagens.database.DAO.HospedagemDAO;
import com.example.serenosviagens.database.DAO.RefeicoesDAO;
import com.example.serenosviagens.database.DAO.ViagemDAO;
import com.example.serenosviagens.database.DAO.AereoDAO;
import com.example.serenosviagens.database.Models.AereoModel;
import com.example.serenosviagens.database.Models.HospedagemModel;
import com.example.serenosviagens.database.Models.RefeicoesModel;
import com.example.serenosviagens.database.Models.DiversosModel;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.GasolinaModel;
import com.example.serenosviagens.database.Models.Resposta;
import com.example.serenosviagens.database.Models.Resultado;
import com.example.serenosviagens.database.Models.ViagemModel;
import com.example.serenosviagens.database.api.API;
import com.example.serenosviagens.utils.Utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class VisualizarResultadoActivity extends AppCompatActivity {

    Utils utils = new Utils();
    ViagemDAO viagemDAO = new ViagemDAO(VisualizarResultadoActivity.this);

    GasolinaDAO gasolinaDAO = new GasolinaDAO(VisualizarResultadoActivity.this);

    AereoDAO aereoDAO = new AereoDAO(VisualizarResultadoActivity.this);

    HospedagemDAO hospedagemDAO = new HospedagemDAO(VisualizarResultadoActivity.this);

    RefeicoesDAO refeicoesDAO = new RefeicoesDAO(VisualizarResultadoActivity.this);

    DiversosDAO diversosDAO = new DiversosDAO(VisualizarResultadoActivity.this);

    private TextView descricao, destino, quantViajantes, duracaoViagem, custoTotal, custoNaoContabilizado, custoPorPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);

        Long idViagem = getIntent().getLongExtra("idViagem", -1L);
        ViagemModel viagemModel = viagemDAO.getById(idViagem);
        Integer duracaoViagemInt = utils.getDuracaoViagem(viagemModel.getData(), viagemModel.getDataFim());
        String duracaoViagemString = duracaoViagemInt + " dias";
        BigDecimal valorTotal = getValorTotal(viagemModel);
        BigDecimal valorNaoAdcTotal = getValorNaoAdcTotal(viagemModel);
        BigDecimal valorPorpessoa = getValorPorPessoa(valorTotal, viagemModel.getQuantPessoas());

        List<DespesasModel> despesasModelList = getAllDespesas(viagemModel);

        List<GasolinaModel> gasolinaModels = getListGasolina(despesasModelList);
        GasolinaModel gasolinaModel = getGasolinaResultado(gasolinaModels);

        List<AereoModel> aereoModels = getListAereo(despesasModelList);
        AereoModel aereoModel = getAereoResultado(aereoModels);

        List<HospedagemModel> hospedagemModels = getListHospedagem(despesasModelList);
        HospedagemModel hospedagemModel = getHospedagemResultado(hospedagemModels);

        List<RefeicoesModel> refeicoesModels = getListRefeicoes(despesasModelList);
        RefeicoesModel refeicoesModel = getRefeicoesResultado(refeicoesModels);
        Integer quantViajantesInt = viagemModel.getQuantPessoas();

        List<DiversosModel> listaEntretenimento = getListDiversos(despesasModelList);
        if(!viagemModel.getSincronizado()){
            Integer idNuvem = viagemModel.getIdNuvem();
            if(idNuvem != null && idNuvem != 0) {
                putViagem(idNuvem, viagemModel.getDestino(), quantViajantesInt, duracaoViagemInt, valorTotal, valorPorpessoa, listaEntretenimento, gasolinaModel, aereoModel, refeicoesModel, hospedagemModel, idViagem);
            } else {
                postViagem(viagemModel.getDestino(), quantViajantesInt, duracaoViagemInt, valorTotal, valorPorpessoa, listaEntretenimento, gasolinaModel, aereoModel, refeicoesModel, hospedagemModel, idViagem);
            }
        }

        descricao = findViewById(R.id.descricao_view);
        destino = findViewById(R.id.destino_view);
        quantViajantes = findViewById(R.id.total_viajantes_view);
        duracaoViagem = findViewById(R.id.duracao_viajem_view);
        custoTotal = findViewById(R.id.custo_total_view);
        custoNaoContabilizado = findViewById(R.id.custo_n_total_view);
        custoPorPessoa = findViewById(R.id.custo_p_pessoa_view);
        descricao.setText(viagemModel.getDescricao());
        destino.setText(viagemModel.getDestino());
        quantViajantes.setText(viagemModel.getQuantPessoas().toString());
        duracaoViagem.setText(duracaoViagemString);
        custoTotal.setText(utils.formataValor(valorTotal));
        custoNaoContabilizado.setText(utils.formataValor(valorNaoAdcTotal));
        custoPorPessoa.setText(utils.formataValor(valorPorpessoa));

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(VisualizarResultadoActivity.this, VisualizarViagemActivity.class);
            startActivity(intent);
        });
    }

    private void putViagem(Integer id, String local, Integer quantViajantesInt, Integer duracaoViagemInt, BigDecimal valorTotal, BigDecimal valorPorpessoa, List<DiversosModel> listaEntretenimento, GasolinaModel gasolinaModel, AereoModel aereoModel, RefeicoesModel refeicoesModel, HospedagemModel hospedagemModel, Long idViagem) {
        API.postResultado(new Resultado(id, local, quantViajantesInt, duracaoViagemInt, valorTotal, valorPorpessoa, listaEntretenimento, gasolinaModel, aereoModel, refeicoesModel, hospedagemModel), new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {

                if (response != null && response.isSuccessful()) {
                    Resposta r = response.body();
                    viagemDAO.setSincronizado(idViagem, true);
                }
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarResultadoActivity.this);
                builder.setMessage(t.getMessage());
                builder.create().show();
            }
        });
    }

    private void postViagem(String local, Integer quantViajantesInt, Integer duracaoViagemInt, BigDecimal valorTotal, BigDecimal valorPorpessoa, List<DiversosModel> listaEntretenimento, GasolinaModel gasolinaModel, AereoModel aereoModel, RefeicoesModel refeicoesModel, HospedagemModel hospedagemModel, Long idViagem) {
        API.postResultado(new Resultado(local, quantViajantesInt, duracaoViagemInt, valorTotal, valorPorpessoa, listaEntretenimento, gasolinaModel, aereoModel, refeicoesModel, hospedagemModel), new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {

                if (response != null && response.isSuccessful()) {
                    Resposta r = response.body();
                    viagemDAO.setIdNuvem(idViagem, Integer.valueOf(r.getDados()));
                    viagemDAO.setSincronizado(idViagem, true);
                }
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarResultadoActivity.this);
                builder.setMessage(t.getMessage());
                builder.create().show();
            }
        });
    }

    private List<DespesasModel> getAllDespesas(ViagemModel viagemModel) {
        DespesasDAO despesasDAO = new DespesasDAO(VisualizarResultadoActivity.this);
        List<DespesasModel> lista = despesasDAO.getByViagemId(viagemModel.getId());

        return lista;
    }
    public List<AereoModel> getListAereo(List<DespesasModel> despesasModels) {
        List<AereoModel> listaAereo = new ArrayList<>();

        List<DespesasModel> listaDespesaAereo = despesasModels.stream()
                .filter(despesa -> despesa.getTipo().equals("AEREO"))
                .collect(Collectors.toList());

        listaDespesaAereo.forEach(v -> {
            listaAereo.add(aereoDAO.getByDespesaId(Long.valueOf(v.getId())));
        });
        return listaAereo;
    }

    public AereoModel getAereoResultado(List<AereoModel> aereoModels) {
        if(aereoModels.isEmpty()) {
            return new AereoModel();
        }
        BigDecimal custoPorPessoa = aereoModels.stream()
                .map(AereoModel::getCustoPessoa)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(aereoModels.size()), RoundingMode.HALF_UP);

        BigDecimal custoAluguelVeiculo = aereoModels.stream()
                .map(AereoModel::getCustoAluguelVeiculo)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(aereoModels.size()), RoundingMode.HALF_UP);

        return new AereoModel(custoPorPessoa, custoAluguelVeiculo);
    }

    public List<RefeicoesModel> getListRefeicoes(List<DespesasModel> despesasModels) {
        List<RefeicoesModel> listaRefeicoes = new ArrayList<>();

        List<DespesasModel> listaDespesaRefeicoes = despesasModels.stream()
                .filter(despesa -> despesa.getTipo().equals("REFEICOES"))
                .collect(Collectors.toList());

        listaDespesaRefeicoes.forEach(v -> {
            listaRefeicoes.add(refeicoesDAO.getByDespesaId(Long.valueOf(v.getId())));
        });

        return listaRefeicoes;
    }

    public RefeicoesModel getRefeicoesResultado(List<RefeicoesModel> refeicoesModels) {
        if(refeicoesModels.isEmpty()) {
            return new RefeicoesModel();
        }
        BigDecimal custoPorRefeicao = refeicoesModels.stream()
                .map(RefeicoesModel::getCustoRefeicao)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(refeicoesModels.size()), RoundingMode.HALF_UP);

        Integer quantidadeRefeicoes = refeicoesModels.stream()
                .mapToInt(RefeicoesModel::getRefeicoesDia)
                .sum();

        return new RefeicoesModel(custoPorRefeicao, quantidadeRefeicoes);
    }

    public List<DiversosModel> getListDiversos(List<DespesasModel> despesasModels) {
        List<DiversosModel> listaDiversos = new ArrayList<>();

        List<DespesasModel> listaDespesaDiversos = despesasModels.stream()
                .filter(despesa -> despesa.getTipo().equals("DIVERSOS"))
                .collect(Collectors.toList());

        listaDespesaDiversos.forEach(v -> {
            listaDiversos.add(diversosDAO.getByDespesaId(Long.valueOf(v.getId())));
        });

        return listaDiversos;
    }

    public List<HospedagemModel> getListHospedagem(List<DespesasModel> despesasModels) {
        List<HospedagemModel> listaHospedagem = new ArrayList<>();

        List<DespesasModel> listaDespesaHospedagem = despesasModels.stream()
                .filter(despesa -> despesa.getTipo().equals("HOSPEDAGEM"))
                .collect(Collectors.toList());

        listaDespesaHospedagem.forEach(v -> {
            listaHospedagem.add(hospedagemDAO.getByDespesaId(Long.valueOf(v.getId())));
        });

        return listaHospedagem;
    }

    public HospedagemModel getHospedagemResultado(List<HospedagemModel> hospedagemModels) {
        if(hospedagemModels.isEmpty()) {
            return new HospedagemModel();
        }
        BigDecimal custoPorNoite = hospedagemModels.stream()
                .map(HospedagemModel::getCustoMedioNoite)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(hospedagemModels.size()), RoundingMode.HALF_UP);

        Integer totalNoites = hospedagemModels.stream()
                .mapToInt(HospedagemModel::getTotalNoite)
                .sum();

        Integer totalQuartos = hospedagemModels.stream()
                .mapToInt(HospedagemModel::getTotalQuartos)
                .sum();

        return new HospedagemModel(custoPorNoite, totalNoites, totalQuartos);
    }


    public List<GasolinaModel> getListGasolina(List<DespesasModel> despesasModels) {
        List<GasolinaModel> listaGasolina = new ArrayList<>();

        List<DespesasModel> listaDespesaGasolina = despesasModels.stream()
                .filter(despesa -> despesa.getTipo().equals("GASOLINA"))
                .collect(Collectors.toList());

        listaDespesaGasolina.forEach(v -> {
            listaGasolina.add(gasolinaDAO.getByDespesaId(Long.valueOf(v.getId())));
        });

        return listaGasolina;
    }

    public GasolinaModel getGasolinaResultado(List<GasolinaModel> gasolinaModels){
        if(gasolinaModels.isEmpty()) {
            return new GasolinaModel();
        }
        Long totalVeiculos = gasolinaModels.stream()
                .mapToLong(GasolinaModel::getTotalVeiculos)
                .sum() / gasolinaModels.size();

        BigDecimal totalEstimadoKM = gasolinaModels.stream()
                .map(GasolinaModel::getTotalEstimadoKM)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(gasolinaModels.size()), RoundingMode.HALF_UP);

        BigDecimal mediaKMLitro = gasolinaModels.stream()
                .map(GasolinaModel::getMediaKMLitro)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(gasolinaModels.size()), RoundingMode.HALF_UP);

        BigDecimal custoMedioLitro = gasolinaModels.stream()
                .map(GasolinaModel::getCustoMedioLitro)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(gasolinaModels.size()), RoundingMode.HALF_UP);

        return new GasolinaModel(totalVeiculos, totalEstimadoKM, mediaKMLitro, custoMedioLitro);
    }

    private BigDecimal getValorTotal(ViagemModel viagemModel) {
        DespesasDAO despesasDAO = new DespesasDAO(VisualizarResultadoActivity.this);
        List<DespesasModel> lista = despesasDAO.getByViagemId(viagemModel.getId());

        return lista.stream()
                .filter(despesa -> "S".equals(despesa.getAdcTotal()))
                .map(DespesasModel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getValorNaoAdcTotal(ViagemModel viagemModel) {
        DespesasDAO despesasDAO = new DespesasDAO(VisualizarResultadoActivity.this);
        List<DespesasModel> lista = despesasDAO.getByViagemId(viagemModel.getId());

        return lista.stream()
                .filter(despesa -> "N".equals(despesa.getAdcTotal()))
                .map(DespesasModel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getValorPorPessoa(BigDecimal valorTotal, Integer quantPessoa) {
        return valorTotal.divide(BigDecimal.valueOf(quantPessoa), RoundingMode.HALF_UP);
    }
}

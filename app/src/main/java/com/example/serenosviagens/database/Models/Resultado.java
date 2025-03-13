package com.example.serenosviagens.database.Models;

import java.math.BigDecimal;
import java.util.List;

public class Resultado {
    private long id;
    private long idConta = 130254;

    private Integer totalViajantes, duracaoViagem;

    private BigDecimal custoTotalViagem, custoPorPessoa;

    private List<DiversosModel> listaEntretenimento;

    private String local;

    private GasolinaModel gasolina;
    private AereoModel aereo;
    private RefeicoesModel refeicao;
    private HospedagemModel hospedagem;


    public Resultado(Integer id, String local, Integer totalViajantes, Integer duracaoViagem, BigDecimal custoTotalViagem, BigDecimal custoPorPessoa, List<DiversosModel> listaEntretenimento, GasolinaModel gasolina, AereoModel aereo, RefeicoesModel refeicao, HospedagemModel hospedagem) {
        this.id = id;
        this.local = local;
        this.totalViajantes = totalViajantes;
        this.duracaoViagem = duracaoViagem;
        this.custoTotalViagem = custoTotalViagem;
        this.custoPorPessoa = custoPorPessoa;
        this.listaEntretenimento = listaEntretenimento;
        this.gasolina = gasolina;
        this.aereo = aereo;
        this.refeicao = refeicao;
        this.hospedagem = hospedagem;
    }

    public Resultado(String local, Integer totalViajantes, Integer duracaoViagem, BigDecimal custoTotalViagem, BigDecimal custoPorPessoa, List<DiversosModel> listaEntretenimento, GasolinaModel gasolina, AereoModel aereo, RefeicoesModel refeicao, HospedagemModel hospedagem) {
        this.local = local;
        this.totalViajantes = totalViajantes;
        this.duracaoViagem = duracaoViagem;
        this.custoTotalViagem = custoTotalViagem;
        this.custoPorPessoa = custoPorPessoa;
        this.listaEntretenimento = listaEntretenimento;
        this.gasolina = gasolina;
        this.aereo = aereo;
        this.refeicao = refeicao;
        this.hospedagem = hospedagem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdConta() {
        return idConta;
    }

    public void setIdConta(long idConta) {
        this.idConta = idConta;
    }

    public Integer getTotalViajantes() {
        return totalViajantes;
    }

    public void setTotalViajantes(Integer totalViajantes) {
        this.totalViajantes = totalViajantes;
    }

    public Integer getDuracaoViagem() {
        return duracaoViagem;
    }

    public void setDuracaoViagem(Integer duracaoViagem) {
        this.duracaoViagem = duracaoViagem;
    }

    public BigDecimal getCustoTotalViagem() {
        return custoTotalViagem;
    }

    public void setCustoTotalViagem(BigDecimal custoTotalViagem) {
        this.custoTotalViagem = custoTotalViagem;
    }

    public BigDecimal getCustoPorPessoa() {
        return custoPorPessoa;
    }

    public void setCustoPorPessoa(BigDecimal custoPorPessoa) {
        this.custoPorPessoa = custoPorPessoa;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public GasolinaModel getGasolina() {
        return gasolina;
    }

    public void setGasolina(GasolinaModel gasolina) {
        this.gasolina = gasolina;
    }

    public AereoModel getAereo() {
        return aereo;
    }

    public void setAereo(AereoModel aereo) {
        this.aereo = aereo;
    }

    public HospedagemModel getHospedagem() {
        return hospedagem;
    }

    public void setHospedagem(HospedagemModel hospedagem) {
        this.hospedagem = hospedagem;
    }

    public List<DiversosModel> getListaEntretenimento() {
        return listaEntretenimento;
    }

    public void setListaEntretenimento(List<DiversosModel> listaEntretenimento) {
        this.listaEntretenimento = listaEntretenimento;
    }
}

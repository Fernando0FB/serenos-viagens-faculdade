package com.example.serenosviagens.utils;

import android.text.Editable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.*;

public class Utils {

    public String formataValor(BigDecimal valor) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("R$#,##0.00", symbols);
        String valorStr = df.format(valor);

        return valorStr;
    }

    public Boolean validaData(String dataString) {
        String regex = "^([0-2][0-9]|(3)[0-1])/(0[1-9]|1[0-2])/\\d{4}$";
        return dataString.matches(regex);
    }

    public String formatToLocalDate(String dataString) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date data = formatoEntrada.parse(dataString);
            return formatoSaida.format(data);
        } catch (ParseException ex){
            return "";
        }
    }

    public String formatData(String dataString) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date data = formatoEntrada.parse(dataString);
            return formatoSaida.format(data);
        } catch (ParseException ex){
            return "";
        }
    }

    public String getInicialSimNao(String simNao) {
        switch (simNao){
            case "Sim":
                return "S";
            case "Não":
                return "N";
            default:
                return "N";
        }
    }

    public Boolean campoIsValid(Editable campo) {
        if (campo.toString().isEmpty() || campo.toString().equals("0")){
            return false;
        } else {
            return true;
        }
    }

    public Integer getDuracaoViagem(String dataInicio, String dataFim) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(dataInicio, formatter);
        LocalDate endDate = LocalDate.parse(dataFim, formatter);
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }
}

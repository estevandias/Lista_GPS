package br.usjt.ciclodevidagpsemapas.data.models;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Clima {
    public String diaSemana;
    public double minTemp;
    public double maxTemp;
    public double umidade;
    public String descricao;

    public Clima(long diaSemana, double minTemp, double maxTemp, double umidade, String descricao) {
        this.diaSemana = convertTimeStampToDay(diaSemana);
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.umidade = umidade;
        this.descricao = descricao;
    }

    @SuppressLint("SimpleDateFormat")
    private static String convertTimeStampToDay (long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp * 1000);
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        return new SimpleDateFormat("EEEE").format(calendar.getTime());
    }
}

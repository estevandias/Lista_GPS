package br.usjt.ciclodevidagpsemapas.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.usjt.ciclodevidagpsemapas.data.models.Clima;
import br.usjt.ciclodevidagpsemapas.data.models.Localizacao;

public class LocalizacaoDAO {

    private Context context;

    public LocalizacaoDAO(Context context) {
        this.context = context;
    }

    public List<Localizacao> recuperaLocalizacoes() {
        SQLHelper helper = new SQLHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        String query = String.format(Locale.getDefault(), "SELECT * FROM %s ORDER BY %s DESC LIMIT 50", Contratos.TABLE_NAME, Contratos.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, null);
        ArrayList<Localizacao> localizacoes = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                double latitude = cursor.getDouble(cursor.getColumnIndex(Contratos.COLUMN_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(Contratos.COLUMN_LONGITUDE));
                long data = cursor.getLong(cursor.getColumnIndex(Contratos.COLUMN_DIA_SEMANA));
                double minTemp = cursor.getDouble(cursor.getColumnIndex(Contratos.COLUMN_MIN_TEMP));
                double maxTemp = cursor.getDouble(cursor.getColumnIndex(Contratos.COLUMN_MAX_TEMP));
                double umidade = cursor.getDouble(cursor.getColumnIndex(Contratos.COLUMN_UMIDADE));
                String descricao = cursor.getString(cursor.getColumnIndex(Contratos.COLUMN_DESCRICAO));

                localizacoes.add(0, new Localizacao(latitude, longitude, new Clima(data, minTemp, maxTemp, umidade, descricao)));
            }
        } finally {
            cursor.close();
            database.close();
            helper.close();
        }

        return localizacoes;
    }

    public void insereLocalizacao(Localizacao localizacao) {
        SQLHelper helper = new SQLHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contratos.COLUMN_LATITUDE, localizacao.latitude);
        contentValues.put(Contratos.COLUMN_LONGITUDE, localizacao.longitude);
        contentValues.put(Contratos.COLUMN_DIA_SEMANA, localizacao.clima.diaSemana);
        contentValues.put(Contratos.COLUMN_MIN_TEMP, localizacao.clima.minTemp);
        contentValues.put(Contratos.COLUMN_MAX_TEMP, localizacao.clima.maxTemp);
        contentValues.put(Contratos.COLUMN_UMIDADE, localizacao.clima.umidade);
        contentValues.put(Contratos.COLUMN_DESCRICAO, localizacao.clima.descricao);

        database.insert(Contratos.TABLE_NAME, null, contentValues);
        database.close();
    }
}

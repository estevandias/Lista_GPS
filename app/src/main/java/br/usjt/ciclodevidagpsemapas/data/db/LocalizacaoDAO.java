package br.usjt.ciclodevidagpsemapas.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

                localizacoes.add(0, new Localizacao(latitude, longitude));
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

        database.insert(Contratos.TABLE_NAME, null, contentValues);
        database.close();
    }
}

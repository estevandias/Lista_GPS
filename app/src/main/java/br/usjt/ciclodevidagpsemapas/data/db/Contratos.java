package br.usjt.ciclodevidagpsemapas.data.db;

class Contratos {
    static final String TABLE_NAME = "localizacoes";

    static final String COLUMN_ID = "id";
    static final String COLUMN_LATITUDE = "latitude";
    static final String COLUMN_LONGITUDE = "longitude";
    static final String COLUMN_DIA_SEMANA = "diaSemana";
    static final String COLUMN_MIN_TEMP = "min_temp";
    static final String COLUMN_MAX_TEMP = "max_temp";
    static final String COLUMN_UMIDADE= "umidade";
    static final String COLUMN_DESCRICAO= "descricao";

    static String createTable() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(" CREATE TABLE ");
        stringBuilder.append(TABLE_NAME);
        stringBuilder.append(" ( ");
        stringBuilder.append(COLUMN_ID);
        stringBuilder.append(" INTEGER PRIMARY KEY, ");
        stringBuilder.append(COLUMN_LATITUDE);
        stringBuilder.append(" DOUBLE, ");
        stringBuilder.append(COLUMN_LONGITUDE);
        stringBuilder.append(" DOUBLE, ");
        stringBuilder.append(COLUMN_DIA_SEMANA);
        stringBuilder.append(" INTEGER, ");
        stringBuilder.append(COLUMN_MIN_TEMP);
        stringBuilder.append(" DOUBLE, ");
        stringBuilder.append(COLUMN_MAX_TEMP);
        stringBuilder.append(" DOUBLE, ");
        stringBuilder.append(COLUMN_UMIDADE);
        stringBuilder.append(" DOUBLE, ");
        stringBuilder.append(COLUMN_DESCRICAO);
        stringBuilder.append(" TEXT ");
        stringBuilder.append(" ); ");

        return stringBuilder.toString();
    }

    static String dropTable() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(" DROP TABLE ");
        stringBuilder.append(TABLE_NAME);
        stringBuilder.append("  ; ");

        return stringBuilder.toString();
    }
}

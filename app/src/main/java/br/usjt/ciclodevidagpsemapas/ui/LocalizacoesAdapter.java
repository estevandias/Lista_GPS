package br.usjt.ciclodevidagpsemapas.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.usjt.ciclodevidagpsemapas.R;
import br.usjt.ciclodevidagpsemapas.data.models.Localizacao;

public class LocalizacoesAdapter extends RecyclerView.Adapter {

    private List<Localizacao> localizacoes;

    LocalizacoesAdapter(List<Localizacao> localizacoes) {
        this.localizacoes = localizacoes;
    }

    @Override
    public int getItemCount() {
        return localizacoes.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new LocalizacoesViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_localizacao, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((LocalizacoesViewHolder) viewHolder).bind(localizacoes.get(position));
    }

    private class LocalizacoesViewHolder extends RecyclerView.ViewHolder {
        private TextView latitudeLabel;
        private TextView longitudeLabel;
        private TextView diaSemanaLabel;
        private TextView umidadeLabel;
        private TextView minTempLabel;
        private TextView maxTempLabel;
        private TextView descricaoLabel;

        LocalizacoesViewHolder(@NonNull View itemView) {
            super(itemView);

            latitudeLabel = itemView.findViewById(R.id.latitude_label);
            longitudeLabel = itemView.findViewById(R.id.longitude_label);
            diaSemanaLabel = itemView.findViewById(R.id.dia_semana_label);
            umidadeLabel = itemView.findViewById(R.id.umidade_label);
            minTempLabel = itemView.findViewById(R.id.min_temp_label);
            maxTempLabel = itemView.findViewById(R.id.max_temp_label);
            descricaoLabel = itemView.findViewById(R.id.descricao_label);
        }

        private void bind(Localizacao localizacao) {
            String latitude = String.valueOf(localizacao.latitude);
            String longitude = String.valueOf(localizacao.longitude);
            String umidade = String.valueOf(localizacao.clima.umidade);
            String minTemp = String.valueOf(localizacao.clima.minTemp);
            String maxTemp = String.valueOf(localizacao.clima.maxTemp);

            latitudeLabel.setText(String.format("Lat: %s", latitude.length() > 10 ? latitude.substring(0, 10) : latitude));
            longitudeLabel.setText(String.format("Long: %s", longitude.length() > 10 ? longitude.substring(0, 10) : longitude));
            diaSemanaLabel.setText(localizacao.clima.diaSemana);
            umidadeLabel.setText(String.format("Umidade: %s", umidade));
            minTempLabel.setText(String.format("Min: %sC", minTemp));
            maxTempLabel.setText(String.format("Max: %sC", maxTemp));
            descricaoLabel.setText(localizacao.clima.descricao);
        }
    }
}

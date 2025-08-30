package com.example.adegadevinho;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MetasActivity extends AppCompatActivity {

    private TextView txtMeta, txtVendido, txtComissao, txtPorcentagem;
    private ProgressBar progressoMeta;

    private DatabaseHelper db;
    private DecimalFormat df = new DecimalFormat("R$ #,##0.00");
    private int representanteId;
    private double porcentagemComissao = 5.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas);

        txtMeta = findViewById(R.id.txtMeta);
        txtVendido = findViewById(R.id.txtVendido);
        txtComissao = findViewById(R.id.txtComissao);
        txtPorcentagem = findViewById(R.id.txtPorcentagem);
        progressoMeta = findViewById(R.id.progressoMeta);

        db = new DatabaseHelper(this);

        if (Sessao.getUsuarioLogado() != null) {
            representanteId = Sessao.getUsuarioLogado().getId();
            carregarDados();
        } else {
            Toast.makeText(this, "Erro ao carregar usuário logado.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void carregarDados() {
        String mesAtual = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());

        double metaMensal = db.getMetaMensalRepresentante(representanteId, mesAtual);
        double totalVendido = db.getTotalVendasMesPorRepresentante(representanteId, mesAtual);

        double porcentagem = metaMensal > 0 ? (totalVendido / metaMensal) * 100 : 0;
        double comissao = totalVendido * (porcentagemComissao / 100);

        txtMeta.setText("Meta do mês: " + df.format(metaMensal));
        txtVendido.setText("Vendido: " + df.format(totalVendido));
        txtComissao.setText("Comissão estimada: " + df.format(comissao));

        progressoMeta.setProgress((int) Math.min(porcentagem, 100));
        txtPorcentagem.setText((int) porcentagem + "% da meta atingida");
    }
}

package com.example.adegadevinho;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RelatoriosActivity extends AppCompatActivity {

    private EditText etDataInicioRel, etDataFimRel;
    private EditText etClienteRel, etRegiaoRel, etRepresentanteRel;
    private Button btnGerarRelatorio;
    private TextView txtTotalRelatorio;
    private ListView listViewRelatorio;

    private DatabaseHelper db;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private DecimalFormat currencyFormat = new DecimalFormat("R$ #,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        etDataInicioRel = findViewById(R.id.etDataInicioRel);
        etDataFimRel = findViewById(R.id.etDataFimRel);
        etClienteRel = findViewById(R.id.etClienteRel);
        etRegiaoRel = findViewById(R.id.etRegiaoRel);
        etRepresentanteRel = findViewById(R.id.etRepresentanteRel);
        btnGerarRelatorio = findViewById(R.id.btnGerarRelatorio);
        txtTotalRelatorio = findViewById(R.id.txtTotalRelatorio);
        listViewRelatorio = findViewById(R.id.listViewRelatorio);

        db = new DatabaseHelper(this);

        setupDatePicker(etDataInicioRel);
        setupDatePicker(etDataFimRel);

        btnGerarRelatorio.setOnClickListener(v -> gerarRelatorio());
    }

    private void setupDatePicker(EditText campo) {
        campo.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        cal.set(year, month, dayOfMonth);
                        campo.setText(dateFormat.format(cal.getTime()));
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void gerarRelatorio() {
        String dataInicio = etDataInicioRel.getText().toString().trim();
        String dataFim = etDataFimRel.getText().toString().trim();
        String nomeCliente = etClienteRel.getText().toString().trim();
        String regiao = etRegiaoRel.getText().toString().trim();
        String representante = etRepresentanteRel.getText().toString().trim();

        int clienteId = -1;
        if (!nomeCliente.isEmpty()) {
            clienteId = db.getClienteIdPorNome(nomeCliente);
            if (clienteId == -1) {
                Toast.makeText(this, "Cliente não encontrado.", Toast.LENGTH_SHORT).show();
                limparRelatorio();
                return;
            }
        }

        int representanteId = -1;
        if (!representante.isEmpty()) {
            representanteId = db.getRepresentanteIdPorNome(representante);
            if (representanteId == -1) {
                Toast.makeText(this, "Representante não encontrado.", Toast.LENGTH_SHORT).show();
                limparRelatorio();
                return;
            }
        }

        if (regiao.isEmpty()) regiao = null;

        List<Pedido> pedidos = db.getRelatorioPedidos(dataInicio, dataFim, clienteId, regiao, representanteId);

        if (pedidos.isEmpty()) {
            Toast.makeText(this, "Nenhum pedido encontrado.", Toast.LENGTH_SHORT).show();
        }

        double total = 0;
        for (Pedido p : pedidos) {
            total += p.getTotal();
        }

        RelatorioAdapter adapter = new RelatorioAdapter(this, pedidos);
        listViewRelatorio.setAdapter(adapter);

        txtTotalRelatorio.setText("Total: " + currencyFormat.format(total));
    }

    private void limparRelatorio() {
        listViewRelatorio.setAdapter(null);
        txtTotalRelatorio.setText("Total: R$ 0,00");
    }
}

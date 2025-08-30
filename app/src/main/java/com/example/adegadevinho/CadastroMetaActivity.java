package com.example.adegadevinho;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import android.view.View;

public class CadastroMetaActivity extends AppCompatActivity {

    private EditText etNomeRepresentante, etMesMeta, etValorMeta;
    private Button btnSalvarMeta;

    private List<Representante> representantes;
    private DatabaseHelper db;
    private int representanteSelecionadoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_meta);

        etNomeRepresentante = findViewById(R.id.etNomeRepresentante);
        etMesMeta = findViewById(R.id.etMesMeta);
        etValorMeta = findViewById(R.id.etValorMeta);
        btnSalvarMeta = findViewById(R.id.btnSalvarMeta);

        db = new DatabaseHelper(this);

        representantes = db.getTodosRepresentantes();

        btnSalvarMeta.setOnClickListener(v -> {
            String nomeRep = etNomeRepresentante.getText().toString().trim();
            String mes = etMesMeta.getText().toString().trim();
            String valorStr = etValorMeta.getText().toString().trim();

            if (nomeRep.isEmpty() || mes.isEmpty() || valorStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            representanteSelecionadoId = buscarIdRepresentantePorNome(nomeRep);

            if (representanteSelecionadoId == -1) {
                Toast.makeText(this, "Representante não encontrado", Toast.LENGTH_SHORT).show();
                return;
            }

            double valor;
            try {
                valor = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            db.salvarMeta(representanteSelecionadoId, mes, valor);
            Toast.makeText(this, "Meta salva com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    private int buscarIdRepresentantePorNome(String nome) {
        for (Representante r : representantes) {
            if (r.getNome().trim().equalsIgnoreCase(nome.trim())) {
                return r.getId();
            }
        }
        return -1;
    }
}

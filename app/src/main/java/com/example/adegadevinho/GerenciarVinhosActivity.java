package com.example.adegadevinho;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GerenciarVinhosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewVinhos;
    private EditText editTextNome, editTextTipo, editTextImagem, editTextPreco, editTextSafra, editTextHarmonizacao, editTextDescricao;
    private Button buttonSalvar, buttonLimpar;

    private VinhoDAO vinhoDAO;
    private List<Vinho> listaVinhos;
    private VinhoAdapter vinhoAdapter;

    private Vinho vinhoAtual = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_vinhos);

        recyclerViewVinhos = findViewById(R.id.recyclerViewVinhos);
        editTextNome = findViewById(R.id.editTextNome);
        editTextTipo = findViewById(R.id.editTextTipo);
        editTextImagem = findViewById(R.id.editTextImagem);
        editTextPreco = findViewById(R.id.editTextPreco);
        editTextSafra = findViewById(R.id.editTextSafra);
        editTextHarmonizacao = findViewById(R.id.editTextHarmonizacao);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonLimpar = findViewById(R.id.buttonLimpar);

        vinhoDAO = new VinhoDAO(this);

        recyclerViewVinhos.setLayoutManager(new LinearLayoutManager(this));
        carregarLista();

        buttonSalvar.setOnClickListener(v -> salvarVinho());
        buttonLimpar.setOnClickListener(v -> limparCampos());
    }

    private void carregarLista() {
        listaVinhos = vinhoDAO.getTodosVinhos();
        vinhoAdapter = new VinhoAdapter(listaVinhos, vinho -> {
            vinhoAtual = vinho;
            preencherCampos(vinho);
        });
        recyclerViewVinhos.setAdapter(vinhoAdapter);
    }

    private void preencherCampos(Vinho vinho) {
        editTextNome.setText(vinho.getNome());
        editTextTipo.setText(vinho.getTipo());
        editTextImagem.setText("");
        editTextPreco.setText(String.valueOf(vinho.getPreco()));
        editTextSafra.setText(String.valueOf(vinho.getSafra()));
        editTextHarmonizacao.setText(vinho.getHarmonizacao());
        editTextDescricao.setText(vinho.getDescricao());
    }

    private void limparCampos() {
        vinhoAtual = null;
        editTextNome.setText("");
        editTextTipo.setText("");
        editTextImagem.setText("");
        editTextPreco.setText("");
        editTextSafra.setText("");
        editTextHarmonizacao.setText("");
        editTextDescricao.setText("");
        carregarLista();
    }

    private void salvarVinho() {
        String nome = editTextNome.getText().toString().trim();
        String tipo = editTextTipo.getText().toString().trim();
        String imagemNome = editTextImagem.getText().toString().trim();
        String precoStr = editTextPreco.getText().toString().trim();
        String safraStr = editTextSafra.getText().toString().trim();
        String harmonizacao = editTextHarmonizacao.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(tipo) || TextUtils.isEmpty(imagemNome)
                || TextUtils.isEmpty(precoStr) || TextUtils.isEmpty(safraStr) || TextUtils.isEmpty(descricao)) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }

        double preco;
        int safra;
        try {
            preco = Double.parseDouble(precoStr);
            safra = Integer.parseInt(safraStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Preço ou safra inválidos!", Toast.LENGTH_SHORT).show();
            return;
        }

        int imagemResId = getResources().getIdentifier(imagemNome, "drawable", getPackageName());
        if (imagemResId == 0) {
            Toast.makeText(this, "Imagem não encontrada. Use o nome do arquivo em drawable (sem extensão).", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vinhoAtual == null) {
            Vinho novoVinho = new Vinho(nome, tipo, imagemResId, descricao, preco, safra, harmonizacao);
            long id = vinhoDAO.inserirVinho(novoVinho);
            if (id != -1) {
                Toast.makeText(this, "Vinho adicionado!", Toast.LENGTH_SHORT).show();
                limparCampos();
            } else {
                Toast.makeText(this, "Erro ao adicionar vinho.", Toast.LENGTH_SHORT).show();
            }
        } else {
            vinhoAtual.setNome(nome);
            vinhoAtual.setTipo(tipo);
            vinhoAtual.setImagem(imagemResId);
            vinhoAtual.setPreco(preco);
            vinhoAtual.setSafra(safra);
            vinhoAtual.setHarmonizacao(harmonizacao);
            vinhoAtual.setDescricao(descricao);

            boolean sucesso = vinhoDAO.atualizarVinho(vinhoAtual);
            if (sucesso) {
                Toast.makeText(this, "Vinho atualizado!", Toast.LENGTH_SHORT).show();
                limparCampos();
            } else {
                Toast.makeText(this, "Erro ao atualizar vinho.", Toast.LENGTH_SHORT).show();
            }
        }

        carregarLista();
    }
}

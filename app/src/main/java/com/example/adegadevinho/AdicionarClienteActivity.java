package com.example.adegadevinho;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdicionarClienteActivity extends AppCompatActivity {

    private EditText edtNome, edtCnpjCpf, edtEndereco, edtResponsavel, edtTelefone, edtEmail, edtDataCadastro, editRegiao, edtBairro;

    private DatabaseHelper databaseHelper;
    private boolean isUpdatingCpfCnpj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cliente);

        databaseHelper = new DatabaseHelper(this);

        edtNome = findViewById(R.id.edtNome);
        edtCnpjCpf = findViewById(R.id.edtCnpjCpf);
        edtEndereco = findViewById(R.id.edtEndereco);
        edtResponsavel = findViewById(R.id.edtResponsavel);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtEmail = findViewById(R.id.edtEmail);
        edtDataCadastro = findViewById(R.id.edtDataCadastro);
        editRegiao = findViewById(R.id.editRegiao);
        edtBairro = findViewById(R.id.edtBairro);
        Button btnSalvar = findViewById(R.id.btnAdicionarCliente);

        configurarMascaras();

        btnSalvar.setOnClickListener(v -> adicionarCliente());
    }

    private void configurarMascaras() {
        edtCnpjCpf.addTextChangedListener(new TextWatcher() {
            private boolean deleting;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                deleting = count > after;
            }

            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdatingCpfCnpj) {
                    isUpdatingCpfCnpj = false;
                    return;
                }
                String str = s.toString().replaceAll("\\D", "");
                if (deleting) return;

                String formatted = (str.length() <= 11) ? formatarCPF(str) : formatarCNPJ(str);

                isUpdatingCpfCnpj = true;
                edtCnpjCpf.setText(formatted);
                edtCnpjCpf.setSelection(formatted.length());
            }
        });

        edtTelefone.addTextChangedListener(new TextWatcher() {
            private boolean deletingTel;
            private boolean isUpdatingTel;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                deletingTel = count > after;
            }
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdatingTel) {
                    isUpdatingTel = false;
                    return;
                }
                String str = s.toString().replaceAll("\\D", "");
                if (deletingTel) return;

                String formatted = (str.length() <= 10)
                        ? formatarTelefoneFixo(str)
                        : formatarTelefoneCelular(str);

                isUpdatingTel = true;
                edtTelefone.setText(formatted);
                edtTelefone.setSelection(formatted.length());
            }
        });

        edtDataCadastro.addTextChangedListener(new TextWatcher() {
            private boolean deletingDate;
            private boolean isUpdatingDate;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                deletingDate = count > after;
            }
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdatingDate) {
                    isUpdatingDate = false;
                    return;
                }
                String str = s.toString().replaceAll("\\D", "");
                if (deletingDate) return;

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < str.length() && i < 8; i++) {
                    sb.append(str.charAt(i));
                    if (i == 1 || i == 3) sb.append('/');
                }

                isUpdatingDate = true;
                edtDataCadastro.setText(sb.toString());
                edtDataCadastro.setSelection(sb.length());
            }
        });

        edtEmail.setInputType(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int st, int before, int count) {
                String email = s.toString().trim();
                if (email.isEmpty()) {
                    edtEmail.setError(null);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtEmail.setError("Email inválido");
                } else {
                    edtEmail.setError(null);
                }
            }
        });
    }

    private String formatarCPF(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length() && i < 11; i++) {
            sb.append(str.charAt(i));
            if (i == 2 || i == 5) sb.append('.');
            else if (i == 8) sb.append('-');
        }
        return sb.toString();
    }

    private String formatarCNPJ(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length() && i < 14; i++) {
            sb.append(str.charAt(i));
            if (i == 1 || i == 4) sb.append('.');
            else if (i == 7) sb.append('/');
            else if (i == 11) sb.append('-');
        }
        return sb.toString();
    }

    private String formatarTelefoneFixo(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length() && i < 10; i++) {
            if (i == 0) sb.append('(');
            sb.append(str.charAt(i));
            if (i == 1) sb.append(") ");
            else if (i == 5) sb.append('-');
        }
        return sb.toString();
    }

    private String formatarTelefoneCelular(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length() && i < 11; i++) {
            if (i == 0) sb.append('(');
            sb.append(str.charAt(i));
            if (i == 1) sb.append(") ");
            else if (i == 6) sb.append('-');
        }
        return sb.toString();
    }

    private void adicionarCliente() {
        String nome = edtNome.getText().toString().trim();
        String cnpjCpf = edtCnpjCpf.getText().toString().trim();
        String endereco = edtEndereco.getText().toString().trim();
        String responsavel = edtResponsavel.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String dataCadastro = edtDataCadastro.getText().toString().trim();
        String regiao = editRegiao.getText().toString().trim();
        String bairro = edtBairro.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Insira um email válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nome.isEmpty() || cnpjCpf.isEmpty() || endereco.isEmpty() ||
                responsavel.isEmpty() || telefone.isEmpty() || email.isEmpty() ||
                dataCadastro.isEmpty() || regiao.isEmpty() || bairro.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        long clienteId = databaseHelper.inserirCliente(
                nome, cnpjCpf, endereco, responsavel, telefone, email, dataCadastro, regiao, bairro
        );

        if (clienteId > 0) {
            Toast.makeText(this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar cliente", Toast.LENGTH_SHORT).show();
        }
    }
}

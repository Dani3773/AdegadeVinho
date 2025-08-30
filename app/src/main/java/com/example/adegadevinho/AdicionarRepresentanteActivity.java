package com.example.adegadevinho;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.util.Patterns;

public class AdicionarRepresentanteActivity extends AppCompatActivity {

    private EditText edtNome, edtCpfCnpj, edtTelefone, edtEmail, edtEndereco;
    private DatabaseHelper databaseHelper;
    private boolean isUpdating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_representante);

        databaseHelper = new DatabaseHelper(this);

        edtNome = findViewById(R.id.edtNomeRepresentante);
        edtCpfCnpj = findViewById(R.id.edtCpfRepresentante);
        edtTelefone = findViewById(R.id.edtTelefoneRepresentante);
        edtEmail = findViewById(R.id.edtEmailRepresentante);
        edtEndereco = findViewById(R.id.edtEnderecoRepresentante);
        Button btnSalvar = findViewById(R.id.btnSalvarRepresentante);

        edtCpfCnpj.addTextChangedListener(new TextWatcher() {
            private boolean deleting;

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                deleting = count > after;
            }
            @Override public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }
                String str = s.toString().replaceAll("\\D", "");
                if (deleting) return;

                String formatted = (str.length() <= 11) ? formatarCPF(str) : formatarCNPJ(str);
                isUpdating = true;
                edtCpfCnpj.setText(formatted);
                edtCpfCnpj.setSelection(formatted.length());
            }
        });

        edtTelefone.addTextChangedListener(new TextWatcher() {
            private boolean deletingTel;
            private boolean isUpdatingTel;

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                deletingTel = count > after;
            }
            @Override public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdatingTel) {
                    isUpdatingTel = false;
                    return;
                }
                String str = s.toString().replaceAll("\\D", "");
                if (deletingTel) return;

                String formatted = (str.length() <= 10) ? formatarTelefoneFixo(str) : formatarTelefoneCelular(str);
                isUpdatingTel = true;
                edtTelefone.setText(formatted);
                edtTelefone.setSelection(formatted.length());
            }
        });

        edtEndereco.addTextChangedListener(new TextWatcher() {
            private boolean deletingCep;

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                deletingCep = count > after;
            }
            @Override public void afterTextChanged(Editable s) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }
                String str = s.toString().replaceAll("\\D", "");
                if (deletingCep) return;

                String formatted = formatarCEP(str);
                isUpdating = true;
                edtEndereco.setText(formatted);
                edtEndereco.setSelection(formatted.length());
            }
        });

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) { }
            @Override public void afterTextChanged(Editable s) { }
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

        btnSalvar.setOnClickListener(v -> salvarRepresentante());
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

    private String formatarCEP(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length() && i < 8; i++) {
            sb.append(str.charAt(i));
            if (i == 4) sb.append('-');
        }
        return sb.toString();
    }

    private void salvarRepresentante() {
        String nome = edtNome.getText().toString().trim();
        String cpfCnpj = edtCpfCnpj.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String endereco = edtEndereco.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Insira um email válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nome.isEmpty() || cpfCnpj.isEmpty() || telefone.isEmpty() ||
                email.isEmpty() || endereco.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Representante rep = new Representante(nome, cpfCnpj, telefone, email, endereco);
        long id = databaseHelper.inserirRepresentante(rep);

        if (id > 0) {
            Toast.makeText(this, "Representante cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar representante", Toast.LENGTH_SHORT).show();
        }
    }
}

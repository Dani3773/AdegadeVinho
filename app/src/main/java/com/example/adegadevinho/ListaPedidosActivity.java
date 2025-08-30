package com.example.adegadevinho;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.*;

public class ListaPedidosActivity extends AppCompatActivity {

    private EditText etDataInicio, etDataFim, editNomeCliente;
    private Spinner spinnerStatus;
    private Button btnFiltrar;
    private ListView listViewPedidos;

    private DatabaseHelper db;
    private List<Pedido> pedidosFiltrados;
    private ArrayAdapter<String> adapterLista;
    private List<Cliente> clientes;
    private String[] statusArray = {"Todos", "Em andamento", "Entregue", "Cancelado"};

    private int clienteSelecionadoId = -1;
    private String statusSelecionado = "";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        etDataInicio = findViewById(R.id.etDataInicio);
        etDataFim = findViewById(R.id.etDataFim);
        editNomeCliente = findViewById(R.id.editNomeCliente);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnFiltrar = findViewById(R.id.btnFiltrar);
        listViewPedidos = findViewById(R.id.listViewPedidos);

        db = new DatabaseHelper(this);

        setupDatePicker(etDataInicio);
        setupDatePicker(etDataFim);

        clientes = db.getTodosClientes();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                statusArray
        );
        adapter.setDropDownViewResource(R.layout.spinner_item);

        spinnerStatus.setAdapter(adapter);

        btnFiltrar.setOnClickListener(v -> aplicarFiltro());

        listViewPedidos.setOnItemClickListener((parent, view, position, id) -> {
            Pedido pedido = pedidosFiltrados.get(position);

            Intent intent = new Intent(ListaPedidosActivity.this, DetalhePedidoActivity.class);
            intent.putExtra("pedido_id", pedido.getId());

            startActivity(intent);
        });
    }

    private void setupDatePicker(EditText campo) {
        campo.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(ListaPedidosActivity.this,
                    (view, year, month, day) -> {
                        calendar.set(year, month, day);
                        campo.setText(dateFormat.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void aplicarFiltro() {
        String dataInicio = etDataInicio.getText().toString().trim();
        String dataFim = etDataFim.getText().toString().trim();
        String nomeCliente = editNomeCliente.getText().toString().trim();

        clienteSelecionadoId = -1;
        if (!nomeCliente.isEmpty()) {
            for (Cliente c : clientes) {
                if (c.getNome().equalsIgnoreCase(nomeCliente)) {
                    clienteSelecionadoId = c.getId();
                    break;
                }
            }
        }

        statusSelecionado = spinnerStatus.getSelectedItem().toString();

        pedidosFiltrados = db.getPedidosFiltrados(dataInicio, dataFim, clienteSelecionadoId, statusSelecionado);
        List<String> pedidosFormatados = new ArrayList<>();

        for (Pedido p : pedidosFiltrados) {
            String linha = "Pedido #" + p.getId() + " - " + p.getData() + " - R$" + String.format("%.2f", p.getTotal());
            pedidosFormatados.add(linha);
        }

        adapterLista = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, pedidosFormatados);
        listViewPedidos.setAdapter(adapterLista);
    }
}

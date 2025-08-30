package com.example.adegadevinho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "adega.db";
    private static final int DATABASE_VERSION = 9;

    public static final String TABLE_CLIENTES = "clientes";
    public static final String COLUMN_CLIENTE_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_CNPJ_CPF = "cnpj_cpf";
    public static final String COLUMN_ENDERECO = "endereco";
    public static final String COLUMN_RESPONSAVEL = "responsavel";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_DATA_CADASTRO = "data_cadastro";
    public static final String COLUMN_BAIRRO = "bairro";
    public static final String COLUMN_REGIAO = "regiao";

    public static final String TABLE_VINHOS = "vinhos";
    public static final String COLUMN_VINHO_ID = "id";
    public static final String COLUMN_VINHO_NOME = "nome";
    public static final String COLUMN_VINHO_TIPO = "tipo";
    public static final String COLUMN_VINHO_IMAGEM = "imagem";
    public static final String COLUMN_VINHO_DESCRICAO = "descricao";
    public static final String COLUMN_VINHO_PRECO = "preco";
    public static final String COLUMN_VINHO_SAFRA = "safra";
    public static final String COLUMN_VINHO_HARMONIZACAO = "harmonizacao";

    public static final String TABLE_REPRESENTANTES = "representantes";
    public static final String COLUMN_REPRESENTANTE_ID = "id";
    public static final String COLUMN_REPRESENTANTE_NOME = "nome";
    public static final String COLUMN_REPRESENTANTE_TELEFONE = "telefone";
    public static final String COLUMN_REPRESENTANTE_CPF = "cpf";
    public static final String COLUMN_REPRESENTANTE_EMAIL = "email";
    public static final String COLUMN_REPRESENTANTE_ENDERECO = "endereco";

    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COLUMN_USUARIO_ID = "id";
    public static final String COLUMN_USUARIO = "usuario";
    public static final String COLUMN_SENHA = "senha";
    public static final String COLUMN_TIPO = "tipo";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLIENTES_TABLE = "CREATE TABLE " + TABLE_CLIENTES + " (" +
                COLUMN_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOME + " TEXT, " +
                COLUMN_CNPJ_CPF + " TEXT, " +
                COLUMN_ENDERECO + " TEXT, " +
                COLUMN_RESPONSAVEL + " TEXT, " +
                COLUMN_TELEFONE + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_DATA_CADASTRO + " TEXT, " +
                COLUMN_BAIRRO + " TEXT, " +
                COLUMN_REGIAO + " TEXT)";
        db.execSQL(CREATE_CLIENTES_TABLE);

    String CREATE_VINHOS_TABLE = "CREATE TABLE " + TABLE_VINHOS + " (" +
                COLUMN_VINHO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VINHO_NOME + " TEXT, " +
                COLUMN_VINHO_TIPO + " TEXT, " +
                COLUMN_VINHO_IMAGEM + " INTEGER, " +
                COLUMN_VINHO_DESCRICAO + " TEXT, " +
                COLUMN_VINHO_PRECO + " REAL, " +
                COLUMN_VINHO_SAFRA + " INTEGER, " +
                COLUMN_VINHO_HARMONIZACAO + " TEXT)";
        db.execSQL(CREATE_VINHOS_TABLE);

        String CREATE_REPRESENTANTES_TABLE = "CREATE TABLE " + TABLE_REPRESENTANTES + " (" +
                COLUMN_REPRESENTANTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_REPRESENTANTE_NOME + " TEXT, " +
                COLUMN_REPRESENTANTE_CPF + " TEXT, " +
                COLUMN_REPRESENTANTE_TELEFONE + " TEXT, " +
                COLUMN_REPRESENTANTE_EMAIL + " TEXT, " +
                COLUMN_REPRESENTANTE_ENDERECO + " TEXT)";
        db.execSQL(CREATE_REPRESENTANTES_TABLE);

        String CREATE_USUARIOS_TABLE = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COLUMN_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO + " TEXT, " +
                COLUMN_SENHA + " TEXT, " +
                COLUMN_TIPO + " TEXT)";
        db.execSQL(CREATE_USUARIOS_TABLE);

        String CREATE_METAS_TABLE = "CREATE TABLE metas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "representante_id INTEGER, " +
                "mes TEXT, " +
                "valor_meta REAL)";
        db.execSQL(CREATE_METAS_TABLE);

        String CREATE_PEDIDOS_TABLE = "CREATE TABLE pedidos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cliente_id INTEGER, " +
                "representante_id INTEGER, " +
                "data TEXT, " +
                "total REAL, " +
                "formaPagamento TEXT, " +
                "status TEXT DEFAULT 'Em andamento', " +
                "FOREIGN KEY(cliente_id) REFERENCES clientes(id), " +
                "FOREIGN KEY(representante_id) REFERENCES representantes(id))";
        db.execSQL(CREATE_PEDIDOS_TABLE);

        String CREATE_ITENS_PEDIDO_TABLE = "CREATE TABLE itens_pedido (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pedido_id INTEGER, " +
                "vinho_id INTEGER, " +
                "quantidade INTEGER, " +
                "preco_unitario REAL, " +
                "FOREIGN KEY(pedido_id) REFERENCES pedidos(id), " +
                "FOREIGN KEY(vinho_id) REFERENCES vinhos(id))";
        db.execSQL(CREATE_ITENS_PEDIDO_TABLE);

        inserirUsuariosPadrao(db);
    }

    private void inserirUsuariosPadrao(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO, "representante");
        values.put(COLUMN_SENHA, "123456");
        values.put(COLUMN_TIPO, "representante");
        db.insert(TABLE_USUARIOS, null, values);

        values.clear();
        values.put(COLUMN_USUARIO, "cliente");
        values.put(COLUMN_SENHA, "abc123");
        values.put(COLUMN_TIPO, "cliente");
        db.insert(TABLE_USUARIOS, null, values);

        values.clear();
        values.put(COLUMN_USUARIO, "admin");
        values.put(COLUMN_SENHA, "ABC123");
        values.put(COLUMN_TIPO, "administrador");
        db.insert(TABLE_USUARIOS, null, values);
    }
    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VINHOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPRESENTANTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS metas");
        db.execSQL("DROP TABLE IF EXISTS pedidos");
        db.execSQL("DROP TABLE IF EXISTS itens_pedido");
        onCreate(db);
    }

    public void salvarMeta(int representanteId, String mes, double valorMeta) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT id FROM metas WHERE representante_id = ? AND mes = ?",
                new String[]{String.valueOf(representanteId), mes});

        ContentValues valores = new ContentValues();
        valores.put("representante_id", representanteId);
        valores.put("mes", mes);
        valores.put("valor_meta", valorMeta);

        if (c.moveToFirst()) {
            int id = c.getInt(c.getColumnIndexOrThrow("id"));
            db.update("metas", valores, "id = ?", new String[]{String.valueOf(id)});
        } else {
            db.insert("metas", null, valores);
        }

        c.close();
        db.close();
    }


    public String validarLogin(String usuario, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tipo = null;

        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_TIPO + " FROM " + TABLE_USUARIOS + " WHERE " +
                        COLUMN_USUARIO + " = ? AND " + COLUMN_SENHA + " = ?",
                new String[]{usuario, senha}
        );

        if (cursor.moveToFirst()) {
            tipo = cursor.getString(0);
        }

        cursor.close();
        db.close();
        return tipo;
    }

    public Usuario getUsuarioPorLogin(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        Usuario usuario = null;

        Cursor c = db.rawQuery("SELECT * FROM usuarios WHERE usuario = ?", new String[]{login});
        if (c.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            usuario.setNome(c.getString(c.getColumnIndexOrThrow("usuario")));
            usuario.setEmail("");
            usuario.setTipo(c.getString(c.getColumnIndexOrThrow("tipo")));
        }

        c.close();
        db.close();
        return usuario;
    }

    public long inserirCliente(String nome, String cnpjCpf, String endereco, String responsavel,
                               String telefone, String email, String dataCadastro, String regiao, String bairro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_CNPJ_CPF, cnpjCpf);
        values.put(COLUMN_ENDERECO, endereco);
        values.put(COLUMN_RESPONSAVEL, responsavel);
        values.put(COLUMN_TELEFONE, telefone);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_DATA_CADASTRO, dataCadastro);
        values.put(COLUMN_BAIRRO, bairro);
        values.put(COLUMN_REGIAO, regiao);

        long id = -1;
        try {
            id = db.insert(TABLE_CLIENTES, null, values);
            if (id == -1) {
                android.util.Log.e("DB_ERROR", "Falha ao inserir cliente. Valores: " + values.toString());
            }
        } catch (Exception e) {
            android.util.Log.e("DB_ERROR", "Erro ao inserir cliente", e);
        } finally {
            db.close();
        }

        return id;
    }

    public long inserirRepresentante(Representante representante) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", representante.getNome());
        values.put("cpf", representante.getCpf());
        values.put("telefone", representante.getTelefone());
        values.put("email", representante.getEmail());
        values.put("endereco", representante.getEndereco());

        long representanteId = db.insert("representantes", null, values);

        if (representanteId != -1) {
            String usuario = gerarUsuarioUnico(representante.getNome(), db);
            String senha = gerarSenhaAleatoria(8);

            ContentValues userValues = new ContentValues();
            userValues.put("usuario", usuario);
            userValues.put("senha", senha);
            userValues.put("tipo", "representante");

            db.insert("usuarios", null, userValues);

            android.util.Log.d("USUARIO_GERADO", "Usuário: " + usuario + " | Senha: " + senha);
        }

        db.close();
        return representanteId;
    }

    private String gerarUsuarioUnico(String nome, SQLiteDatabase db) {
        String base = nome.trim().toLowerCase().replaceAll("\\s+", ".");
        base = base.length() > 15 ? base.substring(0, 15) : base;

        String usuarioFinal = base;
        int sufixo = 1;

        Cursor c = db.rawQuery("SELECT usuario FROM usuarios WHERE usuario = ?", new String[]{usuarioFinal});
        while (c.moveToFirst()) {
            usuarioFinal = base + sufixo;
            c = db.rawQuery("SELECT usuario FROM usuarios WHERE usuario = ?", new String[]{usuarioFinal});
            sufixo++;
        }
        c.close();
        return usuarioFinal;
    }

    private String gerarSenhaAleatoria(int tamanho) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder senha = new StringBuilder();
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < tamanho; i++) {
            int index = random.nextInt(caracteres.length());
            senha.append(caracteres.charAt(index));
        }

        return senha.toString();
    }

    public List<Pedido> getRelatorioPedidosPorNome(String dataInicio, String dataFim, String nomeCliente, String regiao, String nomeRepresentante) {
        List<Pedido> pedidos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder query = new StringBuilder();
        List<String> args = new ArrayList<>();

        query.append("SELECT p.* FROM pedidos p ");
        query.append("INNER JOIN clientes c ON p.cliente_id = c.id ");
        query.append("INNER JOIN usuarios u ON p.representante_id = u.id ");
        query.append("WHERE 1=1 ");

        if (dataInicio != null && !dataInicio.isEmpty()) {
            query.append("AND date(p.data) >= date(?) ");
            args.add(dataInicio);
        }
        if (dataFim != null && !dataFim.isEmpty()) {
            query.append("AND date(p.data) <= date(?) ");
            args.add(dataFim);
        }

        if (nomeCliente != null && !nomeCliente.isEmpty()) {
            query.append("AND c.nome LIKE ? ");
            args.add("%" + nomeCliente + "%");
        }

        if (regiao != null && !regiao.isEmpty()) {
            query.append("AND c.regiao LIKE ? ");
            args.add("%" + regiao + "%");
        }

        if (nomeRepresentante != null && !nomeRepresentante.isEmpty()) {
            query.append("AND u.nome LIKE ? ");
            args.add("%" + nomeRepresentante + "%");
        }

        Cursor cursor = db.rawQuery(query.toString(), args.toArray(new String[0]));

        if (cursor.moveToFirst()) {
            do {
                Pedido pedido = new Pedido();
                pedido.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                pedido.setClienteId(cursor.getInt(cursor.getColumnIndexOrThrow("cliente_id")));
                pedido.setRepresentanteId(cursor.getInt(cursor.getColumnIndexOrThrow("representante_id")));
                pedido.setData(cursor.getString(cursor.getColumnIndexOrThrow("data")));
                pedido.setFormaPagamento(cursor.getString(cursor.getColumnIndexOrThrow("forma_pagamento")));
                pedido.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                pedido.setTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("total")));

                pedidos.add(pedido);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return pedidos;
    }

    public List<String> listarClientes() {
        List<String> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT nome, cnpj_cpf, email FROM clientes", null
        );

        if (cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(0);
                String cnpjCpf = cursor.getString(1);
                String email = cursor.getString(2);

                String dados = "Nome: " + nome + "\nCNPJ/CPF: " + cnpjCpf + "\nEmail: " + email;
                lista.add(dados);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }


    public List<Cliente> getTodosClientes() {
        List<Cliente> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM clientes", null);

        if (c.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setId(c.getInt(c.getColumnIndexOrThrow("id")));
                cliente.setNome(c.getString(c.getColumnIndexOrThrow("nome")));
                cliente.setCnpjCpf(c.getString(c.getColumnIndexOrThrow("cnpj_cpf")));
                cliente.setEndereco(c.getString(c.getColumnIndexOrThrow("endereco")));
                cliente.setResponsavel(c.getString(c.getColumnIndexOrThrow("responsavel")));
                cliente.setTelefone(c.getString(c.getColumnIndexOrThrow("telefone")));
                cliente.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
                cliente.setDataCadastro(c.getString(c.getColumnIndexOrThrow("data_cadastro")));
                cliente.setBairro(c.getString(c.getColumnIndexOrThrow("bairro")));
                cliente.setRegiao(c.getString(c.getColumnIndexOrThrow("regiao")));
                lista.add(cliente);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return lista;
    }

    public boolean atualizarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(COLUMN_NOME, cliente.getNome());
        valores.put(COLUMN_CNPJ_CPF, cliente.getCnpjCpf());
        valores.put(COLUMN_TELEFONE, cliente.getTelefone());
        valores.put(COLUMN_EMAIL, cliente.getEmail());
        valores.put(COLUMN_ENDERECO, cliente.getEndereco());
        valores.put(COLUMN_BAIRRO, cliente.getBairro());
        valores.put(COLUMN_REGIAO, cliente.getRegiao());
        valores.put(COLUMN_RESPONSAVEL, cliente.getResponsavel());
        valores.put(COLUMN_DATA_CADASTRO, cliente.getDataCadastro());

        int linhasAfetadas = db.update(
                TABLE_CLIENTES,
                valores,
                COLUMN_CLIENTE_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())}
        );

        db.close();
        return linhasAfetadas > 0;
    }

    public boolean excluirCliente(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int linhasAfetadas = db.delete("clientes", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return linhasAfetadas > 0;
    }

    public int getClienteIdPorNome(String nome) {
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM clientes WHERE nome = ?", new String[]{nome});
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }
    public int getRepresentanteIdPorNome(String nome) {
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM representantes WHERE nome = ?", new String[]{nome});
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }
    public Cliente getClientePorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cliente cliente = null;
        Cursor c = db.rawQuery("SELECT * FROM clientes WHERE id = ?", new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {
            cliente = new Cliente();
            cliente.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            cliente.setNome(c.getString(c.getColumnIndexOrThrow("nome")));
            cliente.setCnpjCpf(c.getString(c.getColumnIndexOrThrow("cnpj_cpf")));
            cliente.setEndereco(c.getString(c.getColumnIndexOrThrow("endereco")));
            cliente.setResponsavel(c.getString(c.getColumnIndexOrThrow("responsavel")));
            cliente.setTelefone(c.getString(c.getColumnIndexOrThrow("telefone")));
            cliente.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
            cliente.setDataCadastro(c.getString(c.getColumnIndexOrThrow("data_cadastro")));
            cliente.setRegiao(c.getString(c.getColumnIndexOrThrow("regiao")));
        }

        c.close();
        db.close();
        return cliente;
    }

    public Representante getRepresentantePorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Representante representante = null;

        Cursor cursor = db.rawQuery("SELECT * FROM representantes WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            representante = new Representante();
            representante.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            representante.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            representante.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            representante.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow("telefone")));
        }

        cursor.close();
        db.close();
        return representante;
    }

    public List<String> getTodasRegioes() {
        List<String> regioes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT DISTINCT regiao FROM clientes", null);

        if (c.moveToFirst()) {
            do {
                regioes.add(c.getString(c.getColumnIndexOrThrow("regiao")));
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return regioes;
    }

    public void excluirRepresentante(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("representantes", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void atualizarRepresentante(Representante representante) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", representante.getNome());
        values.put("telefone", representante.getTelefone());
        values.put("email", representante.getEmail());
        values.put("cpf", representante.getCpf());
        values.put("endereco", representante.getEndereco());

        db.update("representantes", values, "id = ?", new String[]{String.valueOf(representante.getId())});
        db.close();
    }

    public List<Representante> getTodosRepresentantes() {
        List<Representante> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nome, cpf, telefone, email, endereco FROM representantes", null);

        if (cursor.moveToFirst()) {
            do {
                Representante r = new Representante();
                r.setId(cursor.getInt(0));
                r.setNome(cursor.getString(1));
                r.setCpf(cursor.getString(2));
                r.setTelefone(cursor.getString(3));
                r.setEmail(cursor.getString(4));
                r.setEndereco(cursor.getString(5));
                lista.add(r);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }
    public Vinho getVinhoPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Vinho vinho = null;
        Cursor c = db.rawQuery("SELECT * FROM vinhos WHERE id = ?", new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {
            vinho = new Vinho();
            vinho.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            vinho.setNome(c.getString(c.getColumnIndexOrThrow("nome")));
            vinho.setTipo(c.getString(c.getColumnIndexOrThrow("tipo")));
            vinho.setImagem(c.getInt(c.getColumnIndexOrThrow("imagem")));
            vinho.setDescricao(c.getString(c.getColumnIndexOrThrow("descricao")));
            vinho.setPreco(c.getDouble(c.getColumnIndexOrThrow("preco")));
            vinho.setSafra(c.getInt(c.getColumnIndexOrThrow("safra")));
            vinho.setHarmonizacao(c.getString(c.getColumnIndexOrThrow("harmonizacao")));
        }

        c.close();
        db.close();
        return vinho;
    }

    public List<Vinho> getTodosVinhos() {
        List<Vinho> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM vinhos", null);

        if (c.moveToFirst()) {
            do {
                Vinho vinho = new Vinho();
                vinho.setId(c.getInt(c.getColumnIndexOrThrow("id")));
                vinho.setNome(c.getString(c.getColumnIndexOrThrow("nome")));
                vinho.setTipo(c.getString(c.getColumnIndexOrThrow("tipo")));
                vinho.setImagem(c.getInt(c.getColumnIndexOrThrow("imagem")));
                vinho.setDescricao(c.getString(c.getColumnIndexOrThrow("descricao")));
                vinho.setPreco(c.getDouble(c.getColumnIndexOrThrow("preco")));
                vinho.setSafra(c.getInt(c.getColumnIndexOrThrow("safra")));
                vinho.setHarmonizacao(c.getString(c.getColumnIndexOrThrow("harmonizacao")));
                lista.add(vinho);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return lista;
    }

    public List<Pedido> getPedidosFiltrados(String dataInicio, String dataFim, int clienteId, String status) {
        List<Pedido> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM pedidos WHERE 1=1";
        List<String> args = new ArrayList<>();

        if (!dataInicio.isEmpty()) {
            query += " AND date(data) >= date(?)";
            args.add(dataInicio);
        }

        if (!dataFim.isEmpty()) {
            query += " AND date(data) <= date(?)";
            args.add(dataFim);
        }

        if (clienteId != -1) {
            query += " AND cliente_id = ?";
            args.add(String.valueOf(clienteId));
        }

        if (!status.equalsIgnoreCase("Todos")) {
            query += " AND status = ?";
            args.add(status);
        }

        Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));

        if (cursor.moveToFirst()) {
            do {
                Pedido pedido = new Pedido();
                pedido.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                pedido.setClienteId(cursor.getInt(cursor.getColumnIndexOrThrow("cliente_id")));
                pedido.setRepresentanteId(cursor.getInt(cursor.getColumnIndexOrThrow("representante_id")));
                pedido.setData(cursor.getString(cursor.getColumnIndexOrThrow("data")));
                pedido.setFormaPagamento(cursor.getString(cursor.getColumnIndexOrThrow("formaPagamento")));
                pedido.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                pedido.setTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("total")));

                lista.add(pedido);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    public long criarPedido(int clienteId, int representanteId, String data, String formaPagamento,
                            String status, double total, List<ItemPedido> itens) {
        SQLiteDatabase db = this.getWritableDatabase();
        long pedidoId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("cliente_id", clienteId);
            values.put("representante_id", representanteId);
            values.put("data", data);
            values.put("formaPagamento", formaPagamento);
            values.put("status", status);
            values.put("total", total);
            pedidoId = db.insertOrThrow("pedidos", null, values);

            for (ItemPedido item : itens) {
                ContentValues itemValues = new ContentValues();
                itemValues.put("pedido_id", pedidoId);
                itemValues.put("vinho_id", item.getVinhoId());
                itemValues.put("quantidade", item.getQuantidade());
                itemValues.put("preco_unitario", item.getPrecoUnitario());
                db.insertOrThrow("itens_pedido", null, itemValues);
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
            pedidoId = -1;
        } finally {
            db.endTransaction();
            db.close();
        }


        return pedidoId;
    }

    public Pedido getPedidoPorId(int pedidoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Pedido pedido = null;
        Cursor c = db.rawQuery("SELECT * FROM pedidos WHERE id = ?", new String[]{String.valueOf(pedidoId)});

        if (c.moveToFirst()) {
            pedido = new Pedido();
            pedido.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            pedido.setClienteId(c.getInt(c.getColumnIndexOrThrow("cliente_id")));
            pedido.setRepresentanteId(c.getInt(c.getColumnIndexOrThrow("representante_id")));
            pedido.setData(c.getString(c.getColumnIndexOrThrow("data")));
            pedido.setFormaPagamento(c.getString(c.getColumnIndexOrThrow("formaPagamento")));
            pedido.setStatus(c.getString(c.getColumnIndexOrThrow("status")));
            pedido.setTotal(c.getDouble(c.getColumnIndexOrThrow("total")));
        }

        c.close();
        db.close();
        return pedido;
    }

    public List<ItemPedido> getItensPorPedido(int pedidoId) {
        List<ItemPedido> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        try {
            c = db.rawQuery("SELECT * FROM itens_pedido WHERE pedido_id = ?", new String[]{String.valueOf(pedidoId)});

            if (c.moveToFirst()) {
                do {
                    ItemPedido item = new ItemPedido();
                    item.setId(c.getInt(c.getColumnIndexOrThrow("id")));
                    item.setPedidoId(c.getInt(c.getColumnIndexOrThrow("pedido_id")));
                    item.setVinhoId(c.getInt(c.getColumnIndexOrThrow("vinho_id")));
                    item.setQuantidade(c.getInt(c.getColumnIndexOrThrow("quantidade")));
                    item.setPrecoUnitario(c.getDouble(c.getColumnIndexOrThrow("preco_unitario")));
                    lista.add(item);
                } while (c.moveToNext());
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return lista;
    }

    public double getMetaMensalRepresentante(int representanteId, String mes) {
        SQLiteDatabase db = this.getReadableDatabase();
        double meta = 0.0;

        Cursor c = db.rawQuery(
                "SELECT valor_meta FROM metas WHERE representante_id = ? AND mes = ?",
                new String[]{String.valueOf(representanteId), mes}
        );

        if (c.moveToFirst()) {
            meta = c.getDouble(c.getColumnIndexOrThrow("valor_meta"));
        }

        c.close();
        db.close();
        return meta;
    }

    public double getTotalVendasMesPorRepresentante(int representanteId, String anoMes) {
        SQLiteDatabase db = this.getReadableDatabase();
        double total = 0;

        String query = "SELECT SUM(total) as totalVendido FROM pedidos " +
                "WHERE representante_id = ? AND strftime('%Y-%m', data) = ?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(representanteId), anoMes});

        if (c.moveToFirst()) {
            total = c.getDouble(c.getColumnIndexOrThrow("totalVendido"));
        }

        c.close();
        db.close();
        return total;
    }


    public List<Pedido> getRelatorioPedidos(String dataInicio, String dataFim, int clienteId, String regiao, int representanteId) {
        List<Pedido> lista = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();

            StringBuilder query = new StringBuilder();
            query.append("SELECT p.* FROM pedidos p ")
                    .append("JOIN clientes c ON p.cliente_id = c.id ")
                    .append("WHERE 1=1 ");

            List<String> args = new ArrayList<>();

            if (dataInicio != null && !dataInicio.isEmpty()) {
                query.append("AND date(p.data) >= date(?) ");
                args.add(dataInicio);
            }

            if (dataFim != null && !dataFim.isEmpty()) {
                query.append("AND date(p.data) <= date(?) ");
                args.add(dataFim);
            }

            if (clienteId != -1) {
                query.append("AND p.cliente_id = ? ");
                args.add(String.valueOf(clienteId));
            }

            if (regiao != null && !regiao.isEmpty()) {
                query.append("AND c.regiao = ? ");
                args.add(regiao);
            }

            if (representanteId != -1) {
                query.append("AND p.representante_id = ? ");
                args.add(String.valueOf(representanteId));
            }

            cursor = db.rawQuery(query.toString(), args.toArray(new String[0]));

            if (cursor.moveToFirst()) {
                do {
                    Pedido pedido = new Pedido();
                    pedido.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    pedido.setClienteId(cursor.getInt(cursor.getColumnIndexOrThrow("cliente_id")));
                    pedido.setRepresentanteId(cursor.getInt(cursor.getColumnIndexOrThrow("representante_id")));
                    pedido.setData(cursor.getString(cursor.getColumnIndexOrThrow("data")));
                    pedido.setFormaPagamento(cursor.getString(cursor.getColumnIndexOrThrow("formaPagamento"))); // ajuste caso o nome da coluna seja diferente
                    pedido.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                    pedido.setTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("total")));

                    lista.add(pedido);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return lista;
    }
}
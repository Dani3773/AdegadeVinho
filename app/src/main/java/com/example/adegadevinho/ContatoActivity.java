package com.example.adegadevinho;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContatoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        Button whatsappDaniel = findViewById(R.id.whatsapp_daniel);
        Button whatsappGustavo = findViewById(R.id.whatsapp_gustavo);
        Button whatsappIgor = findViewById(R.id.whatsapp_igor);
        Button whatsappLucas = findViewById(R.id.whatsapp_lucas);
        Button emailButton = findViewById(R.id.email_button);

        whatsappDaniel.setOnClickListener(v -> openWhatsApp("48988612447"));
        whatsappGustavo.setOnClickListener(v -> openWhatsApp("48996046976"));
        whatsappIgor.setOnClickListener(v -> openWhatsApp("48999615935"));
        whatsappLucas.setOnClickListener(v -> openWhatsApp("48998604034"));
        emailButton.setOnClickListener(v -> sendEmail());
    }

    private void openWhatsApp(String numero) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/" + numero));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao abrir o WhatsApp", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "danielfelisberto3@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contato do App");
        startActivity(Intent.createChooser(emailIntent, "Escolha o app de E-mail"));
    }
}

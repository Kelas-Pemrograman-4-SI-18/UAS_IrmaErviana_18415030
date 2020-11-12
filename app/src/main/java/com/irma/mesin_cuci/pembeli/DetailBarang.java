package com.irma.mesin_cuci.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.irma.mesin_cuci.R;
import com.irma.mesin_cuci.server.BaseURL;
import com.squareup.picasso.Picasso;

public class DetailBarang extends AppCompatActivity {

    EditText edtKodeBarang, edtBrand, edtType, edtGaransi, edtBeratBeban, edtHarga, edtStok;
    ImageView gambar;

    String strkodebarang, strbrand, strtype, strgaransi, strberatbeban, strharga, strstok, strgambar, _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        edtKodeBarang = (EditText) findViewById(R.id.edtKodeBarang);
        edtBrand = (EditText) findViewById(R.id.edtBrand);
        edtType = (EditText) findViewById(R.id.edtType);
        edtGaransi = (EditText) findViewById(R.id.edtGaransi);
        edtBeratBeban = (EditText) findViewById(R.id.edtBeratBeban);
        edtHarga = (EditText) findViewById(R.id.edtHarga);
        edtStok = (EditText) findViewById(R.id.edtStok);

        gambar = (ImageView) findViewById(R.id.gambar);

        Intent i = getIntent();
        strkodebarang = i.getStringExtra("kodebarang");
        strbrand = i.getStringExtra("brand");
        strtype = i.getStringExtra("type");
        strgaransi = i.getStringExtra("garansi");
        strberatbeban = i.getStringExtra("beratbeban");
        strharga = i.getStringExtra("harga");
        strstok = i.getStringExtra("stok");
        strgambar = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        edtKodeBarang.setText(strkodebarang);
        edtBrand.setText(strbrand);
        edtType.setText(strtype);
        edtGaransi.setText(strgaransi);
        edtBeratBeban.setText(strberatbeban);
        edtHarga.setText(strharga);
        edtStok.setText(strstok);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strgambar)
                .into(gambar);

    }
}
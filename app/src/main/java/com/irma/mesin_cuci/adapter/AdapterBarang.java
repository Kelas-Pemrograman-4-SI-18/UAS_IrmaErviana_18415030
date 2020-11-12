package com.irma.mesin_cuci.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.irma.mesin_cuci.R;
import com.irma.mesin_cuci.model.ModelBarang;
import com.irma.mesin_cuci.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;


public class AdapterBarang extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelBarang> item;

    public AdapterBarang(Activity activity, List<ModelBarang> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_barang, null);


        TextView kodebarang    = (TextView) convertView.findViewById(R.id.txtKodeBarang);
        TextView brand    = (TextView) convertView.findViewById(R.id.txtBrand);
        TextView type   = (TextView) convertView.findViewById(R.id.txtType);
        TextView garansi    = (TextView) convertView.findViewById(R.id.txtGaransi);
        TextView beratbeban        = (TextView) convertView.findViewById(R.id.txtBeratBeban);
        TextView harga        = (TextView) convertView.findViewById(R.id.txtHarga);
        TextView stok        = (TextView) convertView.findViewById(R.id.txtStok);
        ImageView gambar        = (ImageView) convertView.findViewById(R.id.gambar);

        kodebarang.setText(item.get(position).getKodebarang());
        brand.setText(item.get(position).getBrand());
        type.setText(item.get(position).getType());
        garansi.setText(item.get(position).getGaransi());
        beratbeban.setText(item.get(position).getBeratbeban());
        harga.setText("Rp." + item.get(position).getHarga());
        stok.setText(item.get(position).getStok());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);
        return convertView;
    }
}

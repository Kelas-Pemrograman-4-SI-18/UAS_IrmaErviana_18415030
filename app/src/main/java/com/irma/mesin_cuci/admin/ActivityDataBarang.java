package com.irma.mesin_cuci.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.irma.mesin_cuci.R;
import com.irma.mesin_cuci.adapter.AdapterBarang;
import com.irma.mesin_cuci.model.ModelBarang;
import com.irma.mesin_cuci.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ActivityDataBarang extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterBarang adapter;
    ListView list;

    ArrayList<ModelBarang> newsList = new ArrayList<ModelBarang>();
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_barang);

        getSupportActionBar().setTitle("Data Barang");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterBarang(ActivityDataBarang.this, newsList);
        list.setAdapter(adapter);
        getAllBarang();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityDataBarang.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllBarang() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.databarang, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("Data Barang = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelBarang barang = new ModelBarang();
                                    final String _id = jsonObject.getString("_id");
                                    final String kodebarang = jsonObject.getString("kodebarang");
                                    final String brand = jsonObject.getString("brand");
                                    final String type = jsonObject.getString("type");
                                    final String garansi = jsonObject.getString("garansi");
                                    final String beratbeban = jsonObject.getString("beratbeban");
                                    final String harga = jsonObject.getString("harga");
                                    final String stok = jsonObject.getString("stok");
                                    final String gambar = jsonObject.getString("gambar");
                                    barang.setKodebarang(kodebarang);
                                    barang.setBrand(brand);
                                    barang.setType(type);
                                    barang.setGaransi(garansi);
                                    barang.setBeratbeban(beratbeban);
                                    barang.setHarga(harga);
                                    barang.setStok(stok);
                                    barang.setGambar(gambar);

                                    barang.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(ActivityDataBarang.this, EditBukuDanHapusActivity.class);
                                            a.putExtra("kodebarang", newsList.get(position).getKodebarang());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("brand", newsList.get(position).getType());
                                            a.putExtra("type", newsList.get(position).getType());
                                            a.putExtra("garansi", newsList.get(position).getGaransi());
                                            a.putExtra("beratbeban", newsList.get(position).getBeratbeban());
                                            a.putExtra("harga", newsList.get(position).getHarga());
                                            a.putExtra("stok", newsList.get(position).getStok());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(barang);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}


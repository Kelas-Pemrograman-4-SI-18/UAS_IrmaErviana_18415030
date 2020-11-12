package com.irma.mesin_cuci.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irma.mesin_cuci.R;
import com.irma.mesin_cuci.adapter.AdapterBarang;
import com.irma.mesin_cuci.admin.ActivityDataBarang;
import com.irma.mesin_cuci.admin.EditBukuDanHapusActivity;
import com.irma.mesin_cuci.admin.HomeAdminActivity;
import com.irma.mesin_cuci.model.ModelBarang;
import com.irma.mesin_cuci.server.BaseURL;
import com.irma.mesin_cuci.session.PrefSetting;
import com.irma.mesin_cuci.session.SessionManager;
import com.irma.mesin_cuci.users.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePembeli extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterBarang adapter;
    ListView list;

    ArrayList<ModelBarang> newsList = new ArrayList<ModelBarang>();
    private RequestQueue mRequestQueue;

    FloatingActionButton floatingExit;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pembeli);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharedPreferences();

        session = new SessionManager(HomePembeli.this);

        prefSetting.isLogin(session, prefs);

        getSupportActionBar().setTitle("Data Buku");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);

        floatingExit = (FloatingActionButton) findViewById(R.id.exit);

        newsList.clear();
        adapter = new AdapterBarang(HomePembeli.this, newsList);
        list.setAdapter(adapter);
        getAllBuku();

//        txtNama = (TextView) findViewById(R.id.txtNama);
//        txtNama.setText(PrefSetting.namaLengkap);

        floatingExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomePembeli.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void getAllBuku() {
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

//                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                            // TODO Auto-generated method stub
//                                            Intent a = new Intent(HomePembeli.this, EditBukuDanHapusActivity.class);
//                                            a.putExtra("kodebarang", newsList.get(position).getKodebarang());
//                                            a.putExtra("_id", newsList.get(position).get_id());
//                                            a.putExtra("brand", newsList.get(position).getType());
//                                            a.putExtra("type", newsList.get(position).getType());
//                                            a.putExtra("garansi", newsList.get(position).getGaransi());
//                                            a.putExtra("beratbeban", newsList.get(position).getBeratbeban());
//                                            a.putExtra("harga", newsList.get(position).getHarga());
//                                            a.putExtra("stok", newsList.get(position).getStok());
//                                            a.putExtra("gambar", newsList.get(position).getGambar());
//                                            startActivity(a);
//                                        }
//                                    });

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
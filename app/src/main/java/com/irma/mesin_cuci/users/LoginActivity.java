package com.irma.mesin_cuci.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.irma.mesin_cuci.R;
import com.irma.mesin_cuci.admin.HomeAdminActivity;
import com.irma.mesin_cuci.pembeli.HomePembeli;
import com.irma.mesin_cuci.server.BaseURL;
import com.irma.mesin_cuci.session.PrefSetting;
import com.irma.mesin_cuci.session.SessionManager;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Button btnPindah;
    NoboButton btnlogin;
    EditText edtUserName, edtPassword;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnPindah = (Button) findViewById(R.id.btnPindah);
        btnlogin = (NoboButton) findViewById(R.id.loginBtn);

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharedPreferences();

        session = new SessionManager(this);

        prefSetting.checkLogin(session, prefs);

        btnPindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, ReegistrasiActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String strUserName = edtUserName.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if(strUserName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (strPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    login(strUserName, strPassword);
                }
            }
        });
    }

    public void login(String username, String pw){

// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("pw", pw);

        pDialog.setMessage("Mohon Tunggu.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(BaseURL.login, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status = response.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();

                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String role = jsonObject.getString("role");
                                String _id = jsonObject.getString("_id");
                                String username = jsonObject.getString("username");
                                String namalengkap = jsonObject.getString("namalengkap");
                                String email = jsonObject.getString("email");
                                String notelp = jsonObject.getString("notelp");
                                session.setLogin(true);
                                prefSetting.storeRegIdSharedPreferences(LoginActivity.this, _id, username, namalengkap, email, notelp, role, prefs);

                                if (role.equals("1")) {
                                Intent i = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                startActivity(i);
                                finish();
                                } else {
                                    Intent i = new Intent(LoginActivity.this, HomePembeli.class);
                                    startActivity(i);
                                    finish();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        mRequestQueue.add(req);
    }

    private void  showDialog(){
        if (!pDialog.isShowing()){
            pDialog.show();
        }
    }
    private void hideDialog(){
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}
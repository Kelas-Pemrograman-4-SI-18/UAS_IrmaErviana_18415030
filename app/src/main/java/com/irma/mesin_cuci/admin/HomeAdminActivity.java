package com.irma.mesin_cuci.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.irma.mesin_cuci.R;
import com.irma.mesin_cuci.session.PrefSetting;
import com.irma.mesin_cuci.session.SessionManager;
import com.irma.mesin_cuci.users.LoginActivity;

public class HomeAdminActivity extends AppCompatActivity {

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView CardExit, cardDataBarang, CardInputBrg, carpdrofile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharedPreferences();

        session = new SessionManager(HomeAdminActivity.this);
        prefSetting.isLogin(session, prefs);

        CardExit = (CardView) findViewById(R.id.CardExit);
        cardDataBarang = (CardView) findViewById(R.id.cardDataBarang);
        CardInputBrg = (CardView) findViewById(R.id.CardInputBrg);
        carpdrofile = (CardView) findViewById(R.id.cardprofile);

        CardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomeAdminActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardDataBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, ActivityDataBarang.class);
                startActivity(i);
                finish();
            }
        });
        CardInputBrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, InputDataBarang.class);
                startActivity(i);
                finish();
            }
        });
        carpdrofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
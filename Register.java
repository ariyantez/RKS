package com.example.banksampah;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText username, wa,alamat , password,conPassword;
    TextView login,register;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.first);
        wa = (EditText) findViewById(R.id.last);
        alamat = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pass);
        login = (TextView) findViewById(R.id.btn_login_regis);
        register = (TextView) findViewById(R.id.btn_register_regis);
        progressDialog =new ProgressDialog(Register.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent =  new Intent(Register.this, Login.class);
                startActivity(loginIntent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUsername = username.getText().toString();
                String sWa = wa.getText().toString();
                String sAlamat = alamat.getText().toString();
                String sPassword = password.getText().toString();
                String sConPassword = conPassword.getText().toString();
                if (sPassword.equals(sConPassword) && !sPassword.equals("")){
                    RegisterData(sUsername,sWa,sAlamat,sPassword);
                    Intent loginIntent =  new Intent(Register.this, Login.class);
                    startActivity(loginIntent);
                }else {
                    Toast.makeText(getApplicationContext(),"Registrasi Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void RegisterData(final String username, final String wa, final String alamat,final String password){
        if (checkNetwork()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SRVER_REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject =new JSONObject(response);
                                String resp = jsonObject.getString("response");
                                if (resp.equals("[{\"status\":\"OK\"}]")){
                                    Toast.makeText(getApplicationContext(),"Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),resp, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("wa", wa);
                    params.put("alamat", alamat);
                    params.put("password", password);
                    return params;
                }
            };

            Connection.getInstance(Register.this).addToRequest(stringRequest);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 2000);
        }else{
            Toast.makeText(getApplicationContext(),"Tidak Ada Jaringan", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
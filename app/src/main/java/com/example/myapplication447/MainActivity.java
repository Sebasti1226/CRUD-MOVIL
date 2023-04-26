package com.example.myapplication447;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etName, etPassword, etEmail, etId;
    Button btnCreate, btnFetch;
    RequestQueue requestQueue;
    private static final String URL1 = "http://192.168.20.29/android/save.php";

    @Override

    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_main);


        requestQueue = Volley.newRequestQueue(this);

        //UI
        initUI();
        btnCreate.setOnClickListener(this);
        btnFetch.setOnClickListener(this);
    }


    private void initUI() {
        //editext
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etId = findViewById(R.id.etId);
        //buttons
        btnCreate = findViewById(R.id.btnCreate);
        btnFetch = findViewById(R.id.btnFetch);
    }

    @Override

    public void onClick(View v) {
        int id = v.getId();


        if (id == R.id.btnCreate) {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            createuser(name,email,password);


        } else if (id == R.id.btnFetch) {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("id", etId.getText().toString().trim());
            startActivity(intent);

        }


    }

    private void createuser(final String name,final String email,final String password) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "CORRECTO", Toast.LENGTH_LONG).show();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}
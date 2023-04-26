package com.example.myapplication447;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    Button btnDelete, btnEdit;
    EditText etName, etPassword, etEmail, etId;
    String id;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        requestQueue = Volley.newRequestQueue(this);


        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            id = extras.getString("id");
        }

        //ui
        initUI();

        readUser();

        btnDelete.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

    }

    private void initUI() {
        //editext
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etId = findViewById(R.id.etId);
        //buttons
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);

    }

    private void readUser(){
        String URL = "http://192.168.20.29/android/fetch.php?id=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String name, email, password, years;
                        try {
                            name = response.getString("name");
                            email = response.getString("email");
                            password = response.getString("password");

                            etName.setText(name);
                            etEmail.setText(email);
                            etPassword.setText(password);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                    new Response.ErrorListener() {
                    @Override
                        public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnEdit) {


            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            updateUser(name, email, password);

        }else if (id == R.id.btnDelete) {
            String idUser = etId.getText().toString().trim();


            removeUser(idUser);

        }

    }

    private void updateUser(final String name, final String email, final String password) {
        String URL = "http://192.168.20.29/android/edit.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity2.this, "ACTUALIZACION EXITOSA!!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void removeUser(final String idUser) {
        String URL = "http://192.168.20.29/android/delete.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", idUser);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
package com.example.sqlite2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahTeman extends AppCompatActivity {

    TextView idText;
    EditText edNma, edtelpon;
    Button saveBtn;
    String id, nma, tlp, namaEd, telponEd;
    int sukses;

    private static String url_update = "https://20200140070.praktikumtiumy.com/tambahtm.php";
    private static final String TAG = Edit_Teman.class.getSimpleName();
    private static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_teman);


        idText = findViewById(R.id.textid);
        edNma = findViewById(R.id.tbNama);
        edtelpon=findViewById(R.id.tbTelpon);
        saveBtn=findViewById(R.id.btnSimpan);

        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("kunci1");
        nma=bundle.getString("kunci2");
        tlp=bundle.getString("kunci3");

        idText.setText("Id:" +id);
        edNma.setText(nma);
        edtelpon.setText(tlp);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditData();
            }
        });
    }
    public void EditData() {
        namaEd = edNma.getText().toString();
        telponEd = edtelpon.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringReq = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Respon: " + response.toString());
                try {
                    JSONObject jobj = new JSONObject(response);
                    sukses = jobj.getInt(TAG_SUCCES);
                    if (sukses == 1) {
                        Toast.makeText(TambahTeman.this, "Sukses mengedit data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TambahTeman.this, "gagal", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error : " + error.getMessage());
                Toast.makeText(TambahTeman.this, "Gagal Edit data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id", id);
                params.put("nama", namaEd);
                params.put("telpon", telponEd);

                return params;
            }
        };
        requestQueue.add(stringReq);
        CallHomeActivity();
    }
    public void CallHomeActivity()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }
}
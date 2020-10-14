package com.cryptsproject.findlyrics;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText mEdtArtistName, mEdtSongName;
    private Button mBtnFindLyrics ;
    private TextView mTxtLyrics;
    String lyrics;
    String url;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdtArtistName = findViewById(R.id.edtArtistName);
        mEdtSongName = findViewById(R.id.edtSongName);
        mBtnFindLyrics = findViewById(R.id.btnFindLyrics);
        mTxtLyrics = findViewById(R.id.txtLyrics);

        mBtnFindLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = "https://api.lyrics.ovh/v1/" + mEdtArtistName.getText().toString() + "/" + mEdtSongName.getText().toString();
                url.replaceAll(" ", "20%");

                //RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //HitungString hs = new HitungString();
                        try {
                            if (!response.getString("lyrics").equals("")){
                                //JSONObject jsonObject = new JSONObject(response.toString());
                                mTxtLyrics.setText(response.getString("lyrics"));
                            }else {
                                mTxtLyrics.setText("lirik tidak ditemukan");
                            }

                        } catch (JSONException e) {
                            mTxtLyrics.setText(e.getMessage());
                            //e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            //    Toast.makeText(MainActivity.this,"lirik tidak ditemukan",Toast.LENGTH_SHORT).show();

                            }
                        });

                requestQueue.add(jsonObjectRequest);

            }
        });

    }

//    public class HitungString {
//        public int panjangstring(String s)
//        {
//            return s.length(); // menghitung panjang karakter suatu string s
//        }
//    }
}
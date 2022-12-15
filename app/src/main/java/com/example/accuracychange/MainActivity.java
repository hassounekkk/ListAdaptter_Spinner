package com.example.accuracychange;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import static android.content.ContentValues.TAG;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected Spinner spinner;
    protected Spinner spinner2;
    EditText input ;
    TextView output;
    double quote=0;
    String from;
    String to;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://anyapi.io/api/v1/exchange/convert?base=USD&to=USD&amount=10&apiKey=l9fm0v4nsjger13u5gbbgnt0u9htplfo2397fcl3i48534ji3llvdg";
    String list[] ={"EUR" , "USD" , "JPY" , "BGN" , "PHP" , "SGD" , "THB" , "ZAR" , "INR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.Input);
        output = findViewById(R.id.output);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Convert();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, list);
       // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this , R.array.Acuraccy , android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinner)
        {
            from =list[i];

        }
        else if(adapterView.getId() == R.id.spinner2)
        {
            to=list[i];
        }
        getData(from , to);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void Convert(View v){
        double value=0;
        try {
             value = Double.parseDouble(input.getText().toString()) * quote;
        }catch (Exception e){}

        output.setText(""+value);
    }
    public void Convert(){
        double value=0;
        try {
             value = Double.parseDouble(input.getText().toString()) * quote;
        }catch (Exception e){}

        output.setText(""+value);
    }






    private void getData( String from , String to) {
        // RequestQueue initialized
       // String str = "to="+to+"&from="+from+"&amount="+1;

        String url ="https://anyapi.io/api/v1/exchange/convert?base="+from+"&to="+to+"&amount=1&apiKey=l9fm0v4nsjger13u5gbbgnt0u9htplfo2397fcl3i48534ji3llvdg";

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        Log.d(TAG, "here");

        // String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson g = new Gson();
                MyCurrency cu = g.fromJson(response.toString(), MyCurrency.class);
                try {
                    quote= cu.rate;
                    Convert();
                Toast.makeText(getApplicationContext(), "Response :" + quote, Toast.LENGTH_LONG).show();//display the response on screen
            }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "MALL :" + quote, Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
                Toast.makeText(getApplicationContext(), "MALLL :" + url, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("apikey", "7QxNbTOyHlTtuRhOIEJLvBl9lepSNN8i");
                //params.put("Accept-Language", "fr");

                return params;
            }
        };;

        mRequestQueue.add(mStringRequest);
    }

}

class MyCurrency {
    public String base;
    public String to;
    public double rate;
}






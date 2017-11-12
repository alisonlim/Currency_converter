package com.example.alisonlim.trusted_currency;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private EditText et;
    private EditText et2;
    private TextView usd, euro, inr;
    private Button btn;
    private Spinner spin;
    private Spinner spin2;
    private int index = 0;
    private double inputvalue;
    private String result[] = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        et = (EditText) findViewById(R.id.et);
        et2 = (EditText) findViewById(R.id.et2);
        usd = (TextView) findViewById(R.id.usd);
        euro = (TextView) findViewById(R.id.euro);
        inr = (TextView) findViewById(R.id.inr);
        btn = (Button) findViewById(R.id.btn);
        spin = (Spinner) findViewById(R.id.spin); findViewById(R.id.spin2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usd.setText("wait...");
                euro.setText("wait...");
                inr.setText("wait...");

                if (et.getText().toString().trim().length() > 0 && !et.getText().toString().trim().equals(".")) {
                    String textValue = et.getText().toString();
                    inputvalue = Double.parseDouble(textValue);

                    new calculate().execute();
                }
            }
        });
    }

    public class calculate extends AsyncTask<String, String, String[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {
            if (index == 0) {
                String uRl;
                try {
                    uRl = getJson("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20csv%20where%20url%3D%22http%3A%2F%2Ffinance.yahoo.com%2Fd%2Fquotes.csv%3Fe%3D.csv%26f%3Dnl1d1t1%26s%3Dusdeur%3DX%22%3B&format=json&callback=\n");
                    JSONObject USDtojObj;
                    USDtojObj = new JSONObject(uRl);

                    JSONArray rateArray = USDtojObj.getJSONObject("query").getJSONObject("results").getJSONArray("rate");
                    result[0] = rateArray.getJSONObject(0).getString("Rate");
                    result[1] = rateArray.getJSONObject(1).getString("Rate");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (index == 1) {
                String uRl;
                try {
                    uRl = getJson("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20csv%20where%20url%3D%22http%3A%2F%2Ffinance.yahoo.com%2Fd%2Fquotes.csv%3Fe%3D.csv%26f%3Dnl1d1t1%26s%3Dusdeur%3DX%22%3B&format=json&callback=\n");
                    JSONObject EurotojObj;
                    EurotojObj = new JSONObject(uRl);

                    JSONArray rateArray = EurotojObj.getJSONObject("query").getJSONObject("results").getJSONArray("rate");
                    result[0] = rateArray.getJSONObject(0).getString("Rate");
                    result[1] = rateArray.getJSONObject(1).getString("Rate");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (index == 2) {
                String uRl;
                try {
                    uRl = getJson("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20csv%20where%20url%3D%22http%3A%2F%2Ffinance.yahoo.com%2Fd%2Fquotes.csv%3Fe%3D.csv%26f%3Dnl1d1t1%26s%3Dusdeur%3DX%22%3B&format=json&callback=\n");
                    JSONObject INRtojObj;
                    INRtojObj = new JSONObject(uRl);

                    JSONArray rateArray = INRtojObj.getJSONObject("query").getJSONObject("results").getJSONArray("rate");
                    result[0] = rateArray.getJSONObject(0).getString("Rate");
                    result[1] = rateArray.getJSONObject(1).getString("Rate");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if(index == 0){
                double usdtoeuroval, usdtoinrval, usdtoeuroinp, usdtoinrinp, usdtousdinp;
                usdtousdinp = inputvalue * 1;
                usd.setText(""+usdtousdinp);

                usdtoeuroval = Double.parseDouble(result[0]);
                usdtoeuroinp = inputvalue * usdtoeuroval;
                euro.setText(""+usdtoeuroinp);

                usdtoinrval = Double.parseDouble(result[1]);
                usdtoinrinp = inputvalue * usdtoinrval;
                inr.setText(""+usdtoinrinp);
            }else if(index == 1){
                double eurotousdval, eurotoinrval, eurotousdinp, eurotoinrinp, eurotoeuroinp;
                eurotoeuroinp = inputvalue * 1;
                euro.setText(""+eurotoeuroinp);

                eurotousdval = Double.parseDouble(result[0]);
                eurotousdinp = inputvalue * eurotousdval;
                usd.setText(""+eurotousdinp);

                eurotoinrval = Double.parseDouble(result[1]);
                eurotoinrinp = inputvalue * eurotoinrval;
                inr.setText(""+eurotoinrinp);
            }else if(index == 2){
                double inrtousdval, inrtoeuroval, inrtousdinp, inrtoeuroinp, inrtoinrinp;

                inrtoinrinp = inputvalue * 1;
                inr.setText(""+inrtoinrinp);

                inrtousdval = Double.parseDouble(result[0]);
                inrtousdinp = inputvalue * inrtousdval;
                usd.setText(""+inrtousdinp);

                inrtoeuroval = Double.parseDouble(result[1]);
                inrtoeuroinp = inputvalue * inrtoeuroval;
                euro.setText(""+inrtoeuroinp);
            }
        }

        public String getJson(String url) throws ClientProtocolException, IOException {

            StringBuilder build = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String con;
            while ((con = reader.readLine()) != null) {
                build.append(con);
            }
            return build.toString();
        }
    }
}

package com.lab3.lab3sqlvolley.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.lab3.lab3sqlvolley.MySingleton;
import com.lab3.lab3sqlvolley.R;
import com.lab3.lab3sqlvolley.Setting_VAR;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView textView;
    private Request request;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        textView = root.findViewById(R.id.text_home);
        final Button btn1 = root.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  pruebaVolley();
                // peticionGson();
                  //recibirJson();
            }
    });

        return root;

    }


    private void baseRequest(){

        StringRequest request = new StringRequest(Request.Method.GET, Setting_VAR.URL_PRUEBA, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<String, String>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id", "1");
                return map;
            }
        };


        MySingleton.getInstance(getContext()).addToRequestQueue(request);

    }




    private void pruebaVolley(){
        String url = "http://httpbin.org/html";

    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            System.out.println(response.substring(0, 16));
            textView.setText(response.substring(0, 16));

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            System.out.println("Something went wrong!");
            Toast.makeText(getContext(), "Sin Conexion a Internet", Toast.LENGTH_SHORT).show();
            volleyError.printStackTrace();
        }

    });

        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

      }



    private void peticionGson(){
        String url = "http://192.168.57.1/service2020/json.php";
        String url1 = Setting_VAR.URL_PRUEBA;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                textView.setText(Integer.parseInt("Response: "+ response.toString()));
                Toast.makeText(getContext(), ""+ response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //TODO HANDLE ERROR
            }

        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }


        private void recibirJson(){

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Setting_VAR.URL_PRUEBA, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respuestaJSON = new JSONObject(response.toString());

                        String var1 = respuestaJSON.getString("id");
                        String var2 = respuestaJSON.getString("nombre");
                        textView.setText("Response: " + response.toString());

                        Toast.makeText(getContext(), "Id: " + var1 + "\nNombre: " + var2, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }

            });

            MySingleton.getInstance(getContext()).addToRequestQueue(request);



        }


    }



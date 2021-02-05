package com.example.payo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<AdapterItem> dataArrayList=new ArrayList<>();
    View v;
    AdapterClass adapter;
    private RequestQueue requestQueue;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_home,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView= view.findViewById(R.id.recycler_view);
        progressBar=view.findViewById(R.id.progress_horizontal);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestQueue= Volley.newRequestQueue(getContext());

        parseJsone();
    }

    private void parseJsone() {
        String url="https://reqres.in/api/users?page=1";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject data=jsonArray.getJSONObject(i);
                                dataArrayList.add(new AdapterItem(data.getString("first_name"),data.getString("last_name")
                                        ,data.getString("avatar"),data.getString("email")));
                            }
                            Collections.sort(dataArrayList, new Comparator<AdapterItem>() {
                                @Override
                                public int compare(AdapterItem adapterItem, AdapterItem t1) {
                                    return t1.getfName().compareTo(adapterItem.getfName());
                                }

                            });
                            adapter=new AdapterClass(getActivity(),dataArrayList);
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }


}

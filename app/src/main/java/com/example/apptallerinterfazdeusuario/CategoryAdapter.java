package com.example.apptallerinterfazdeusuario;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter {

    private final String URL = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";

    RequestQueue requestQueue;
    List<Model> items;

    public CategoryAdapter(Context context) {
        super(context, 0);

        requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                items = parseJSON(response);
                notifyDataSetChanged();
                Log.e("CategoryAdapter RESPONDE ", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CategoryAdapter ERROR ", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItemCategory;

        listItemCategory = null == convertView ? layoutInflater.inflate(
                R.layout.activity_category_adapter,
                parent,
                false) : convertView;

        Model item = items.get(position);

        TextView textName = (TextView) listItemCategory.findViewById(R.id.textName);
        TextView textCategory = (TextView) listItemCategory.findViewById(R.id.textCategory);
        TextView textSummary = (TextView) listItemCategory.findViewById(R.id.textSummary);

        textName.setText(item.getName());
        textCategory.setText(item.getCategory());
        textSummary.setText(item.getSummary());

        return listItemCategory;
    }

    public List<Model> parseJSON(JSONObject jsonObject) {

        List<Model> categoryList = new ArrayList<>();

        try {
            JSONObject feedJSON = jsonObject.getJSONObject("feed");
            JSONArray entryJSON = feedJSON.getJSONArray("entry");

            for (int i = 0; i < entryJSON.length(); i++) {
                try {
                    JSONObject entryObject = entryJSON.getJSONObject(i);
                    JSONObject nameJSON = entryObject.getJSONObject("im:name");
                    JSONObject summaryJSON = entryObject.getJSONObject("summary");
                    JSONObject categoryJSON = entryObject.getJSONObject("category");
                    JSONObject atributtescategoryJSON = categoryJSON.getJSONObject("attributes");

                    Model category = new Model(
                            nameJSON.getString("label"),
                            atributtescategoryJSON.getString("label"),
                            summaryJSON.getString("label")
                    );

                    categoryList.add(category);

                } catch (JSONException e) {
                    Log.e("CategoryAdapter", "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            Log.e("CategoryAdapter", "Error - " + e.getMessage());
        }

        return categoryList;
    }
}


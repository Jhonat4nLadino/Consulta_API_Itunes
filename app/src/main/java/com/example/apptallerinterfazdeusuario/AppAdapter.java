package com.example.apptallerinterfazdeusuario;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AppAdapter extends ArrayAdapter {

    private final String URL = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";

    RequestQueue requestQueue; //De la libreria volley, cola de peticiones.
    List<Model> items;

    public AppAdapter(Context context) {
        super(context, 0);

        //Manejo del endPoint
        //peticion de tipo JSON -> {}object ó []array

        requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                items = parseJSON(response);
                notifyDataSetChanged();
                Log.e("AppAdapter RESPONDE ", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AppAdapter ERROR ", error.toString());
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemApps;

        listItemApps = null == convertView ? layoutInflater.inflate(
                R.layout.activity_app_adapter,
                parent,
                false) : convertView;

        Model item = items.get(position);

        TextView textName = listItemApps.findViewById(R.id.textName);
        TextView textRights = listItemApps.findViewById(R.id.textRights);
        TextView textSummary = listItemApps.findViewById(R.id.textSummary);
        final ImageView image = listItemApps.findViewById(R.id.image);

        textName.setText(item.getName());
        textRights.setText(item.getRights());
        textSummary.setText(item.getSummary());

        //traer la imagen desde una URL

        ImageRequest request = new ImageRequest(item.getImage(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                image.setImageBitmap(response);
            }
        }, 0, 0, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error cargando imagen : ", error.getMessage());
            }
        });

        requestQueue.add(request);

        return listItemApps;

    }

    public List<Model> parseJSON(JSONObject jsonObject) {

        List<Model> appList = new ArrayList<>();

        try {
            JSONObject feedJSON = jsonObject.getJSONObject("feed");
            JSONArray entryJSON = feedJSON.getJSONArray("entry");

            for (int i = 0; i < entryJSON.length(); i++) {
                try {
                    JSONObject entryObject = entryJSON.getJSONObject(i);
                    JSONObject nameJSON = entryObject.getJSONObject("im:name");
                    JSONObject summaryJSON = entryObject.getJSONObject("summary");
                    JSONObject rightsJSON = entryObject.getJSONObject("rights");

                    JSONArray imageJSON = entryObject.getJSONArray("im:image");
                    JSONObject imageObject = imageJSON.getJSONObject(2);

                    Model app = new Model(
                            nameJSON.getString("label"),
                            imageObject.getString("label"),
                            rightsJSON.getString("label"),
                            summaryJSON.getString("label")
                    );

                    appList.add(app);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return appList;
    }
}

package cs2b01.utec.chat_mobile;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class ContactsActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        mRecyclerView = findViewById(R.id.main_recycler_view);
        setTitle("@"+getIntent().getExtras().get("username").toString());
    }

    @Override
    protected void onResume(){
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getUsers();
    }

    public Activity getActivity(){
        return this;
    }


    //Obtener los usuarios del servidor
    public void getUsers(){
        String url = "http://10.0.2.2:8000/users";
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONArray parameters = new JSONArray();
        final int userId = getIntent().getExtras().getInt("user_id");
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                parameters,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            //Paso 1: Crear la vista para cada usuario (element_view.xml)
                            //Paso 2: Crear la vista de los elementos dinamicos e injectarlo en Rcyc view
                            mAdapter = new ChatAdapter(response, getActivity(), userId);
                            mRecyclerView.setAdapter(mAdapter);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();

            }
        });
        queue.add(jsonObjectRequest);

    }

}
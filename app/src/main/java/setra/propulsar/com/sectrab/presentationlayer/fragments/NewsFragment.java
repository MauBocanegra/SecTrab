package setra.propulsar.com.sectrab.presentationlayer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.adapters.NewsAdapter;
import setra.propulsar.com.sectrab.domainlayer.models.News;
import setra.propulsar.com.sectrab.domainlayer.ws.WS;

public class NewsFragment extends Fragment implements WS.OnWSRequested, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<News> news;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int skip=0;
    private int take=10;
    int userID=-1;

    View view;

    public static NewsFragment newInstance(){ return new NewsFragment();}
    public NewsFragment(){ }

    // -------------------------------------------- //
    // ---------------- LIFE CYCLE ---------------- //
    // -------------------------------------------- //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentnews_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        news = new ArrayList<News>();

        mAdapter = new NewsAdapter(news, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutFragNews);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(NewsFragment.this);
        getNewsNotifications(false);

        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Skip",skip);
        params.put("Take",take);
        WS.getNewsList(params,this);

        return view;
    }


    // ----------------------------------------------- //
    // -------------- INTERNAL METHODS --------------- //
    // ----------------------------------------------- //

    private void getNewsNotifications(boolean isBottomList){

        if (isBottomList){
            skip+=take;
        }

        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Skip",skip);
        params.put("Take",take);
        WS.getNewsList(params,this);

    }

    private void addToList(ArrayList<News> newNews){

        for (int i=0; i<newNews.size(); i++){
            news.add(newNews.get(i));
        }
        mAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(false);
    }

    // ---------------------------------------------------------- //
    // -------------- SWIPE REFRESH IMPLEMENTATION -------------- //
    // ---------------------------------------------------------- //

    @Override
    public void onRefresh() {
        Log.d("TAG","onRefresh");
        news.clear();
        skip=0; take=10;
        getNewsNotifications(false);

    }

    @Override
    public void wsAnswered(JSONObject json) {
        Log.d("GETNews",json.toString());
        int ws=0; int status=-1;
        try{status=json.getInt("status");}catch(Exception e){e.printStackTrace();}
        if(status!=0){/*ERRRRRRROOOOOOOORRRRRRR*/}

        try {
            ws = json.getInt("ws");
            switch (ws){
                case WS.WS_getNewsList:{
                    JSONObject data = json.getJSONObject("data");
                    JSONArray newsNewsJArray = data.getJSONArray("jsonArray");
                    ArrayList<News> newNews = new ArrayList<News>();

                    for (int i=0; i<newsNewsJArray.length(); i++){
                        JSONObject newNewsJSONObject = newsNewsJArray.getJSONObject(i);
                        News newNewsJ = new News();

                        newNewsJ.setIdNoticia(newNewsJSONObject.getInt("Id"));
                        newNewsJ.setLinkImagenNoticia(newNewsJSONObject.getString("Image"));
                        newNewsJ.setTituloNoticia(newNewsJSONObject.getString("Title"));
                        newNewsJ.setInfoNoticia(newNewsJSONObject.getString("Description"));
                        newNewsJ.setDatetime(newNewsJSONObject.getString("Published"));

                        newNews.add(newNewsJ);
                    }
                    addToList(newNews);

                    if(newNews.size()==0){
                        Toast.makeText(getActivity(),"Sin noticias por el momento", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"Estas son las noticias.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            } catch (JSONException e) { e.printStackTrace(); }
    }
}

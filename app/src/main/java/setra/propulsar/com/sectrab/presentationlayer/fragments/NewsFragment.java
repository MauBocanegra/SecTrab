package setra.propulsar.com.sectrab.presentationlayer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

    private int skipIni=0;
    private int takeIni=3;
    private int skip=skipIni;
    private int take=takeIni;
    int userID=-1;

    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private boolean topRequested=false;

    View view;
    View progressLoading;

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
        progressLoading=view.findViewById(R.id.progressBarNewsFrag);
        progressLoading.setVisibility(View.VISIBLE);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(NewsFragment.this);

        mRecyclerView.addOnScrollListener(setScrollListener());
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Skip",skipIni);
        params.put("Take",takeIni);
        WS.getNewsList(params,this);

        return view;
    }


    // ----------------------------------------------- //
    // -------------- INTERNAL METHODS --------------- //
    // ----------------------------------------------- //

    private void getNewsNotifications(boolean isTopList){

        mSwipeRefreshLayout.setRefreshing(true);

        if (isTopList){
            skip+=take;
        }else{
            skip=skipIni; take=takeIni;
            news.clear();
            mAdapter.notifyDataSetChanged();
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
        mLayoutManager.scrollToPosition(news.size());

        mSwipeRefreshLayout.setRefreshing(false);
    }

    // ---------------------------------------------------------- //
    // -------------- SWIPE REFRESH IMPLEMENTATION -------------- //
    // ---------------------------------------------------------- //

    @Override
    public void onRefresh() {
        Log.d("TAG","onRefresh");
        news.clear();
        skipIni=0; takeIni=3;
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

                    if (newsNewsJArray.length()==0 && topRequested){
                        mSwipeRefreshLayout.setRefreshing(false);
                        return;
                    }

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
                    topRequested=false;

                    if(newNews.size()==0){
                        Toast.makeText(getActivity(),getString(R.string.news_nohay), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            progressLoading.setVisibility(View.GONE);

            } catch (JSONException e) { e.printStackTrace(); }
    }

    // ---------------------------------------------------- //
    // -------------- SCROLL IMPLEMENTATION --------------- //
    // ---------------------------------------------------- //

    private RecyclerView.OnScrollListener setScrollListener(){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (topRequested){return;}

                if (dy < 0 ){

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount+pastVisiblesItems)==totalItemCount){
                        topRequested=true;
                        getNewsNotifications(true);
                    }
                }
            }
        };
    }
}

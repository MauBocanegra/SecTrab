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
    public NewsFragment(){

    }

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
        return view;
    }

    // --------------------------------------------- //
    // ---------------- OWN METHODS ---------------- //
    //---------------------------------------------- //

    private void descargarNews(){
        News news1 = new News();
        News news2 = new News();
        News news3 = new News();
        News news4 = new News();
        News news5 = new News();
        News news6 = new News();
        news1.setTituloNoticia("Instituciones financieras respaldan, la estrategia 'Nayarit seguro'.");
        news2.setTituloNoticia("Instituciones financieras respaldan, la estrategia 'Nayarit seguro'.");
        news3.setTituloNoticia("Instituciones financieras respaldan, la estrategia 'Nayarit seguro'.");
        news4.setTituloNoticia("Instituciones financieras respaldan, la estrategia 'Nayarit seguro'.");
        news5.setTituloNoticia("Instituciones financieras respaldan, la estrategia 'Nayarit seguro'.");
        news6.setTituloNoticia("Instituciones financieras respaldan, la estrategia 'Nayarit seguro'.");
        news1.setInfoNoticia("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        news2.setInfoNoticia("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        news3.setInfoNoticia("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        news4.setInfoNoticia("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        news5.setInfoNoticia("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        news6.setInfoNoticia("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        news1.setLinkImagenNoticia("http://diario.mx/images/imagen.php?i=2018-03-LOC13888462d1a7479_8.jpg");
        news2.setLinkImagenNoticia("https://cdn.oem.com.mx/periodicoelmexicano/2018/06/oficinas-de-gobierno-600x400.jpg");
        news3.setLinkImagenNoticia("http://diario.mx/images/imagen.php?i=2018-03-LOC13888462d1a7479_8.jpg");
        news4.setLinkImagenNoticia("https://cdn.oem.com.mx/periodicoelmexicano/2018/06/oficinas-de-gobierno-600x400.jpg");
        news5.setLinkImagenNoticia("http://diario.mx/images/imagen.php?i=2018-03-LOC13888462d1a7479_8.jpg");
        news6.setLinkImagenNoticia("https://cdn.oem.com.mx/periodicoelmexicano/2018/06/oficinas-de-gobierno-600x400.jpg");

        news.add(news1); news.add(news2);
        news.add(news3); news.add(news4);
        news.add(news5); news.add(news6);
        mAdapter.notifyDataSetChanged();
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
                    JSONObject data = json.getJSONObject("news");
                    JSONArray newsNewsJArray = data.getJSONArray("Value");
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

            } catch (JSONException e) { e.printStackTrace();
        }
    }
}

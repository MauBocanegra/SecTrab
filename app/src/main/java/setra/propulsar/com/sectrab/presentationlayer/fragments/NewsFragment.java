package setra.propulsar.com.sectrab.presentationlayer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.adapters.NewsAdapter;
import setra.propulsar.com.sectrab.domainlayer.models.News;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<News> mDataset;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Toolbar toolbar;

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

        toolbar = view.findViewById(R.id.toolbar_frag_news);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentnews_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mDataset = new ArrayList<News>();

        mAdapter = new NewsAdapter(mDataset, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutFragNews);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(NewsFragment.this);
        descargarNews();
        return view;
    }

    // --------------------------------------------- //
    // ---------------- OWN METHODS ---------------- //
    //---------------------------------------------- //

    private void descargarNews(){
        News news1 = new News();
        News news2 = new News();
        news1.setTituloNoticia("Instituciones financieras respaldan");
        news2.setTituloNoticia("Instituciones financieras respaldan");
        news1.setInfoNoticia("El secretario de SETRAPODE, Ernesto Navaroo Gonzales");
        news2.setInfoNoticia("El secretario de SETRAPODE, Ernesto Navaroo Gonzales");

        mDataset.add(news1); mDataset.add(news2);
        mAdapter.notifyDataSetChanged();
    }

    // ---------------------------------------------------------- //
    // -------------- SWIPE REFRESH IMPLEMENTATION -------------- //
    // ---------------------------------------------------------- //

    @Override
    public void onRefresh() {

    }
}

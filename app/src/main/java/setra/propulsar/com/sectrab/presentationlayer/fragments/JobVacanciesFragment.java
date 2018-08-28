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
import setra.propulsar.com.sectrab.domainlayer.adapters.JobsAdapter;
import setra.propulsar.com.sectrab.domainlayer.models.Jobs;
import setra.propulsar.com.sectrab.domainlayer.ws.WS;

public class JobVacanciesFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener, WS.OnWSRequested {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private JobsAdapter mAdapter;
    private ArrayList<Jobs> jobs;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int skipIni=0;
    private int takeIni=3;
    private int skip=skipIni;
    private int take=takeIni;

    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private boolean topRequested=false;

    View view;
    View progressLoading;

    public static JobVacanciesFragment newInstance(){return new JobVacanciesFragment();}
    public JobVacanciesFragment(){}

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
        view = inflater.inflate(R.layout.fragment_job_vacancies, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentjobs_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        jobs = new ArrayList<Jobs>();

        mAdapter = new JobsAdapter(jobs, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutFragJobs);
        progressLoading=view.findViewById(R.id.progressBarNewsFrag);
        progressLoading.setVisibility(View.VISIBLE);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(JobVacanciesFragment.this);

        mRecyclerView.addOnScrollListener(setScrollListener());
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Skip",skip);
        params.put("Take",take);
        WS.getJobsList(params,this);

        return view;
    }

    // ----------------------------------------------- //
    // -------------- INTERNAL METHODS --------------- //
    // ----------------------------------------------- //

    private void getJobs(boolean isBottomList){

        mSwipeRefreshLayout.setRefreshing(true);

        if (isBottomList){
            skip+=take;
        }else{
            skip=skipIni; take=takeIni;
            jobs.clear();
            mAdapter.notifyDataSetChanged();
        }
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Skip",skip);
        params.put("Take",take);
        WS.getJobsList(params,this);
    }

    private void addToList (ArrayList<Jobs> newJobs){
        for (int i=0; i<newJobs.size(); i++){
            jobs.add(newJobs.get(i));
        }
        mAdapter.notifyDataSetChanged();
        mLayoutManager.scrollToPosition(jobs.size());
        mSwipeRefreshLayout.setRefreshing(false);
    }

    // ---------------------------------------------------------- //
    // -------------- SWIPE REFRESH IMPLEMENTATION -------------- //
    // ---------------------------------------------------------- //

    @Override
    public void onRefresh() {
        Log.d("TAG","onRefresh");
        jobs.clear();
        skipIni=0; takeIni=3;
        getJobs(false);
    }

    // ---------------------------------------------------------- //
    // -------------- WEB SERVICES IMPLEMENTATION --------------- //
    // ---------------------------------------------------------- //

    @Override
    public void wsAnswered(JSONObject json) {
        Log.d("GETJobs",json.toString());
        int ws=0; int status=-1;
        try{status=json.getInt("status");}catch(Exception e){e.printStackTrace();}
        if(status!=0){/*ERRRRRRROOOOOOOORRRRRRR*/}

        try {
            ws = json.getInt("ws");
            switch (ws) {
                case WS.WS_getJobsList:{
                    JSONObject data = json.getJSONObject("data");
                    JSONArray newJobsJArray = data.getJSONArray("jsonArray");
                    ArrayList<Jobs> newJobs = new ArrayList<Jobs>();

                    if (newJobsJArray.length()==0 && topRequested){
                        mSwipeRefreshLayout.setRefreshing(false);
                        return;
                    }

                    for (int i=0; i<newJobsJArray.length(); i++){
                        JSONObject newJobsJSONObject = newJobsJArray.getJSONObject(i);
                        Jobs newJobsJ = new Jobs();

                        newJobsJ.setIdEmpleo(newJobsJSONObject.getInt("Id"));
                        newJobsJ.setLinkLogoEmpresa(newJobsJSONObject.getString("Logo"));
                        newJobsJ.setNombreEmpresa(newJobsJSONObject.getString("Company"));
                        newJobsJ.setSectorEmpresa(newJobsJSONObject.getString("Sector"));
                        newJobsJ.setTitlePuestoEmpresa(newJobsJSONObject.getString("Title"));
                        newJobsJ.setDescripcionEmpleo(newJobsJSONObject.getString("Description"));
                        newJobsJ.setTelefonoEmpresa(newJobsJSONObject.getString("Phone"));
                        newJobsJ.setEmailEmpresa(newJobsJSONObject.getString("Email"));
                        newJobsJ.setDatetime(newJobsJSONObject.getString("Published"));

                        newJobs.add(newJobsJ);
                    }
                    addToList(newJobs);
                    topRequested=false;

                    if(newJobs.size()==0){
                        Toast.makeText(getActivity(),getString(R.string.jobs_nohay), Toast.LENGTH_SHORT).show();
                    }

                }
                progressLoading.setVisibility(View.GONE);
            }
        }catch (JSONException e) { e.printStackTrace(); }

    }

    // ---------------------------------------------------- //
    // -------------- SCROLL IMPLEMENTATION --------------- //
    // ---------------------------------------------------- //

    private RecyclerView.OnScrollListener setScrollListener(){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (topRequested){return;}

                if (dy < 0 ){

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount+pastVisiblesItems)==totalItemCount){
                        topRequested=true;
                        getJobs(true);
                    }
                }
            }
        };
    }
}

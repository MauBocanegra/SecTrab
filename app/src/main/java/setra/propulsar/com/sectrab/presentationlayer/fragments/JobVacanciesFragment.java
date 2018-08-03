package setra.propulsar.com.sectrab.presentationlayer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.adapters.JobsAdapter;
import setra.propulsar.com.sectrab.domainlayer.models.Jobs;

public class JobVacanciesFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private JobsAdapter mAdapter;
    private ArrayList<Jobs> mDataset;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_vacancies, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentjobs_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mDataset = new ArrayList<Jobs>();

        mAdapter = new JobsAdapter(mDataset, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutFragJobs);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(JobVacanciesFragment.this);

        return view;
    }

    @Override
    public void onRefresh() {

    }
}

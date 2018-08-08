package setra.propulsar.com.sectrab.presentationlayer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.adapters.ContactsAdapter;
import setra.propulsar.com.sectrab.domainlayer.models.Contacts;

public class ContactsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ContactsAdapter mAdapter;
    private ArrayList<Contacts> mDataset;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public static ContactsFragment newInstance(){return new ContactsFragment();}
    public ContactsFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // -------------------------------------------- //
    // ---------------- LIFE CYCLE ---------------- //
    // -------------------------------------------- //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentcontacts_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mDataset = new ArrayList<Contacts>();

        mAdapter = new ContactsAdapter(mDataset, getContext());
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutFragContacts);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(ContactsFragment.this);

        return view;
    }

    // ---------------------------------------------------------- //
    // -------------- SWIPE REFRESH IMPLEMENTATION -------------- //
    // ---------------------------------------------------------- //

    @Override
    public void onRefresh() {
        Log.d("TAG","onRefresh");

    }

}

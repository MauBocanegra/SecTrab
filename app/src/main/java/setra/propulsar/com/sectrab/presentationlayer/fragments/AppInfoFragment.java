package setra.propulsar.com.sectrab.presentationlayer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import setra.propulsar.com.sectrab.R;

public class AppInfoFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public static AppInfoFragment newInstance(){return new AppInfoFragment();}
    public AppInfoFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_info, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentappinfo_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

}

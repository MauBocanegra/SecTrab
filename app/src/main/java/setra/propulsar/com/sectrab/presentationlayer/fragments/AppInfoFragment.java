package setra.propulsar.com.sectrab.presentationlayer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.adapters.InfoChatAdapter;
import setra.propulsar.com.sectrab.domainlayer.models.InfoChat;

public class AppInfoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private InfoChatAdapter mAdapter;
    private ArrayList<InfoChat> mDataset;

    public static AppInfoFragment newInstance(){return new AppInfoFragment();}
    public AppInfoFragment(){}


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
        View view = inflater.inflate(R.layout.fragment_app_info, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentappinfo_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mDataset = new ArrayList<InfoChat>();

        mAdapter = new InfoChatAdapter(mDataset, getContext());
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    // --------------------------------------------- //
    // ---------------- OWN METHODS ---------------- //
    //---------------------------------------------- //

    public void descargarContenido(){
        InfoChat infochat1 = new InfoChat();
        InfoChat infochat2 = new InfoChat();
        infochat1.setTitulo("Terminos y condiciones");
        infochat2.setTitulo("Condiciones de uso");

    }

}

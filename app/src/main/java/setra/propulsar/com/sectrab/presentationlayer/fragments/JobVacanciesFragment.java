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
import setra.propulsar.com.sectrab.domainlayer.adapters.JobsAdapter;
import setra.propulsar.com.sectrab.domainlayer.models.Jobs;

public class JobVacanciesFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private JobsAdapter mAdapter;
    private ArrayList<Jobs> mDataset;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    View view;

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

        mDataset = new ArrayList<Jobs>();

        mAdapter = new JobsAdapter(mDataset, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutFragJobs);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(JobVacanciesFragment.this);
        descargarJobs();
        return view;
    }

    // --------------------------------------------- //
    // ---------------- OWN METHODS ---------------- //
    //---------------------------------------------- //

    private void descargarJobs(){

        Jobs jobs1 = new Jobs();
        Jobs jobs2 = new Jobs();
        Jobs jobs3 = new Jobs();
        Jobs jobs4 = new Jobs();
        jobs1.setNombreEmpresa("Santader");
        jobs2.setNombreEmpresa("Softek");
        jobs3.setNombreEmpresa("Santader");
        jobs4.setNombreEmpresa("Softek");
        jobs1.setInfoEmpresa("Banking");
        jobs2.setInfoEmpresa("I.T.");
        jobs3.setInfoEmpresa("Banking");
        jobs4.setInfoEmpresa("I.T.");
        jobs1.setPuestoEmpresa("Gerente Grupos Especializados");
        jobs2.setPuestoEmpresa("GENERALISTA DE RECURSOS HUMANOS");
        jobs3.setPuestoEmpresa("Gerente Grupos Especializados");
        jobs4.setPuestoEmpresa("GENERALISTA DE RECURSOS HUMANOS");
        jobs1.setDescripcionEmpleo("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
        jobs2.setDescripcionEmpleo("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
        jobs3.setDescripcionEmpleo("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
        jobs4.setDescripcionEmpleo("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
        jobs1.setUbicacionEmpleo("CDMX");
        jobs2.setUbicacionEmpleo("CDMX");
        jobs3.setUbicacionEmpleo("CDMX");
        jobs4.setUbicacionEmpleo("CDMX");
        jobs1.setLinkImagenEmpresa("https://www.expoknews.com/wp-content/uploads/2013/04/Santander.jpg");
        jobs2.setLinkImagenEmpresa("https://qph.fs.quoracdn.net/main-thumb-t-127841-200-ijmvviflpziltgehxhefkmnwirkiebeg.jpeg");
        jobs3.setLinkImagenEmpresa("https://www.expoknews.com/wp-content/uploads/2013/04/Santander.jpg");
        jobs4.setLinkImagenEmpresa("https://qph.fs.quoracdn.net/main-thumb-t-127841-200-ijmvviflpziltgehxhefkmnwirkiebeg.jpeg");

        mDataset.add(jobs1); mDataset.add(jobs2); mDataset.add(jobs3); mDataset.add(jobs4);
    }

    @Override
    public void onRefresh() {
        Log.d("TAG","onRefresh");

    }
}

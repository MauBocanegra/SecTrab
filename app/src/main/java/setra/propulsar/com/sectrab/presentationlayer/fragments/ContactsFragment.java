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
        descargarContactos();
        return view;
    }

    // --------------------------------------------- //
    // ---------------- OWN METHODS ---------------- //
    //---------------------------------------------- //
    public void descargarContactos(){
        Contacts contacts1 = new Contacts();
        Contacts contacts2 = new Contacts();
        Contacts contacts3 = new Contacts();
        Contacts contacts4 = new Contacts();
        Contacts contacts5 = new Contacts();
        Contacts contacts6 = new Contacts();
        Contacts contacts7 = new Contacts();
        Contacts contacts8 = new Contacts();
        contacts1.setNombreEmpresa("Secretaria del Trabajo, Productividad y Desarrollo Economico");
        contacts2.setNombreEmpresa("Secretaria del Trabajo, Productividad y Desarrollo Economico");
        contacts3.setNombreEmpresa("Secretaria del Trabajo, Productividad y Desarrollo Economico");
        contacts4.setNombreEmpresa("Secretaria del Trabajo, Productividad y Desarrollo Economico");
        contacts5.setNombreEmpresa("Secretaria del Trabajo, Productividad y Desarrollo Economico");
        contacts6.setNombreEmpresa("Secretaria del Trabajo, Productividad y Desarrollo Economico");
        contacts7.setNombreEmpresa("Secretaria del Trabajo, Productividad y Desarrollo Economico");
        contacts8.setNombreEmpresa("Secretaria del Trabajo, Productividad y Desarrollo Economico");
        contacts1.setUbicacionEmpresa("Tepic, Nayarit");
        contacts2.setUbicacionEmpresa("Tepic, Nayarit");
        contacts3.setUbicacionEmpresa("Tepic, Nayarit");
        contacts4.setUbicacionEmpresa("Tepic, Nayarit");
        contacts5.setUbicacionEmpresa("Tepic, Nayarit");
        contacts6.setUbicacionEmpresa("Tepic, Nayarit");
        contacts7.setUbicacionEmpresa("Tepic, Nayarit");
        contacts8.setUbicacionEmpresa("Tepic, Nayarit");
        contacts1.setTelefonoEmpresa("Tel: (311) 258-0929");
        contacts2.setTelefonoEmpresa("Tel: (311) 258-0929");
        contacts3.setTelefonoEmpresa("Tel: (311) 258-0929");
        contacts4.setTelefonoEmpresa("Tel: (311) 258-0929");
        contacts5.setTelefonoEmpresa("Tel: (311) 258-0929");
        contacts6.setTelefonoEmpresa("Tel: (311) 258-0929");
        contacts7.setTelefonoEmpresa("Tel: (311) 258-0929");
        contacts8.setTelefonoEmpresa("Tel: (311) 258-0929");

        mDataset.add(contacts1); mDataset.add(contacts2); mDataset.add(contacts3); mDataset.add(contacts4); mDataset.add(contacts5); mDataset.add(contacts6); mDataset.add(contacts7); mDataset.add(contacts8);
    }

    // ---------------------------------------------------------- //
    // -------------- SWIPE REFRESH IMPLEMENTATION -------------- //
    // ---------------------------------------------------------- //

    @Override
    public void onRefresh() {
        Log.d("TAG","onRefresh");

    }

}

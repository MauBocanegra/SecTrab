package setra.propulsar.com.sectrab.presentationlayer.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.models.Msg;

public class ChatFragment extends Fragment implements OnMapReadyCallback{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private android.support.v7.widget.LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView switchButtonBotImg;
    private ImageView switchButtonStaffImg;
    private String officerName;
    private String officerURLImage;
    private TextView textStaff;
    int userID;
    private int skipIni=0; private int takeIni=10;
    private int skip=skipIni;
    private int take=takeIni;

    int scaleIni = 0;
    int scaleFin = 1;
    boolean enabled = false;

    Handler h;
    Runnable getMessagesRepeatedly;

    private EditText editText;
    private FloatingActionButton fab;
    private ProgressBar progress;
    private View buttonLocation;
    private View buttonImg;
    private FloatingActionButton buttonCam;
    private FloatingActionButton buttonGallery;
    private View cardEditText;
    private ArrayList<Msg> messages;
    private ArrayList<Msg> respChat;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private View mapContainer;
    private MarkerOptions mMarkerOptions;
    private Marker mMarker;
    private boolean locationJustSent;
    private double lat; private double lon;
    private int initTime;

    private View galleryContainer;
    private GridView gallery;
    private View progressGalleryLoaded;

    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private boolean topRequested=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);


        messages = new ArrayList<Msg>();
        respChat = new ArrayList<Msg>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.msgRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.chatSwipeRefreshLayout);
        swipeRefreshLayout.setEnabled(false);

        fab = (FloatingActionButton) view.findViewById(R.id.fab_sendMessage);
        editText = (EditText)view.findViewById(R.id.editText_mensaje);
        progress = (ProgressBar)view.findViewById(R.id.progress_chat);
        buttonLocation = view.findViewById(R.id.buttonLocation);
        buttonImg = view.findViewById(R.id.buttonImage);
        cardEditText = view.findViewById(R.id.cardMensaje);
        galleryContainer = view.findViewById(R.id.galleryContainer);
        gallery = (GridView)view.findViewById(R.id.gridview);
        buttonCam = (FloatingActionButton) view.findViewById(R.id.fab_camera);
        buttonGallery = (FloatingActionButton)view.findViewById(R.id.fab_gallery);
        progressGalleryLoaded = view.findViewById(R.id.progressGalleryLoading);

        return view;
    }

    // ----------------------------------------------- //
    // ----------------- OWN METHODS ----------------- //
    // ----------------------------------------------- //



    @Override
    public void onMapReady(GoogleMap map) {
        mMap=map;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(12.0f);
        mMap.setMaxZoomPreference(17.0f);
    }
}

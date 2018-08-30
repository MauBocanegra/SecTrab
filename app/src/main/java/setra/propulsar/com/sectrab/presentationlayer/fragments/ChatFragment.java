package setra.propulsar.com.sectrab.presentationlayer.fragments;

import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.adapters.GalleryAdapter;
import setra.propulsar.com.sectrab.domainlayer.adapters.MsgAdapter;
import setra.propulsar.com.sectrab.domainlayer.models.Msg;
import setra.propulsar.com.sectrab.domainlayer.models.OtherMsg;
import setra.propulsar.com.sectrab.domainlayer.models.OwnMsg;
import setra.propulsar.com.sectrab.domainlayer.services.UserClient;
import setra.propulsar.com.sectrab.domainlayer.ws.WS;
import setra.propulsar.com.sectrab.presentationlayer.Dialogs.DialogShowImg;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ChatFragment extends Fragment implements OnMapReadyCallback, WS.OnWSRequested, GalleryAdapter.GalleryLoadedListener{

    // ------------------------------------------------- //
    // ----------------- DECLARATIONS ------------------ //
    // ------------------------------------------------- //

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private android.support.v7.widget.LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=123;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=334;
    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=321;
    final int MY_PERMISSIONS_REQUEST_CAMERA=231;
    final int PHOTO_ACTIVITY_REQUEST=31;
    final int GALLERY_CHOOSER=12;
    GoogleApiClient mGoogleApiClient;

    private ArrayList<Msg> messages;
    private ArrayList<Msg> respChat;

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

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private View mapContainer;
    private MarkerOptions mMarkerOptions;
    private Marker mMarker;
    private boolean locationJustSent;
    private double lat; private double lon;
    private int initTime;

    private View galleryContainer;
    private GalleryAdapter mGalleryAdapter;
    private GridView gallery;
    private View progressGalleryLoaded;
    DialogShowImg dialog;

    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private boolean topRequested=false;

    // ----------------------------------------------- //
    // ----------------- LIFE CYCLE ------------------ //
    // ----------------------------------------------- //

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    WS.showSucces("Para enviar tu ubicación debes permitirnos obtenerla",buttonLocation);
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showGallery();
                }else{
                    WS.showSucces("Para enviar una imágen debes permitir el acceso a tus archivos",buttonImg);
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showGallery();
                }else{
                    WS.showSucces("Para enviar una imágen debes permitir el acceso a tus archivos",buttonImg);
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_CAMERA:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCamera();
                }else{
                    WS.showSucces("Para capturar una foto debes permitir el acceso a la misma",buttonImg);
                }
                return;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
        userID = sharedPreferences.getInt("userID",0);

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(ChatFragment.this);
        mapContainer = view.findViewById(R.id.mapContainer);

        messages = new ArrayList<Msg>();
        respChat = new ArrayList<Msg>();
        mRecyclerView = (RecyclerView)view.findViewById(R.id.msgRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MsgAdapter(messages, ChatFragment.this);
        mRecyclerView.setAdapter(mAdapter);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSend(view);
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Log.d("TextEditListener","i="+i+" key="+keyEvent.toString());
                return false;
            }
        });
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLocation();
            }
        });
        buttonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(galleryContainer.getVisibility()==View.VISIBLE){
                    hideGallery();
                }else{
                    clickGallery();
                }
            }
        });
        buttonCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCamera();
            }
        });
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickGalleryChooser();
            }
        });

        mRecyclerView.addOnScrollListener(setScrollListener());
        Map<String, Object> params = new LinkedHashMap<>();
        WS.getOfficerInfo(params,this);

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Awareness.API)
                .build();
        mGoogleApiClient.connect();

        switchButtonStaffImg = (ImageView)view.findViewById(R.id.switchButtonStaffImg);
        textStaff = (TextView)view.findViewById(R.id.textStaff);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Mandamos a actualizar los mensajes cada n segundos
        h = new Handler(){
            @Override
            public void  handleMessage(Message msg){
                switch(msg.what){
                    case 0:
                        this.removeMessages(0);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
        h.postDelayed(getMessagesRepeatedly, 30000);
    }

    @Override
    public void onStop() {
        super.onStop();
        h.removeCallbacks(getMessagesRepeatedly);
    }


    // ----------------------------------------------- //
    // ----------------- OWN METHODS ----------------- //
    // ----------------------------------------------- //

    //Gestionar el envio de mensajes mediante la accion del FAB
    private void clickSend(View view){

        if(mapContainer.getVisibility()==View.VISIBLE){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("¿Deseas enviar la ubicación establecida por el marcador rojo");
            // Add the buttons
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    scaleIni = 1;
                    scaleFin = 0;
                    fabAnimate(false);
                    locationJustSent=true;

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
                    userID = sharedPreferences.getInt("userID",0);
                    Map<String, Object> params = new LinkedHashMap<>();
                    params.put("UserId",userID);
                    params.put("DestinationId",1);
                    params.put("MessageTypeId",3);
                    params.put("Text",""+String.format("%.6f", lat)+","+ String.format("%.6f", lon));
                    WS.sendMessage(params,ChatFragment.this);

                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {}
            });

            // Create the AlertDialog
            AlertDialog dialog = builder.create();

            dialog.show();
            return;
        }

        //Obtenemos el texto
        String mensaje = editText.getEditableText().toString();
        if(mensaje.isEmpty()){
            Snackbar snack=Snackbar.make(view, "No puedes mandar mensajes vacios", Snackbar.LENGTH_SHORT);
            View snackBarView = snack.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            snack.setAction("Action", null).show();
            snack.show();
            return;
        }

        scaleIni = 1;
        scaleFin = 0;
        fabAnimate(false);

        swipeRefreshLayout.setRefreshing(true);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
        userID = sharedPreferences.getInt("userID",0);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("UserId",userID);
        params.put("DestinationId",1);
        params.put("MessageTypeId",1);
        params.put("Text",mensaje);
        WS.sendMessage(params,this);
    }

    private void onClickLocation(){
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }else{
            getLocation();
        }
    }

    private void clickGalleryChooser(){

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(pickIntent, "Seleccionar imagen");
        startActivityForResult(chooserIntent, GALLERY_CHOOSER);
    }

    private void showCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, PHOTO_ACTIVITY_REQUEST);
        }
    }

    private void clickCamera(){
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{android.Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }else{
            showCamera();
        }
    }

    private void getMessages(boolean isTopList){

        swipeRefreshLayout.setRefreshing(true);

        if(isTopList){
            skip+=take;
        }else{
            skip=skipIni; take=takeIni;
            messages.clear();
            mAdapter.notifyDataSetChanged();
        }

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
        userID = sharedPreferences.getInt("userID",0);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("UserId",userID);
        params.put("Skip",skip);
        params.put("Take",take);
        WS.getMessages(params,this);
    }


    private void addToList(ArrayList<Msg> newMsgs){

        for(int i=0; i<newMsgs.size(); i++){
            messages.add(newMsgs.get(i));
        }

        for(Msg msg : messages){
            respChat.add(msg);
        }

        mAdapter.notifyDataSetChanged();
        mLayoutManager.scrollToPosition(messages.size());

        swipeRefreshLayout.setRefreshing(false);

        //Log.d("DebCases","casesLength="+benefs.size());
        //mSwipeRefreshLayout.setRefreshing(false);
        //bottomRequested=false;
    }

    @NonNull
    private RequestBody createPartFromString (String descriptionString){
        return RequestBody.create(MultipartBody.FORM, descriptionString);
    }

    private void uploadFileRetrofit(String fileLocation){

        File file = new File(fileLocation);

        //Create retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(WS.WS_URL+"Message/SendImageMessages/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        //Get client & call object for the request
        UserClient client = retrofit.create(UserClient.class);

        //finally execute the request
        Log.d("asdfg","userID="+userID+"fileName="+file.getName());
        Call<ResponseBody> call = client.uploadPhoto(
                createPartFromString(""+userID),
                createPartFromString("3"),
                createPartFromString("4"),
                prepareFilePart("fileContents",fileLocation)
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("ImageDebug","CORRECTO CALL = "+call.request().toString()+" response = "+response.toString());
                //Toast.makeText(ChatActivity.this, "YES!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);

                hideGallery();
                getMessages(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("asdfg","ERROR "+call.toString());
                t.printStackTrace();
                //Toast.makeText(ChatActivity.this, "NO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showGallery(){

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        hideMap();

        if(mGalleryAdapter == null){
            mGalleryAdapter = new GalleryAdapter(ChatFragment.this);
            gallery.setAdapter(mGalleryAdapter);
            mGalleryAdapter.setGalleryLoadedListener(ChatFragment.this);
            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, final View viewImg, int i, long l) {

                    dialog = new DialogShowImg();
                    dialog.setImgURLandListener(((ImageView) viewImg).getTag().toString(), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String tag = ((ImageView)viewImg).getTag().toString();
                            Log.d("UPLOADING","------- requested : "+tag);
                            rewriteCompressedImg(tag);
                        }
                    });
                    dialog.show(getActivity().getSupportFragmentManager(),"");

                }
            });
        }

        galleryContainer.setVisibility(View.VISIBLE);
    }

    private String saveImage(Bitmap finalBitmap) {

        String locationDir="";
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String fname = "Image-"+ 1000 +".png";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            locationDir = root+"/saved_images/"+fname;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationDir;
    }

    private void rewriteCompressedImg(String fileLocation){
        try {
            Glide
                    .with(getContext())
                    .load(new File(fileLocation)).asBitmap()
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>(800,1200) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            // Possibly runOnUiThread()
                            uploadFileRetrofit(saveImage(resource));

                        }
                    });
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, String fileLocation){
        swipeRefreshLayout.setRefreshing(true);
        File file = new File(fileLocation);

        Log.d("asdfg","mimeType = "+getMimeType(fileLocation));
        Log.d("asdfg","contentResolver="+getActivity().getContentResolver().getType(Uri.fromFile(file)));
        Log.d("asdfg","URI="+Uri.fromFile(file).toString());

        Uri myUri = Uri.parse(fileLocation);
        RequestBody requestFile = RequestBody.create(
                //MediaType.parse(getContentResolver().getType(Uri.fromFile(file))), file
                MediaType.parse(getMimeType(fileLocation)), file
        );

        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private void clickGallery(){
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }else if(ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        }else{
            showGallery();
        }
    }

    private void hideGallery(){
        galleryContainer.setVisibility(View.GONE);
    }

    private void showMap(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        hideGallery();

        Handler h3 = new Handler();
        h3.postDelayed(new Runnable(){
            public void run(){
                fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_add_location));
                buttonImg.setVisibility(View.GONE);
                cardEditText.setVisibility(View.GONE);
                ((ImageView)buttonLocation).setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_back));
                //tituloAccion.setVisibility(View.VISIBLE);
            }
        }, 100);
    }

    private void hideMap(){
        fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_send_material));
        buttonImg.setVisibility(View.VISIBLE);
        cardEditText.setVisibility(View.VISIBLE);
        ((ImageView)buttonLocation).setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_place));


        Handler h3 = new Handler();
        h3.postDelayed(new Runnable(){
            public void run(){
                mapContainer.setVisibility(View.GONE);

                try{ mMap.setMyLocationEnabled(false);}
                catch (SecurityException e){e.printStackTrace();}
            }
        }, 300);
    }

    private void getLocation(){

        if(mapContainer.getVisibility()==View.VISIBLE){
            hideMap();
            return;
        }

        try {
            Awareness.SnapshotApi.getLocation(mGoogleApiClient).setResultCallback(new ResultCallback<LocationResult>() {
                @Override
                public void onResult(@NonNull LocationResult locationResult) {
                    if (!locationResult.getStatus().isSuccess()) {
                        Log.e("LocationAwareness", "Could not detect user location");
                        WS.showError("No pudimos obtener tu ubicación, intenta nuevamente", buttonLocation);
                        return;
                    }

                    if(mMap==null){
                        WS.showError("Ocurrió un error al mostrar el mapa, intenta nuevamente", buttonLocation);
                        return;
                    }

                    showMap();

                    try{ mMap.setMyLocationEnabled(true);}
                    catch (SecurityException e){e.printStackTrace();}

                    Location location = locationResult.getLocation();
                    lat=location.getLatitude();
                    lon=location.getLongitude();
                    mMarkerOptions = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude()));
                    mapContainer.setVisibility(View.VISIBLE);
                    CameraPosition mCameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(),location.getLongitude()))
                            .zoom(15)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
                    if(mMarker==null)
                        mMarker = mMap.addMarker(mMarkerOptions);

                    Handler h2 = new Handler();
                    h2.postDelayed(new Runnable(){
                        public void run(){
                            mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                                @Override
                                public void onCameraMove() {
                                    LatLng latlon = mMap.getCameraPosition().target;
                                    mMarker.setPosition(latlon);
                                    lat=latlon.latitude; lon=latlon.longitude;
                                }
                            });
                        }
                    }, 3000);

                }
            });
        }catch(SecurityException ex){}
    }

    //ESCONDE DE MANERA ANIMADA EL FAB Y MUESTRA EL PROGRESS QUE ESTA DETRAS SIEMPRE VISIBLE
    private void fabAnimate(final boolean visible){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getActivity().getBaseContext(),
                    android.R.interpolator.fast_out_slow_in);

            fab.animate()
                    .scaleX(scaleIni)
                    .scaleY(scaleIni)
                    .setInterpolator(interpolador)
                    .setDuration(600)
                    .setStartDelay(0)
                    .setListener(new Animator.AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator animation){}

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            fab.animate()
                                    .scaleY(scaleFin)
                                    .scaleX(scaleFin)
                                    .setInterpolator(interpolador)
                                    .setDuration(600)
                                    .start();
                            editText.setEnabled(visible);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation){}
                        @Override
                        public void onAnimationRepeat(Animator animation){}
                    });
        }
    }

    // ---------------------------------------------------------- //
    // -------------- WEB SERVICES IMPLEMENTATION --------------- //
    // ---------------------------------------------------------- //

    @Override
    public void wsAnswered(JSONObject json) {
        Log.d("Messages",json.toString());
        int ws=0; int status=-1;
        try{status=json.getInt("status");}catch(Exception e){e.printStackTrace();}
        if(status!=0){/*ERRRRRRROOOOOOOORRRRRRR*/}

        try{
            ws = json.getInt("ws");
            switch (ws){
                case WS.WS_getMessages:{

                    JSONObject data = json.getJSONObject("data");
                    JSONArray newMsgJArray = data.getJSONArray("jsonArray");
                    ArrayList<Msg> newMsgs = new ArrayList<Msg>();

                    if(newMsgJArray.length()==0 && topRequested){
                        swipeRefreshLayout.setRefreshing(false);
                        return;
                    }

                    for(int i=0; i<newMsgJArray.length();i++){
                        JSONObject newMsgJSONObject = newMsgJArray.getJSONObject(i);
                        Log.d("MSGDEB","sentfrom="+((newMsgJSONObject.getInt("CreatorId")==userID) ? "mine" : "other"));
                        Msg newMsg=null;
                        if(newMsgJSONObject.getInt("CreatorId")==userID){
                            newMsg = new OwnMsg();
                        }else{
                            newMsg = new OtherMsg();
                            newMsg.setAvatarURL(officerURLImage);
                        }

                        newMsg.setId(newMsgJSONObject.getInt("CreatorId"));
                        newMsg.setSenderName(newMsgJSONObject.getString("CreatorName"));
                        newMsg.setMsg(newMsgJSONObject.getString("Index"));
                        newMsg.setTimeStamp(newMsgJSONObject.getString("Created"));
                        newMsg.setUrl(newMsgJSONObject.getString("Message"));
                        newMsg.setType(newMsgJSONObject.getInt("TypeId"));

                        newMsgs.add(newMsg);
                    }

                    if(newMsgs.size()==0 && !topRequested){
                        Toast.makeText(getActivity(),"Sin mensajes", Toast.LENGTH_SHORT).show();
                    }

                    addToList(newMsgs);
                    //respBot = messages;

                    topRequested=false;
                    break;
                }

                case WS.WS_sendMessage:{
                    scaleIni = 0;
                    scaleFin = 1;
                    fabAnimate(true);
                    getMessages(false);
                    if(locationJustSent){locationJustSent=false; hideMap();}
                    editText.setText("");
                    break;
                }

                case WS.WS_getOfficerInfo:{
                    JSONObject data = json.getJSONObject("data");
                    officerName=data.getString("Name");
                    officerURLImage=data.getString("ImageUrl");

                    textStaff.setText(officerName);
                    Picasso.with(getContext()).load(officerURLImage).into(switchButtonStaffImg);
                    break;
                }
            }
        } catch (JSONException e) { e.printStackTrace(); }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Intent pickIntent = new Intent(Intent.ACTION_PICK);
        Log.d("ActivityResult","GalleryChooser ? "+(requestCode==GALLERY_CHOOSER ? "YES" :"NO"));

        if(requestCode==GALLERY_CHOOSER && resultCode== Activity.RESULT_OK){
            try{

                Uri uri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    final String filePath = cursor.getString(columnIndex);
                    Log.d("DEBUGESTEXD","filePath="+filePath);
                    Log.d("UPLOADING","------- requested : "+filePath);

                    dialog = new DialogShowImg();
                    dialog.setImgURLandListener(filePath, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("HAHA","Enviar...");
                            rewriteCompressedImg(filePath);
                        }
                    });
                    dialog.show(getActivity().getSupportFragmentManager(),"");

                    //uploadFileRetrofit(filePath);
                }
                cursor.close();
            }catch(Exception e){e.printStackTrace();}
        }
    }

    // ---------------------------------------------------- //
    // -------------- SCROLL IMPLEMENTATION --------------- //
    // ---------------------------------------------------- //

    private RecyclerView.OnScrollListener setScrollListener(){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(topRequested){return;}

                if(dy < 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if((visibleItemCount+pastVisiblesItems)==totalItemCount){
                        topRequested=true;
                        getMessages(true);
                    }
                }
            }
        };
    }

    // ------------------------------------------------------- //
    // -------------- MAP READY IMPLEMENTATION --------------- //
    // ------------------------------------------------------- //

    @Override
    public void onMapReady(GoogleMap map) {

        mMap=map;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(12.0f);
        mMap.setMaxZoomPreference(17.0f);

    }

    // ------------------------------------------------------------ //
    // -------------- GALLERY LOADED IMPLEMENTATION --------------- //
    // ------------------------------------------------------------ //

    @Override
    public void onGalleryLoaded() {
        progressGalleryLoaded.setVisibility(View.GONE);
    }


}

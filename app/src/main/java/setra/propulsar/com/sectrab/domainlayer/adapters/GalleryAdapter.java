package setra.propulsar.com.sectrab.domainlayer.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.presentationlayer.fragments.ChatFragment;

public class GalleryAdapter extends BaseAdapter {

    private ChatFragment fragment;

    ArrayList<String> imagesPath;
    Context mContext;

    public GalleryAdapter(ChatFragment a){

        fragment=a;

        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String publicDCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+ "/";
                String publicPICS = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/";
                String publicDOWNS = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/";

                File[] publicDCIMFiles = (new File(publicDCIM)).listFiles();
                File[] publicPICSFiles = (new File(publicPICS)).listFiles();
                File[] publicDOWNSFiles = (new File(publicDOWNS)).listFiles();

                ArrayList<File> files = new ArrayList<File>();

                Log.d("files","publicDCIM="+publicDCIM.toString()+" length="+publicDCIMFiles.length);
                Log.d("files","publicPICS="+publicPICS.toString()+" length="+publicPICSFiles.length);

                Log.d("files","-------------");


                int countPublicFiles=0;
                for(File filePIC : publicPICSFiles){
                    Log.d("files","PICS="+filePIC.toString());
                    if(filePIC.isDirectory()){
                        File[] innerPIC = filePIC.listFiles();
                        for(File innerArchsPIC : innerPIC){
                            if(!innerArchsPIC.isDirectory()){
                                files.add(innerArchsPIC);
                                countPublicFiles++;
                            }
                            if(countPublicFiles>=20){break;}
                        }
                    }
                }

                Log.d("files","# ALLFiles="+files.size());

                imagesPath = new ArrayList<String>();

                int countFiles=0;
                for(File file : files){
                    try {
                        String extension = MimeTypeMap.getFileExtensionFromUrl(file.toURI().toURL().toString());
                        if(extension!=null){
                            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                            if(mimeType.contains("image")){
                                imagesPath.add(file.getAbsolutePath());
                                countFiles++;
                            }
                        }
                    }catch(Exception e){}
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Log.d("files","# ALLFiles="+imagesPath.size());
                notifyDataSetChanged();
                mGalleryLoadedListener.onGalleryLoaded();
            }
        };
        task.execute();

    }

    @Override
    public int getCount() {
        if(imagesPath==null)return 0; else return imagesPath.size();
    }

    @Override
    public Object getItem(int i) {
        return imagesPath.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, mContext.getResources().getDisplayMetrics());
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, px));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) view;
        }

        imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_image_ph));
        //imageView.setImageBitmap(BitmapFactory.decodeFile(imagesPath.get(i)));
        Picasso.with(mContext).load(Uri.fromFile(new File(imagesPath.get(i)))).into(imageView);
        imageView.setTag(imagesPath.get(i));
        return imageView;
    }

    public GalleryLoadedListener mGalleryLoadedListener;
    public interface GalleryLoadedListener{
        public void onGalleryLoaded();
    };
    public void setGalleryLoadedListener(GalleryLoadedListener l){
        mGalleryLoadedListener=l;
    }
}

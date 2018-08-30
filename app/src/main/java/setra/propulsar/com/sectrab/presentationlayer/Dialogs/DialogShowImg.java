package setra.propulsar.com.sectrab.presentationlayer.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import setra.propulsar.com.sectrab.R;

public class DialogShowImg extends AppCompatDialogFragment {

    View view;
    static String imgURL;
    static View.OnClickListener listenerEnviar;

    ImageView imagePreview;

    public static DialogShowImg newInstance(){
        DialogShowImg fragment = new DialogShowImg();
        return fragment;
    }

    public static void setImgURLandListener(String url, View.OnClickListener l){
        imgURL = url;
        listenerEnviar=l;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.alertdialog_confirm_image, container, false);
        Log.d("Y Existe?","existe? = "+imgURL);
        Glide
                .with(getContext())
                .load(new File(imgURL))
                .into((ImageView)view.findViewById(R.id.imageToSendDialog));


        view.findViewById(R.id.buttonCerrarDiag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        view.findViewById(R.id.buttonEnviarDiag).setOnClickListener(listenerEnviar);
        return view;
    }
}

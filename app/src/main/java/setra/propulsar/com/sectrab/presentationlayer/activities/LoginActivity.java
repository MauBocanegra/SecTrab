package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import setra.propulsar.com.sectrab.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout textInputCorreo;
    private TextInputLayout textInputContra;
    private EditText editTextCorreo;
    private EditText editTextContra;
    boolean doubleBackToExitPressedOnce = false;

    // -------------------------------------------- //
    // ---------------- LIFE CYCLE ---------------- //
    // -------------------------------------------- //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Implementamos el listener de los toques para los botones
        findViewById(R.id.buttonLoginEntrar).setOnClickListener(this);
        findViewById(R.id.buttonContraOlvidada).setOnClickListener(this);
        findViewById(R.id.buttonRegister).setOnClickListener(this);
        findViewById(R.id.buttonSkip).setOnClickListener(this);

        //Instanciamos las variables que se usaran
        textInputCorreo = (TextInputLayout) findViewById(R.id.textInputCorreoLogin);
        textInputContra = (TextInputLayout) findViewById(R.id.textInputContraseñaLogin);
        editTextCorreo = (EditText) findViewById(R.id.editTextCorreoElectronicoLogin);
        editTextContra = (EditText) findViewById(R.id.editTextContraseñaLogin);

    }

    // --------------------------------------------- //
    // ---------------- OWN METHODS ---------------- //
    //---------------------------------------------- //

    private void hideKeyboard(){
        try{
            View view = this.getCurrentFocus();
            if(view != null){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private boolean isValidEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private int validateFields() {

        //instanciamos las cadenas que se obtienen de los campos
        String stringCorreo = editTextCorreo.getText().toString();
        String stringContra = editTextContra.getText().toString();

        int errorCount = 0;

        if (stringCorreo.isEmpty() || !isValidEmail(stringCorreo)) {
            textInputCorreo.setError(getString(R.string.login_error_correo_vacio));
            errorCount++;
        } else { textInputCorreo.setError(null);}

        if (stringContra.isEmpty()){
            textInputContra.setError(getString(R.string.login_error_contra_vacia));
            errorCount++;
        } else { textInputContra.setError(null);}

        return errorCount;
    }

    public void onBackPressed(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Presiona nuevamente ATRÁS para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        },2000);

        return;
    }

    // ----------------------------------------------------------- //
    // ---------------- INTERFACES IMPLEMENTATION ---------------- //
    //------------------------------------------------------------ //

    @Override
    public void onClick(View view) {
        hideKeyboard();

        int errorCount = 0;

        switch (view.getId()){

            case R.id.buttonLoginEntrar:{

                Log.d("DEBLogin","CLICKED");

                String stringCorreo = editTextCorreo.getEditableText().toString();
                String stringContra = editTextContra.getEditableText().toString();

                if (validateFields()>0){return;}
                break;
            }

            case R.id.buttonRegister:{
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;}

            case R.id.buttonSkip:{
                Intent intent = new Intent(LoginActivity.this, MainNavigationActivity.class);
                startActivity(intent);
                break;}
        }

    }
}


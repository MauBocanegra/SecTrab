package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import setra.propulsar.com.sectrab.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout textInputCorreo;
    private TextInputLayout textInputContra;
    private EditText editTextCorreo;
    private EditText editTextContra;

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

    // ----------------------------------------------------------- //
    // ---------------- INTERFACES IMPLEMENTATION ---------------- //
    //------------------------------------------------------------ //

    @Override
    public void onClick(View view) {
        hideKeyboard();

        int errorCount = 0;

        switch (view.getId()){

            case R.id.buttonLoginEntrar:{

                //instanciamos las cadenas que se obtienen de los campos
                String stringCorreo = editTextCorreo.getText().toString();
                String stringContra = editTextContra.getText().toString();

                //Si el campo de usuario esta vacio muestra el error en el inputLayout
                if(stringCorreo.isEmpty()){
                    textInputCorreo.setError(getString(R.string.login_error_correo_vacio));
                    errorCount++;
                }else{
                    textInputCorreo.setError(null);
                }

                //Si el campo de contra esta vacio muestra el error en el inputLayout
                if(stringContra.isEmpty()){
                    textInputContra.setError(getString(R.string.login_error_contra_vacia));
                    errorCount++;
                }else {
                    textInputContra.setError(null);
                }

                //Si los campos no estan vacios intenta hacer el login
                if(errorCount==0){

                }
            }
            break;

            case R.id.buttonRegister:{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Inicia actividad de Registro
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },700);
            }
        }

    }
}


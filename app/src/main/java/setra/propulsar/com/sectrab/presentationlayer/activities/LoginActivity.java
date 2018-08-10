package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.ws.WS;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, WS.OnWSRequested {

    private TextInputLayout textInputCorreo;
    private TextInputLayout textInputContra;
    private EditText editTextCorreo;
    private EditText editTextContra;
    View buttonLogin;
    View progressEntrar;
    View buttonContra;
    View progressContra;
    boolean doubleBackToExitPressedOnce = false;

    // -------------------------------------------- //
    // ---------------- LIFE CYCLE ---------------- //
    // -------------------------------------------- //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WS.getInstance(getApplicationContext());

        //Implementamos el listener de los toques para los botones
        buttonLogin = findViewById(R.id.buttonLoginEntrar);
        buttonLogin.setOnClickListener(this);
        buttonContra = findViewById(R.id.buttonContraOlvidada);
        buttonContra.setOnClickListener(this);
        findViewById(R.id.buttonRegister).setOnClickListener(this);
        findViewById(R.id.buttonSkip).setOnClickListener(this);
        progressEntrar = findViewById(R.id.progressBarEntrar);
        progressContra = findViewById(R.id.progressBarContra);

        //Instanciamos las variables que se usaran
        textInputCorreo = (TextInputLayout) findViewById(R.id.textInputCorreoLogin);
        textInputContra = (TextInputLayout) findViewById(R.id.textInputContraseñaLogin);
        editTextCorreo = (EditText) findViewById(R.id.editTextCorreoElectronicoLogin);
        editTextContra = (EditText) findViewById(R.id.editTextContraseñaLogin);

    }

    // --------------------------------------------- //
    // ---------------- OWN METHODS ---------------- //
    //---------------------------------------------- //

    private void hideKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
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
        } else {
            textInputCorreo.setError(null);
        }

        if (stringContra.isEmpty()) {
            textInputContra.setError(getString(R.string.login_error_contra_vacia));
            errorCount++;
        } else {
            textInputContra.setError(null);
        }

        return errorCount;
    }

    private int validateContra() {
        String correo = editTextCorreo.getEditableText().toString();

        if (correo.isEmpty() || !isValidEmail(correo)) {
            textInputCorreo.setError("Introduce el correo del cual deseas recuperar tu contraseña");
            return 1;
        } else {
            textInputCorreo.setError(null);
            return 0;
        }
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Presiona nuevamente ATRÁS para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

        return;
    }

    // ----------------------------------------------------------- //
    // ---------------- INTERFACES IMPLEMENTATION ---------------- //
    //------------------------------------------------------------ //

    @Override
    public void onClick(View view) {
        hideKeyboard();

        switch (view.getId()) {

            case R.id.buttonLoginEntrar: {
                Log.d("DEBLogin", "CLICKED");

                View someview = this.getCurrentFocus();
                if(someview!=null){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(someview.getWindowToken(), 0);
                }

                String stringCorreo = editTextCorreo.getEditableText().toString();
                String stringContra = editTextContra.getEditableText().toString();

                if (validateFields() > 0) { return; }

                progressEntrar.setVisibility(View.VISIBLE);
                buttonLogin.setEnabled(false);

                Map<String, Object> params = new LinkedHashMap<>();
                params.put("UserName", stringCorreo);
                params.put("Password", stringContra);
                WS.getInstance(LoginActivity.this).userSignIn(params, this);

                break;
            }

            case R.id.buttonContraOlvidada: {
                PopupMenu popup = new PopupMenu(LoginActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.menu_contra, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (validateContra() == 0) {
                            progressContra.setVisibility(View.VISIBLE);
                            Map<String, Object> params = new LinkedHashMap<>();
                            params.put("Email", editTextCorreo.getEditableText().toString());
                            WS.recoverPassword(params, LoginActivity.this);
                        }
                        return true;
                    }
                });

                popup.show();
                break;
            }
            case R.id.buttonRegister: {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.buttonSkip: {
                Intent intent = new Intent(LoginActivity.this, MainNavigationActivity.class);
                startActivity(intent);
                break;
            }
        }

    }

    // ------------------------------------------- //
    // -------------- WEB SERVICES --------------- //
    // ------------------------------------------- //

    @Override
    public void wsAnswered(JSONObject json) {

        Log.d("LoginActivity", json.toString());
        int ws = 0;
        int status = -1;
        try { status = json.getInt("status"); } catch (Exception e) { e.printStackTrace(); }
        if (status != 0) {/*ERRRRRRROOOOOOOORRRRRRR*/}


        try {
            ws = json.getInt("ws");
            switch (ws) {

                case WS.WS_recoverPassword: {
                    JSONObject data = json.getJSONObject("data");
                    if (data.getInt("Updated") == 1) {
                        WS.showSucces("Se envió a tu correo la nueva contraseña", buttonLogin);
                        progressContra.setVisibility(View.GONE);
                    } else {
                        WS.showSucces("El correo ingresado no existe en la base de datos", buttonLogin);
                        progressContra.setVisibility(View.GONE);
                    }
                    break;
                }

                case WS.WS_userSignIn:{
                    JSONObject data = json.getJSONObject("data");

                    int authenticate = data.getInt("Authenticate");
                    if(authenticate!=1){

                        Snackbar snack=Snackbar.make(findViewById(R.id.constraitLayoutLogin), "Error al iniciar sesión, verifica tus datos", Snackbar.LENGTH_LONG);
                        View snackBarView = snack.getView();
                        snackBarView.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorAccent));
                        snack.setAction("Action", null).show();
                        snack.show();
                        return;
                    }

                    SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userID",data.getInt("UserId"));
                    Log.d("UserSignIn","UserSignIn = "+data.getInt("UserId"));
                    editor.putString("email",data.getString("Email"));
                    editor.putBoolean("loggedIn",true);
                    editor.commit();
                    String messageToShow=null;
                    try{
                        messageToShow=data.getString("Message");
                    }catch(Exception e){}
                    Toast.makeText(this, messageToShow==null ? getString(R.string.iniOKLogin) : messageToShow, Toast.LENGTH_SHORT).show();
                    Log.d("DEB DATA","id="+data.getInt("UserId")+" correo="+data.getString("Email"));
                    Intent intent = new Intent(getApplicationContext(), MainNavigationActivity.class);
                    startActivity(intent);
                    break;
                    }
            }
        }catch(Exception e){e.printStackTrace();}
        finally {
            progressEntrar.setVisibility(View.GONE);
            buttonLogin.setEnabled(true);
        }
    }
}



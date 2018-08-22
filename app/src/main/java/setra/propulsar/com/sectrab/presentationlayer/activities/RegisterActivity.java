package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.ws.WS;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, WS.OnWSRequested {

    private TextInputLayout textInputNombre;
    private TextInputLayout textInputCorreo;
    private TextInputLayout textInputContra;
    private EditText editTextNombre;
    private EditText editTextCorreo;
    private EditText editTextContra;
    View buttonRegister;
    View progressButtonRegister;

    // -------------------------------------------- //
    // ---------------- LIFE CYCLE ---------------- //
    // -------------------------------------------- //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Asignacion del toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRegister);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Implementamos el listener de los toques para los botones
        buttonRegister = findViewById(R.id.buttonEntrarRegistro);
        buttonRegister.setOnClickListener(this);
        progressButtonRegister = findViewById(R.id.progressBarRegistro);
        findViewById(R.id.buttonSaltarRegistro).setOnClickListener(this);

        //Instanciamos las variables que se usaran
        textInputNombre = (TextInputLayout) findViewById(R.id.textInputNombreCompletoRegistro);
        textInputCorreo = (TextInputLayout) findViewById(R.id.textInputLayoutCorreoElectronicoRegistro);
        textInputContra = (TextInputLayout) findViewById(R.id.textInputContraseñaRegistro);
        editTextNombre = (EditText) findViewById(R.id.editTextNombreRegistro);
        editTextCorreo = (EditText) findViewById(R.id.editTextCorreoElectronicoRegistro);
        editTextContra = (EditText) findViewById(R.id.editTextContraseñaRegistro);

    }

    // --------------------------------------------- //
    // ---------------- OWN METHODS ---------------- //
    //---------------------------------------------- //

    private void HideKeyboard() {
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
        String stringNombre = editTextNombre.getText().toString();
        String stringCorreo = editTextCorreo.getText().toString();
        String stringContra = editTextContra.getText().toString();

        int errorCount = 0;

        if (stringNombre.isEmpty()) {
            textInputNombre.setError(getString(R.string.register_error_nombre_vacio));
            errorCount++;
        } else {
            textInputNombre.setError(null);
        }

        if (stringCorreo.isEmpty() || !isValidEmail(stringCorreo)) {
            textInputCorreo.setError(getString(R.string.register_error_correo_vacio));
            errorCount++;
        } else {
            textInputCorreo.setError(null);
        }

        if (stringContra.isEmpty()) {
            textInputContra.setError(getString(R.string.register_error_contra_vacia));
            errorCount++;
        } else if (stringContra.length() < 6) {
            textInputContra.setError("Tu contraseña debe tener al menos 6 caracteres");
            errorCount++;
        } else {
            textInputContra.setError(null);
        }

        return errorCount;
    }

    // ----------------------------------------------------------- //
    // ---------------- INTERFACES IMPLEMENTATION ---------------- //
    //------------------------------------------------------------ //

    @Override
    public void onClick(View view) {
        HideKeyboard();

        switch (view.getId()) {

            case R.id.buttonEntrarRegistro: {

                Log.d("DEBRegister", "CLICKED");

                String stringNombre = editTextNombre.getEditableText().toString();
                String stringCorreo = editTextCorreo.getEditableText().toString();
                String stringContra = editTextContra.getEditableText().toString();

                if (validateFields() > 0) { return; }

                progressButtonRegister.setVisibility(View.VISIBLE);
                buttonRegister.setEnabled(false);

                Map<String, Object> params = new LinkedHashMap<>();
                params.put("Name", stringNombre);
                params.put("Email", stringCorreo);
                params.put("Password", stringContra);
                WS.registerMail(params, this);

                break;
            }

            case R.id.buttonSaltarRegistro: {
                Intent intent = new Intent(RegisterActivity.this, MainNavigationActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void wsAnswered(JSONObject json) {
        Log.d("RegisterActivity", json.toString());
        int ws = 0;
        int status = -1;
        try {
            status = json.getInt("status");
        } catch (Exception e) { e.printStackTrace(); }

        if (status != 0) {/*ERRRRRRROOOOOOOORRRRRRR*/}

        try {
            ws = json.getInt("ws");
            switch (ws) {
                case WS.WS_registerMail: {
                    JSONObject data = json.getJSONObject("data");
                    Log.d("RegisterActivityDEBUG",data.toString());

                    if(!data.getBoolean("Success")) {
                        WS.showMessage(data.getString("ErrorMessage"), RegisterActivity.this);
                        return;
                    }

                    Toast.makeText(this, getString(R.string.iniOKRegistro), Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userID",data.getInt("UserId"));
                    editor.putString("email",data.getString("Email"));
                    editor.putBoolean("loggedIn",data.getBoolean("Success"));
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), MainNavigationActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            }
        }catch(Exception e){ e.printStackTrace(); }
        finally {
            progressButtonRegister.setVisibility(View.GONE);
            buttonRegister.setEnabled(true);
        }
    }
}
package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    }

    @Override
    public void onClick(View view) {

    }
}

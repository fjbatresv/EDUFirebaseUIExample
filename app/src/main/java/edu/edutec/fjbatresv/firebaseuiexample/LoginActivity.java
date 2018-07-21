package edu.edutec.fjbatresv.firebaseuiexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private static final int RC_SIGN_IN = 100;
    @BindView(R.id.edit)
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() == null){
            AuthUI.SignInIntentBuilder intentBuilder = AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(getProviders())
                    .setIsSmartLockEnabled(false);
            Intent intent = intentBuilder.build();
            startActivityForResult(intent, RC_SIGN_IN);
        }
    }

    public List<AuthUI.IdpConfig> getProviders() {
        List<AuthUI.IdpConfig> providers = new ArrayList<AuthUI.IdpConfig>();
        providers.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        providers.add(new AuthUI.IdpConfig.FacebookBuilder().build());
        providers.add(new AuthUI.IdpConfig.TwitterBuilder().build());
        providers.add(new AuthUI.IdpConfig.EmailBuilder().build());
        providers.add(new AuthUI.IdpConfig.PhoneBuilder().build());
        return providers;
    }

    @OnClick(R.id.btn)
    public void sendDb(){
        Date date = new Date();
        String key = ref.child("posts").push().getKey();
        ref.child("posts").child(key)
                .setValue(new Post(edit.getText().toString(), date.getTime()));
        edit.setText(null);
        Toast.makeText(this, "Enviado a la DB", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Log.e("SIGN_IN_OK", String.valueOf(auth.getCurrentUser().getUid()));
            }else{
                Log.e("SIGN_IN_FAIL", String.valueOf(data.getDataString()));
            }
        }
    }
}

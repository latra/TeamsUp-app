

package com.example.teamsup.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.teamsup.Messaging.MessagingService;
import com.example.teamsup.R;
import com.example.teamsup.models.UserModel;
import com.example.teamsup.provider.AuthProvider;
import com.example.teamsup.utils.ConstantsUtils;
import com.example.teamsup.utils.FirebaseUtils;
import com.example.teamsup.utils.UserDataManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";

    private FirebaseAuth mAuth;
    private SignInButton btGoogle;
    private GoogleSignInClient googleSignInClient;
    private RelativeLayout progressBar;
    private AuthProvider authProvider;
    MessagingService msgService;

    Button btnEntrar;
    EditText input_email;
    EditText input_password;

    CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_login_app);
        btGoogle = findViewById(R.id.bt_google);

        progressBar = findViewById(R.id.rl_progress);

// Configure Google Sign In
        authProvider = new AuthProvider();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        btGoogle.setOnClickListener(v -> signInGoogle());

        mAuth = FirebaseAuth.getInstance();

        btnEntrar = findViewById(R.id.btnEntrar);
        input_email = findViewById(R.id.input_mail);
        input_password = findViewById(R.id.input_pswd);
        remember = findViewById(R.id.remember);


        if (mAuth.getCurrentUser() != null) login();

        btnEntrar.setOnClickListener((view) -> {
            String email = input_email.getText().toString();
            String password = input_password.getText().toString();
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    login();
                                } else {
                                    Toast.makeText(getApplication(), "Email y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplication(), "Debes introducir un Email", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplication(), "Debes introducir una contraseña", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

        }
    }

    public void registrate(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login() {


        UserDataManager userDataUtils = new UserDataManager(this);
        FirebaseUtils.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null)
                    userDataUtils.ReadUserData(documentSnapshot.toObject(UserModel.class));
                else {
                    UserModel userModel = new UserModel();
                    userModel.databaseId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                }
            }
        });

        Log.d(TAG, "Subscribing to teamsUp topic");
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("teamsup")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        msgService = new MessagingService();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        msgService.createDeviceOnDB(token);
                    }
                });

        Intent intent = new Intent(this, TemplateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        LoginActivity.this.finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantsUtils.REQUEST_CODE_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("ERROR", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        authProvider.googleSignIn(account).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                String idUser = authProvider.getUserId();
                login();

            } else {
                hideProgressBar();
                Toast.makeText(LoginActivity.this, getText(R.string.couldNotLoginWithGoogle), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void signInGoogle() {
        showProgressBar();
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, ConstantsUtils.REQUEST_CODE_GOOGLE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}


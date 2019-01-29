package com.androidtan.www.aplikasi_patrol.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidtan.www.aplikasi_patrol.Activity.Main.MainActivity;
import com.androidtan.www.aplikasi_patrol.Helpers.CekKoneksi;
import com.androidtan.www.aplikasi_patrol.Helpers.SharedPrefManager;
import com.androidtan.www.aplikasi_patrol.Helpers.LoginDialog;
import com.androidtan.www.aplikasi_patrol.Helpers.LoginDialog2;
import com.androidtan.www.aplikasi_patrol.Models.UserModel;
import com.androidtan.www.aplikasi_patrol.Network.ApiServices;
import com.androidtan.www.aplikasi_patrol.Network.InitRetrofit;
import com.androidtan.www.aplikasi_patrol.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

   private EditText etUsernameLogin, etPasswdLogin;
   private Button btnLogin;
   private String Username, Password;
   private SharedPrefManager session;
   protected Context mContext;
   private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inisialisasi widget pada layout
        mDialog = new ProgressDialog(LoginActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        btnLogin = findViewById(R.id.btnLogin);
        etUsernameLogin = findViewById(R.id.etUsernameLogin);
        etPasswdLogin = findViewById(R.id.etPasswdLogin);
        mContext = this;
        session = new SharedPrefManager(this);
        cekSession();
        login();
    }

    private void cekSession() {
        if (session.getSpAlreadyLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }


    private void login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CekKoneksi.isConnectedToInternet(getBaseContext())) {
                    mDialog.show();

                    //memasukkan nilai dari editText ke variabel
                    Username = etUsernameLogin.getText().toString();
                    Password = etPasswdLogin.getText().toString();

                    //ngecek jika 0 atau tidak
                    if (Username.matches("")) {
                        final int status = 1;
                        LoginDialog.paketLogin(status);
                        openDialog();
                        mDialog.dismiss();
                    } else if (Password.matches("")) {
                        final int status = 2;
                        LoginDialog.paketLogin(status);
                        openDialog();
                        mDialog.dismiss();
                    } else {
                        ApiServices api = InitRetrofit.getInstance();
                        Call<UserModel> loginCall = api.requestLogin(Username, Password);
                        loginCall.enqueue(new Callback<UserModel>() {
                            @Override
                            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                if (response.isSuccessful()) {
                                    boolean status = response.body().getSuccess();
                                    if (status) {
                                        final boolean pwd = response.body().getPassword();
                                        if (pwd) {
                                            final int id = response.body().getId();
                                            final String nama = response.body().getNama();

                                            session.saveSPInt(session.SP_IDUSER, id);
                                            session.saveSPString(session.SP_FULLNAME, nama);
                                            session.saveSPBoolean(session.SP_ALREADY_LOGIN, true);

                                            //berpindah ke activity selanjutnya
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                        } else {
                                            mDialog.dismiss();
                                            openDialog2();
                                        }
                                    } else {
                                        mDialog.dismiss();
                                        final int paketStatus = 0;
                                        LoginDialog.paketLogin(paketStatus);
                                        openDialog();
                                    }

                                } else {

                                }
                            }

                            @Override
                            public void onFailure(Call<UserModel> call, Throwable t) {
                                Log.e("debug", "onFailure: ERROR > " + t.toString());
                                finish();
                            }

                        });
                    }
                } else{
                    Toast.makeText(LoginActivity.this, "please check your connection !!", Toast.LENGTH_SHORT).show();
                    return;
                }
        }
    });
    }

    private void openDialog() {
        //memanggil class dialog
        LoginDialog dialogue = new LoginDialog();
        dialogue.show(getSupportFragmentManager(), "login dialog");
    }

    private void openDialog2() {
        //memanggil class dialog
        LoginDialog2 dialogue = new LoginDialog2();
        dialogue.show(getSupportFragmentManager(), "login dialog");
    }
}

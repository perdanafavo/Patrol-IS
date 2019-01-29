package com.androidtan.www.aplikasi_patrol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtan.www.aplikasi_patrol.Activity.Detail2Activity;
import com.androidtan.www.aplikasi_patrol.Models.PatrolModel;
import com.androidtan.www.aplikasi_patrol.Network.InitRetrofit;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class PreviewActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView txtLokasi;
    private Button btnCamera,btnUpload;
    private String dataLat,dataLng;
    private static String direktoriGambar, namaGambar;
    //get current location
    private FusedLocationProviderClient client;
    private ProgressDialog dialog, mDialog;
    private static final String TAG = PreviewActivity.class.getSimpleName();
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        mDialog = new ProgressDialog(PreviewActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();

        id = getIntent().getIntExtra("id", 0);
        namaGambar = getIntent().getStringExtra("namaGambar");
        //get current location
        requestPermission();
        getLokasi();
        kameraKlik();
        uploadKlik();

        imageView = (ImageView)findViewById(R.id.imageView);
        Bitmap bmp=BitmapFactory.decodeFile(direktoriGambar);
        imageView.setImageBitmap(bmp);
    }

    private void uploadFile(final String selectedFilePath){

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead,bytesAvailable,bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length-1];

        if (!selectedFile.isFile()){
            dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }else{
            try{
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL("http://patrolis.000webhostapp.com/uploadFile.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file",selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer,0,bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0){
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer,0,bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PreviewActivity.this,"File Not Found",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(PreviewActivity.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(PreviewActivity.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }

    }

    private void uploadKlik() {
        btnUpload = (Button)findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(PreviewActivity.this,"","Uploading file..",true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadFile(direktoriGambar);
                    }
                }).start();

                InitRetrofit.getInstance()
                        .updatePatrol(id, namaGambar, dataLat, dataLng)
                        .enqueue(new Callback<PatrolModel>() {
                            @Override
                            public void onResponse(Call<PatrolModel> call, Response<PatrolModel> response) {
                                dialog.dismiss();
                                Intent move = new Intent(PreviewActivity.this, Detail2Activity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                move.putExtra("id",id);
                                startActivity(move);
                            }
                            @Override
                            public void onFailure(Call<PatrolModel> call, Throwable t) {
                                System.out.println("ini lagi");
                            }
                        });
                //instruksi uploadnya taruh di sini ya budjhank!!111!!!11!!!
                //direktori foto yang diupload = direktoriGambar(tipe datanya String)
                //data lokasi juga diupload, variabelnya udah ada di getLokasi()
            }
        });
    }

    private void kameraKlik() {
        btnCamera = (Button)findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(PreviewActivity.this, CameraActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                move.putExtra("id",id);
                move.putExtra("namaGambar", namaGambar);
                move.putExtra("Bool",true);
                startActivity(move);
            }
        });
    }

    private void getLokasi() {
        txtLokasi=(TextView)findViewById(R.id.txtLokasi);

        //getLokasi
        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(PreviewActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        client.getLastLocation().addOnSuccessListener(PreviewActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    //data yang akan diupdate ke database
                    dataLat=String.valueOf(location.getLatitude());
                    dataLng=String.valueOf(location.getLongitude());

                    //data yang akan ditampilkan
                    String locDisplay="Latitude: "+location.getLatitude()+"\nLongitude: "+location.getLongitude();
                    txtLokasi.setText(locDisplay);
                } else {
                    txtLokasi.setText("Latitude: null\nLongitude: null");
                }
            }
        });
        mDialog.dismiss();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

    public static void paketGambar(String direktoriGambar){
        PreviewActivity.direktoriGambar=direktoriGambar;
    }
}

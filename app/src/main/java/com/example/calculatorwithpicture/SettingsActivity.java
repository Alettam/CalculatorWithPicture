package com.example.calculatorwithpicture;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SettingsActivity extends AppCompatActivity {

    private EditText pictureEdTxt;
    private Button okBtn;

    public static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);
        initviews();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionStatus = ContextCompat.checkSelfPermission(
                        SettingsActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);


                if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SettingsActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION_READ_STORAGE
                    );
                }else {
                    loadImg();
                }
            }
        });
    }

    private void initviews() {
        pictureEdTxt = findViewById(R.id.editText);
        okBtn = findViewById(R.id.okBtn);

    }



    private void loadImg() {

        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS),
                pictureEdTxt.getText().toString());
        if (isExternalStorageReadable()) {
            if (file.exists()) {
                this.finish();
                Intent intent1 = new Intent(SettingsActivity.this, MainActivity.class);
                intent1.putExtra("put_picture", pictureEdTxt.getText().toString());
                startActivity(intent1);
            } else {
                Toast.makeText(SettingsActivity.this, "Картинка " + pictureEdTxt.getText().toString() + " не найдена", Toast.LENGTH_LONG).show();
            }
        }

    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults){
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //work
                    loadImg();
                } else {
                   Toast.makeText(SettingsActivity.this,"Нет разрешения",Toast.LENGTH_LONG).show();
                }
        }
    }
}


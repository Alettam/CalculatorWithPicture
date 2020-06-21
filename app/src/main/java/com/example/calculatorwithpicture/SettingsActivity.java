package com.example.calculatorwithpicture;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SettingsActivity extends AppCompatActivity {

    private EditText pictureEdTxt;
    private Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);
        initviews();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loadImg();
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
        if (isExternalStorageWritable()) {
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

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}

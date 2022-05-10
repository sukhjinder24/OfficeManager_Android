package com.example.androidtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageLoader extends AppCompatActivity {
    int Select = 1;
    Uri uri;
    ImageView imageView;
//    ImageView imageView21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_loader);
        Button choose = (Button) findViewById(R.id.choose);
        Button fUpLoad = (Button) findViewById(R.id.fUpLoad);
        imageView = findViewById(R.id.images);
//        imageView21 = findViewById(R.id.images21);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Select);
            }
        });

//        fUpLoad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    finalUpload(view);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Select && resultCode == RESULT_OK && data != null && data.getData() != null)
            ;
        uri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public void finalUpload(View view) throws IOException {
//
//        File f = new File(Environment.getExternalStorageDirectory() + "/filename");
//        f.createNewFile();
//
//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
//        byte[] bitmapdata = bos.toByteArray();
//
//
////          write the bytes in file
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(f);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            fos.write(bitmapdata);
//            fos.flush();
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", f.getName(), reqFile);
//
//
////            CallingAPI
//
//        RetrofitClient.getInstance().getMyApi().postImage(body).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(getApplicationContext(), "Successfully posted: " + response.message(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Failed : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}

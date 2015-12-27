package com.arsegg.asciiartstudio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arsegg.asciiartstudio.util.ASCIIFactory;

import java.io.InputStream;

public class AsciiImageActivity extends AppCompatActivity {

    private static final String TAG = "AsciiImageActivity";
    private static final int PICK_IMAGE = 0;

    private static int width = 100; // text length in characters
    private static Uri fileName; // path to image

    private TextView textView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asciiimage);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Setting monotype font (Courier New)
        textView = (TextView) findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "cour.ttf");
        textView.setTypeface(typeface);

        if (savedInstanceState == null) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        } else {
            new MyAsyncTask().execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            fileName = data.getData();
            Log.d(TAG, "Image's path is " + fileName);
            new MyAsyncTask().execute();
        }
    }

    /* Why do i use non static version of AsyncTask? Because TextView.getWidth() returns 0 if UI is
    * not ready. Time of picture decoding is enough to load UI.*/
    private class MyAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.d(TAG, "Decoding image...");
                InputStream inputStream = getContentResolver().openInputStream(fileName);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap = ASCIIFactory.resize(bitmap, width);
                bitmap = ASCIIFactory.toGrayscale(bitmap);
                return ASCIIFactory.toASCII(bitmap);
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            Log.d(TAG, "Job done!");
            progressBar.setVisibility(View.INVISIBLE);
            textView.setText(string);
            final float screenWidth = textView.getWidth();
            Log.d(TAG, "screenWidth = " + screenWidth);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenWidth / width);
        }
    }
}

package com.example.camerawithrotation;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private Button scanButton;
    private TextView resultTextView;
    private String savedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = findViewById(R.id.scanButton);
        resultTextView = findViewById(R.id.resultTextView);

        if (savedInstanceState != null) {
            savedText = savedInstanceState.getString("savedText", "");
            resultTextView.setText(savedText);
        }

        setButtonPosition(getResources().getConfiguration().orientation);

        // Button click listener
        scanButton.setOnClickListener(view -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setOrientationLocked(false);
            integrator.initiateScan();
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setButtonPosition(newConfig.orientation);
    }

    private void setButtonPosition(int orientation) {
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(500,200);
        LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(500,200);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            btnParams.setMargins(1000, 200,1000, 100);
            txtParams.setMargins(1000,100,1000,200);
        } else {
            btnParams.setMargins(300,300,300,100);
            txtParams.setMargins(300,100,300,100);
        }
        scanButton.setLayoutParams(btnParams);
        resultTextView.setLayoutParams(txtParams);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the text content to restore after configuration change
        outState.putString("savedText", savedText);
        Log.d("SavedText", "Saved text: " + savedText); // Log to check if the text is saved
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String contents = result.getContents();
            if (contents != null) {
                savedText = contents;
                resultTextView.setText(contents);
                copyToClipboard(contents);
            } else {
                Toast.makeText(this, "Scan canceled or failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void copyToClipboard(String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("QR Code Content", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}

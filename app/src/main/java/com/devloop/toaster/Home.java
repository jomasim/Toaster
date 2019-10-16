package com.devloop.toaster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.camerakit.CameraKitView;
import com.devloop.toaster.Camera.FaceRect;
import com.devloop.toaster.Camera.GraphicOverlay;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity {
    AlertDialog alertDialog;
    @BindView(R.id.camera_view)
    CameraKitView cameraKitView;
    @BindView(R.id.graphic_overlay)
    GraphicOverlay graphicOverlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setTitle("Face Detection");
        ButterKnife.bind(this);
        alertDialog = new AlertDialog.Builder(this)
                .setMessage("Processing image...")
                .setCancelable(false)
                .create();
    }

    @OnClick(R.id.scan_photo)
    public void takePhoto() {
        Log.d("image", "taking photo");
        cameraKitView.captureImage((cameraKitView, bytes) -> {
            Log.d("image", "stopping camera");
            cameraKitView.onStop();
            alertDialog.show();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            bitmap = Bitmap.createScaledBitmap(bitmap, cameraKitView.getWidth(), cameraKitView.getHeight(), false);
            runDetector(bitmap);
        });
        graphicOverlay.clear();
    }


    public void runDetector(Bitmap bitmap) {
        // get image bitmap
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        // firebase face detector options
        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .build();

        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options);

        detector.detectInImage(image).addOnSuccessListener(faces -> {
            Log.d("image", String.valueOf(faces.size()));
            Log.d("image", faces.toString());
            processFaces(faces);
            Log.d("image", "success");
        }).addOnFailureListener(e -> {
            alertDialog.dismiss();
            Log.d("image", "fail");
            Toast.makeText(this, "No face detected",
                    Toast.LENGTH_LONG).show();
        });
    }

    private void processFaces(List<FirebaseVisionFace> faces) {
        for (int i = 0; i < faces.size(); i++) {
            FirebaseVisionFace face = faces.get(i);
            FaceRect faceOverlay = new FaceRect(graphicOverlay, face.getBoundingBox());
            graphicOverlay.add(faceOverlay);
        }
        alertDialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraKitView.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraKitView.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

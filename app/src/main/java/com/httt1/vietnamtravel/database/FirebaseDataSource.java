package com.httt1.vietnamtravel.database;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class FirebaseDataSource {
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final StorageReference storageRef = storage.getReference();
    private StorageReference getImgFireBase(String link) {

        StorageReference imageRef = storageRef.child(link); // Đường dẫn ảnh trong Firebase Storage //ImgTour/CBBTTV/1.jpeg

        return imageRef;
    }
}

package com.example.firestoreunderstanding;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    //adding listener for load from database only make sense when app is on the foreground




    private EditText editTextTitle, editTextDesc;
    private Button button,buttonload;
    private TextView textViewData;

    private String codeFromFirebase;


    public static final String TAG = "MainActivity";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";

    //private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //private DocumentReference noteRef = db.collection("Notebook").document("My First Not");

    //private DocumentReference noteRefV2 = db.document("Notebook/My First Not");

    //firestore setting

    final static public FirebaseFirestore reference = FirebaseFirestore.getInstance();

     final static  CollectionReference  checkFirst = reference.collection("employee");

    //create new collection and document

    private CollectionReference secondRef = reference.collection("allRate");

    final static CollectionReference thirdTry = reference.collection("scoreCard");


    //
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        textViewData = findViewById(R.id.textViewId);
        editTextDesc = findViewById(R.id.editTextDescID);
        editTextTitle = findViewById(R.id.editTextTitleID);

        button = findViewById(R.id.logInId);

        buttonload = findViewById(R.id.getCodeID);


        Map<String, Object> kk = new HashMap<>();

        kk.put("makan","check");
        kk.put("sedap","tak");

        secondRef.document("ikan").set(kk);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(codeFromFirebase!=null){

                    checkCredential(codeFromFirebase, editTextDesc.getText().toString());
                }

            }
        });

        buttonload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gettingCallback();

            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                textViewData.setText("phone success credential..");
                verifyWithCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                codeFromFirebase =s;
                textViewData.setText("enter code and log in");

            }
        };


    }

    private void checkCredential(String codeFromFirebase, String toString) {


        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeFromFirebase,toString);

        verifyWithCredential(credential);

    }

    private void verifyWithCredential(PhoneAuthCredential phoneAuthCredential) {




        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {



                if(task.isSuccessful()){

                    textViewData.setText("entering");

                    userIsLoggedIn();
                }
            }
        });


    }

    private void userIsLoggedIn() {



        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){


            final Query query = checkFirst.whereEqualTo("phone",user.getPhoneNumber());


            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                    if(queryDocumentSnapshots.isEmpty()){

                        //if not data exist, add user to database

                        Map<String,Object> mm = new HashMap<>();

                        mm.put("phone", user.getPhoneNumber());
                        mm.put("uid",user.getUid());

                        String user1 = user.getPhoneNumber() + " user1";

                        checkFirst.document(user1).set(mm).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Log.i("checkk"," added new document no collection");
                                }
                            }
                        });

                        //create new collection with new doc,

                        Map<String, Object> kk = new HashMap<>();

                        String s = user.getUid();

                        kk.put("uid", user.getUid());
                        kk.put("name", true);

                        thirdTry.document(s).set(kk).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Log.i("checkk"," create new collection new document");
                                }
                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("checkk"," fail create new collection new document");
                            }
                        });

                       // textViewData.setText("insert");

                        //String s = secondRef.document().getId();

                       // secondRef.document(user.getUid()); //will this create new collection with new document




                    }

                }

            });


            Intent intent = new Intent(MainActivity.this,CheckInActivity.class);
            startActivity(intent);


        }else {
            textViewData.setText("failed");
        }


    }

    private void gettingCallback() {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(editTextTitle.getText().toString(),
                40,
                TimeUnit.SECONDS,
                this,
                mCallbacks);


    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        
//        //listener
//        
//        noteRefV2.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {  //this, context will detach 
//            // activity on appropriate, we dont watch to always fetch data on foreground.
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//
//                if(e!=null){ //say that some user
//
//                    Toast.makeText(MainActivity.this, "error load listener", Toast.LENGTH_SHORT).show();
//                    return; //important because we want to leave tshi method if there is error
//
//
//                }
//
//
//                if(documentSnapshot.exists()){
//
//                    String title = documentSnapshot.getString(KEY_TITLE);
//                    String description = documentSnapshot.getString(KEY_DESCRIPTION);
//
//                    textViewData.setText(title + " "+ description );
//
//
//                }
//            }
//        });
//
//    }
//
//    private void loadMethod() {
//
//        noteRefV2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                if(documentSnapshot.exists()){
//
//                    String title = documentSnapshot.getString(KEY_TITLE);
//                    String description = documentSnapshot.getString(KEY_DESCRIPTION);
//
//                    textViewData.setText(title + " "+ description );
//
//                }else {
//                    Toast.makeText(MainActivity.this, "document does not exist", Toast.LENGTH_SHORT).show();
//                }
//            }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, "error load", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//
//
//    }
//
//    private void myMethod() {
//
//        String title = editTextTitle.getText().toString();
//        String description = editTextDesc.getText().toString();
//
//            Map<String, Object>  note = new HashMap<>();
//
//            note.put(KEY_TITLE, title);
//            note.put(KEY_DESCRIPTION,description);
//
//        Map<String, Object>  note2 = new HashMap<>();
//
//        note2.put(KEY_TITLE, title);
//        note2.put(KEY_DESCRIPTION,description);
//
//
//
//        Log.i("farkk","1");
//
//        //db.collection("Notebook").document("My First Not").
//
//        db.collection("Notebook").document("My First Note3").set(note2)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                        Log.i("farkk","1");
//
//                        Toast.makeText(MainActivity.this, "success  ", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                        Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//        ;
//
//
//
//    }


}

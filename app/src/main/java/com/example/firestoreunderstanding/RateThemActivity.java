package com.example.firestoreunderstanding;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class RateThemActivity extends AppCompatActivity {

   // private RatingBar ratingBar;

    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    private ArrayList<String> rateTry = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_them);

    //ratingBar = findViewById(R.id.ratingBarID);

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            MainActivity.thirdTry.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if(task.isSuccessful()){

                            for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){


                                rateTry.add(queryDocumentSnapshot.getString("uid"));

                            }

                            String removee = user.getUid();

                            rateTry.remove(removee);


                    }

                }
            });


     initializeRecyler();


        recyclerView = findViewById(R.id.recyclerbroID);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new CustomeRecylerAdapter(rateTry);





    }

    private void initializeRecyler() {




    }
}

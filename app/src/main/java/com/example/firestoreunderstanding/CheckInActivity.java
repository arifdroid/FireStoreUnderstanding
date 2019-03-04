package com.example.firestoreunderstanding;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CheckInActivity extends AppCompatActivity {

    private ListView listView;

    private List<String> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        listView = findViewById(R.id.listtViewID);

       // DocumentReference ss = MainActivity.reference.collection("new Collection").document();
        Log.i("checkk","1");

        populateListView();



//       if(userList!=null){
//           Log.i("checkk","3");
//
//
//
//           Log.i("checkk","4");
//
//
//           Log.i("checkk","5");
//
//
//
//    }


}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.example_menu, menu);


        return super.onCreateOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(item.getItemId()==R.id.menuID){
            if(user!=null){
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(CheckInActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        }


        return super.onOptionsItemSelected(item);
    }

    private void populateListView() {

        Log.i("checkk","99");

        MainActivity.checkFirst.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if(task!=null){

                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        Log.i("checkk",documentSnapshot.getString("phone"));

                        userList.add(documentSnapshot.getString("phone"));

                        Log.i("checkk","2");
                    }

                    ArrayAdapter adapter = new ArrayAdapter(CheckInActivity.this,android.R.layout.simple_list_item_1, userList);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);

                }
            }
        });
    }
}

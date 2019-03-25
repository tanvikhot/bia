package com.pixelround.myinsta;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;

    private EditText mSearchField;
    private RecyclerView searchResultsView;

    private CollectionReference mCompaniesDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // DB handle
        mCompaniesDatabase = FirebaseFirestore.getInstance().collection("companies");

        // Views
        mSearchField = (EditText) findViewById(R.id.search_text);
        mSearchField.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    firebaseCompanySearch(v.getText().toString());
                    return true;
            }
        });

        searchResultsView = (RecyclerView) findViewById(R.id.search_results);
        searchResultsView.setHasFixedSize(true);
        searchResultsView.setLayoutManager(new LinearLayoutManager(this));

    }

    private FirestoreRecyclerAdapter<Companies, CompaniesViewHolder> firebaseRecyclerAdapter;


    private void firebaseCompanySearch(String searchQuery) {
        Query query = mCompaniesDatabase.whereEqualTo("name", searchQuery);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle error
                    //...
                    return;
                }

                // Convert query snapshot to a list of chats
                List<Companies> chats = snapshot.toObjects(Companies.class);
                System.out.println(chats);

                // Update UI
                // ...
            }
        });

        FirestoreRecyclerOptions<Companies> firebaseRecyclerOptions = new FirestoreRecyclerOptions.Builder<Companies>().setQuery(query, Companies.class).build();


        firebaseRecyclerAdapter = new FirestoreRecyclerAdapter<Companies, CompaniesViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull CompaniesViewHolder holder, int position, @NonNull Companies model) {
                holder.setDetails(model.getName(), model.getFriendlinessScore(), model.getEqualityScore(), model.getFamilyScore());
            }

            @NonNull
            @Override
            public CompaniesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_layout, viewGroup, false);
                return new CompaniesViewHolder(view);
            }
        };

        searchResultsView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    // Company data holder view

    public static class CompaniesViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        public CompaniesViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDetails(String companyName, Double friendlinessScore, Double equalityScore, Double familyScore) {
            TextView companyNameTextView = (TextView) mView.findViewById(R.id.company_name);
            TextView friendlinessScoreTextView = (TextView) mView.findViewById(R.id.friendliness_score);
            TextView equalityScoreTextView = (TextView) mView.findViewById(R.id.equality_score);
            TextView familyScoreTextView = (TextView) mView.findViewById(R.id.family_score);

            companyNameTextView.setText(companyName);
            friendlinessScoreTextView.setText(String.format("%.2f%%", friendlinessScore));
            equalityScoreTextView.setText(String.format("%.2f%%", equalityScore));
            familyScoreTextView.setText(String.format("%.2f%%", familyScore));
        }
    }
}

package com.pixelround.myinsta;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;

    private EditText mSearchField;
    private RecyclerView searchResultsView;

    private DatabaseReference mCompaniesDatabase;

    private FirebaseRecyclerOptions<Companies> firebaseRecyclerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // DB handle
        mCompaniesDatabase = FirebaseDatabase.getInstance().getReference("companies");

        // Views
        mSearchField = (EditText) findViewById(R.id.search_text);
        mSearchField.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    firebaseCompanySearch();
                    return true;
                }
                return false;
            }
        });

        searchResultsView = (RecyclerView) findViewById(R.id.search_results);
        searchResultsView.setHasFixedSize(true);
        searchResultsView.setLayoutManager(new LinearLayoutManager(this));

        firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Companies>().setQuery(mCompaniesDatabase, Companies.class).build();
    }



    private void firebaseCompanySearch() {
        FirebaseRecyclerAdapter<Companies, CompaniesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Companies, CompaniesViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull CompaniesViewHolder holder, int position, @NonNull Companies model) {
                holder.setDetails(model.getName(), model.getFriendlinessScore(), model.getEqualityScore(), model.getFamilyScore());
            }

            @NonNull
            @Override
            public CompaniesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;
            }
        };

        searchResultsView.setAdapter(firebaseRecyclerAdapter);
    }

    // Company data holder view

    public class CompaniesViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        public CompaniesViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDetails(String companyName, Double friendlinessScore, Double equalityScore, Double familyScore) {
            TextView companyNameTextView = (TextView) findViewById(R.id.company_name);
            TextView friendlinessScoreTextView = (TextView) findViewById(R.id.friendliness_score);
            TextView equalityScoreTextView = (TextView) findViewById(R.id.equality_score);
            TextView familyScoreTextView = (TextView) findViewById(R.id.family_score);

            companyNameTextView.setText(companyName);
            friendlinessScoreTextView.setText(String.format("%.2f", friendlinessScore));
            equalityScoreTextView.setText(String.format("%.2f", equalityScore));
            familyScoreTextView.setText(String.format("%.2f", familyScore));
        }
    }
}

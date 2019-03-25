package com.pixelround.myinsta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

//    private EditText mSearchField;
    private RecyclerView searchResultsView;

    private CollectionReference mCompaniesDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // DB handle
        mCompaniesDatabase = FirebaseFirestore.getInstance().collection("companies");

        // Views

        searchResultsView = (RecyclerView) findViewById(R.id.search_results);
        searchResultsView.setHasFixedSize(true);
        searchResultsView.setLayoutManager(new LinearLayoutManager(this));

        firebaseCompanySearch("");

        setTitle("Companies");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private FirestoreRecyclerAdapter<Companies, CompaniesViewHolder> firebaseRecyclerAdapter;

    private void firebaseCompanySearch(String searchQuery) {
        Query query = mCompaniesDatabase.orderBy("name").startAt(searchQuery).endAt(searchQuery+"\uf8ff");

        FirestoreRecyclerOptions<Companies> firebaseRecyclerOptions = new FirestoreRecyclerOptions.Builder<Companies>().setQuery(query, Companies.class).build();


        firebaseRecyclerAdapter = new FirestoreRecyclerAdapter<Companies, CompaniesViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull CompaniesViewHolder holder, int position, @NonNull Companies model) {
                holder.setDetails(model);
            }

            @NonNull
            @Override
            public CompaniesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_layout, viewGroup, false);
                view.setOnClickListener(mOnClickListener);
                return new CompaniesViewHolder(view);
            }
        };

        searchResultsView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        firebaseCompanySearch(newText);
        return false;
    }

    // Company data holder view

    public static class CompaniesViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        public CompaniesViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDetails(Companies company) {
            mView.setTag(company.getName());
            TextView companyNameTextView = (TextView) mView.findViewById(R.id.company_name);
            TextView friendlinessScoreTextView = (TextView) mView.findViewById(R.id.friendliness_score);
            TextView equalityScoreTextView = (TextView) mView.findViewById(R.id.equality_score);
            TextView familyScoreTextView = (TextView) mView.findViewById(R.id.family_score);
            ImageView logoImageView = (ImageView) mView.findViewById(R.id.logo_image);

            companyNameTextView.setText(company.getName());
            friendlinessScoreTextView.setText(String.format("%.0f%%", company.getFriendlinessScore()));
            equalityScoreTextView.setText(String.format("%.0f%%", company.getEqualityScore()));
            familyScoreTextView.setText(String.format("%.0f%%", company.getFamilyScore()));

            if (company.getLogo() != null && company.getLogo().trim().length() > 0) {
                Picasso.get().load(company.getLogo()).into(logoImageView);
            }
        }
    }

    private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    public class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("name", (String) v.getTag());
            System.out.println(v.getTag());
            Intent jobTitlesIntent = new Intent(SearchActivity.this, JobTitlesActivity.class);
            jobTitlesIntent.putExtras(bundle);
            startActivity(jobTitlesIntent);
        }
    }
}

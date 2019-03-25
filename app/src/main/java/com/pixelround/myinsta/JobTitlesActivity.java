package com.pixelround.myinsta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;

public class JobTitlesActivity extends AppCompatActivity {

    private CollectionReference mCompaniesDatabase;

    private RecyclerView jobTitlesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_titles);
        System.out.println(getIntent().getExtras().getString("name"));

        mCompaniesDatabase = FirebaseFirestore.getInstance().collection("salaries");

        jobTitlesView = (RecyclerView) findViewById(R.id.job_titles_list);
        jobTitlesView.setHasFixedSize(true);
        jobTitlesView.setLayoutManager(new LinearLayoutManager(this));

        setUpJobTitles(getIntent().getExtras().getString("name"));

        setTitle("Departments at " + getIntent().getExtras().getString("name"));
    }

    private FirestoreRecyclerAdapter<Salary, JobTitlesViewHolder> firebaseRecyclerAdapter;

    private void setUpJobTitles(String companyName) {
        Query query = mCompaniesDatabase.whereEqualTo("name", companyName);
        FirestoreRecyclerOptions<Salary> firebaseRecyclerOptions = new FirestoreRecyclerOptions.Builder<Salary>().setQuery(query, Salary.class).build();


        firebaseRecyclerAdapter = new FirestoreRecyclerAdapter<Salary, JobTitlesViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull JobTitlesViewHolder holder, int position, @NonNull Salary model) {
                holder.setDetails(model);
            }

            @NonNull
            @Override
            public JobTitlesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.job_titles_layout, viewGroup, false);
                view.setOnClickListener(mOnClickListener);
                return new JobTitlesViewHolder(view);
            }
        };

        jobTitlesView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class JobTitlesViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        public JobTitlesViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDetails(Salary salary) {
            mView.setTag(salary);
            TextView jobTitleView = (TextView) mView.findViewById(R.id.job_title);
            jobTitleView.setText(salary.getJobType());
        }
    }

    private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    public class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Salary salary = (Salary) v.getTag();
            System.out.println(v.getTag());
            System.out.println(new Gson().toJson(salary));

            Bundle bundle = new Bundle();
            bundle.putString("salary", new Gson().toJson(salary));


            Intent salaryDetailsIntent = new Intent(JobTitlesActivity.this, SalaryDetailsActivity.class);
            salaryDetailsIntent.putExtras(bundle);
            startActivity(salaryDetailsIntent);
        }
    }

}

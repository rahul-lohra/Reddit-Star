package com.android.rahul_lohra.redditstar.Activity.Dummy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.rahul_lohra.redditstar.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DummyDashboardActivity extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView rv;
    AdapterReddit adapterReddit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddit);
        ButterKnife.bind(this);

        setFakeAdapter();
    }

    void setFakeAdapter()
    {
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("d");
        stringList.add("e");
        stringList.add("f");


        rv.setLayoutManager(new LinearLayoutManager(this));
        adapterReddit = new AdapterReddit(this,stringList);
        rv.setAdapter(adapterReddit);
    }

}

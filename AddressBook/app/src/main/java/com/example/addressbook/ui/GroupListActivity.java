package com.example.addressbook.ui;

import android.os.Bundle;

import com.example.addressbook.data.AddressBookApi;
import com.example.addressbook.data.Group;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.LayoutDirection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.addressbook.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupListActivity extends AppCompatActivity {

    private RecyclerView ui_groupListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ui_groupListRecyclerView = (RecyclerView)findViewById(R.id.group_recycler_view);
        ui_groupListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ui_groupListRecyclerView.setAdapter(new GroupListAdapter());
    }

    class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupCardHolder> {
        private List<Group> _groupList;

        public GroupListAdapter() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AddressBookApi service = retrofit.create(AddressBookApi.class);
            service.getGroupList().enqueue(new Callback<List<Group>>() {
                @Override
                public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                    Log.i("retrofit", "Download ok");
                    _groupList = response.body();
                    notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Group>> call, Throwable t) {

                }
            });
        }

        @NonNull
        @Override
        public GroupCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View cell = LayoutInflater.from(GroupListActivity.this).inflate(R.layout.group_cell, parent,false);
            GroupCardHolder holder = new GroupCardHolder(cell);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull GroupCardHolder holder, int position) {
            holder.layoutForGroup(_groupList.get(position));
        }

        @Override
        public int getItemCount() {
            int itemCount = 0;
            if (_groupList != null) {
                itemCount = _groupList.size();
            }
            return itemCount;
        }

        class GroupCardHolder extends RecyclerView.ViewHolder{

            private final TextView ui_groupTitle;

            public GroupCardHolder(@NonNull View itemView) {
                super(cell);
                ui_groupTitle = (TextView)cell.findViewById(R.id.group_title);
            }

            public void layoutForGroup(Group group) {
                ui_groupTitle.setText(group.getTitle());
            }
        }
    }

}

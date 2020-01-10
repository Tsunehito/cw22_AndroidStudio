package com.example.addressbook.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AddressBookApi {
    @GET("/groups")
    Call<List<Group>> getGroupList();

    @GET("/groups/{group}")
    Call<Group> getGroup(@Path("group") int groupId);
}

package com.example.maximebritto.addressbook.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by trainermac on 02/03/16.
 */
public interface AddressBookApi {
    @GET("/groups")
    Call<List<Group>> getGroupList();

    @GET("/groups/{group}")
    Call<Group> getGroup(@Path("group") int groupId);
}

package com.app.tasknitintyagi.util



import com.app.tasknitintyagi.model.FeedListModel
import retrofit2.Response
import retrofit2.http.*

interface RemoteDataSource {

    @GET(URLHelper.FEED_URL)
    suspend fun feedList(): Response<FeedListModel>
}
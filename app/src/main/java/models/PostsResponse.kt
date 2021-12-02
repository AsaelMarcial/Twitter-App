package models

import com.google.gson.annotations.SerializedName

data class PostsResponse(
    @SerializedName("posts")
    var posts: List<Post>
)

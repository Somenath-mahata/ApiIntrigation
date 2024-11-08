package com.example.apiintrigation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apiintrigation.adapter.PostsAdapter
import com.example.apiintrigation.database.AppDatabase
import com.example.apiintrigation.databinding.ActivityMainBinding
import com.example.apiintrigation.model.Post
import com.example.apiintrigation.retrofit.ApiClient
import com.example.apiintrigation.utils.isInternetAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var postsList = ArrayList<Post>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getDatabase(this)
        if (isInternetAvailable(this)) {
            apiCallGetPosts()
        } else {
            loadPostsFromDatabase()
        }
    }

    private fun apiCallGetPosts() {
        ApiClient.apiService.getPosts()
            .enqueue(object : Callback<ArrayList<Post>> {
                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    try {
                        if (response.code() == 500) {
                            Toast.makeText(
                                this@MainActivity,
                                "Internal Server Error.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (response.code() == 404) {
                            Toast.makeText(
                                this@MainActivity,
                                "Something went Wrong.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (response.isSuccessful) {
                                postsList = response.body()!!
                                savePostsToDatabase(postsList)
                                binding.rvPostList.apply {
                                    adapter = PostsAdapter(this@MainActivity, postsList)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    private fun savePostsToDatabase(posts: ArrayList<Post>) {
        CoroutineScope(Dispatchers.IO).launch {
            database.postDao().insertAll(posts)
        }
    }

    private fun loadPostsFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val savedPosts = database.postDao().getAllPosts()
            withContext(Dispatchers.Main) {
                displayPosts(ArrayList(savedPosts))
            }
        }
    }

    private fun displayPosts(posts: ArrayList<Post>) {
        binding.rvPostList.apply {
            adapter = PostsAdapter(this@MainActivity, posts)
        }
    }
}
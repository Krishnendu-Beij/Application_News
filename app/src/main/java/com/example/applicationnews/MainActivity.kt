package com.example.applicationnews

import android.os.Bundle
import android.util.Log
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.applicationsomething.Article
import com.example.applicationsomething.News
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var myadapter : NewsAdapter
    private var articles = mutableListOf<Article>()
    var pageNumber = 1
    var totalResults = -1
    var isScrolling = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myadapter = NewsAdapter(this,articles)
        recyclerViewNewsList.adapter = myadapter
        val myLayoutManager = LinearLayoutManager(this)
//        myLayoutManager.onItemsChanged(recyclerViewNewsList)
        recyclerViewNewsList.layoutManager = myLayoutManager
        recyclerViewNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("MainActivity","First Visible Item ${ myLayoutManager.findFirstVisibleItemPosition() }")
                Log.d("MainActivity","Total Cont ${ myLayoutManager.itemCount }")
                if (totalResults > myLayoutManager.itemCount && myLayoutManager.findFirstVisibleItemPosition() >= myLayoutManager.itemCount -5) {
                    pageNumber++
                    getNews()
                }
            }

        })

//        val myLayoutManager = StackLayoutManager()
//        myLayoutManager.horizontalLayout = false
//        recyclerViewNewsList.layoutManager = myLayoutManager
        getNews()

    }
    private fun getNews() {
        Log.d("MainActivity","Request Sent For $pageNumber")
        val news = NewsService.newsInstance.getHeadlines("in", pageNumber)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    Log.d("MainActivity", news.toString())
                    totalResults = news.totalResults
                    articles.addAll(news.articles)
                    myadapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("MainActivity", "Error in Fetching News", t)
            }
        })
    }
}
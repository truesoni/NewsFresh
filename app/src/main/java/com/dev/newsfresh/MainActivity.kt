package com.dev.newsfresh

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter :NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
  fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }

private fun fetchData(){
    val link = "https://newsdata.io/api/1/news?apikey=pub_3843cde1d24d75b515721d04b442203dedb&country=in"
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET,
        link,
        null,
        {
        val newsJsonArray = it.getJSONArray("results")
            val newsArray = ArrayList<News>()
            for(i in 0 until newsJsonArray.length()){
                val newsJsonObject = newsJsonArray.getJSONObject(i)
                val news = News(
                    newsJsonObject.getString("title"),
                    newsJsonObject.getString("creator"),
                    newsJsonObject.getString("link"),
                    newsJsonObject.getString("image_url"),
                    newsJsonObject.getString("pubDate"),
                    newsJsonObject.getString("source_id")
                )
                newsArray.add(news)
            }
            mAdapter.updateNews(newsArray)
        },
        { error ->
            // TODO: Handle error
        }
    )

// Access the RequestQueue through your singleton class.
    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.link))
    }
}
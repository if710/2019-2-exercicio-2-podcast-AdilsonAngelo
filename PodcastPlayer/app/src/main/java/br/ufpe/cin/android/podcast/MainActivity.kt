package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import br.ufpe.cin.android.podcast.DB.AppDataBase
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toast("oncreate")

        initRecyclerView()

        val database = db()
        doAsync {
            var items = fetchData()

            database.itemDao().deleteAllItems()
            items.forEach {
                database.itemDao().insertItems(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        toast("onresume")

        val database = db()
        doAsync {
            val items = database.itemDao().getAllItems()

            uiThread {
                recyclerAdapter.submitList(items)
            }
        }
    }

    private fun fetchData(): List<ItemFeed> {
        val xmlurl =
            "https://s3-us-west-1.amazonaws.com/podcasts.thepolyglotdeveloper.com/podcast.xml"

        val xml: String = URL(xmlurl).readText()
        return Parser.parse(xml)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)

            recyclerAdapter = RecyclerAdapter()
            adapter = recyclerAdapter
        }
    }

    private fun db(): AppDataBase {
        return Room.databaseBuilder(
            this.applicationContext,
            AppDataBase::class.java,
            "AppDB.db"
        ).build()
    }
}
package br.ufpe.cin.android.podcast

import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import br.ufpe.cin.android.podcast.Shared.STORAGE_PERMISSIONS
import br.ufpe.cin.android.podcast.Shared.WRITE_EXTERNAL_STORAGE_REQUEST
import br.ufpe.cin.android.podcast.Shared.isExternalStorageAvailable
import kotlinx.android.synthetic.main.itemlista.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.ItemHolder>() {
    private var items: List<ItemFeed> = ArrayList()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.itemlista,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ItemHolder, position: Int) {
        holder.bind(items[position])
    }


    fun submitList(itemsFeed: List<ItemFeed>) {
        items = itemsFeed
    }

    class ItemHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var itemfeed: ItemFeed? = null

        val itemTitle: TextView = v.item_title
        val itemAction: Button = v.item_action
        val itemDate: TextView = v.item_date

        fun bind(itemFeed: ItemFeed) {
            itemTitle.setText(itemFeed.title)
            itemDate.setText(itemFeed.pubDate)
            itemAction.setOnClickListener(View.OnClickListener {
                if (!isExternalStorageAvailable(it.context)) {
                    doAsync {
                        val content = URL(itemFeed.link).readBytes()
                        val file =
                            File(Environment.DIRECTORY_MUSIC + File.pathSeparator + itemFeed.title + ".mp3")
                        try {
                            val fileOutputStream = FileOutputStream(file)
                            fileOutputStream.write(content)
                            fileOutputStream.close()
                            uiThread { _ ->
                                Toast.makeText(it.context, "Download de ${itemFeed.title} concluido!", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                } else {

                }
            })
        }
    }

}
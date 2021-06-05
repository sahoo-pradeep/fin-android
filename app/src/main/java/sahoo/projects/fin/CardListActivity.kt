package sahoo.projects.fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_card_list.*
import sahoo.projects.fin.model.CardDetail

class CardListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        // get cardListItems from db
        val cardListItems = mutableListOf(
            CardDetail(),
            CardDetail()
        )

        val adapter = CardListAdapter(cardListItems)
        rvCardList.adapter = adapter
        rvCardList.layoutManager = LinearLayoutManager(this)

        // On adding a new cardListItem
        //adapter.notifyDataSetChanged() //Update all views - dont use it
        //adapter.notifyItemInserted(cardListItems.size - 1) //Update only last inserted view

        fabAdd.setOnClickListener {

            Intent(this, CardDetailActivity::class.java).also {
                it.putExtra("EXTRA_CARD_DETAIL", CardDetail())
                startActivity(it)
            }
        }
    }
}
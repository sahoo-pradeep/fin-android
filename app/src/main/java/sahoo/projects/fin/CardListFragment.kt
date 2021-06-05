package sahoo.projects.fin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_card_list.*
import sahoo.projects.fin.model.CardDetail

class CardListFragment : Fragment(R.layout.fragment_card_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get cardListItems from db
        val cardListItems = mutableListOf(
            CardDetail(),
            CardDetail(bank = "ICICI", cardHolderName = "Pradeep Kumar Sahoo")
        )

        val adapter = CardListAdapter(cardListItems)
        rvCardList.adapter = adapter
        rvCardList.layoutManager = LinearLayoutManager(view.context)



        // On adding a new cardListItem
        //adapter.notifyDataSetChanged() //Update all views - dont use it
        //adapter.notifyItemInserted(cardListItems.size - 1) //Update only last inserted view

        fabAdd.setOnClickListener {

            Intent(view.context, CardDetailActivity::class.java).also {
                it.putExtra("EXTRA_CARD_DETAIL", CardDetail())
                startActivity(it)
            }
        }
    }
}
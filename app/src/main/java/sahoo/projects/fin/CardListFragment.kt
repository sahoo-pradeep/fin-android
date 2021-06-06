package sahoo.projects.fin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_card_list.*
import kotlinx.coroutines.runBlocking
import sahoo.projects.fin.dao.CardDetailDao
import sahoo.projects.fin.dao.FinDatabase
import sahoo.projects.fin.model.CardDetail

class CardListFragment : Fragment(R.layout.fragment_card_list) {
    lateinit var dao: CardDetailDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = FinDatabase.getInstance(view.context).cardDetailDao

        fabAdd.setOnClickListener {
            Intent(view.context, CardDetailActivity::class.java).also {
                it.putExtra("EXTRA_CARD_DETAIL", CardDetail())
                it.putExtra("EXTRA_IS_NEW_CARD", true)
                startActivity(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val cardListItems = mutableListOf<CardDetail>()

        runBlocking {
            cardListItems.addAll(dao.getAllCardDetails())
        }

        val adapter = CardListAdapter(cardListItems)
        rvCardList.adapter = adapter
        rvCardList.layoutManager = LinearLayoutManager(requireView().context)
    }
}
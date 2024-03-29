package sahoo.projects.fin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_card_list_item.view.*
import sahoo.projects.fin.model.CardDetail

class CardListAdapter(
    private var cardDetails: List<CardDetail>,
) : RecyclerView.Adapter<CardListAdapter.CardDetailViewHolder>() {

    inner class CardDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_card_list_item, parent, false)
        return CardDetailViewHolder(view)
    }

    override fun onBindViewHolder(holderCardDetail: CardDetailViewHolder, position: Int) {
        holderCardDetail.itemView.apply {
            btnCardListItem.text = cardDetails[position].getDisplayName()
            btnCardListItem.setOnClickListener {
                Intent(it.context, CardDetailActivity::class.java).also { intent ->
                    intent.putExtra("EXTRA_CARD_DETAIL", cardDetails[position])
                    intent.putExtra("EXTRA_IS_NEW_CARD", false)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = cardDetails.size
}
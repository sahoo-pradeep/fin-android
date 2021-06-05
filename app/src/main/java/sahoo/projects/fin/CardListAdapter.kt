package sahoo.projects.fin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_card_list_item.view.*
import sahoo.projects.fin.model.CardDetail

class CardListAdapter(
    private var cardDetails: List<CardDetail>
) : RecyclerView.Adapter<CardListAdapter.CardListViewHolder>() {

    inner class CardListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_card_list_item, parent, false)
        return CardListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {
        val cardDetail = cardDetails[position]
        holder.itemView.apply {
            btnCardListItem.text = "${cardDetail.bank} ${cardDetail.cardType.verbose}: ${cardDetail.cardHolderName.split(" ").firstOrNull()}"
        }
    }

    override fun getItemCount(): Int = cardDetails.size


}
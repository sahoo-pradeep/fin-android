package sahoo.projects.fin

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_card_detail.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sahoo.projects.fin.dao.CardDetailDao
import sahoo.projects.fin.dao.FinDatabase
import sahoo.projects.fin.model.CardDetail

class CardDetailActivity : AppCompatActivity() {
    lateinit var dao: CardDetailDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
        dao = FinDatabase.getInstance(this).cardDetailDao

        var cardDetail = intent.getSerializableExtra("EXTRA_CARD_DETAIL") as CardDetail
        val isNewCard = intent.getSerializableExtra("EXTRA_IS_NEW_CARD") as Boolean
        populateFields(cardDetail)

        val cardTypes = CardDetail.CardType.verboseValues()
        val cardTypesAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cardTypes)
        actvCardType.setAdapter(cardTypesAdapter)

        tilCardNumber.setEndIconOnClickListener {
            val clipboard =  getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("text", etCardNumber.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
        }

        btnSave.setOnClickListener {
            cardDetail = updateCardDetail(cardDetail)

            runBlocking {
                dao.insert(cardDetail)
            }

            Toast.makeText(
                applicationContext, "${cardDetail.getDisplayName()} Saved",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        if (isNewCard) {
            btnDelete.visibility = View.GONE
        }

        btnDelete.setOnClickListener {
            lifecycleScope.launch {
                dao.delete(cardDetail)
            }
            Toast.makeText(
                applicationContext,
                "${cardDetail.getDisplayName()} Deleted",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun populateFields(cardDetail: CardDetail) {
        etBank.setText(cardDetail.bank)
        actvCardType.setText(cardDetail.cardType?.verbose)
        etCardNumber.setText(cardDetail.cardNumber)
        etCvv.setText(cardDetail.cvv)
        etCardHolderName.setText(cardDetail.cardHolderName)
        etExpiryMonth.setText(cardDetail.expiryMonth)
        etExpiryYear.setText(cardDetail.expiryYear)
        //add grid
    }

    private fun updateCardDetail(cardDetail: CardDetail): CardDetail = cardDetail.copy(
        bank = etBank.text.toString(),
        cardType = CardDetail.CardType.getCardType(actvCardType.text.toString()),
        cardNumber = etCardNumber.text.toString(),
        cvv = etCvv.text.toString(),
        cardHolderName = etCardHolderName.text.toString(),
        expiryMonth = etExpiryMonth.text.toString(),
        expiryYear = etExpiryYear.text.toString()
    )
}
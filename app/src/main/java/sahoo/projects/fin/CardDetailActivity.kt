package sahoo.projects.fin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_card_detail.*
import sahoo.projects.fin.model.CardDetail

class CardDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        val cardDetail = intent.getSerializableExtra("EXTRA_CARD_DETAIL") as CardDetail
        populateFields(cardDetail)

        // Note: If drop down items are not visible, move below lines to onResume()
        val cardTypes = CardDetail.CardType.verboseValues()
        val cardTypesAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cardTypes)
        actvCardType.setAdapter(cardTypesAdapter)

        btnSave.setOnClickListener {
            Toast.makeText(applicationContext, "${cardDetail.bank} ${cardDetail.cardType.verbose} Saved", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnDelete.setOnClickListener {
            Toast.makeText(applicationContext, "${cardDetail.bank} ${cardDetail.cardType.verbose} Deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun populateFields(cardDetail: CardDetail) {
        etBank.setText(cardDetail.bank)
        actvCardType.setText(cardDetail.cardType.verbose)
        etCardNumber.setText(cardDetail.cardNumber)
        etCvv.setText(cardDetail.cvv)
        etCardHolderName.setText(cardDetail.cardHolderName)
        etExpiryMonth.setText(cardDetail.expiryMonth)
        etExpiryYear.setText(cardDetail.expiryYear)
    }
}
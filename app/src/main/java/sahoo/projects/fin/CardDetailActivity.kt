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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_card_detail.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sahoo.projects.fin.dao.CardDetailDao
import sahoo.projects.fin.dao.FinDatabase
import sahoo.projects.fin.model.CardDetail
import sahoo.projects.fin.util.SecurityUtil

class CardDetailActivity : AppCompatActivity() {
    lateinit var dao: CardDetailDao
    private var paused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isNewCard = intent.getSerializableExtra("EXTRA_IS_NEW_CARD") as Boolean
        if (isNewCard) {
            showCardDetails()
            btnDelete.visibility = View.GONE
        } else {
            authAndShowCardDetails()
        }
    }

    override fun onResume() {
        super.onResume()
        if (paused) {
            authAndShowCardDetails()
            paused = false
        }
    }

    override fun onPause() {
        super.onPause()
        paused = true
    }

    private fun authAndShowCardDetails() {
        //Biometrics with white background
        setContentView(R.layout.fragment_white_bg)
        SecurityUtil.authenticate(this, this) { showCardDetails() }
    }

    private fun showCardDetails() {
        setContentView(R.layout.activity_card_detail)
        dao = FinDatabase.getInstance(this).cardDetailDao

        val cardDetail = intent.getSerializableExtra("EXTRA_CARD_DETAIL") as CardDetail

        populateView(cardDetail)

        val cardTypes = CardDetail.CardType.verboseValues()
        val cardTypesAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cardTypes)
        actvCardType.setAdapter(cardTypesAdapter)

        tilCardNumber.setEndIconOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("text", etCardNumber.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied ${etCardNumber.text}", Toast.LENGTH_SHORT).show()
        }

        btnSave.setOnClickListener { cardDetail.saveToDB() }

        btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Delete Card")
                .setMessage("Are you sure you want to delete ${cardDetail.bank} ${cardDetail.cardType.verbose}")
                .setPositiveButton("Delete") { _, _ -> cardDetail.deleteFromDB() }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun populateView(cardDetail: CardDetail) {
        etBank.setText(cardDetail.bank)
        actvCardType.setText(cardDetail.cardType.verbose)
        etCardNumber.setText(cardDetail.cardNumber)
        etCvv.setText(cardDetail.cvv)
        etCardHolderName.setText(cardDetail.cardHolderName)
        etExpiryMonth.setText(cardDetail.expiryMonth)
        etExpiryYear.setText(cardDetail.expiryYear)
        //todo: add grid
    }

    private fun CardDetail.updated() = this.copy(
        bank = etBank.text.toString(),
        cardType = CardDetail.CardType.getCardType(actvCardType.text.toString()),
        cardNumber = etCardNumber.text.toString(),
        cvv = etCvv.text.toString(),
        cardHolderName = etCardHolderName.text.toString(),
        expiryMonth = etExpiryMonth.text.toString(),
        expiryYear = etExpiryYear.text.toString()
    )

    private fun CardDetail.saveToDB() {
        val cardDetail = this.updated()
        runBlocking {
            dao.insert(cardDetail)
        }

        Toast.makeText(
            applicationContext, "${cardDetail.getDisplayName()} Saved",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    private fun CardDetail.deleteFromDB() {
        val cardDetail = this
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
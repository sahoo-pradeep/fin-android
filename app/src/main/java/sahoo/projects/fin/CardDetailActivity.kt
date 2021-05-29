package sahoo.projects.fin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_card_detail.*

class CardDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        // If drop down items are not visible, move below lines to onResume()
        val cardTypes = resources.getStringArray(R.array.card_types)
        val cardTypesAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_card_type, cardTypes)
        actvCardType.setAdapter(cardTypesAdapter)

        btnSave.setOnClickListener {
            Toast.makeText(applicationContext, "Saved!", Toast.LENGTH_SHORT).show()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
package sahoo.projects.fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_card_list.*

class CardListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        fabAdd.setOnClickListener {
            Intent(this, CardDetailActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}
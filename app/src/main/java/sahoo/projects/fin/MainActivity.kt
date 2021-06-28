package sahoo.projects.fin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import sahoo.projects.fin.util.SecurityUtil

class MainActivity : AppCompatActivity() {
    private val cardListFragment = CardListFragment()
    private val bankFragment = BankFragment()
    private val whiteBgFragment = WhiteBgFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCurrentFragment(whiteBgFragment)

        // Biometric
        SecurityUtil.authenticate(this, this) {setCurrentFragment(cardListFragment)}

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miCard -> setCurrentFragment(cardListFragment)
                R.id.miBank -> setCurrentFragment(bankFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}
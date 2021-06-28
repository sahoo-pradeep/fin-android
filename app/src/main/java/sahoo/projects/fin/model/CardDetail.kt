package sahoo.projects.fin.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import sahoo.projects.fin.util.Formatter.twoDigits
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Entity
data class CardDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val bank: String? = null,
    val cardType: CardType = CardType.CREDIT,
    val cardNumber: String? = null,
    val cvv: String? = null,
    val cardHolderName: String? = null,
    val expiryMonth: String? = LocalDate.now().month.twoDigits(),
    val expiryYear: String? = LocalDate.now().year.toString(),
    @Embedded(prefix = "grid_")
    val grid: CardGrid? = null

) : Serializable {
    enum class CardType(val verbose: String, val short: String) {
        DEBIT("Debit Card", "DC"),
        CREDIT("Credit Card", "CC");

        companion object {
            fun getCardType(cardTypeVerbose: String): CardType {
                return values().first { it.verbose == cardTypeVerbose }
            }

            fun verboseValues(): Array<String> {
                return values().map { it.verbose }.toTypedArray()
            }
        }
    }

    fun getDisplayName(): String =
        StringJoiner(" ")
            .add(cardHolderName?.split(" ")?.firstOrNull())
            .add("::")
            .add(bank)
            .add("(${cardType.short})")
            .add("::")
            .add(cardNumber?.takeLast(4))
            .toString()
}
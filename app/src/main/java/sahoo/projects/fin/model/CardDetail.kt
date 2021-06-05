package sahoo.projects.fin.model

import sahoo.projects.fin.util.Formatter.twoDigits
import java.io.Serializable
import java.time.LocalDate

class CardDetail(
    val bank: String = "HDFC",
    val cardType: CardType = CardType.CREDIT,
    val cardNumber: String = "1234",
    val cvv: String = "123",
    val cardHolderName: String = "Pradeep",
    val expiryMonth: String = LocalDate.now().month.twoDigits(),
    val expiryYear: String = LocalDate.now().year.toString()
) : Serializable {
    enum class CardType(val verbose: String) {
        DEBIT("Debit Card"),
        CREDIT("Credit Card");

        companion object {
            fun getCardType(cardTypeVerbose: String): CardType? {
                return values().firstOrNull { it.verbose == cardTypeVerbose }
            }

            fun verboseValues(): Array<String> {
                return values().map { it.verbose }.toTypedArray()
            }
        }
    }
}
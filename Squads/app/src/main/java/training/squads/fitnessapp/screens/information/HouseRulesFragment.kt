package training.squads.fitnessapp.screens.information

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import training.squads.fitnessapp.R


/**
 * A [Fragment] instance
 * Used to display the mission statement of fitness center Squads
 * @property houseRulesText The text set the house rules
 */
class HouseRulesFragment: Fragment() {

    private lateinit var houseRulesText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_house_rules, container, false)

        houseRulesText = view.findViewById(R.id.house_rules_list)

        setHouseRulesText()

        return view
    }

    private fun setHouseRulesText() {
        val houseRules = """
            Een lidmaatschap is persoonlijk en kan niet worden doorgegeven aan anderen.
            De trainer/toezichter heeft het recht de toegang te weigeren of te ontzeggen aan leden of bezoekers als hun gedrag hiertoe aanleiding geeft.
            Deelname aan de trainingen gebeurt op eigen verantwoordelijkheid. Respecteer je eigen grenzen en consulteer eventueel een (medisch) specialist. Je bent verantwoordelijk voor je eigen welzijn. In geval van medische klachten, gelieve het advies van uw arts op te volgen.
            SQUADS is niet verantwoordelijk voor verlies, diefstal of ongevallen.
            Er is een totaalverbod op roken en het gebruik en/of verspreiden van verboden middelen binnen de sportruimte.
            Verbaal en/ of lichamelijk geweld en ongewenste intimiteiten worden niet getolereerd.
            Het is verboden de sportruimte te betreden met schoenen die buiten worden gedragen. Draag steeds correcte, schone en veilige sportkledij.
            Het is verplicht het materiaal te reinigen na elk gebruik. Alle materialen dienen met respect behandeld te worden en dienen na gebruik op de voorziene plaats worden opgeruimd.
            Gelieve niet te telefoneren tijdens de les.
            Kom op tijd zodat de les ook tijdig kan starten. Wanneer je door omstandigheden niet aanwezig kan zijn, gelieve dit ten laatste 1u op voorhand te laten weten via telefoon / Whatsapp. Anders zal een beurt worden afgetrokken van de beurtenkaart.
        """.trimIndent()

        val arr = houseRules.split("\n".toRegex()).toTypedArray()

        val ssb = SpannableStringBuilder()
        for (i in arr.indices) {
            val line = arr[i]
            val ss = SpannableString(line)
            ss.setSpan(BulletSpan(10), 0, line.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            ssb.append(ss)

            if (i + 1 < arr.size) ssb.append("\n")
        }

        houseRulesText.text = ssb
    }
}
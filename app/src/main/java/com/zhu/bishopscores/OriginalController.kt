package com.zhu.bishopscores

import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import org.w3c.dom.Text

class OriginalController(private val calc_view: View): CalcController(calc_view){

    var value = 0

    private val dilationGroup: RadioGroup = calc_view.findViewById(R.id.dilation_group)
    private val effacementGroup: RadioGroup = calc_view.findViewById(R.id.effacement_group)
    private val stationGroup: RadioGroup = calc_view.findViewById(R.id.station_group)
    private val consistencyGroup: RadioGroup = calc_view.findViewById(R.id.consistency_group)
    private val positionGroup: RadioGroup = calc_view.findViewById(R.id.position_group)

    private val scoreTextView: TextView = calc_view.findViewById(R.id.score_tv)
    private val successTextView: TextView = calc_view.findViewById(R.id.success_tv)
    private val plusTextView: TextView = calc_view.findViewById(R.id.calc_plus_note)

    private val checkedChanged = RadioGroup.OnCheckedChangeListener { _, _ ->
        updateValue()
        updateView()
    }

    init {
        dilationGroup.setOnCheckedChangeListener(checkedChanged)
        effacementGroup.setOnCheckedChangeListener(checkedChanged)
        stationGroup.setOnCheckedChangeListener(checkedChanged)
        consistencyGroup.setOnCheckedChangeListener(checkedChanged)
        positionGroup.setOnCheckedChangeListener(checkedChanged)

        scoreTextView.text = "0"
    }

    override fun updateView() {
        scoreTextView.text = value.toString()

        successTextView.text = when {

            value <= 6 -> {
                plusTextView.text = ""
                plusTextView.visibility = View.GONE
                context.resources.getString(R.string.success_unfavorable)
            }

            value == 7 || value == 8 -> {
                plusTextView.text = ""
                plusTextView.visibility = View.GONE
                context.resources.getString(R.string.success_favorable)
            }


            value >= 9 -> {
                plusTextView.text = context.resources.getString(R.string.success_favorable_plus_text)
                plusTextView.visibility = View.VISIBLE
                context.resources.getString(R.string.success_favorable_plus)
            }

            else -> {
                plusTextView.text = ""
                plusTextView.visibility = View.GONE
                context.resources.getString(R.string.success_unknown)
            }
        }

    }

    override fun updateValue() {
        value = dilationGroup.checkedIndex() +
                effacementGroup.checkedIndex() +
                stationGroup.checkedIndex() +
                consistencyGroup.checkedIndex() +
                positionGroup.checkedIndex()
    }
}
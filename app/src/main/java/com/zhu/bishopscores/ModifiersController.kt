package com.zhu.bishopscores

import android.view.View
import android.widget.RadioGroup
import android.widget.TextView

class ModifiersController(private val calc_view: View): CalcController(calc_view){

    var value = -1

    private val dilationGroup: RadioGroup = calc_view.findViewById(R.id.dilation_group)
    private val effacementGroup: RadioGroup = calc_view.findViewById(R.id.effacement_group)
    private val stationGroup: RadioGroup = calc_view.findViewById(R.id.station_group)
    private val consistencyGroup: RadioGroup = calc_view.findViewById(R.id.consistency_group)
    private val positionGroup: RadioGroup = calc_view.findViewById(R.id.position_group)

    private val priorVagGroup: RadioGroup = calc_view.findViewById(R.id.prior_vag_group)
    private val preeclGroup: RadioGroup = calc_view.findViewById(R.id.preecl_group)
    private val electiveGroup: RadioGroup = calc_view.findViewById(R.id.elective_group)
    private val promGroup: RadioGroup = calc_view.findViewById(R.id.prom_group)
    private val postdatesGroup: RadioGroup = calc_view.findViewById(R.id.postdates_group)

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

        priorVagGroup.setOnCheckedChangeListener(checkedChanged)
        preeclGroup.setOnCheckedChangeListener(checkedChanged)
        electiveGroup.setOnCheckedChangeListener(checkedChanged)
        promGroup.setOnCheckedChangeListener(checkedChanged)
        postdatesGroup.setOnCheckedChangeListener(checkedChanged)

        updateView()
    }

    override fun updateView() {
        scoreTextView.text = value.toString()

        successTextView.text = when {

            value <= 4 -> {
                plusTextView.text = context.resources.getString(R.string.success_modifiers_plus_text)
                plusTextView.visibility = View.VISIBLE
                context.resources.getString(R.string.success_unfavorable_plus)
            }

            value in 5..9 -> {
                plusTextView.text = context.resources.getString(R.string.success_modifiers_plus_text)
                plusTextView.visibility = View.VISIBLE
                context.resources.getString(R.string.success_intermediate_plus)
            }


            value >= 10 -> {
                plusTextView.text = context.resources.getString(R.string.success_modifiers_plus_text)
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

        when(priorVagGroup.checkedIndex()) {
            0 -> value -= 1
            else -> value += priorVagGroup.checkedIndex()
        }

        if(preeclGroup.checkedIndex() == 0) {
            value += 1
        }

        if(promGroup.checkedIndex() == 0) {
            value -= 1
        }

        if(electiveGroup.checkedIndex() == 0) {
            value += 1
        }

        if(postdatesGroup.checkedIndex() == 0) {
            value -= 1
        }
    }
}
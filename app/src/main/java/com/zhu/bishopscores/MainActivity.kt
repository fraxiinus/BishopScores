package com.zhu.bishopscores

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bishop_calculator_original.*
import kotlinx.android.synthetic.main.bishop_calculator_original.calculator_original
import kotlinx.android.synthetic.main.bishop_calculator_simplified.*

class MainActivity : AppCompatActivity() {

    private lateinit var contentView: ViewFlipper
    private lateinit var originalController: OriginalController
    private lateinit var simplifiedController: SimplifiedController

    private val mSpinnerListener = object: AdapterView.OnItemSelectedListener  {

        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            contentView.displayedChild = pos
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.control_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = mSpinnerListener
        contentView = mainContent

        originalController = OriginalController(calculator_original)
        simplifiedController = SimplifiedController(calculator_simplified)

        setSupportActionBar(mainToolbar)
    }
}

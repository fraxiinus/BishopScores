package com.zhu.bishopscores

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bishop_calculator_original.calculator_original
import kotlinx.android.synthetic.main.bishop_calculator_simplified.*

class MainActivity : AppCompatActivity() {

    private lateinit var contentView: ViewFlipper
    private lateinit var originalController: OriginalController
    private lateinit var simplifiedController: SimplifiedController
    private lateinit var toolbar: Toolbar

    private val mSpinnerListener = object: AdapterView.OnItemSelectedListener  {

        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            contentView.displayedChild = pos
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
        }
    }

    private val mAboutButtonListener = View.OnClickListener {
        when(contentView.displayedChild) {
            // Original
            0 -> {
                val ft = this.supportFragmentManager.beginTransaction()
                ft.replace(R.id.overlay_placeholder, InformationFragment.newInstance(resources.getString(R.string.info_original_score_title), resources.getString(R.string.info_original_score_body)))
                ft.addToBackStack(null)
                ft.commit()
            }

            // Simplified
            1 -> {
                val ft = this.supportFragmentManager.beginTransaction()
                ft.replace(R.id.overlay_placeholder, InformationFragment.newInstance(resources.getString(R.string.info_simplified_score_title), resources.getString(R.string.info_simplified_score_body)))
                ft.addToBackStack(null)
                ft.commit()
            }

            // Modifiers
            2 -> {

            }
        }
    }

    private val mDisclaimerButtonListener = View.OnClickListener {
        val ft = this.supportFragmentManager.beginTransaction()
        ft.replace(R.id.overlay_placeholder, InformationFragment.newInstance(resources.getString(R.string.info_disclaimer_title), resources.getString(R.string.info_disclaimer_body)))
        ft.addToBackStack(null)
        ft.commit()
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

        toolbar = mainToolbar

        disclaimer_action.setOnClickListener(mDisclaimerButtonListener)
        info_button.setOnClickListener(mAboutButtonListener)

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.about_bishop_action -> {
            val ft = this.supportFragmentManager.beginTransaction()
            ft.replace(R.id.overlay_placeholder, InformationFragment.newInstance(resources.getString(R.string.info_about_bishop_title), resources.getString(R.string.info_about_bishop_body)))
            ft.addToBackStack(null)
            ft.commit()
            true
        }

        R.id.about_app_action -> {
            val ft = this.supportFragmentManager.beginTransaction()
            ft.replace(R.id.overlay_placeholder, InformationFragment.newInstance(resources.getString(R.string.info_about_app_title), resources.getString(R.string.info_about_app_body)))
            ft.addToBackStack(null)
            ft.commit()
            true
        }

        R.id.references_action -> {
            val ft = this.supportFragmentManager.beginTransaction()
            ft.replace(R.id.overlay_placeholder, InformationFragment.newInstance(resources.getString(R.string.info_references_title), resources.getString(R.string.info_references_body)))
            ft.addToBackStack(null)
            ft.commit()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}

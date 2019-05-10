package com.zhu.bishopscores

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bishop_calculator_modifiers.*
import kotlinx.android.synthetic.main.bishop_calculator_original.calculator_original
import kotlinx.android.synthetic.main.bishop_calculator_simplified.*

class MainActivity : AppCompatActivity() {

    private lateinit var contentView: ViewFlipper
    private lateinit var originalController: OriginalController
    private lateinit var simplifiedController: SimplifiedController
    private lateinit var modifiersController: ModifiersController
    private lateinit var toolbar: Toolbar

    private val mSpinnerListener = object: AdapterView.OnItemSelectedListener  {

        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            contentView.displayedChild = pos

            if(pos == 2) {
                // Show warning sign
                info_button.text = resources.getString(R.string.info_button_warning)
            } else {
                info_button.text = resources.getString(R.string.info_button)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
        }
    }

    private val mAboutButtonListener = View.OnClickListener {
        when(contentView.displayedChild) {
            // Original
            0 -> {
                replaceFragment(
                    InformationFragment.newInstance(
                        resources.getString(R.string.info_original_score_title),
                        resources.getString(R.string.info_original_score_body)
                    )
                )

            }

            // Simplified
            1 -> {
                replaceFragment(
                    InformationFragment.newInstance(
                        resources.getString(R.string.info_simplified_score_title),
                        resources.getString(R.string.info_simplified_score_body)
                    )
                )
            }

            // Modifiers
            2 -> {
                replaceFragment(
                    InformationFragment.newInstance(
                        resources.getString(R.string.info_modifiers_score_title),
                        resources.getString(R.string.info_modifiers_score_body)
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = main_toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val spinner: Spinner = findViewById(R.id.control_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_array,
            R.layout.spinner_main_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = mSpinnerListener
        contentView = main_content

        originalController = OriginalController(calculator_original)
        simplifiedController = SimplifiedController(calculator_simplified)
        modifiersController = ModifiersController(calculator_modifiers)

        info_button.setOnClickListener(mAboutButtonListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.disclaimer_action -> {
            replaceFragment(
                InformationFragment.newInstance(
                    resources.getString(R.string.info_disclaimer_title),
                    resources.getString(R.string.info_disclaimer_body)
                )
            )
            true
        }

        R.id.about_bishop_action -> {
            replaceFragment(
                InformationFragment.newInstance(
                    resources.getString(R.string.info_about_bishop_title),
                    resources.getString(R.string.info_about_bishop_body)
                )
            )
            true
        }

        R.id.about_app_action -> {
            replaceFragment(
                InformationFragment.newInstance(
                    resources.getString(R.string.info_about_app_title),
                    resources.getString(R.string.info_about_app_body)
                )
            )
            true
        }

        R.id.references_action -> {
            replaceFragment(
                InformationFragment.newInstance(
                    resources.getString(R.string.info_references_title),
                    resources.getString(R.string.info_references_body)
                )
            )
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val ft = this.supportFragmentManager.beginTransaction()
        ft.replace(R.id.overlay_placeholder, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }
}

package com.zhu.bishopscores

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
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
    private lateinit var drawer: DrawerLayout
    private lateinit var prefs: SharedPreferences

    private var fragmentVisible = false

    private val mNavigationViewItemClickListener = NavigationView.OnNavigationItemSelectedListener { item ->

        when(item.groupId) {
            R.id.calc_nav_items -> {
                when(item.itemId) {
                    R.id.original_calc_action -> {
                        contentView.displayedChild = 0
                    }

                    R.id.simplified_calc_action -> {
                        contentView.displayedChild = 1
                    }

                    R.id.modifiers_calc_action -> {
                        contentView.displayedChild = 2
                    }
                }
            }
            else -> {
                when (item.itemId) {
                    R.id.disclaimer_action -> {
                        replaceFragment(
                            InformationFragment.newInstance(
                                resources.getString(R.string.info_disclaimer_title),
                                resources.getString(R.string.info_disclaimer_body)
                            )
                        )
                    }

                    R.id.about_bishop_action -> {
                        replaceFragment(
                            InformationFragment.newInstance(
                                resources.getString(R.string.info_about_bishop_title),
                                resources.getString(R.string.info_about_bishop_body)
                            )
                        )
                    }

                    R.id.references_action -> {
                        replaceFragment(
                            InformationFragment.newInstance(
                                resources.getString(R.string.info_references_title),
                                resources.getString(R.string.info_references_body)
                            )
                        )

                    }

                    R.id.about_app_action -> {
                        replaceFragment(
                            InformationFragment.newInstance(
                                resources.getString(R.string.info_about_app_title),
                                resources.getString(R.string.info_about_app_body)
                            )
                        )
                    }
                }
            }
        }

        if(drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START)
        }

        true
    }

    private val mToolbarNavigationOnClickListener = View.OnClickListener {
        if(!drawer.isDrawerOpen(Gravity.START)) {
            drawer.openDrawer(Gravity.START)
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar.setNavigationOnClickListener(mToolbarNavigationOnClickListener)

        drawer = drawer_layout

        nav_menu.setCheckedItem(R.id.original_calc_action)
        nav_menu.setNavigationItemSelectedListener(mNavigationViewItemClickListener)

        contentView = main_content

        originalController = OriginalController(calculator_original)
        simplifiedController = SimplifiedController(calculator_simplified)
        modifiersController = ModifiersController(calculator_modifiers)

        info_button.setOnClickListener(mAboutButtonListener)

        prefs = this.getSharedPreferences("com.zhu.bishopscores", Context.MODE_PRIVATE)
    }

    override fun onPause() {
        prefs.edit().putFloat("com.zhu.bishopscores.fontsizemulti", (application as ApplicationData).fontSizeMultiplier).apply()

        super.onPause()
    }

    override fun onResume() {
        (application as ApplicationData).fontSizeMultiplier = prefs.getFloat("com.zhu.bishopscores.fontsizemulti", 1f)

        super.onResume()
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if(fragmentVisible) {
            supportFragmentManager.popBackStack()
        } else {
            fragmentVisible = true
        }

        val ft = this.supportFragmentManager.beginTransaction()
        ft.replace(R.id.overlay_placeholder, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }
}

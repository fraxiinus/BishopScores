package com.zhu.bishopscores

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.WindowManager
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
            // If selected item is a calculator
            R.id.calc_nav_items -> {
                // update content to selected calculator
                when(item.itemId) {
                    R.id.original_calc_action -> {
                        contentView.displayedChild = 0
                        updateDefaultCalculator(0)
                        updateCalculatorViews(0)
                    }

                    R.id.simplified_calc_action -> {
                        contentView.displayedChild = 1
                        updateDefaultCalculator(1)
                        updateCalculatorViews(1)
                    }

                    R.id.modifiers_calc_action -> {
                        contentView.displayedChild = 2
                        updateDefaultCalculator(2)
                        updateCalculatorViews(2)
                    }
                }

                if(fragmentVisible) {
                    supportFragmentManager.popBackStack()
                }
            }
            // Otherwise, they selected a information fragment
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

        // Close the drawer when item is selected
        if(drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START)
        }

        true
    }

    private val mToolbarNavigationOnClickListener = View.OnClickListener {
        // If drawer is not open, open the drawer
        if(!drawer.isDrawerOpen(Gravity.START)) {
            drawer.openDrawer(Gravity.START)
        }
    }

    private val mAboutButtonListener = View.OnClickListener {
        // Display information fragment based on selected calculator
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

        // Setup the toolbar
        toolbar = main_toolbar
        setSupportActionBar(toolbar)
        // Enable the navigation button (hamburger menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        supportActionBar?.setHomeButtonEnabled(true)
        // Attach the listener function to the nav button
        toolbar.setNavigationOnClickListener(mToolbarNavigationOnClickListener)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.primaryDarkColor)
        }

        // Attach the listener function to the navigation drawer items
        nav_menu.setNavigationItemSelectedListener(mNavigationViewItemClickListener)

        // Attach the listener function for the about calculator button
        info_button.setOnClickListener(mAboutButtonListener)

        // Assign the drawer object
        drawer = drawer_layout
        // Assign the view spinner object
        contentView = main_content
        // Assign the shared preferences
        prefs = this.getSharedPreferences("com.zhu.bishopscores", Context.MODE_PRIVATE)

        // Create and assign the calculator controllers
        originalController = OriginalController(calculator_original)
        simplifiedController = SimplifiedController(calculator_simplified)
        modifiersController = ModifiersController(calculator_modifiers)
    }

    override fun onPause() {
        // Put global values into shared preferences
        prefs.edit().putInt("com.zhu.bishopscores.default.calculator", (application as ApplicationData).preferredCalculator).apply()

        super.onPause()
    }

    override fun onResume() {
        // Take values from shared preferences and put into global values
        (application as ApplicationData).preferredCalculator = prefs.getInt("com.zhu.bishopscores.default.calculator", 0)

        // set the calculator display to the saved index
        contentView.displayedChild = (application as ApplicationData).preferredCalculator

        // Set the selected calculator in the side menu and update texts in the app
        when((application as ApplicationData).preferredCalculator) {
            0 -> {
                nav_menu.setCheckedItem(R.id.original_calc_action)
                updateCalculatorViews(0)
            }

            1 -> {
                nav_menu.setCheckedItem(R.id.simplified_calc_action)
                updateCalculatorViews(1)
            }

            2 -> {
                nav_menu.setCheckedItem(R.id.modifiers_calc_action)
                updateCalculatorViews(2)
            }
        }

        super.onResume()
    }

    override fun onBackPressed() {
        // If drawer is open, close it, otherwise do default action
        if(drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START)
        } else {
            super.onBackPressed()
        }
    }


    private fun updateCalculatorViews(index: Int) {
        // Update toolbar title and info button text based on current calculator
        when (index) {
            0 -> {
                supportActionBar?.title = resources.getString(R.string.calc_name_original)
                info_button.text = resources.getString(R.string.info_button)
            }

            1 -> {
                supportActionBar?.title = resources.getString(R.string.calc_name_simplified)
                info_button.text = resources.getString(R.string.info_button)
            }

            2 -> {
                supportActionBar?.title = resources.getString(R.string.calc_name_modifiers)
                info_button.text = resources.getString(R.string.info_button_warning)
            }
        }
    }

    private fun updateDefaultCalculator(index: Int) {
        (application as ApplicationData).preferredCalculator = index
    }

    private fun replaceFragment(fragment: Fragment) {
        // If a fragment is already on screen,
        if(fragmentVisible) {
            // remove it
            supportFragmentManager.popBackStack()
        } else {
            // otherwise, remember we are putting a fragment on screen
            fragmentVisible = true
        }

        // Create fragment
        val ft = this.supportFragmentManager.beginTransaction()
        ft.replace(R.id.overlay_placeholder, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }
}

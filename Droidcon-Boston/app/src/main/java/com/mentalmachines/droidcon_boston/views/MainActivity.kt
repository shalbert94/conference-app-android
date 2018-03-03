package com.mentalmachines.droidcon_boston.views

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.mentalmachines.droidcon_boston.R
import com.mentalmachines.droidcon_boston.views.agenda.AgendaFragment
import com.mentalmachines.droidcon_boston.views.social.SocialFragment
import kotlinx.android.synthetic.main.main_activity.drawer_layout
import kotlinx.android.synthetic.main.main_activity.navView
import kotlinx.android.synthetic.main.main_activity.toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initNavDrawerToggle()

        replaceFragment(getString(R.string.str_agenda))
        navView.setCheckedItem(R.id.nav_agenda)
    }


    private fun initNavDrawerToggle() {

        setSupportActionBar(toolbar)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer_layout,
                R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(actionBarDrawerToggle)

        navView.setNavigationItemSelectedListener { item ->

            navView.setCheckedItem(item.itemId)

            //Closing drawer on item click
            drawer_layout.closeDrawers()

            when (item.itemId) {
            // Respond to the action bar's Up/Home button
                android.R.id.home -> if (fragmentManager.backStackEntryCount > 0) {
                    fragmentManager.popBackStack()
                } else if (fragmentManager.backStackEntryCount == 1) {
                    // to avoid looping below on initScreen
                    super.onBackPressed()
                    finish()
                }
                R.id.nav_agenda -> replaceFragment(getString(R.string.str_agenda))
                R.id.nav_my_schedule -> replaceFragment(getString(R.string.str_my_schedule))
                R.id.nav_faq -> replaceFragment(getString(R.string.str_faq))
                R.id.nav_social -> replaceFragment(getString(R.string.str_social))
                R.id.nav_coc -> replaceFragment(getString(R.string.str_coc))
                R.id.nav_about -> replaceFragment(getString(R.string.str_about_us))
            }
            true
        }

        if (supportActionBar != null) {
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    public override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // This is required to make the drawer toggle work
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)

    }

    fun replaceFragment(title: String) {
        updateToolbarTitle(title)

        // Get the fragment by tag
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(title)

        if (fragment == null) {
            // Initialize the fragment based on tag
            when (title) {
                resources.getString(R.string.str_agenda) -> fragment = AgendaFragment.newInstance()
                resources.getString(R.string.str_my_schedule) -> fragment = AgendaFragment.newInstanceMySchedule()
                resources.getString(R.string.str_faq) -> fragment = FAQFragment()
                resources.getString(R.string.str_social) -> fragment = SocialFragment()
                resources.getString(R.string.str_coc) -> fragment = CocFragment()
                resources.getString(R.string.str_about_us) -> fragment = AboutFragment()
            }
            // Add fragment with tag
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, title).commit()
        } else {
            supportFragmentManager.beginTransaction()
                    // detach the fragment that is currently visible
                    .detach(supportFragmentManager.findFragmentById(R.id.fragment_container))
                    // attach the fragment found as per the tag
                    .attach(fragment)
                    // commit fragment transaction
                    .commit()
        }
    }

    private fun updateToolbarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar?.title = title
        }
    }


    fun faqClick(v: View) {
        val tnt = Intent(Intent.ACTION_VIEW)
        tnt.data = Uri.parse(v.tag as String)
        startActivity(tnt)
    }
}
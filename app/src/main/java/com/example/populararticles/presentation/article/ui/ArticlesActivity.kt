package com.example.populararticles.presentation.article.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.populararticles.R

enum class FilterType(val value: String) {
    DAILY("1"),
    WEEKLY("7"),
    MONTHLY("30")

}

class ArticlesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_filter_period, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            // todo send retrieve intent with suitable FilterType
            R.id.btn_Daily -> {

            }
            R.id.btn_weekly -> {
            }
            R.id.btn_Monthly -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
package com.example.researchcenterlabact

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.researchcenterlabact.api.ApiClient
import com.example.researchcenterlabact.auth.SessionManager
import com.example.researchcenterlabact.model.User

class DashboardActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tvWelcomeTitle = findViewById<TextView>(R.id.tv_welcome_title)
        val tvUserId = findViewById<TextView>(R.id.tv_user_id)
        val tvEmail = findViewById<TextView>(R.id.tv_email)
        val tvFirstName = findViewById<TextView>(R.id.tv_first_name)
        val tvLastName = findViewById<TextView>(R.id.tv_last_name)
        val tvCreatedAt = findViewById<TextView>(R.id.tv_created_at)
        val progressDashboard = findViewById<ProgressBar>(R.id.progress_dashboard)

        // Nav bar
        val navDashboard = findViewById<TextView>(R.id.nav_dashboard)
        val navProfile = findViewById<TextView>(R.id.nav_profile)
        val navLogout = findViewById<TextView>(R.id.nav_logout)

        // Highlight active tab
        navDashboard.setTextColor(resources.getColor(R.color.indigo_700, null))
        navDashboard.setTypeface(navDashboard.typeface, android.graphics.Typeface.BOLD)
        navDashboard.textSize = 14f

        navDashboard.setOnClickListener { /* Already here */ }

        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        navLogout.setOnClickListener { showLogoutDialog() }

        // Load profile data
        loadProfile(tvWelcomeTitle, tvUserId, tvEmail, tvFirstName, tvLastName, tvCreatedAt, progressDashboard)
    }

    private fun loadProfile(
        tvWelcomeTitle: TextView, tvUserId: TextView, tvEmail: TextView,
        tvFirstName: TextView, tvLastName: TextView, tvCreatedAt: TextView,
        progress: ProgressBar
    ) {
        val token = SessionManager.getToken(this)
        if (token == null) {
            goToLogin()
            return
        }

        progress.visibility = View.VISIBLE

        ApiClient.getProfile(token, object : ApiClient.ApiCallback<User> {
            override fun onSuccess(result: User) {
                runOnUiThread {
                    progress.visibility = View.GONE
                    tvWelcomeTitle.text = "Welcome back, ${result.firstName}!"
                    tvUserId.text = result.userId.toString()
                    tvEmail.text = result.email
                    tvFirstName.text = result.firstName
                    tvLastName.text = result.lastName
                    tvCreatedAt.text = formatDate(result.createdAt)
                }
            }

            override fun onError(error: String) {
                runOnUiThread {
                    progress.visibility = View.GONE
                    SessionManager.clearSession(this@DashboardActivity)
                    goToLogin()
                }
            }
        })
    }

    private fun showLogoutDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setDimAmount(0.5f)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(64, 48, 64, 48)
            background = resources.getDrawable(R.drawable.bg_card, null)
            elevation = 8f
        }

        val title = TextView(this).apply {
            text = "Sign Out?"
            textSize = 20f
            setTextColor(resources.getColor(R.color.gray_800, null))
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
        }

        val message = TextView(this).apply {
            text = "Are you sure you want to sign out?"
            textSize = 14f
            setTextColor(resources.getColor(R.color.gray_500, null))
            gravity = Gravity.CENTER
            setPadding(0, 16, 0, 40)
        }

        val buttonRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        val btnCancel = Button(this).apply {
            text = "Cancel"
            textSize = 14f
            setTextColor(resources.getColor(R.color.gray_700, null))
            background = resources.getDrawable(R.drawable.bg_edittext, null)
            isAllCaps = false
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            params.marginEnd = 12
            layoutParams = params
            setPadding(0, 24, 0, 24)
            setOnClickListener { dialog.dismiss() }
        }

        val btnSignOut = Button(this).apply {
            text = "Sign Out"
            textSize = 14f
            setTextColor(Color.WHITE)
            background = resources.getDrawable(R.drawable.bg_button_primary, null)
            isAllCaps = false
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            params.marginStart = 12
            layoutParams = params
            setPadding(0, 24, 0, 24)
            setOnClickListener {
                dialog.dismiss()
                performLogout()
            }
        }

        buttonRow.addView(btnCancel)
        buttonRow.addView(btnSignOut)
        layout.addView(title)
        layout.addView(message)
        layout.addView(buttonRow)

        dialog.setContentView(layout)
        dialog.show()
    }

    private fun performLogout() {
        val token = SessionManager.getToken(this)
        if (token != null) {
            ApiClient.logout(token, object : ApiClient.ApiCallback<String> {
                override fun onSuccess(result: String) {
                    runOnUiThread {
                        SessionManager.clearSession(this@DashboardActivity)
                        goToLogin()
                    }
                }
                override fun onError(error: String) {
                    runOnUiThread {
                        SessionManager.clearSession(this@DashboardActivity)
                        goToLogin()
                    }
                }
            })
        } else {
            SessionManager.clearSession(this)
            goToLogin()
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun formatDate(raw: String): String {
        return try {
            // "2026-02-14T10:30:00" → "Feb 14, 2026"
            if (raw.contains("T")) {
                val parts = raw.split("T")[0].split("-")
                val months = arrayOf(
                    "", "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                )
                val month = months[parts[1].toInt()]
                val day = parts[2].toInt()
                val year = parts[0]
                "$month $day, $year"
            } else raw
        } catch (e: Exception) {
            raw
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finishAffinity()
    }
}

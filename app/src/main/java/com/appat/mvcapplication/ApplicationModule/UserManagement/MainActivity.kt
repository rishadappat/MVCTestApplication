package com.appat.mvcapplication.ApplicationModule.UserManagement

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.appat.mvcapplication.API.APIClasses.UserManagementAPI
import com.appat.mvcapplication.Models.RequestModel.CustomerLoginRequest
import com.appat.mvcapplication.R
import com.appat.mvcapplication.Utilities.Utility
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: AppCompatButton
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginButton = find(R.id.loginButton)
        usernameEditText = find(R.id.usernameEditText)
        passwordEditText = find(R.id.passwordEditText)

        loginButton.setOnClickListener {
            Utility.showLoaderDialog(this, "Loading")
            val params = CustomerLoginRequest(usernameEditText.text.toString(), passwordEditText.text.toString(), "")
            UserManagementAPI.UserLogin(params, { loginResponse->
                Toast.makeText(this, "User Login Success: ${loginResponse?.dataField?.get(0)?.pkUserId}", Toast.LENGTH_LONG).show()
            }, {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
        }
    }
}

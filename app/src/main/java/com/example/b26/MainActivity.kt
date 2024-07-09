package com.example.b26

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.b26.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var count = 0
    var send:String? = null
    var message:String? = null
    var isCheck:Boolean?= null

    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnCount.setOnClickListener {
            count++
            binding.btnCount.text = count.toString()
        }
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }

    private fun saveData() {
        sharedPreferences = this.getSharedPreferences("saveDate", Context.MODE_PRIVATE)
        send = binding.edtSend.text.toString()
        message = binding.edtMessage.text.toString()
        isCheck = binding.chkRemember.isChecked
        val editor = sharedPreferences.edit()
        editor.putString("key_send", send)
        editor.putString("key_message", message)
        editor.putBoolean("key_remmeber", isCheck!!)
        editor.putInt("key_count", count)
        editor.apply()
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        LoadData()
    }

    private fun LoadData() {
        sharedPreferences = this.getSharedPreferences("saveDate", Context.MODE_PRIVATE)
        send =sharedPreferences.getString("key_send", null)
        message = sharedPreferences.getString("key_message", null)
        isCheck = sharedPreferences.getBoolean("key_remmeber", false)
        count = sharedPreferences.getInt("key_count", 0)

        binding.edtSend.setText(send)
        binding.edtMessage.setText(message)
        binding.chkRemember.isChecked = isCheck!!
        binding.btnCount.text = count.toString()


    }
}
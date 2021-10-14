package com.atech.dashboard

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        sptype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    Toast.makeText(
                        this@Setting,
                        "Selected Category ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                    return result(position)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    private fun result(position: Int) {
        try {
            val cap = input_capacity.text.toString().toInt()
            var limit = 0.0
            when (position) {
                1 -> limit = 0.3
                2 -> limit = 0.35
                3 -> limit = 0.4
                4 -> limit = 0.5
            }
            val final = limit*cap
            return button(final.toInt())
        } catch (e: Exception) {
            Toast.makeText(this, "Masukan nilai kapasitas", Toast.LENGTH_SHORT).show()
        }

    }

    private fun button(final: Any) {
        val button: Button = findViewById(R.id.button_save)
        button.setOnClickListener {
            val database = FirebaseDatabase.getInstance().reference
            database.child("yolov3/rCap").setValue(final)
            Toast.makeText(this@Setting, "Saving", Toast.LENGTH_SHORT).show()
        }
    }
}
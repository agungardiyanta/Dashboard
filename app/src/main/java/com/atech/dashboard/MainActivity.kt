package com.atech.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG ="mytag"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = FirebaseDatabase.getInstance().reference

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                Log.d(TAG, token)
//                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            })
        val gdata = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    val enter = i.child("pmasuk").value.toString().toInt()
                    val exit = i.child("pkeluar").value.toString().toInt()
                    val total = enter - exit

                    num_enter.text = enter.toString()
                    num_exit.text = exit.toString()
                    number_total.text = total.toString()
                }

            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("cancel", p0.toString())
            }

        }
        database.addValueEventListener(gdata)
        database.addListenerForSingleValueEvent(gdata)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.optionmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.setting -> {
                val intent = Intent(this, Setting::class.java)
                startActivity(intent)
            }
        }
    }
}
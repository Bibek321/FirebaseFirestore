package com.example.iteradmin.firebasefirestore

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db=FirebaseFirestore.getInstance()
        val name=findViewById<EditText>(R.id.name)
        val branch=findViewById<EditText>(R.id.branch)
        val city=findViewById<EditText>(R.id.city)
        val save=findViewById<Button>(R.id.save)
        val load=findViewById<Button>(R.id.load)

        save.setOnClickListener{
               val n=name.text.toString()
               val c=city.text.toString()
               val b=branch.text.toString()

               val map= hashMapOf(
                       "name" to n,
                       "city" to c,
                       "branch" to b)

               db.collection("users")
                       .add(map)
                       .addOnSuccessListener {
                           Toast.makeText(this,"Added to fire Store",Toast.LENGTH_LONG).show()
                       }
                       .addOnCanceledListener {
                           Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()

                       }

        }
        load.setOnClickListener {
            db.collection("users")
                    .get()
                    .addOnSuccessListener {querySnapshot ->
                        for(d in querySnapshot.documents) {
                            val name: String = d.data?.get("name").toString()
                            Toast.makeText(this, name, Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show()
                    }
        }

    }
}

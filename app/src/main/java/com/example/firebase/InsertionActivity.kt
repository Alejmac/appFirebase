package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName : EditText
    private lateinit var etEmpAge : EditText
    private lateinit var etEmpSalary : EditText
    private lateinit var btnSavedata : Button

    private lateinit var dbRef :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpSalary = findViewById(R.id.etEmpSalary)
        btnSavedata = findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")


        btnSavedata.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

         val  empName = etEmpName.text.toString()
         val  empAge = etEmpAge.text.toString()
         val  empSalary = etEmpSalary.text.toString()

        //validamos que los campos no esten null
        if(empName.isEmpty()) {
            etEmpName.error = "erro al ingresar los datos"
        }
        if(empAge.isEmpty()) {
            etEmpAge.error = "erro al ingresar los datos"
        }
        if(empSalary.isEmpty()) {
            etEmpSalary.error = "erro al ingresar los datos"
        }
        val  empId = dbRef.push().key!!
         val  employee = EmployeModel(empId,empName,empAge,empSalary)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener{
                Toast.makeText(this, "data inserted ", Toast.LENGTH_LONG).show()

                etEmpSalary.text.clear()
                etEmpAge.text.clear()
                etEmpName.text.clear()

            }.addOnFailureListener{
                err->
                Toast.makeText(this, "error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}
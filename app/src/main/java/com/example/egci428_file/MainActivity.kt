package com.example.egci428_file

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader



class MainActivity : AppCompatActivity() {
    private val file = "mydata.txt"
    private var data: String? = null
    private var editText: EditText? = null
    private var dataList = ArrayList<String>()
    lateinit var adapter: ArrayAdapter<String>

    //lateinit var editTxt:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        val listView = findViewById<ListView>(R.id.listView)

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, dataList)
        listView.adapter = adapter

    }

    fun save(view: View) {
        data = editText!!.text.toString()
        try {
            val fOut = openFileOutput(file, Context.MODE_APPEND)
            fOut.write((data + "\n").toByteArray()) // Append a newline character to the data
            fOut.close()
            Toast.makeText(baseContext, "Data has been saved", Toast.LENGTH_SHORT).show()

            // Add the new data to the dataList and notify the adapter of the changes
            dataList.add(data!!)
            adapter.notifyDataSetChanged() //real time update

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun read(view: View){
        try{
            val fIn = openFileInput(file)
            val mfile = InputStreamReader(fIn)
            val br = BufferedReader(mfile)
            var line = br.readLine()

            dataList.clear() // Clear the existing data

            while (line != null){
                dataList.add(line) // Add each line to the dataList
                line = br.readLine()
            }

            br.close()
            mfile.close()
            fIn.close()

            adapter.notifyDataSetChanged() // Notify the adapter of the changes in data

            Toast.makeText(baseContext, "Read Data", Toast.LENGTH_SHORT).show()

        } catch (e: Exception){
            e.printStackTrace()
        }
    }

}
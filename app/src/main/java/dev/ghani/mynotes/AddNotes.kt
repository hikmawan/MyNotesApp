package dev.ghani.mynotes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*

class AddNotes : AppCompatActivity() {

    val dbTable="Notes"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
    }

    fun buAdd(view: View){
        var dbManager=DbManager(this)

        var values = ContentValues()
        values.put("TITLE",etTitle.text.toString())
        values.put("CONTENT",etContent.text.toString())

        val ID = dbManager.Insert(values)
        if (ID>0){
            Toast.makeText(this, " note is added", Toast.LENGTH_LONG).show()
            finish()
        }else{
            Toast.makeText(this," Cannot add note ", Toast.LENGTH_LONG).show()
        }
    }
}

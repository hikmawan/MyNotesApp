package dev.ghani.mynotes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cardview.view.*

class MainActivity : AppCompatActivity() {
    var listNotes=ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Add dummy data
        listNotes.add(Note(1,"Meet with My Friends","Dulu waktu masih kerjasama sama gagas media, sering dapet buku gratis. Daripada disimpen di rumah, saya jadiin give away buat yang order ayam tulang lunak... "))
        listNotes.add(Note(2,"Buy Meatballs","Dulu waktu masih kerjasama sama gagas media, sering dapet buku gratis. Daripada disimpen di rumah, saya jadiin give away buat yang order ayam tulang lunak... "))
        listNotes.add(Note(3,"Excercise Karate","Dulu waktu masih kerjasama sama gagas media, sering dapet buku gratis. Daripada disimpen di rumah, saya jadiin give away buat yang order ayam tulang lunak... "))

        var myNotesAdapter = MyNotesAdapter(listNotes)
        lvNotes.adapter = myNotesAdapter
    }

    inner class MyNotesAdapter:BaseAdapter{
        var listNotesAdapter = ArrayList<Note>()
        constructor(listNotesAdapter:ArrayList<Note>):super(){
            this.listNotesAdapter=listNotesAdapter
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.cardview,null)
            var myNote=listNotesAdapter[position]
            myView.tvTitle.text = myNote.noteName
            myView.tvContent.text = myNote.noteDes

            return myView
        }

        override fun getItem(position: Int): Any {
            return listNotesAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNotesAdapter.size
        }
    }
}

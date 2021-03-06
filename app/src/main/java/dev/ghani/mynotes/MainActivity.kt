package dev.ghani.mynotes

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cardview.view.*

class MainActivity : AppCompatActivity() {
    var listNotes=ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Add dummy data
//        listNotes.add(Note(1,"Meet with My Friends","Dulu waktu masih kerjasama sama gagas media, sering dapet buku gratis. Daripada disimpen di rumah, saya jadiin give away buat yang order ayam tulang lunak... "))
//        listNotes.add(Note(2,"Buy Meatballs","Dulu waktu masih kerjasama sama gagas media, sering dapet buku gratis. Daripada disimpen di rumah, saya jadiin give away buat yang order ayam tulang lunak... "))
//        listNotes.add(Note(3,"Excercise Karate","Dulu waktu masih kerjasama sama gagas media, sering dapet buku gratis. Daripada disimpen di rumah, saya jadiin give away buat yang order ayam tulang lunak... "))

//        var myNotesAdapter = MyNotesAdapter(listNotes)
//        lvNotes.adapter = myNotesAdapter

        //Load from DB
        LoadQuery("%")
    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
        Toast.makeText(this,"onResume",Toast.LENGTH_LONG).show()
    }

    fun LoadQuery(title:String){
        var dbManager=DbManager(this)
        val projections= arrayOf("ID","TITLE","CONTENT")
        val selectionArgs= arrayOf(title)
        val cursor = dbManager.Query(projections,"TITLE LIKE ?",selectionArgs,"TITLE")

        listNotes.clear()
        if(cursor.moveToFirst()){
            do{
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val TITLE = cursor.getString(cursor.getColumnIndex("TITLE"))
                val CONTENT = cursor.getString(cursor.getColumnIndex("CONTENT"))
                listNotes.add(Note(ID,TITLE,CONTENT))

            }while(cursor.moveToNext())
        }
        var myNotesAdapter = MyNotesAdapter(this,listNotes)
        lvNotes.adapter = myNotesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val sv = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                LoadQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.addNote->{
                var intent = Intent(this,AddNotes::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdapter:BaseAdapter{
        var listNotesAdapter = ArrayList<Note>()
        var context:Context?=null
        constructor(context: Context,listNotesAdapter:ArrayList<Note>):super(){
            this.listNotesAdapter=listNotesAdapter
            this.context=context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.cardview,null)
            var myNote=listNotesAdapter[position]
            myView.tvTitle.text = myNote.noteName
            myView.tvContent.text = myNote.noteDes
            myView.ivDelete.setOnClickListener(View.OnClickListener {
                var dbManager=DbManager(this.context!!)
                val selectionArgs= arrayOf(myNote.noteId.toString())
                dbManager.Delete("ID=?",selectionArgs)
                LoadQuery("%")
            })

            myView.ivEdit.setOnClickListener( View.OnClickListener {
//                val note
                GoToUpdate(myNote)
            })

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

    fun GoToUpdate(note:Note){
        var intent = Intent(this,AddNotes::class.java)

        intent.putExtra("ID", note.noteId)
        intent.putExtra("TITLE", note.noteName)
        intent.putExtra("CONTENT", note.noteDes)
        startActivity(intent)
    }
}

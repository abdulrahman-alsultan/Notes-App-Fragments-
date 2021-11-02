package com.example.notesappfragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfragments.ListFragment
import com.example.notesappfragments.R
import com.example.notesappfragments.data.Note
import kotlinx.android.synthetic.main.note_row.view.*

class NoteAdapter(private val listFragment: ListFragment): RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {
    private var notes = emptyList<Note>()

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notes[position]

        holder.itemView.apply {
            tvNote.text = note.noteText
            ibEditNote.setOnClickListener {
                /**
                 * We will use Shared Preferences here to pass the NoteId from our NoteAdapter to the Update Fragment
                 * There is a much cleaner way of doing this, but we will leave that up to you
                 * Hint: look into 'navArgs'
                 * Another option is 'Shared ViewModel'
                 */
                with(listFragment.sharedPreferences.edit()) {
                    putString("NoteId", note.id)
                    apply()
                }
                listFragment.findNavController().navigate(R.id.action_listFragment_to_update)
            }
            ibDeleteNote.setOnClickListener {
                listFragment.listViewModel.deleteNote(note.id)
            }
        }
    }

    override fun getItemCount() = notes.size

    fun update(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }
}

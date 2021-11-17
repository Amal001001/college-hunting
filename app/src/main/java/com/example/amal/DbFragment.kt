package com.example.amal

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amal.DBviewModel.DBActivityViewModel
import com.example.amal.adapters.UniversitiesAdapter
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DbFragment : Fragment() {

    lateinit var dbActivityViewModel: DBActivityViewModel

    private lateinit var rvBD: RecyclerView
    private lateinit var universitiesAdapter: UniversitiesAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_db, container, false)

        dbActivityViewModel = ViewModelProvider(this).get(DBActivityViewModel::class.java)
        dbActivityViewModel.readFromDB().observe(viewLifecycleOwner, { shows -> universitiesAdapter.update(shows) })

        rvBD = view.findViewById(R.id.rvBD)
        universitiesAdapter = UniversitiesAdapter(this)
        rvBD.adapter = universitiesAdapter
        rvBD.layoutManager = LinearLayoutManager(requireContext())
        rvBD.adapter!!.notifyDataSetChanged()

        return view
    }

    fun toastNote(note:String){
        Toast.makeText(requireContext(), note, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    fun deleteDialog(Id: Int){
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val confirmDelete = TextView(requireContext())
        confirmDelete.text = "  Are you sure you want to delete this University?"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    _, _ -> dbActivityViewModel.deleteUniversity(Id) })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel() })
        val alert = dialogBuilder.create()
        alert.setTitle("Delete University")
        alert.setView(confirmDelete)
        alert.show()
    }

    fun updateDialog(Id :Int,name :String,country :String) {

        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Update Notes")

        val updatedtitle = EditText(requireContext())
        alert.setView(updatedtitle)

        alert.setPositiveButton("Update") { _, _ ->
            val updatedtitle = updatedtitle.text.toString()

            dbActivityViewModel.updateUniversity(Id,name,country,updatedtitle)
            Toast.makeText(requireContext(), "Notes Updated", Toast.LENGTH_LONG).show()
        }

        alert.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alert.setCancelable(false)
        alert.show()
    }

}
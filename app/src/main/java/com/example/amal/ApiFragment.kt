package com.example.amal

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amal.DBviewModel.DBActivityViewModel
import com.example.amal.adapters.RecyclerViewAdapter
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ApiFragment : Fragment() {

    lateinit var etSearch: EditText
    lateinit var btnSearch: Button
    var search =""

    lateinit var univirsities : ArrayList<Univirsity>
    private lateinit var rvApi: RecyclerView
    private lateinit var rvAdapter: RecyclerViewAdapter

    lateinit var progressDialog : ProgressDialog

    lateinit var dbActivityViewModel: DBActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_api, container, false)

        dbActivityViewModel = ViewModelProvider(this).get(DBActivityViewModel::class.java)

        etSearch = view.findViewById(R.id.etSearch)
        btnSearch = view.findViewById(R.id.btnSearch)

        univirsities = arrayListOf()
        rvApi = view.findViewById(R.id.rvApi)
        rvAdapter = RecyclerViewAdapter(this,univirsities)
        rvApi.adapter = rvAdapter
        rvApi.layoutManager = LinearLayoutManager(requireContext())

        btnSearch.setOnClickListener {
            search = etSearch.text.toString()
            if(etSearch.text.isNotEmpty()) { requestAPI() }
            etSearch.text.clear()
            univirsities.clear()
            rvAdapter.notifyDataSetChanged()

            progressDialog = ProgressDialog(requireContext())
            progressDialog.setMessage("Please wait")
            progressDialog.show()

            //Hide keyboard
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        }

        return view
    }

    private fun requestAPI(){
        CoroutineScope(Dispatchers.IO).launch {

            val data = async { fetchShows() }.await()
            if(data.isNotEmpty()){ showShows(data)}
        }
    }

    private fun fetchShows(): String {
        var response = ""
        try
        { response = URL("http://universities.hipolabs.com/search?name=$search").readText(Charsets.UTF_8)
        } catch (e: Exception)
        { println("Error: $e") }
        return response
    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun showShows(data: String) {

        withContext(Dispatchers.Main) {

            progressDialog.dismiss()
            val jsonArr = JSONArray(data)

            for (i in 0 until jsonArr.length())
            {
                val universityName = jsonArr.getJSONObject(i).getString("name")
                val universityCountry = jsonArr.getJSONObject(i).getString("country")

                univirsities.add(Univirsity(universityName,universityCountry))
            }

            rvAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("SetTextI18n")
    fun addToDBDialog(name:String,country:String){
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Add", DialogInterface.OnClickListener {
                    _, _ -> addToDB(name,country)
                Toast.makeText(requireContext(), "$name added", Toast.LENGTH_SHORT).show()})
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel() })
        val alert = dialogBuilder.create()
        alert.setTitle("Add to database")
        alert.show()
    }

    fun addToDB(name:String,country:String) {
        dbActivityViewModel.addUniversity(name,country,"")
    }

}
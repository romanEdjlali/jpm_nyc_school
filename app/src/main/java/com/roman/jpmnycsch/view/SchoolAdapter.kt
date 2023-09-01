package com.roman.jpmnycsch.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.roman.jpmnycsch.R
import com.roman.jpmnycsch.model.School

/*
* Class: SchoolAdapter
* Owner: Roman Edjlali
* Date Created: 08/31/2023 19:43 PM
*/

class SchoolAdapter(private val schools: ArrayList<School>) :
    RecyclerView.Adapter<SchoolAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDbn: TextView = itemView.findViewById(R.id.tvDbn)
        private val tvSchoolName: TextView = itemView.findViewById(R.id.tvSchoolName)

        fun bind(school: School) {
            tvDbn.text = school.dbn
            tvSchoolName.text =school.school_name
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSchoolList(newSchools: List<School>) {
        schools.clear()
        schools.addAll(newSchools)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
    )

    override fun getItemCount(): Int = schools.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(schools[position])
        holder.itemView.setOnClickListener {
            // Display shipment destination for the selected driver
            Toast.makeText(
                holder.itemView.context,
                "DBN: ${schools[position].dbn}\nSchool Name: ${schools[position].school_name}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
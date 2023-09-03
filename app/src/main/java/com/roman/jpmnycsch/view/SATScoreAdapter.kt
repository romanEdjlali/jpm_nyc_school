package com.roman.jpmnycsch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roman.jpmnycsch.R
import com.roman.jpmnycsch.model.SATScore
import com.roman.jpmnycsch.model.School


/*
* Class: SchoolAdapter
* Owner: Roman Edjlali
* Date Created: 09/02/2023 15:08
*/

class SATScoreAdapter(private val dataSet: List<SATScore>) :
    RecyclerView.Adapter<SATScoreAdapter.SatScoreViewHolder>() {

    // ViewHolder class to hold the view components
    class SatScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dbn: TextView = view.findViewById(R.id.tvDetailDbn)
        private val schoolName: TextView = view.findViewById(R.id.tvDetailSchoolName)
        private val numTestTakers: TextView = view.findViewById(R.id.tvTestTakers)
        private val mathScore: TextView = view.findViewById(R.id.tvMathScore)
        private val readingScore: TextView = view.findViewById(R.id.tvReadingScore)
        private val writingScore: TextView = view.findViewById(R.id.tvWritingScore)

        fun bind(satScore: SATScore) {
            dbn.text = satScore.dbn
            schoolName.text =satScore.school_name
            mathScore.text = satScore.sat_math_avg_score.toString()
            numTestTakers.text = satScore.num_of_sat_test_takers.toString()
            readingScore.text = satScore.sat_critical_reading_avg_score.toString()

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_detail, parent, false)
        return SatScoreViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: SatScoreViewHolder, position: Int) {
        //val score = dataSet[position]
        /*holder.schoolName.text = score.school_name
        holder.numTestTakers.text = "Test Takers: ${score.num_of_sat_test_takers}"
        holder.mathScore.text = "Math: ${score.sat_math_avg_score}"
        holder.readingScore.text = "Reading: ${score.sat_critical_reading_avg_score}"
        holder.writingScore.text = "Writing: ${score.sat_writing_avg_score}"*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
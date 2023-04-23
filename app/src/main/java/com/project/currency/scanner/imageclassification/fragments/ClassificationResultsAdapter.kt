package com.project.currency.scanner.imageclassification.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.currency.scanner.imageclassification.databinding.ItemClassificationResultBinding
import com.project.currency.scanner.imageclassification.model.OnReceivedListener
import com.project.currency.scanner.imageclassification.model.Result
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.vision.classifier.Classifications
import kotlin.math.min

class ClassificationResultsAdapter :
    RecyclerView.Adapter<ClassificationResultsAdapter.ViewHolder>() {
    companion object {
        private const val NO_VALUE = " "
    }

    private var categories: MutableList<Category?> = mutableListOf()
    private var adapterSize: Int = 0
    private lateinit var onReceivedListener: OnReceivedListener

    fun updateResults(listClassifications: List<Classifications>?) {
        categories = MutableList(adapterSize) { null }
        listClassifications?.let { it ->
            if (it.isNotEmpty()) {
                val sortedCategories = it[0].categories.sortedBy { it?.index }
                val min = min(sortedCategories.size, categories.size)
                for (i in 0 until min) {
                    categories[i] = sortedCategories[i]
                }
            }
        }
    }

    fun updateAdapterSize(size: Int) {
        adapterSize = size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        categories[position].let { category ->
            holder.bind(category?.label, category?.score)
            onReceivedListener.onResult(
                Result(
                    label = category?.label ?: "",
                    score = (category?.score?.times(100f)) ?: 1f
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClassificationResultBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(val binding: ItemClassificationResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(label: String?, score: Float?) {
            with(binding) {
                tvLabel.text = label ?: NO_VALUE
                tvScore.text =
                    if (score != null) String.format("%.0f", score * 100) + " %  " else NO_VALUE
            }
        }
    }

    fun setOnReceivedListener(onReceivedListener: OnReceivedListener) {
        this.onReceivedListener = onReceivedListener
    }
}

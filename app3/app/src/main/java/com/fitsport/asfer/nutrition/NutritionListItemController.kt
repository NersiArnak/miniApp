package com.fitsport.asfer.nutrition

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fitsport.asfer.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class NutritionListItemController(
) : BindableItemController<NutritionListItem, NutritionListItemController.Holder>() {

    override fun getItemId(data: NutritionListItem) =
        data.hashCode().toString()

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) : BindableViewHolder<NutritionListItem>(
        parent,
        R.layout.nutrition_list_item
    ) {
        override fun bind(data: NutritionListItem) {
            itemView.findViewById<TextView>(R.id.titleNutrition).text = data.title

            itemView.findViewById<TextView>(R.id.nameNutrition).text = data.name

            itemView.findViewById<TextView>(R.id.morningNutrition).text = data.morning

            itemView.findViewById<TextView>(R.id.dinnerNutrition).text = data.dinner
            itemView.findViewById<TextView>(R.id.supperNutrition).text = data.supper
        }
    }

}

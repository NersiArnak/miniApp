package org.fitmyss.asfe.trainings

import android.view.ViewGroup
import android.widget.TextView
import org.fitmyss.asfe.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class TrainingListItemController(
) : BindableItemController<TrainingListItem, TrainingListItemController.Holder>() {

    override fun getItemId(data: TrainingListItem) =
        data.hashCode().toString()

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    //Иттерирует по массиву
    inner class Holder(parent: ViewGroup) : BindableViewHolder<TrainingListItem>(
        parent,
        R.layout.training_list_item
    ) {
        override fun bind(data: TrainingListItem) {
            itemView.findViewById<TextView>(R.id.titleTraining).text = data.title

            itemView.findViewById<TextView>(R.id.planTraining).text = data.plan

            itemView.findViewById<TextView>(R.id.aimTraining).text = data.aim

            itemView.findViewById<TextView>(R.id.timeTraining).text = data.time
        }
    }
}

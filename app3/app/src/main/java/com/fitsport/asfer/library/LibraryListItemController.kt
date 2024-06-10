package com.fitsport.asfer.library

import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fitsport.asfer.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class LibraryListItemController : BindableItemController<LibraryListItem, LibraryListItemController.Holder>() {

    override fun getItemId(data: LibraryListItem): String =
        data.hashCode().toString()

    override fun createViewHolder(parent: ViewGroup): Holder =
        Holder(parent)

    inner class Holder(parent: ViewGroup) : BindableViewHolder<LibraryListItem>(
        parent,
        R.layout.library_list_item
    ) {
        override fun bind(data: LibraryListItem) {
            Glide.with(itemView.context).load(data.image).into(itemView.findViewById(R.id.imageLibrary))
            itemView.findViewById<TextView>(R.id.titleLibrary).text = data.title
            itemView.findViewById<TextView>(R.id.statusLibrary).text = data.status
            itemView.findViewById<TextView>(R.id.descriptionLibrary).text = data.description }
    }
}

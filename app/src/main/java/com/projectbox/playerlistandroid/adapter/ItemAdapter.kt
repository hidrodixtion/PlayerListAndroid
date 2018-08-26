package com.projectbox.playerlistandroid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectbox.playerlistandroid.R
import com.projectbox.playerlistandroid.event.ClickEvent
import com.projectbox.playerlistandroid.model.TeamPlayer
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by adinugroho
 */
class ItemAdapter(private var list: List<TeamPlayer>): RecyclerView.Adapter<ItemVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemVH(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(vh: ItemVH, position: Int) {
        vh.bind(list[position].name, list[position].position)

        vh.containerView.setOnClickListener {
            EventBus.getDefault().post(ClickEvent(position))
        }
    }

    fun update(list: List<TeamPlayer>) {
        this.list = list
        notifyDataSetChanged()
    }
}

class ItemVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(title: String, subtitle: String?) {
        txt_title.text = title

        subtitle?.let {
            txt_description.visibility = View.VISIBLE
            txt_description.text = it
        }
    }
}
package com.arny.celestiatools.presenter.planets

import com.arny.celestiatools.R
import com.arny.celestiatools.data.adapters.SimpleAbstractAdapter
import com.arny.celestiatools.data.models.Planet
import kotlinx.android.synthetic.main.planets_list_item_layout.view.*

class PlanetsAdapter : SimpleAbstractAdapter<Planet>() {
    override fun getLayout(): Int {
        return R.layout.planets_list_item_layout
    }

    override fun bindView(item: Planet, viewHolder: VH) {
        viewHolder.itemView.apply {
            tv_planet_name.text = item.name
            setOnClickListener {
                listener?.onItemClick(viewHolder.adapterPosition, item)
            }
        }
    }

    override fun getDiffCallback(): DiffCallback<Planet>? {
        return object : DiffCallback<Planet>() {
            override fun areItemsTheSame(oldItem: Planet, newItem: Planet): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Planet, newItem: Planet): Boolean {
                return oldItem.name == newItem.name && oldItem.radius == newItem.radius && oldItem.mass == newItem.mass
            }
        }
    }
}
package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.databinding.AstroidItemBinding

class AsteroidAdapter (val onClickListener :AsteroidOnClickListener)
    : ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder> (DiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(
            AstroidItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bindView(asteroid)
        holder.itemView.setOnClickListener {
            onClickListener.onclick(asteroid)
        }
    }

    class AsteroidViewHolder(private var binding : AstroidItemBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bindView (asteroid : Asteroid){
                binding.asteroid=asteroid
                binding.executePendingBindings()
            }
    }
}

class AsteroidOnClickListener (val clickListener: (asteroid : Asteroid)-> Unit) {
    fun onclick (asteroid: Asteroid)= clickListener(asteroid)
}

object DiffCallback :DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id==newItem.id
    }
}

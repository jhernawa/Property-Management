package com.example.propertymanagementappwithmvm.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.propertymanagementappwithmvm.R
import com.example.propertymanagementappwithmvm.databinding.LayoutGridPropertyBinding
import com.example.propertymanagementappwithmvm.models.Property
import com.example.propertymanagementappwithmvm.models.PropertyDeleteResponse
import com.example.propertymanagementappwithmvm.viewmodels.PropertyGetViewModel

class RecyclerViewPropertiesAdapter(var listPropertyActivityContext : Context, var listPropertyActivity : LifecycleOwner, var propertyGetViewModel: PropertyGetViewModel) : RecyclerView.Adapter<RecyclerViewPropertiesAdapter.MyViewHolder>(), PropertyGetViewModel.PropertyGetViewModelAdapterInterface{

    private var propertiesList : ArrayList<Property> = ArrayList()
    private var positionToBeDeleted : Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{

        var viewDataBinding = DataBindingUtil.inflate<LayoutGridPropertyBinding>(LayoutInflater.from(parent.context), R.layout.layout_grid_property, parent, false)
        return MyViewHolder(viewDataBinding)

    }

    override fun getItemCount(): Int {
        return propertiesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(propertiesList.get(position), position)
    }

    fun setData(propertiesList : ArrayList<Property>){
        this.propertiesList = propertiesList
        notifyDataSetChanged()
    }

    fun deleteItem() {
        propertiesList.removeAt(positionToBeDeleted)
        notifyItemRemoved(positionToBeDeleted)
        notifyItemRangeChanged(positionToBeDeleted, propertiesList.size)
        
    }

    override fun onSuccessDelete(propertyDeleteReponse: LiveData<PropertyDeleteResponse>) {
        propertyDeleteReponse.observe(listPropertyActivity, object : Observer<PropertyDeleteResponse> {
            override fun onChanged(t: PropertyDeleteResponse?) {
                Toast.makeText(listPropertyActivityContext, t!!.msg[0], Toast.LENGTH_SHORT).show()

                deleteItem()
            }

        })
    }

    inner class MyViewHolder(var viewDataBinding : LayoutGridPropertyBinding) : RecyclerView.ViewHolder(viewDataBinding.root){

        fun bind(property : Property, position: Int){
            viewDataBinding.property = property

            viewDataBinding.imageViewDelete.setOnClickListener{

                positionToBeDeleted = position
                propertyGetViewModel.deleteProperty(property.id)


            }

        }
    }


}
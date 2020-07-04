package com.example.exploredogs.views

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.exploredogs.R
import com.example.exploredogs.Util.getprogressdrawable
import com.example.exploredogs.Util.loadimage
import com.example.exploredogs.databinding.ItemDogBinding
import com.example.exploredogs.models.Dogbreed
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_dog.view.*


class DogsListAdapter(val dogList: ArrayList<Dogbreed>) :
    RecyclerView.Adapter<DogsListAdapter.DogViewHoler>(),DogClickLitsner {
    fun UpdateDogList(newDogList:List<Dogbreed>){
       dogList.clear()
        dogList.addAll(newDogList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHoler {
        val inflater = LayoutInflater.from(parent.context)
      //  var view = inflater.inflate(R.layout.item_dog, parent, false)
        var view=DataBindingUtil.inflate<ItemDogBinding>(inflater,R.layout.item_dog,parent,false )
        return DogViewHoler(view)
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    override fun onBindViewHolder(holder: DogViewHoler, position: Int) {
        holder.view.dog=dogList[position]
        holder.view.listener=this
     /*   holder.view.name.text = dogList[position].dogBreed
        holder.view.lifeSpan.text = dogList[position].lifeSpan

        holder.view.setOnClickListener {
            val action=ListFragmentDirections.actionDeatailFragment()
            action.doguid=dogList[position].uuid
            Navigation.findNavController(it).navigate(action)
        }
        holder.view.dogImage.loadimage(dogList[position].imageUrl,
            getprogressdrawable(holder.view.dogImage.context))*/
    }

   // class DogViewHoler(var view: View) : RecyclerView.ViewHolder(view)

    class DogViewHoler(var view: ItemDogBinding) : RecyclerView.ViewHolder(view.root)

    override fun onDogClicked(v: View) {
       val action=ListFragmentDirection.actionDeatailFragment()
        action.doguid=v.dogId.text.toString().toInt()
       Navigation.findNavController(v).navigate(action)
    }


}



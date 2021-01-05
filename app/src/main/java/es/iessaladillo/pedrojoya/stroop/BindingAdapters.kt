package es.iessaladillo.pedrojoya.stroop

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player


@BindingAdapter("setColorText")
fun TextView.setColorText( color:Int ){
    setTextColor(ContextCompat.getColor(context,color))
}

@BindingAdapter("setAvatar")
fun ImageView.setAvatar(player:Player?){
   if(player == null){
       setImageResource(R.drawable.logo)
   }else{
       setImageResource(player.avatarResId)
   }
}

@BindingAdapter("setResource")
fun ImageView.setResource(resource:Int){
    setImageResource(resource)
}


@BindingAdapter("goneUnless")
fun View.goneUnless(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:setInteger")
fun TextView.setInteger(value:Int){
    text = value.toString()
}
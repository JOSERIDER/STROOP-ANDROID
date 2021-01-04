package es.iessaladillo.pedrojoya.stroop.extensions

import androidx.lifecycle.LiveData


fun LiveData<Int>.getValue(defaultValue:Int) : Int = value ?: defaultValue

fun LiveData<String>.getValue(defaultValue:String) : String = value ?: defaultValue

fun LiveData<Long>.getValue(defaultValue:Long) : Long = value ?: defaultValue

fun LiveData<Boolean>.getValue(defaultValue:Boolean) : Boolean = value ?: defaultValue
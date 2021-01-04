package es.iessaladillo.pedrojoya.stroop.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

fun Spinner.doOnItemSelected( onItemSelected : (element : Any) -> Unit ) = object : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val element = selectedItem
        onItemSelected(element)
    }
}

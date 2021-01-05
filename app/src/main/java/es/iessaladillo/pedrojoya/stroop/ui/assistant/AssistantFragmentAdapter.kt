package es.iessaladillo.pedrojoya.stroop.ui.assistant

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.databinding.AssistantFragmentBinding
import es.iessaladillo.pedrojoya.stroop.databinding.AssistantPageBinding
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.assistant_page.*


const val NUMBER_OF_PAGES = 8

typealias onClickListener = (position :Int) -> Unit
class AssistantFragmentAdapter(private val application: Application) : RecyclerView.Adapter<AssistantFragmentAdapter.ViewHolder>() {


    lateinit var finishButton: onClickListener



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AssistantPageBinding.inflate(layoutInflater, parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = NUMBER_OF_PAGES

    inner class ViewHolder(private val binding: AssistantPageBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnAssistantFinish.setOnClickListener{ finishButton(bindingAdapterPosition) }
        }

        fun bind(position: Int) {
            setIcon(position)
        }


        private fun setIcon(position: Int) {
            when (position) {
                    0 -> setValues(R.drawable.logo,R.string.assistant_stroopDescription, R.color.stroopOption)
                    1 -> setValues(R.drawable.ic_play_black_24dp, R.string.assistant_playDescription,R.color.playOption)
                    2 -> setValues(R.drawable.ic_settings_black_24dp,R.string.assistant_settingsDescription,R.color.settingsOption)
                    3 -> setValues(R.drawable.ic_ranking_black_24dp,R.string.assistant_rankingDescription, R.color.rankingOption)
                    4 -> setValues(R.drawable.ic_assistant_black_24dp, R.string.assistant_assistantDescription, R.color.assistantOption)
                    5 -> setValues(R.drawable.ic_player_black_24dp, R.string.assistant_playerDescription, R.color.playerOption)
                    6 -> setValues(R.drawable.ic_about_black_24dp, R.string.assistant_aboutDescription,R.color.aboutOption)
                    7 -> setValues(R.drawable.ic_finish_black_24dp,R.string.assistant_finishDescription,R.color.finishOption, true)
            }

        }

        @SuppressLint("NewApi")
        private fun setValues(iconId:Int, description:Int, backgroundColor:Int, isLastPage: Boolean = false){
            binding.imgAssistantIcon.setImageResource(iconId)
            binding.lblAssistantDescription.text = application.getString(description)
                binding.assistantPageRoot.setBackgroundColor( application.getColor(backgroundColor))
            if (isLastPage){
                binding.btnAssistantFinish.visibility = View.VISIBLE
            }
        }

    }
}
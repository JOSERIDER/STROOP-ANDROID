package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.base.enums.GameMode
import es.iessaladillo.pedrojoya.stroop.data.pojo.PlayerGame
import es.iessaladillo.pedrojoya.stroop.databinding.RankingItemBinding
import es.iessaladillo.pedrojoya.stroop.ui.player.playerCreation.OnclikItem

class RankingFragmentAdapter(private val viewModel: RankingFragmentViewModel):ListAdapter<PlayerGame, RankingFragmentAdapter.ViewHolder>(PlayerGameDiffCallback) {


    lateinit var onItemClick: OnclikItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RankingItemBinding = RankingItemBinding.inflate(
            layoutInflater, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


   inner class ViewHolder(private val binding: RankingItemBinding):RecyclerView.ViewHolder(binding.root) {

       init {
           binding.root.setOnClickListener { onItemClick(bindingAdapterPosition) }
       }
       fun bind(playerGame: PlayerGame){
           binding.game = playerGame
            binding.vm = viewModel
            binding.gameMode = GameMode.ALL
           binding.executePendingBindings()
       }
    }


    object PlayerGameDiffCallback : DiffUtil.ItemCallback<PlayerGame>() {
        override fun areItemsTheSame(oldItem: PlayerGame, newItem: PlayerGame): Boolean = oldItem.game.id == newItem.player.id

        override fun areContentsTheSame(oldItem: PlayerGame, newItem: PlayerGame): Boolean = oldItem == newItem
    }

}
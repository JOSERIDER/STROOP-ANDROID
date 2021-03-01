package es.iessaladillo.pedrojoya.stroop.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import es.iessaladillo.pedrojoya.stroop.data.repository.*


@Module
@InstallIn(ViewModelComponent::class)
abstract  class ViewModelModule {

    @Binds
    abstract fun bindGameRepository(gameRepositoryImp: GameRepositoryImp): GameRepository

    @Binds
    abstract fun bindPlayerGameRepository(playerGameRepositoryImp: PlayerGameRepositoryImp): PlayerGameRepository

    @Binds
    abstract fun bindPlayerRepository(playerRepositoryImp: PlayerRepositoryImp):PlayerRepository
}
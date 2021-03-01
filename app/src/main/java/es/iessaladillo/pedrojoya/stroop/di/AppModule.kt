package es.iessaladillo.pedrojoya.stroop.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.iessaladillo.pedrojoya.stroop.data.StroopDatabase
import es.iessaladillo.pedrojoya.stroop.data.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerGameDao
import es.iessaladillo.pedrojoya.stroop.data.repository.GameRepositoryImp
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerGameRepositoryImp
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStroopDatabase(app: Application): StroopDatabase =
        Room.databaseBuilder(
            app,
            StroopDatabase::class.java,
            "stroop_database"
        ).build()

    @Provides
    @Singleton
    fun provideGameDao(stroopDatabase: StroopDatabase):GameDao = stroopDatabase.game

    @Provides
    @Singleton
    fun providePlayerDao(stroopDatabase: StroopDatabase):PlayerDao = stroopDatabase.playerDao

    @Provides
    @Singleton
    fun providesPlayerGameDao(stroopDatabase: StroopDatabase):PlayerGameDao = stroopDatabase.playerGameDao



    @Provides
    @Singleton
    fun provideGameRepository(gameDao: GameDao):GameRepositoryImp = GameRepositoryImp(gameDao)

    @Provides
    @Singleton
    fun providePlayerRepository(playerDao: PlayerDao):PlayerRepositoryImp = PlayerRepositoryImp(playerDao)

    @Provides
    @Singleton
    fun providePlayerGameRepository(playerGamerDao: PlayerGameDao):PlayerGameRepositoryImp = PlayerGameRepositoryImp(playerGamerDao)

}
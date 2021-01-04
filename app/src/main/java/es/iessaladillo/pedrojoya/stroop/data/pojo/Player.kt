package es.iessaladillo.pedrojoya.stroop.data.pojo

import androidx.room.*

@Entity(
   indices = [
      Index(
         name = "PLAYER_INDEX_NAME_UNIQUE",
         value = ["player_name", "player_avatar"],
         unique = true
      )
   ]
)
data class Player (
   @PrimaryKey(autoGenerate = true)
   val id:Long,
   @ColumnInfo(name ="player_name", collate = ColumnInfo.NOCASE)
   val name:String,
   @ColumnInfo(name ="player_avatar")
   val avatarResId:Int
)
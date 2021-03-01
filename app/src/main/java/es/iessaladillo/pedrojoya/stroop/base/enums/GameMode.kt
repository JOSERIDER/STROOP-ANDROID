package es.iessaladillo.pedrojoya.stroop.base.enums

enum class GameMode(val mode: String) {

    ALL("All"), TIME("Time"), ATTEMPS("Attempts");

    override fun toString(): String {
        return mode
    }

    companion object {
        fun toGameMode(m: String): GameMode? {
            for (i in values()){
                if (i.mode == m){
                    return i
                }
            }
            return null
        }
    }
}

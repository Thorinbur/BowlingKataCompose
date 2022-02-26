class Simulator(val gameLength: Int = 10) {
    fun addRoll(roll: Int) {
        if (game.frames.count{it.isFinished} == gameLength){
            addBonusRoll(roll)
        } else {

            val lastFrame = game.frames.lastOrNull()
            val frames = if (lastFrame?.isFinished == false) {
                val newLast = Frame(lastFrame!!.firstRoll, roll)
                game.frames.dropLast(1) + newLast

            } else game.frames + Frame(roll, null)
            game = Game(frames)
        }
    }

    private fun addBonusRoll(roll: Int) {
        if(game.bonusRoll1 == null) {
            game = Game(game.frames, roll)
        } else {
            game = Game(game.frames, game.bonusRoll1, roll)
        }
    }

    fun hasRoll(): Boolean {
        if (game.frames.count{it.isFinished} < gameLength) return true
        val lastFrame = game.frames.last()
        return lastFrame.isSpare && game.bonusRoll1 == null || lastFrame.isStrike && game.bonusRoll2 == null
    }

    val pinsRemaining:Int get(){
        val lastFrame = game.frames.lastOrNull()
        return if(game.frames.count{it.isFinished} < gameLength) {
            10 - (lastFrame?.firstRoll?.takeIf { !lastFrame.isFinished } ?: 0)
        } else {
            (10 - (game.bonusRoll1 ?: 0)).takeIf { it != 0 } ?: 10
        }
    }
    var game = Game(emptyList())
}
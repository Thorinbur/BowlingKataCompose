class Parser {
    fun getRepresentation(frame: Frame?, bonus1:Int? = null, bonus2: Int? = null): String {
        if (frame == null) return ""
        val firstRoll = getCharacter(frame.firstRoll)
        val secondRoll = if (frame.isSpare) "/" else getCharacter(frame.secondRoll)
        val bonusRoll1 = getCharacter(bonus1)
        val bonusRoll2 = getCharacter(bonus2)
        return "$firstRoll$secondRoll$bonusRoll1$bonusRoll2"
    }

    private fun getCharacter(roll: Int?) = when (roll){
            10 -> "x"
            0 -> "-"
            null -> ""
            else -> roll.toString()
        }
}
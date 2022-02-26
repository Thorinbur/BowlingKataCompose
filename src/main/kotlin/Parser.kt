class Parser {
    fun getRepresentation(frame: Frame?, bonus1:Int? = null, bonus2: Int? = null): String {
        if (frame == null) return ""
        val firstRoll = if (frame.isStrike) "x" else frame.firstRoll
        val secondRoll = if (frame.isSpare) "/" else frame.secondRoll?:""
        val bonusRoll1 = getBonusRepresentation(bonus1)
        val bonusRoll2 = getBonusRepresentation(bonus2)
        return "$firstRoll$secondRoll$bonusRoll1$bonusRoll2"
    }

    private fun getBonusRepresentation(bonus1: Int?) = bonus1?.let { if (it == 10) "x" else it.toString() } ?: ""
}
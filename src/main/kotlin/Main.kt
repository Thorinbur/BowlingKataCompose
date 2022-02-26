// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlin.random.Random

private const val GAME_LENGTH = 10

@Composable
@Preview
fun App() {
    val simulator = Simulator(GAME_LENGTH)
    val random = Random
    var game by remember { mutableStateOf(simulator.game)}

    MaterialTheme {
        Column {
            ScoreBoard(game, Scorer(), Parser())
            Spacer(Modifier.size(4.dp))
            Row{
                Button(onClick = {
                    simulator.addRoll(random.nextInt(simulator.pinsRemaining + 1))
                    game = simulator.game
                }){
                    Text("Simulate Roll")
                }
                Spacer(Modifier.size(8.dp))
                Button(onClick = {
                    simulator.game = Game(emptyList())
                    game = simulator.game
                }){
                    Text("Reset")
                }
            }
        }

    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ScoreBoard(game: Game, scorer: Scorer, parser: Parser) {
    Row {
        repeat(GAME_LENGTH) {
            val representation = if (it == GAME_LENGTH - 1){
                parser.getRepresentation(game.frames.getOrNull(it), game.bonusRoll1, game.bonusRoll2)
            } else {
                parser.getRepresentation(game.frames.getOrNull(it))
            }
            Frame(scorer.scoreFrame(game, it), representation)
        }
        Total(scorer.scoreGame(game))
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Frame(frameScore: Int?, frameRolls: String) {
    Row(Modifier.border(BorderStroke(2.dp, Color.Black))) {
        Score(frameScore)
        Box(Modifier.border(BorderStroke(1.dp, Color.Black)).padding(all = 4.dp)) {
            Rolls(frameRolls.padEnd(3, ' '))
        }
    }
}

@Composable
fun Total(frameScore: Int) {
    Box(Modifier.border(BorderStroke(2.dp, Color.Black)).padding(end = 6.dp)) {
        Score(frameScore)
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Score(score: Int?) {
    Box(modifier = Modifier.padding(8.dp, 8.dp, 2.dp, 8.dp)) {
        Text((score?:"").toString().padEnd(2, ' '), fontSize = TextUnit(18f, TextUnitType.Sp))
    }
}


@OptIn(ExperimentalUnitApi::class)
@Composable
fun Rolls(rolls: String) {
    Text(rolls, fontSize = TextUnit(14f, TextUnitType.Sp))
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

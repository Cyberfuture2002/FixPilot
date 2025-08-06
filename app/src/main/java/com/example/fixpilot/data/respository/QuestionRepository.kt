package com.example.fixpilot.data.respository

import com.example.fixpilot.data.model.Answer
import com.example.fixpilot.data.model.Question

object QuestionRepository {

    private val questions = listOf(
        Question(
            id = "q1",
            text = "Startet dein PC überhaupt, wenn du ihn einschaltest?",
            answers = listOf(
                Answer("Ja", "q2"),
                Answer("Nein", "q11")  // Problem PC startet nicht
            )
        ),
        Question(
            id = "q2",
            text = "Zeigt dein Monitor ein Bild oder bleibt er schwarz?",
            answers = listOf(
                Answer("Bild wird angezeigt", "q3"),
                Answer("Bild bleibt schwarz", "q12") // Problem Bildschirm schwarz
            )
        ),
        Question(
            id = "q3",
            text = "Stürzt dein PC unerwartet ab oder rebootet er plötzlich?",
            answers = listOf(
                Answer("Ja", "q13"), // Problem PC stürzt ab
                Answer("Nein", "q4")
            )
        ),
        Question(
            id = "q4",
            text = "Funktioniert deine Internetverbindung ohne Probleme?",
            answers = listOf(
                Answer("Ja", "q5"),
                Answer("Nein", "q14") // Problem Internetverbindung
            )
        ),
        Question(
            id = "q5",
            text = "Laufen deine Programme flüssig oder reagieren sie langsam?",
            answers = listOf(
                Answer("Langsam oder hängen", "q15"), // Problem Programme langsam
                Answer("Flüssig", "q6")
            )
        ),
        Question(
            id = "q6",
            text = "Hast du Ton über Lautsprecher oder Kopfhörer?",
            answers = listOf(
                Answer("Ja", "q7"),
                Answer("Nein", "q16") // Problem kein Ton
            )
        ),
        Question(
            id = "q7",
            text = "Funktionieren Tastatur und Maus wie gewohnt?",
            answers = listOf(
                Answer("Ja", "q8"),
                Answer("Nein", "q17") // Problem Eingabegeräte
            )
        ),
        Question(
            id = "q8",
            text = "Funktionieren Windows Updates problemlos?",
            answers = listOf(
                Answer("Ja", "q9"),
                Answer("Nein", "q18") // Problem Updates schlagen fehl
            )
        ),
        Question(
            id = "q9",
            text = "Ist dein PC ruhig oder wird er sehr laut?",
            answers = listOf(
                Answer("Sehr laut", "q19"), // Problem PC laut
                Answer("Ruhig", "q10")
            )
        ),
        Question(
            id = "q10",
            text = "Hast du versehentlich wichtige Dateien gelöscht?",
            answers = listOf(
                Answer("Ja", "q20"), // Problem Datei gelöscht
                Answer("Nein", "") // Ende, kein Problem erkannt
            )
        ),

        // Lösungen (keine Antworten, Endpunkte)
        Question(
            id = "q11",
            text = "Der PC startet nicht. Prüfe Stromversorgung, Kabel und Netzteil.",
            answers = emptyList()
        ),
        Question(
            id = "q12",
            text = "Der Bildschirm bleibt schwarz. Überprüfe Monitoranschlüsse und Grafikkarte.",
            answers = emptyList()
        ),
        Question(
            id = "q13",
            text = "PC stürzt ab. Überprüfe CPU-Temperatur, Lüfter und RAM.",
            answers = emptyList()
        ),
        Question(
            id = "q14",
            text = "Internet funktioniert nicht. Starte Router neu und prüfe Netzwerkeinstellungen.",
            answers = emptyList()
        ),
        Question(
            id = "q15",
            text = "Programme sind langsam. Schließe unnötige Anwendungen und prüfe Festplatte.",
            answers = emptyList()
        ),
        Question(
            id = "q16",
            text = "Kein Ton. Prüfe Soundeinstellungen und Treiber.",
            answers = emptyList()
        ),
        Question(
            id = "q17",
            text = "Eingabegeräte funktionieren nicht. Prüfe USB-Anschlüsse und Treiber.",
            answers = emptyList()
        ),
        Question(
            id = "q18",
            text = "Windows-Updates schlagen fehl. Führe Update-Problembehandlung aus.",
            answers = emptyList()
        ),
        Question(
            id = "q19",
            text = "PC ist laut. Reinige Lüfter und überprüfe Temperaturen.",
            answers = emptyList()
        ),
        Question(
            id = "q20",
            text = "Datei gelöscht. Prüfe Papierkorb oder nutze Wiederherstellungssoftware.",
            answers = emptyList()
        )
    )

    fun getQuestion(id: String): Question? {
        return questions.find { it.id == id }
    }
}

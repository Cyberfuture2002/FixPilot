package com.example.fixpilot.data.respository

import com.example.fixpilot.data.model.Answer
import com.example.fixpilot.data.model.Question

object QuestionRepository {

    val tooltipInfos: Map<String, String> = mapOf(
        "Treiber" to "Ein Treiber ist eine Software, die das Betriebssystem mit der Hardware kommunizieren lässt.",
        "Soundeinstellungen" to "Hier kannst du z. B. Lautsprecher auswählen und Lautstärke einstellen.",
        "Festplatte" to "Speichert Programme und Dateien dauerhaft.",
        "Eingabegeräte" to "Tastatur, Maus oder ähnliche Geräte zur Steuerung des PCs.",
        "Update-Problembehandlung" to "Windows-Tool, das bei Update-Problemen hilft.",
        "Grafikkarte" to "Erzeugt die Bilder für den Monitor.",
        "RAM" to "Kurzzeitspeicher für schnelle Datenzugriffe.",
        "USB-Anschlüsse" to "Verbindungen für Peripheriegeräte wie Maus und Tastatur.",
        "Lüfter" to "Kühlt PC-Komponenten.",
        "Papierkorb" to "Temporärer Speicher gelöschter Dateien.",
        "CPU" to "Der Hauptprozessor, führt Befehle aus."
    )

    private val questions = listOf(
        Question(
            id = "q1",
            text = "Startet dein PC überhaupt, wenn du ihn einschaltest?",
            answers = listOf(
                Answer("Ja", "q2"),
                Answer("Nein", "q11")
            )
        ),
        Question(
            id = "q2",
            text = "Zeigt dein Monitor ein Bild oder bleibt er schwarz?",
            answers = listOf(
                Answer("Bild wird angezeigt", "q3"),
                Answer("Bild bleibt schwarz", "q12")
            )
        ),
        Question(
            id = "q3",
            text = "Stürzt dein PC unerwartet ab oder rebootet er plötzlich?",
            answers = listOf(
                Answer("Ja", "q13"),
                Answer("Nein", "q4")
            )
        ),
        Question(
            id = "q4",
            text = "Funktioniert deine Internetverbindung ohne Probleme?",
            answers = listOf(
                Answer("Ja", "q5"),
                Answer("Nein", "q14")
            )
        ),
        Question(
            id = "q5",
            text = "Laufen deine Programme flüssig oder reagieren sie langsam?",
            answers = listOf(
                Answer("Langsam oder hängen", "q15"),
                Answer("Flüssig", "q6")
            )
        ),
        Question(
            id = "q6",
            text = "Hast du Ton über {Soundeinstellungen} oder Kopfhörer?",
            answers = listOf(
                Answer("Ja", "q7"),
                Answer("Nein", "q16")
            )
        ),
        Question(
            id = "q7",
            text = "Funktionieren {Eingabegeräte} wie Tastatur und Maus wie gewohnt?",
            answers = listOf(
                Answer("Ja", "q8"),
                Answer("Nein", "q17")
            )
        ),
        Question(
            id = "q8",
            text = "Funktionieren Windows Updates problemlos?",
            answers = listOf(
                Answer("Ja", "q9"),
                Answer("Nein", "q18")
            )
        ),
        Question(
            id = "q9",
            text = "Ist dein PC ruhig oder wird er sehr laut?",
            answers = listOf(
                Answer("Sehr laut", "q19"),
                Answer("Ruhig", "q10")
            )
        ),
        Question(
            id = "q10",
            text = "Hast du versehentlich wichtige Dateien gelöscht?",
            answers = listOf(
                Answer("Ja", "q20"),
                Answer("Nein", "")
            )
        ),

        // Endpunkte
        Question(
            id = "q11",
            text = "Der PC startet nicht. Prüfe Stromversorgung, Kabel und Netzteil.",
            answers = emptyList()
        ),
        Question(
            id = "q12",
            text = "Der Bildschirm bleibt schwarz. Überprüfe Monitoranschlüsse und {Grafikkarte}.",
            answers = emptyList()
        ),
        Question(
            id = "q13",
            text = "PC stürzt ab. Überprüfe {CPU}, {Lüfter} und {RAM}.",
            answers = emptyList()
        ),
        Question(
            id = "q14",
            text = "Internet funktioniert nicht. Starte Router neu und prüfe Netzwerkeinstellungen.",
            answers = emptyList()
        ),
        Question(
            id = "q15",
            text = "Programme sind langsam. Schließe unnötige Anwendungen und prüfe {Festplatte}.",
            answers = emptyList()
        ),
        Question(
            id = "q16",
            text = "Kein Ton. Prüfe {Soundeinstellungen} und {Treiber}.",
            answers = emptyList()
        ),
        Question(
            id = "q17",
            text = "{Eingabegeräte} funktionieren nicht. Prüfe {USB-Anschlüsse} und {Treiber}.",
            answers = emptyList()
        ),
        Question(
            id = "q18",
            text = "Windows-Updates schlagen fehl. Führe {Update-Problembehandlung} aus.",
            answers = emptyList()
        ),
        Question(
            id = "q19",
            text = "PC ist laut. Reinige {Lüfter} und überprüfe Temperaturen.",
            answers = emptyList()
        ),
        Question(
            id = "q20",
            text = "Datei gelöscht. Prüfe {Papierkorb} oder nutze Wiederherstellungssoftware.",
            answers = emptyList()
        )
    )

    fun getQuestion(id: String): Question? {
        return questions.find { it.id == id }
    }
}

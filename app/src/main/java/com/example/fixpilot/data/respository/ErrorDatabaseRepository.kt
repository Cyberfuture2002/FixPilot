package com.example.fixpilot.data.respository

import com.example.fixpilot.data.model.ErrorEntry

object ErrorDatabase {

    private val errors = listOf(
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1001", "System startet nicht, schwarzer Bildschirm.", "Prüfe alle Kabel und Peripheriegeräte. Starte im abgesicherten Modus und führe eine Systemwiederherstellung durch."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1002", "Blauer Bildschirm mit Fehlercode 0x0000007B (INACCESSIBLE_BOOT_DEVICE).", "Überprüfe die Bootreihenfolge und verbinde keine externen Laufwerke. Aktualisiere die Festplattentreiber."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1003", "Windows Update schlägt fehl mit Fehlercode 0x80070057.", "Setze den Windows Update-Cache zurück und führe das Update erneut aus."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1004", "Explorer.exe stürzt regelmäßig ab.", "Starte den Explorer-Prozess neu und führe einen Malware-Scan durch."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1005", "Netzwerkverbindung wird nicht aufgebaut, Fehlercode 0x800704cf.", "Setze die Netzwerkadapter zurück und überprüfe die IP-Konfiguration."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1006", "Drucker wird nicht erkannt.", "Installiere den Druckertreiber neu und überprüfe die Druckerwarteschlange."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1007", "Fehler 0xC0000005 beim Programmstart (Zugriffsverletzung).", "Führe eine Systemdateiprüfung (sfc /scannow) durch und aktualisiere die Treiber."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1008", "Windows startet langsam und reagiert träge.", "Führe eine Festplattenprüfung (chkdsk) durch und bereinige temporäre Dateien."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1009", "Fehler 0x80070005 – Zugriff verweigert bei Update.", "Führe Windows Update mit administrativen Rechten aus und überprüfe Berechtigungen."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1010", "Task-Manager lässt sich nicht öffnen.", "Starte den Rechner neu und überprüfe, ob Malware aktiv ist."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1011", "System friert ein und reagiert nicht mehr.", "Überprüfe die Hardwaretemperaturen und führe einen Speichertest durch."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1012", "Datei-Explorer zeigt keine Dateien an.", "Starte den Explorer-Prozess neu und setze den Ordner-Cache zurück."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1013", "Windows-Registrierung beschädigt.", "Führe eine Systemwiederherstellung durch oder setze das System zurück."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1014", "Fehler 0x800F0922 bei der Installation von Updates.", "Stelle sicher, dass die Internetverbindung aktiv ist und genügend Speicherplatz vorhanden ist."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1015", "Bluetooth funktioniert nicht mehr.", "Starte den Bluetooth-Dienst neu und installiere den Treiber neu."),
        ErrorEntry("Betriebssystem", "Windows", null, "WIN32-500", "Windows startet mit der Fehlermeldung 'Critical Process Died'.", "Führe eine Reparaturinstallation durch und überprüfe die Festplatte auf Fehler."),
        ErrorEntry("Betriebssystem", "Windows", null, "NET-404", "Netzwerkgerät nicht gefunden.", "Setze den Netzwerkadapter zurück und installiere den Treiber neu."),
        ErrorEntry("Betriebssystem", "Windows", null, "BOOT_FAIL_01", "Bootfehler: Kein Betriebssystem gefunden.", "Überprüfe die Bootreihenfolge im BIOS und repariere den Bootsektor mit 'bootrec'."),
        ErrorEntry("Betriebssystem", "Windows", null, "UPDATE_ERR_01", "Windows Update hängt bei 0% fest.", "Starte den Windows Update-Dienst neu und lösche den SoftwareDistribution-Ordner."),
        ErrorEntry("Betriebssystem", "Windows", null, "MEM_ERR_01", "Speicherfehler: Windows erkennt RAM nicht korrekt.", "Führe einen RAM-Test mit Memtest86 durch und tausche fehlerhafte Module aus."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1016", "Fehler beim Starten von Windows-Diensten.", "Überprüfe Dienste auf manuelle Deaktivierung und setze sie zurück."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1017", "USB-Gerät wird nicht erkannt.", "Teste USB-Geräte an anderen Ports, aktualisiere USB-Treiber."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1018", "Fehler 0x80070020: Zugriff verweigert bei Dateizugriff.", "Schließe Programme, die die Datei nutzen, und versuche es erneut."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1019", "Windows Defender lässt sich nicht aktivieren.", "Überprüfe, ob andere Antivirenprogramme installiert sind, und deaktiviere sie."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1020", "Fehler beim Laden von Treibern nach Update.", "Führe eine Treiber-Rollback durch oder installiere Treiber neu."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1021", "Dateisystemfehler bei Zugriff auf NTFS-Partition.", "Führe chkdsk auf der betroffenen Partition aus."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1022", "Fehler 0x80073712 bei Windows Update.", "Führe das Tool zur Problembehandlung für Windows Update aus."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1023", "Bildschirmflackern nach Grafikkartentreiber-Update.", "Installiere einen älteren Treiber oder aktualisiere auf die neueste Version."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1024", "Fehler 0x80070490 bei Update.", "Bereinige beschädigte Update-Dateien mit DISM-Tool."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1025", "Windows-Explorer reagiert langsam.", "Lösche temporäre Dateien und aktualisiere den Grafikkartentreiber."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1026", "Audioausgabe funktioniert nicht.", "Überprüfe Soundeinstellungen und installiere Audiotreiber neu."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1027", "Fehler beim Herunterfahren.", "Prüfe laufende Prozesse und aktualisiere Systemkomponenten."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1028", "Systemdateien beschädigt.", "Führe sfc /scannow aus, um Dateien zu reparieren."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1029", "Fehler 0x80070570 – beschädigte Dateien beim Kopieren.", "Versuche, die Dateien mit einem anderen Programm zu kopieren oder lade sie neu herunter."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1030", "Netzwerkfreigabe nicht erreichbar.", "Prüfe Freigabeberechtigungen und Netzwerkeinstellungen."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1031", "Problem mit Windows Lizenzaktivierung.", "Führe die Aktivierung erneut aus und prüfe die Internetverbindung."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1032", "Fehler 0xC004F074 – Aktivierungsserver nicht erreichbar.", "Stelle sicher, dass keine Firewall die Verbindung blockiert."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1033", "Windows Store Apps lassen sich nicht öffnen.", "Setze den Store-Cache zurück (wsreset)."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1034", "Fehler 0x80073CF9 beim Installieren von Apps.", "Bereinige den Windows Apps-Cache und versuche es erneut."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1035", "Systemzeit wird nicht synchronisiert.", "Überprüfe die Zeiteinstellungen und aktiviere die automatische Zeitsynchronisation."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1036", "Bildschirmauflösung lässt sich nicht ändern.", "Aktualisiere den Grafikkartentreiber und setze die Anzeigeeinstellungen zurück."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1037", "Bluetooth-Gerät koppelt nicht.", "Entferne das Gerät aus der Liste und koppel neu."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1038", "Windows Firewall lässt sich nicht aktivieren.", "Setze die Firewall-Einstellungen zurück."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1039", "Fehler 0x80072EE7 – DNS-Server nicht erreichbar.", "Setze die DNS-Einstellungen zurück und überprüfe die Netzwerkverbindung."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1040", "Anmeldefehler: Benutzerprofil kann nicht geladen werden.", "Erstelle ein neues Benutzerprofil oder repariere das bestehende."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1041", "Fehler 0x800705B4 bei Windows Update.", "Starte den Windows Update-Dienst neu und führe Problembehandlung aus."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1042", "Fehler 0xC1900101 – Treiber inkompatibel bei Update.", "Deinstalliere problematische Treiber vor Update."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1043", "Windows aktiviert keine automatische Reparatur.", "Erstelle einen Wiederherstellungsdatenträger und führe Reparatur manuell durch."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1044", "Fehler 0x80070002 – Datei nicht gefunden beim Update.", "Prüfe Update-Dateien auf Vollständigkeit und setze Update-Komponenten zurück."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1045", "Fehler bei der Anmeldung mit Microsoft-Konto.", "Überprüfe Internetverbindung und Kontoeinstellungen."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1046", "Dienste starten nicht automatisch.", "Überprüfe Dienstkonfiguration und setze Starttyp neu."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1047", "Fehler 0x80004005 – unbekannter Fehler.", "Prüfe die Ereignisanzeige und führe Systemdiagnose durch."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1048", "Fehler 0x8007000E – nicht genügend Speicher.", "Schließe unnötige Programme und erweitere virtuellen Arbeitsspeicher."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1049", "Windows Sicherheitsupdates lassen sich nicht installieren.", "Führe Windows Update-Problembehandlung aus und lösche den Update-Cache."),
        ErrorEntry("Betriebssystem", "Windows", null, "ERR1050", "Fehler 0x80240016 – Update wurde übersprungen.", "Starte das Update manuell neu.")
    )

    fun findError(
        category: String,
        system: String,
        program: String?,
        errorCode: String
    ): ErrorEntry? {
        return errors.find {
            it.category == category &&
                    it.system == system &&
                    it.program == program &&
                    it.errorCode.equals(errorCode, ignoreCase = true)
        }
    }

    fun getErrorCodesForCategory(category: String?): List<String> {
        return if (category == null) emptyList()
        else errors.filter { it.category == category }.map { it.errorCode }
    }
}

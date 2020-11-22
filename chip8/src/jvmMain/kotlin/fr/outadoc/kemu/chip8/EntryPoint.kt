package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.display.SwingChip8Runner
import javax.swing.SwingUtilities
import javax.swing.UIManager

fun main() {
    try {
        System.setProperty("apple.laf.useScreenMenuBar", "true")
        System.setProperty(
            "com.apple.mrj.application.apple.menu.about.name",
            SwingChip8Runner.APP_TITLE
        )
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    SwingUtilities.invokeLater {
        SwingChip8Runner().show()
    }
}
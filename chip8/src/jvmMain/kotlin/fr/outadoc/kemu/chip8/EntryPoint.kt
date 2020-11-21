package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.display.JvmDesktopChip8Window
import javax.swing.SwingUtilities
import javax.swing.UIManager

fun main() {
    try {
        System.setProperty("apple.laf.useScreenMenuBar", "true")
        System.setProperty("com.apple.mrj.application.apple.menu.about.name",
            JvmDesktopChip8Window.APP_TITLE
        )
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    SwingUtilities.invokeLater {
        JvmDesktopChip8Window().start()
    }
}
package fr.outadoc.kemu.chip8.desktop

import java.awt.Toolkit
import javax.swing.*

fun menuBar(block: JMenuBar.() -> Unit): JMenuBar {
    return JMenuBar().apply {
        block()
    }
}

fun JMenuBar.menu(title: String, block: JMenu.() -> Unit): JMenu {
    return JMenu(title).apply {
        block()
        this@menu.add(this)
    }
}

fun JMenu.item(title: String, shortcut: Int? = null, block: JMenuItem.() -> Unit): JMenuItem {
    return JMenuItem(title).apply {
        shortcut?.let { shortcut ->
            accelerator = KeyStroke.getKeyStroke(
                shortcut,
                Toolkit.getDefaultToolkit().menuShortcutKeyMask
            )
        }

        block()
        this@item.add(this)
    }
}

var JMenuItem.onClick: () -> Unit
    get() = { actionListeners.firstOrNull() }
    set(value) {
        actionListeners.forEach {
            removeActionListener(it)
        }

        addActionListener {
            value()
        }
    }

fun JMenu.group(title: String, block: JMenuWithGroup.() -> Unit): JMenuWithGroup {
    return JMenuWithGroup(
        menu = JMenu(title),
        group = ButtonGroup()
    ).apply {
        block()
        this@group.add(menu)
    }
}

fun JMenuWithGroup.option(title: String, block: JRadioButtonMenuItem.() -> Unit): JRadioButtonMenuItem {
    return JRadioButtonMenuItem(title).apply {
        menu.add(this)
        group.add(this)
        block()
    }
}

class JMenuWithGroup(val menu: JMenu, val group: ButtonGroup)
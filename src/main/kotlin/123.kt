import java.awt.Color
import java.awt.Rectangle
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.SwingUtilities


fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        val myFrame = JFrame("Hello World")
        myFrame.setSize(900, 700)
        myFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        myFrame.layout = null
        for (i in 1..20) {
            for (j in 1..20) {
                val myButton = JButton()
                myButton.bounds = Rectangle(30 * i ,  30 * j, 30, 30)
                myFrame.add(myButton)
                myFrame.isVisible = true
                myButton.background = Color.BLACK
            }
        }
    }
}
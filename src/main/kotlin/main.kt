import java.awt.Color
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.filechooser.FileSystemView
import kotlin.random.Random
import javax.swing.JOptionPane



        fun main(args: Array<String>) {
            SwingUtilities.invokeLater {

                val SizeWindow : String
                SizeWindow = JOptionPane.showInputDialog("Input size")
                var kolvo1 = SizeWindow.toInt()

                //     ======================>
                val myFrame = JFrame("LIFE")
                myFrame.setSize(900, 700) // Магические константы
                myFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
                myFrame.layout = null

                val MoveButton = JButton("MOVE")
                myFrame.add(MoveButton)
                MoveButton.bounds = Rectangle(650, 500, 100, 90) // Магические константы

                var arrayButtonStatus: Array<Int> = arrayOf() // неговорящее название
                var arrayButton: Array<JButton> = arrayOf()

                val GenerateButton = JButton("GENERATE")
                myFrame.add(GenerateButton)
                GenerateButton.bounds = Rectangle(650, 350, 100, 90) // Магические константы

                for (i in 0..kolvo1-1) { // Магические константы
                    for (j in 0..kolvo1-1) { // Магические константы
                        val myButton = JButton()
                        myButton.bounds = Rectangle(j*(600/kolvo1), i*(600/kolvo1), 600/kolvo1, 600/kolvo1) // Магические константы
                        myFrame.add(myButton)
                        myFrame.isVisible = true
                        myButton.background = Color.BLACK
                        myButton.addActionListener(ActionListener {
                            if (myButton.background == Color.WHITE) myButton.background = Color.BLACK
                            else
                                myButton.background = Color.WHITE
                        })
                        arrayButton = arrayButton.plus(myButton)
                        arrayButtonStatus = arrayButtonStatus.plus(0)
                    }
                }

                GenerateButton.addActionListener(ActionListener {
                    // Этот список не нужен, можно же написать просто цикл
                    for (i in 0..kolvo1 * kolvo1-1) {
                        arrayButtonStatus[i] = (0..1).random()
                        if (arrayButtonStatus[i] == 1) arrayButton[i].background = Color.WHITE
                        else
                            arrayButton[i].background = Color.BLACK

                    }
                })

                MoveButton.addActionListener(ActionListener {
                    for (i in 0..kolvo1 * kolvo1-1) { // Магические константы
                        if (arrayButton[i].background == Color.BLACK ) arrayButtonStatus[i] = 0
                        else
                            arrayButtonStatus[i] = 1

                    }

                    for (i in 0..kolvo1 * kolvo1 - 1) { // Магические константы // И вообще этот цикл ужасен, тут намешаны в кучу отображение поля и бизнес-логика
                        var s = 0
                        if ((i % kolvo1) != 0 && (i - kolvo1 - 1 >= 0) && arrayButtonStatus[i - kolvo1 - 1] == 1) s++
                        if ((i - kolvo1 >= 0) && arrayButtonStatus[i - kolvo1] == 1) s++
                        if ((i % kolvo1) != kolvo1-1 && i - kolvo1 + 1 >= 0 && arrayButtonStatus[i - kolvo1 + 1] == 1) s++
                        if ((i % kolvo1) != 0 && arrayButtonStatus[i - 1] == 1) s++
                        if ((i % kolvo1) != kolvo1 - 1 && arrayButtonStatus[i + 1] == 1) s++
                        if ((i % kolvo1) != 0 && (i + kolvo1 - 1 <= kolvo1 * kolvo1 - 1) && arrayButtonStatus[i + kolvo1 - 1] == 1) s++
                        if ((i + kolvo1 <= kolvo1 * kolvo1 - 1) && arrayButtonStatus[i + kolvo1] == 1) s++
                        if (((i % kolvo1) != kolvo1 - 1) && (i + kolvo1 + 1 <= kolvo1 * kolvo1) && arrayButtonStatus[i + kolvo1 + 1] == 1 ) s++
                        if (((s == 3 || s == 2) && arrayButtonStatus[i] == 1) || ((s == 3) && arrayButtonStatus[i] == 0 )) arrayButton[i].background = Color.WHITE
                        else
                            arrayButton[i].background = Color.BLACK
                    }

                })
            }
        }





import java.awt.Color
import java.awt.Rectangle
import java.awt.event.ActionListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.swing.*
import kotlin.system.exitProcess


fun main(args: Array<String>) {

    SwingUtilities.invokeLater {
        val fileChooser = JFileChooser("data")

        var autoPlay = false
        var sizeWindow = 20
        var arrayLivGenInARow: Array<Int> = arrayOf()

        var minForLife = 2
        var maxForLife = 3
        var minForBirth = 3
        var maxForBirth = 3

        var arrayButtonStatus: Array<Int> = arrayOf() // неговорящее название

        var arrayButton: Array<JButton> = arrayOf()

        fun saveTable(file: File) {
            val writer = file.bufferedWriter()
            writer.write("$sizeWindow")
            writer.newLine()
            writer.write("$minForLife $maxForLife")
            writer.newLine()
            writer.write("$minForBirth $maxForBirth")
            writer.newLine()
            for (i in 0..sizeWindow-1) {
                for (j in 0..sizeWindow-1) {
                    if (arrayButtonStatus[i * sizeWindow + j] == 0) writer.write("0 ")
                    else
                        writer.write("1 ")
                }
                writer.newLine()
            }
            writer.close()
        }


        //     ======================>
        val myFrame = JFrame("LIFE")
        myFrame.setSize(900, 700) // Магические константы
        myFrame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
        myFrame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                val clickResult = JOptionPane.showConfirmDialog(myFrame, "Хотите сохранить текущее положение в файл?")
                if (clickResult == JOptionPane.OK_OPTION) {
                    val fileResult = fileChooser.showOpenDialog(null)
                    if (fileResult == JFileChooser.APPROVE_OPTION) {
                        val file = fileChooser.selectedFile
                        saveTable(file)
                        myFrame.isVisible
                        myFrame.dispose()
                        exitProcess(0)

                    }
                }
                if (clickResult == JOptionPane.NO_OPTION) {
                    myFrame.isVisible
                    myFrame.dispose()
                    exitProcess(0)
                }

            }
        })

        myFrame.layout = null

        val moveButton = JButton("MOVE")
        myFrame.add(moveButton)
        moveButton.bounds = Rectangle(650, 400, 100, 80) // Магические константы

        val rulesButton = JButton("RULES")
        myFrame.add(rulesButton)
        rulesButton.bounds = Rectangle(650, 500, 100, 80)

        val generateButton = JButton("GENERATE")
        myFrame.add(generateButton)
        generateButton.bounds = Rectangle(650, 300, 100, 80) // Магические константы

        val nMoveButton = JButton("N_MOVE")
        myFrame.add(nMoveButton)
        nMoveButton.bounds = Rectangle(650, 200, 100, 80)

        val autoMoveButton = JButton("AUTO")
        myFrame.add(autoMoveButton)
        autoMoveButton.bounds = Rectangle(650, 100, 100, 80)
        autoMoveButton.background = Color.RED


        fun loadTable(file: File) {
            val reader = file.bufferedReader()
            sizeWindow = reader.readLine().toInt()
            val forLifeRul = reader.readLine()
            minForLife = forLifeRul.trim().split(" ")[0].toInt()
            maxForLife = forLifeRul.trim().split(" ")[1].toInt()
            val forBirRul = reader.readLine()
            minForLife = forBirRul.trim().split(" ")[0].toInt()
            maxForLife = forBirRul.trim().split(" ")[1].toInt()
            arrayButtonStatus = reader.readLines().map { it.trim().split(" ") }.flatMap { it.toList() }.map{it.toInt()}.toTypedArray()
        }

        val returnValue = fileChooser.showOpenDialog(null)
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            val openedFile = fileChooser.selectedFile
            loadTable(openedFile)
        }
        if (returnValue == JFileChooser.CANCEL_OPTION) {
            sizeWindow = JOptionPane.showInputDialog("Введите размер окна").toInt()
            for (i in 0 until sizeWindow * sizeWindow) {
                arrayButtonStatus = arrayButtonStatus.plus(0)
            }
        }

        for (i in 0 until sizeWindow) { // Магические константы
            for (j in 0 until sizeWindow) { // Магические константы
                val myButton = JButton()
                myButton.bounds = Rectangle(
                    j * (600 / sizeWindow)+20,
                    i * (600 / sizeWindow)+20,
                    600 / sizeWindow,
                    600 / sizeWindow
                ) // Магические константы
                myFrame.add(myButton)
                myFrame.isVisible = true
                arrayButton = arrayButton.plus(myButton)
                if(arrayButtonStatus[i*20+j] == 0 ) myButton.background = Color.BLACK
                else
                    myButton.background = Color.WHITE
                myButton.addActionListener(ActionListener {
                    if (myButton.background == Color.WHITE) myButton.background = Color.BLACK
                    else
                        myButton.background = Color.WHITE
                })


            }
        }

        rulesButton.addActionListener(ActionListener {
            val forLife: String
            forLife =
                JOptionPane.showInputDialog("Введите минимальное и максимально кол-во клеток для выживания клетки (включительно)")
            minForLife = forLife.split(" ")[0].toInt()
            maxForLife = forLife.split(" ")[1].toInt()
            val forBirth: String
            forBirth =
                JOptionPane.showInputDialog("Введите минимальное и максимально кол-во клеток для рождения клетки (включительно)")
            minForBirth = forBirth.split(" ")[0].toInt()
            maxForBirth = forBirth.split(" ")[1].toInt()
        })

        fun move() {
            for (i in 0 until sizeWindow * sizeWindow) { // Магические константы
                if (arrayButton[i].background == Color.BLACK) arrayButtonStatus[i] = 0
                else
                    arrayButtonStatus[i] = 1
            }

            for (i in 0..sizeWindow * sizeWindow - 1) { // Магические константы // И вообще этот цикл ужасен, тут намешаны в кучу отображение поля и бизнес-логика
                var s = 0
                for (j in -1..1) {
                    for (k in -1..1) {
                        if (k == 0 && j == 0) continue
                        if ((i%sizeWindow!=sizeWindow-1 || k != 1) && (i%sizeWindow!=0 || k != -1) && (i + k + j * sizeWindow >= 0) && (i + k + j * sizeWindow < sizeWindow * sizeWindow) && arrayButtonStatus[i + k + j * sizeWindow] == 1) s++
                    }
                }
                if (((s >= minForLife && s <= maxForLife) && arrayButtonStatus[i] == 1) || ((s >= minForBirth && s <= maxForBirth) && arrayButtonStatus[i] == 0)) {
                    arrayButton[i].background = Color.WHITE
                } else {
                    arrayButton[i].background = Color.BLACK
                }

            }
        }

        autoMoveButton.addActionListener(ActionListener {
            if (autoMoveButton.background == Color.RED) {
                autoMoveButton.background = Color.BLUE
                autoPlay = !autoPlay
            } else {
                autoMoveButton.background = Color.RED
                autoPlay = !autoPlay
            }

        })

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
            if (autoPlay) move()
        }, 0, 200, TimeUnit.MILLISECONDS)

        fun randomGenerate() {
            for (i in 0..sizeWindow * sizeWindow - 1) {
                arrayButtonStatus[i] = (0..1).random()
                if (arrayButtonStatus[i] == 1) arrayButton[i].background = Color.WHITE
                else
                    arrayButton[i].background = Color.BLACK
            }
        }


        fun nMove(n: Int) {
            for (i in 1..n) {
                move()
            }
        }

        nMoveButton.addActionListener(ActionListener {
            val howMany: String
            howMany = JOptionPane.showInputDialog("Введите сколько ходов необходимо сделать")
            val howManyInt = howMany.toInt()
            nMove(howManyInt)
        })

        generateButton.addActionListener(ActionListener {
            // Этот список не нужен, можно же написать просто цикл
            randomGenerate()
        })

        moveButton.addActionListener(ActionListener {
            move()
        })
    }
}





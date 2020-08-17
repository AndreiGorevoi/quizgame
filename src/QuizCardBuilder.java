/*
@author Andrei Gorevoi
*/

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class QuizCardBuilder {
    JFrame frame;
    JPanel panel;
    JButton nextCardButton;
    JTextArea question;
    JTextArea answer;
    List<Card> cardList;


    public static void main(String[] args) {
        QuizCardBuilder quizCardBuilder = new QuizCardBuilder();
        quizCardBuilder.go();
    }

    public void go(){
//        Creating block
        frame = new JFrame();
        cardList = new LinkedList<>();
        Box box = new Box(BoxLayout.Y_AXIS);
        Box buttonsBox = new Box(BoxLayout.Y_AXIS);
        panel = new JPanel();
        nextCardButton= new JButton("next card");
        question = new JTextArea();
        answer = new JTextArea();
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu playMenu = new JMenu("Play");
        JMenuItem itemNew = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem start = new JMenuItem("start");

        JLabel questionLabel = new JLabel("Write your question:");
        JLabel answerLabel = new JLabel("Write answer: ");
        nextCardButton.addActionListener(new NextCardActionClicker());

        saveItem.addActionListener(new SaveCardsAction());
        itemNew.addActionListener(new NewMenuListener());
        start.addActionListener(new StartPlayListener());
        fileMenu.add(itemNew);
        fileMenu.add(saveItem);
        playMenu.add(start);
        menuBar.add(fileMenu);
        menuBar.add(playMenu);


        question.setColumns(20);
        answer.setColumns(20);
        answer.setRows(10);
        question.setRows(10);
        box.add(questionLabel);
        box.add(question);
        box.add(answerLabel);
        box.add(answer);
        buttonsBox.add(nextCardButton);
        panel.setBackground(Color.gray);
        panel.add(box);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.add(BorderLayout.CENTER,panel);
        frame.add(BorderLayout.SOUTH,buttonsBox);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    public class NextCardActionClicker implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            cardList.add(new Card(question.getText(),answer.getText()));
            answer.setText("");
            question.setText("");
        }
    }

    public class SaveCardsAction implements ActionListener{
        @SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }

    public class NewMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            cardList.clear();
            answer.setText("");
            question.setText("");
        }
    }

    public class StartPlayListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            QuizCardPlay qp = new QuizCardPlay();
            qp.repaintFrame();
        }
    }

    @SneakyThrows
    private void saveFile(File file){
        BufferedWriter bw = new BufferedWriter(new FileWriter(file+".txt"));
        for (Card card : cardList) {
            String tempString = card.getQuestion() + " / " + card.getAnswer();
            bw.write(tempString + "\n");
        }
        bw.close();
    }
}

/*
@author Andrei Gorevoi
*/

import lombok.Data;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Data
public class QuizCardPlay {
    JFrame playFrame;
    JTextArea question;
    JTextArea answer;
    JButton newQuestion;
    JButton showAnswer;
    List<Card> cardList;
    String currentQuestion;
    String currentAnswer;
    Box textAreaBox;
    Box buttonBox;


    public void repaintFrame(){
        textAreaBox = new Box(BoxLayout.Y_AXIS);
        buttonBox = new Box(BoxLayout.Y_AXIS);
        cardList = new LinkedList<>();
        newQuestion = new JButton("Next question");
        showAnswer = new JButton("Show answer");
        JLabel questionLabel = new JLabel("Write your question:");
        JLabel answerLabel= new JLabel("Write answer: ");
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem fileOpen = new JMenuItem("Open");
        fileOpen.addActionListener(new OpenFileListener());
        newQuestion.addActionListener(new NextQuestionListener());
        showAnswer.addActionListener(new ShowAnswerListener());
        file.add(fileOpen);
        menu.add(file);
        question= new JTextArea();
        answer = new JTextArea();
        question.setRows(10);
        question.setColumns(20);
        answer.setRows(10);
        answer.setColumns(20);
        textAreaBox.add(questionLabel);
        textAreaBox.add(question);
        textAreaBox.add(answerLabel);
        textAreaBox.add(answer);
        buttonBox.add(newQuestion);
        buttonBox.add(showAnswer);

        playFrame = new JFrame();
        playFrame.setJMenuBar(menu);
        playFrame.setSize(500,500);
        playFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        playFrame.add(BorderLayout.CENTER,textAreaBox);
        playFrame.add(BorderLayout.SOUTH,buttonBox);
        playFrame.setVisible(true);
    }

    public class OpenFileListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(playFrame);
            File file = fileChooser.getSelectedFile();
            startGame(file);
        }
    }
    public class ShowAnswerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            answer.setText(currentAnswer);
        }
    }
    public class NextQuestionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                currentQuestion = ((LinkedList<Card>) cardList).getFirst().getQuestion();
                currentAnswer = ((LinkedList<Card>) cardList).getFirst().getAnswer();
                ((LinkedList<Card>) cardList).removeFirst();
                question.setText(currentQuestion);
                answer.setText("");
            }catch (NoSuchElementException ex){
                question.setText("It was last question :(");
                answer.setText("");
            }

        }
    }

    @SneakyThrows
    private void startGame(File file){
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp;
        while ((temp=br.readLine())!=null){
            String[] splitArray = temp.split("/");
            cardList.add(new Card(splitArray[0],splitArray[1]));
        }
        br.close();
        currentQuestion = ((LinkedList<Card>) cardList).getFirst().getQuestion();
        currentAnswer = ((LinkedList<Card>) cardList).getFirst().getAnswer();
        ((LinkedList<Card>) cardList).removeFirst();
        question.setText(currentQuestion);
    }
}

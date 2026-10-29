import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class InteractiveQuiz extends JFrame {
    private final List<Question> questions = new ArrayList<>();
    private int current = 0;
    private int score = 0;

    private final JLabel questionLabel = new JLabel();
    private final JLabel progressLabel = new JLabel();
    private final JRadioButton[] options = {
        new JRadioButton(), new JRadioButton(), new JRadioButton(), new JRadioButton()
    };
    private final ButtonGroup group = new ButtonGroup();
    private final JButton nextButton = new JButton("Next");

    public InteractiveQuiz() {
        setTitle("Interactive Quiz - Java Swing");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        loadQuestions();

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20));
        progressLabel.setFont(new Font("Arial", Font.BOLD, 15));
        header.add(progressLabel, BorderLayout.WEST);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(25, 35, 10, 35));

        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(questionLabel);
        center.add(Box.createVerticalStrut(25));

        for (JRadioButton option : options) {
            option.setFont(new Font("Arial", Font.PLAIN, 16));
            option.setAlignmentX(Component.LEFT_ALIGNMENT);
            group.add(option);
            center.add(option);
            center.add(Box.createVerticalStrut(12));
        }

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
        nextButton.addActionListener(e -> nextQuestion());
        bottom.add(nextButton);

        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        showQuestion();
    }

    private void loadQuestions() {
        questions.add(new Question(
            "Which keyword is used to create a class in Java?",
            new String[]{"class", "struct", "define", "object"}, 0));

        questions.add(new Question(
            "Which component is used to display a single line of text in Swing?",
            new String[]{"JTextArea", "JLabel", "JButton", "JPanel"}, 1));

        questions.add(new Question(
            "Which method starts a Java application?",
            new String[]{"start()", "run()", "main()", "init()"}, 2));

        questions.add(new Question(
            "Which layout arranges components from left to right?",
            new String[]{"BorderLayout", "GridLayout", "FlowLayout", "CardLayout"}, 2));

        questions.add(new Question(
            "Which Swing component allows the user to select one option from several choices?",
            new String[]{"JRadioButton", "JTextField", "JLabel", "JPanel"}, 0));

        questions.add(new Question(
            "Which keyword is used to inherit a class in Java?",
            new String[]{"implements", "extends", "inherits", "super"}, 1));

        questions.add(new Question(
            "What is the default value of an int instance variable in Java?",
            new String[]{"1", "null", "0", "-1"}, 2));

        questions.add(new Question(
            "Which package contains most basic Swing components?",
            new String[]{"java.io", "java.awt", "javax.swing", "java.sql"}, 2));

        questions.add(new Question(
            "Which method is commonly used to set the size of a JFrame?",
            new String[]{"setSize()", "setFrame()", "size()", "resizeFrame()"}, 0));

        questions.add(new Question(
            "Which interface is commonly used for handling button clicks?",
            new String[]{"Runnable", "ActionListener", "MouseMotionListener", "Serializable"}, 1));
    }

    private void showQuestion() {
        Question q = questions.get(current);
        progressLabel.setText("Question " + (current + 1) + " of " + questions.size());
        questionLabel.setText("<html>" + (current + 1) + ". " + q.text + "</html>");

        group.clearSelection();
        for (int i = 0; i < options.length; i++) {
            options[i].setText(q.options[i]);
        }

        nextButton.setText(current == questions.size() - 1 ? "Finish" : "Next");
    }

    private void nextQuestion() {
        int selected = -1;
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(
                this, "Please select an answer.", "No Answer",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selected == questions.get(current).answer) {
            score++;
        }

        current++;

        if (current < questions.size()) {
            showQuestion();
        } else {
            showResult();
        }
    }

    private void showResult() {
        double percentage = score * 100.0 / questions.size();

        String message = String.format(
            "Quiz Completed!%n%nScore: %d/%d%nPercentage: %.1f%%",
            score, questions.size(), percentage);

        int choice = JOptionPane.showOptionDialog(
            this, message, "Quiz Result",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, new String[]{"Restart", "Exit"}, "Restart");

        if (choice == 0) {
            current = 0;
            score = 0;
            showQuestion();
        } else {
            System.exit(0);
        }
    }

    private static class Question {
        String text;
        String[] options;
        int answer;

        Question(String text, String[] options, int answer) {
            this.text = text;
            this.options = options;
            this.answer = answer;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InteractiveQuiz quiz = new InteractiveQuiz();
            quiz.setVisible(true);
        });
    }
}

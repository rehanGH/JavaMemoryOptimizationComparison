package Java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StringComparison {

    private static volatile boolean stopComparison = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("String Concatenation Comparison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton runButton = new JButton("Run Comparison");
        JButton stopButton = new JButton("Stop");
        JTextArea resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopComparison = false;
                runComparison(resultTextArea);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopComparison = true;
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(runButton);
        buttonPanel.add(stopButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    private static void runComparison(JTextArea resultTextArea) {
        // Strings to concatenate
        String str1 = "Hello";
        String str2 = "World";
        int iterations = 1000; // Number of iterations for performance comparison

        Thread comparisonThread = new Thread(() -> {
            while (!stopComparison) {
                // Measure and compare concatenation using StringBuilder
                long startTimeStringBuilder = System.nanoTime();
                StringBuilder stringBuilderResult = concatenateWithStringBuilder(str1, str2, iterations);
                long endTimeStringBuilder = System.nanoTime();
                long durationStringBuilder = endTimeStringBuilder - startTimeStringBuilder;

                // Measure and compare concatenation using +
                long startTimeClassicConcatenation = System.nanoTime();
                String classicConcatenationResult = concatenateUsingPlus(str1, str2, iterations);
                long endTimeClassicConcatenation = System.nanoTime();
                long durationClassicConcatenation = endTimeClassicConcatenation - startTimeClassicConcatenation;

                // Display results in the text area
                SwingUtilities.invokeLater(() -> {
                    resultTextArea.setText(String.format(
                            "Using StringBuilder: %d nanoseconds\nUsing + operator: %d nanoseconds\n\nResults:\n\n%s\n\n%s",
                            durationStringBuilder,
                            durationClassicConcatenation,
                            stringBuilderResult.toString(),
                            classicConcatenationResult
                    ));
                });

                // Sleep for a short interval to avoid UI freezing
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        comparisonThread.start();
    }

    private static StringBuilder concatenateWithStringBuilder(String str1, String str2, int iterations) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            result.append(str1).append(" ").append(str2).append("\n");
        }
        return result;
    }

    private static String concatenateUsingPlus(String str1, String str2, int iterations) {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += str1 + " " + str2 + "\n";
        }
        return result;
    }
}


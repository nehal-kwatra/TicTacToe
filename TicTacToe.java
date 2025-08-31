import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[] buttons = new JButton[9];
    private boolean xTurn = true;  // X always starts
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private boolean darkMode = false;  // Default: Light Mode
    private JPanel boardPanel;

    // Scoreboard
    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Status label
        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH);

        // Grid for buttons
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        Font btnFont = new Font("Arial", Font.BOLD, 40);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(btnFont);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            boardPanel.add(buttons[i]);
        }
        add(boardPanel, BorderLayout.CENTER);

        // Bottom panel (Reset + Theme Toggle)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 18));
        resetButton.addActionListener(e -> resetGame());
        bottomPanel.add(resetButton);

        JButton themeButton = new JButton("Toggle Theme");
        themeButton.setFont(new Font("Arial", Font.PLAIN, 18));
        themeButton.addActionListener(e -> toggleTheme());
        bottomPanel.add(themeButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Scoreboard at the bottom
        scoreLabel = new JLabel("X Wins: 0 | O Wins: 0 | Draws: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel, BorderLayout.NORTH);

        applyTheme(); // Apply default theme
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (!btn.getText().equals("")) return; // Ignore if already clicked

        btn.setText(xTurn ? "X" : "O");
        btn.setForeground(xTurn ? Color.RED : Color.BLUE);

        if (checkWin()) {
            if (xTurn) {
                xWins++;
                statusLabel.setText("Player X wins!");
            } else {
                oWins++;
                statusLabel.setText("Player O wins!");
            }
            updateScoreboard();
            disableButtons();
        } else if (isDraw()) {
            draws++;
            statusLabel.setText("It's a draw!");
            updateScoreboard();
        } else {
            xTurn = !xTurn;
            statusLabel.setText("Player " + (xTurn ? "X" : "O") + "'s turn");
        }
    }

    private boolean checkWin() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = buttons[i].getText();
        }

        // Check rows, columns, diagonals
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].equals("") &&
                board[i][0].equals(board[i][1]) &&
                board[i][1].equals(board[i][2])) return true;

            if (!board[0][i].equals("") &&
                board[0][i].equals(board[1][i]) &&
                board[1][i].equals(board[2][i])) return true;
        }

        if (!board[0][0].equals("") &&
            board[0][0].equals(board[1][1]) &&
            board[1][1].equals(board[2][2])) return true;

        if (!board[0][2].equals("") &&
            board[0][2].equals(board[1][1]) &&
            board[1][1].equals(board[2][0])) return true;

        return false;
    }

    private boolean isDraw() {
        for (JButton btn : buttons) {
            if (btn.getText().equals("")) return false;
        }
        return true;
    }

    private void disableButtons() {
        for (JButton btn : buttons) {
            btn.setEnabled(false);
        }
    }

    private void resetGame() {
        for (JButton btn : buttons) {
            btn.setText("");
            btn.setEnabled(true);
        }
        xTurn = true;
        statusLabel.setText("Player X's turn");
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        applyTheme();
    }

    private void applyTheme() {
        if (darkMode) {
            getContentPane().setBackground(Color.DARK_GRAY);
            statusLabel.setForeground(Color.WHITE);
            scoreLabel.setForeground(Color.ORANGE);
            boardPanel.setBackground(Color.BLACK);
            for (JButton btn : buttons) {
                btn.setBackground(Color.GRAY);
                btn.setForeground(Color.WHITE);
            }
        } else {
            getContentPane().setBackground(Color.WHITE);
            statusLabel.setForeground(Color.BLACK);
            scoreLabel.setForeground(Color.BLUE);
            boardPanel.setBackground(Color.LIGHT_GRAY);
            for (JButton btn : buttons) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.BLACK);
            }
        }
        repaint();
    }

    private void updateScoreboard() {
        scoreLabel.setText("X Wins: " + xWins + " | O Wins: " + oWins + " | Draws: " + draws);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}

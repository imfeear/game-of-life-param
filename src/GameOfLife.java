import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Implementação do Jogo da Vida de Conway com interface gráfica.
 */
public class GameOfLife {
    private JFrame frame;
    private JPanel gridPanel;
    private boolean[][] grid;
    private int rows;
    private int cols;
    private Timer timer;

    /**
     * Construtor da classe GameOfLife.
     *
     * @param rows Número de linhas do grid.
     * @param cols Número de colunas do grid.
     */
    public GameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new boolean[rows][cols];
        this.frame = new JFrame("Game of Life");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        this.gridPanel = new JPanel();
        this.gridPanel.setLayout(new GridLayout(rows, cols));
        this.frame.add(gridPanel, BorderLayout.CENTER);

        this.timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextGeneration();
                updateGrid();
            }
        });
    }

    /**
     * Inicia a interface gráfica.
     */
    public void start() {
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Preenche o grid com células vivas ou mortas com base em uma string de população.
     *
     * @param population String representando a população inicial.
     */
    public void populateFromString(String population) {
        String[] rows = population.split("#");
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            for (int j = 0; j < row.length(); j++) {
                char cell = row.charAt(j);
                grid[i][j] = (cell == '1');
            }
        }
    }

    /**
     * Preenche o grid com células vivas ou mortas aleatoriamente.
     */
    public void randomizeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Math.random() > 0.5;
            }
        }
    }

    private void updateGrid() {
        gridPanel.removeAll();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JPanel cellPanel = new JPanel();
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                cellPanel.setBackground(grid[i][j] ? Color.BLACK : Color.WHITE);
                gridPanel.add(cellPanel);
            }
        }
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Executa as gerações do jogo.
     *
     * @param generations Número de gerações a serem executadas.
     * @param speed       Velocidade em milissegundos entre as gerações.
     */
    /**
     * Executa as gerações do jogo.
     *
     * @param generations Número de gerações a serem executadas.
     * @param speed       Velocidade em milissegundos entre as gerações.
     */
    public void runGenerations(int generations, int speed) {
        for (int gen = 0; gen < generations; gen++) {
            System.out.println("Geração " + (gen + 1) + ":");
            printPopulation();
            nextGeneration();
            updateGrid();
            pause(speed);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Imprime a população atual no console.
     */
    private void printPopulation() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] ? "1" : "#");
            }
            System.out.println();
        }
        System.out.println();
    }
    /**
     * Pausa a execução do programa por um certo período de tempo.
     *
     * @param milliseconds O número de milissegundos a pausar.
     */
    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calcula a próxima geração do jogo aplicando as regras do Conway's Game of Life.
     */
    private void nextGeneration() {
        boolean[][] newGrid = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int neighbors = countNeighbors(i, j);
                if (grid[i][j]) {
                    // Regras para células vivas
                    if (neighbors < 2) {
                        newGrid[i][j] = false; // Solidão
                    } else if (neighbors > 3) {
                        newGrid[i][j] = false; // Superpopulação
                    } else {
                        newGrid[i][j] = true; // Sobrevive para a próxima geração
                    }
                } else {
                    // Regra para células mortas
                    if (neighbors == 3) {
                        newGrid[i][j] = true; // Ressuscita
                    }
                }
            }
        }

        grid = newGrid;
    }
    /**
     * Conta o número de vizinhos vivos de uma célula em uma posição específica no grid.
     *
     * @param x A coordenada x da célula.
     * @param y A coordenada y da célula.
     * @return O número de vizinhos vivos da célula.
     */
    private int countNeighbors(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < cols && !(i == x && j == y)) {
                    if (grid[i][j]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}


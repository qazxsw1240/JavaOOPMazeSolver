package org.tutoring.maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ListIterator;

/**
 * This GUI application class is for visualizing Maze.
 *
 * @author qazxsw1240
 */
public class MazeVisualizer {
    private final SwingFrame frame;

    /**
     * Creates a maze visualizer with the specified steps.
     *
     * @param mazeList The maze solving steps.
     */
    public MazeVisualizer(List<Maze> mazeList) {
        if (mazeList == null) {
            throw new IllegalArgumentException("maze is null.");
        }
        if (mazeList.isEmpty()) {
            throw new IllegalArgumentException("mazeList is empty.");
        }
        this.frame = new SwingFrame(mazeList);
    }

    /**
     * Starts the GUI application.
     */
    public void start() {
        this.frame.setVisible(true);
    }

    private static class SwingFrame extends JFrame {
        private static final String TITLE = "Maze Visualizer";

        private final ListIterator<Maze> iterator;
        private final StepManagerPanel stepManagerPanel;
        private final MazePaintPanel mazePaintPanel;

        public SwingFrame(List<Maze> mazeList) {
            super(TITLE);
            this.iterator = mazeList.listIterator();
            this.stepManagerPanel = new StepManagerPanel();
            this.mazePaintPanel = this.iterator.hasNext() ? new MazePaintPanel(this.iterator.next()) : null;
            initialize();
        }

        private void initialize() {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setResizable(false);
            add(this.stepManagerPanel, BorderLayout.NORTH);
            add(this.mazePaintPanel, BorderLayout.CENTER);
            pack();
            setLocationRelativeTo(null);
            this.stepManagerPanel.setPreviousButtonClickedListener(this::onPreviousButtonClicked);
            this.stepManagerPanel.setNextButtonClickedListener(this::onNextButtonClicked);
            setFocusable(true);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    onKeyPressed(e);
                }
            });
        }

        private void onPreviousButtonClicked() {
            if (!this.iterator.hasPrevious()) {
                return;
            }
            this.mazePaintPanel.render(this.iterator.previous());
        }

        private void onNextButtonClicked() {
            if (!this.iterator.hasNext()) {
                return;
            }
            this.mazePaintPanel.render(this.iterator.next());
        }

        private void onKeyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
                onPreviousButtonClicked();
                return;
            }
            if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
                onNextButtonClicked();
            }
        }
    }

    private static class StepManagerPanel extends JPanel {
        private final JButton previousButton;
        private final JButton nextButton;
        private volatile Runnable previousButtonClickedListener;
        private volatile Runnable nextButtonClickedListener;

        public StepManagerPanel() {
            this.previousButton = new JButton("Previous");
            this.nextButton = new JButton("Next");
            this.previousButtonClickedListener = null;
            this.nextButtonClickedListener = null;
            initialize();
        }

        public void setPreviousButtonClickedListener(Runnable listener) {
            if (listener == null) {
                throw new IllegalArgumentException("listener is null.");
            }
            this.previousButtonClickedListener = listener;
        }

        public void setNextButtonClickedListener(Runnable listener) {
            if (listener == null) {
                throw new IllegalArgumentException("listener is null.");
            }
            this.nextButtonClickedListener = listener;
        }

        private void initialize() {
            add(this.previousButton);
            add(this.nextButton);
            this.previousButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Runnable listener = StepManagerPanel.this.previousButtonClickedListener;
                    if (listener == null) {
                        return;
                    }
                    listener.run();
                }
            });
            this.nextButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Runnable listener = StepManagerPanel.this.nextButtonClickedListener;
                    if (listener == null) {
                        return;
                    }
                    listener.run();
                }
            });
        }
    }

    private static class MazePaintPanel extends JPanel {
        private static final int MIN_PANEL_SIZE = 200;
        private static final int PANEL_SIZE_BOUND = 800;
        private static final int TILE_SPAN = 40;
        private static final int PADDING = 100;

        private final int tileSpan;
        private volatile MazePainter mazePainter;

        public MazePaintPanel(Maze maze) {
            this.tileSpan = calculateTileSpan(maze);
            this.mazePainter = new MazePainter(this.tileSpan, maze);
            initialize();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (this.mazePainter == null) {
                return;
            }
            this.mazePainter.paint((Graphics2D) g);
        }

        @Override
        public Dimension getPreferredSize() {
            Maze maze = this.mazePainter.getMaze();
            int columnDimension = maze.getColumnDimension();
            int rowDimension = maze.getRowDimension();
            int columnTileSpan = Math.max(columnDimension * this.tileSpan, MIN_PANEL_SIZE);
            int rowTileSpan = Math.max(rowDimension * this.tileSpan, MIN_PANEL_SIZE);
            return new Dimension(columnTileSpan + PADDING, rowTileSpan + PADDING);
        }

        public void render(Maze maze) {
            this.mazePainter = new MazePainter(this.tileSpan, maze);
            repaint();
        }

        private void initialize() {
            setLayout(null);
        }

        private int calculateTileSpan(Maze maze) {
            int columnDimension = maze.getColumnDimension();
            int rowDimension = maze.getRowDimension();
            int columnTileSpan = columnDimension * TILE_SPAN;
            int rowTileSpan = rowDimension * TILE_SPAN;
            if (columnTileSpan > PANEL_SIZE_BOUND || rowTileSpan > PANEL_SIZE_BOUND) {
                return Math.min(PANEL_SIZE_BOUND / columnDimension, PANEL_SIZE_BOUND / rowDimension);
            }
            return TILE_SPAN;
        }
    }

    private static class MazePainter {
        private static final Stroke DEFAULT_STROKE = new BasicStroke(2.0f);

        private final int tileSpan;
        private final Maze maze;

        public MazePainter(int tileSpan, Maze maze) {
            this.tileSpan = tileSpan;
            this.maze = maze;
        }

        public Maze getMaze() {
            return this.maze;
        }

        public void paint(Graphics2D graphics) {
            Rectangle bounds = graphics.getClipBounds();
            int xSpan = this.tileSpan * this.maze.getColumnDimension();
            int ySpan = this.tileSpan * this.maze.getRowDimension();
            int halfPaddingX = (bounds.width - xSpan) / 2;
            int halfPaddingY = (bounds.height - ySpan) / 2;
            int currentXPadding = halfPaddingX;
            int currentYPadding = halfPaddingY;
            graphics.setColor(Color.BLACK);
            graphics.setStroke(DEFAULT_STROKE);
            graphics.drawRect(currentXPadding - 1, currentYPadding - 1, xSpan + 2, ySpan + 2);
            String s = this.maze.toString();
            for (char c : s.toCharArray()) {
                if (c == MazeTile.ROAD.toChar()) {
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(currentXPadding, currentYPadding, this.tileSpan, this.tileSpan);
                } else if (c == MazeTile.WALL.toChar()) {
                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(currentXPadding, currentYPadding, this.tileSpan, this.tileSpan);
                } else if (c == 'O') {
                    graphics.setColor(Color.BLUE);
                    graphics.fillRect(currentXPadding, currentYPadding, this.tileSpan, this.tileSpan);
                } else if (c == '\n') {
                    currentXPadding = halfPaddingX;
                    currentYPadding += this.tileSpan;
                    continue;
                }
                currentXPadding += this.tileSpan;
            }
        }
    }
}

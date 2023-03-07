package chessgame;

import chessgame.entity.AIPlayer;
import chessgame.entity.BoardGame;
import chessgame.entity.BoardGamePosition;
import chessgame.entity.ChessManColor;
import chessgame.entity.Chessman;
import chessgame.entity.Player;
import chessgame.entity.chessmantype.Bishop;
import chessgame.entity.chessmantype.King;
import chessgame.entity.chessmantype.Knight;
import chessgame.entity.chessmantype.Pawn;
import chessgame.entity.chessmantype.Queen;
import chessgame.entity.chessmantype.Rook;
import chessgame.gui.ImageManager;
import chessgame.handler.MouseHandler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.util.Random;
import javax.swing.JFrame;

public class Game extends JFrame {

    public static String GAME_TITLE = "Chess Board Game";
    public static int GAME_WIDTH = 720;
    public static int GAME_HEIGHT = 660;
    public static int GAME_EXTRA_WIDTH = 0;
    public static String PLAYER1_NAME = "";
    public static String PLAYER2_NAME = "Player 2";

    private String title;
    private int width;
    private int height;
    private boolean isRunning = true;
    private BoardGame boardGame;
    private Rule rule;
    private JFrame parent;
    private boolean isPlayWithAI;

    public Game(String title, int width, int height) throws HeadlessException {
        super(title);
        this.title = title;
        this.width = width;
        this.height = height;
        setVisible(true);
        setSize(width + GAME_EXTRA_WIDTH, height);
        setIconImage(ImageManager.getInstance().images.get("knight_black"));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        isRunning = false;
        parent.setVisible(true);
    }

    public Game() throws HeadlessException {
        this(GAME_TITLE, GAME_WIDTH, GAME_HEIGHT);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Rule getRule() {
        return rule;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    public JFrame getParent() {
        return parent;
    }

    public void setParent(JFrame parent) {
        this.parent = parent;
    }

    public Runnable start(boolean isPlayWithAI, JFrame parent) {
        this.isPlayWithAI = isPlayWithAI;
        this.parent = parent;
        return new Runnable() {
            @Override
            public void run() {
                Graphics g = getGraphics();
                long now;
                long updateTime;
                long wait;

                long lastFpsCheck = 0;
                int currentFps = 0;
                int totalFrames = 0;

                final int TARGET_FPS = 144;
                final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
                init();
                while (isRunning) {
                    now = System.nanoTime();
                    totalFrames++;
                    if (System.nanoTime() > lastFpsCheck + 1000000000) {
                        lastFpsCheck = System.nanoTime();
                        currentFps = totalFrames;
                        totalFrames = 0;
//                System.out.println("Current Fps: " + currentFps);
                    }
                    revalidate();
                    g.setColor(Color.white);
                    g.fillRect(0, 0, getWidth() - 10, getHeight() - 10);

                    try {
                        tick();
                        render(g);
                    } catch (Exception ex) {

                    }
                    updateTime = System.nanoTime() - now;
                    wait = (OPTIMAL_TIME - updateTime) / 1000000;

                    try {
                        Thread.sleep(wait);
                    } catch (Exception e) {
//                System.out.println(e.toString());
                    }
                }
            }
        };
    }

    public void init() {
        boardGame = new BoardGame(new BoardGamePosition[8][8], new Chessman[8][8], this);
        Player[] players = new Player[2];
        ChessManColor firstPlayerColor;
        ChessManColor secondPlayerColor;
        int randomChoose = new Random().nextInt(2);
        if (randomChoose == 0) {
            firstPlayerColor = ChessManColor.White;
            secondPlayerColor = ChessManColor.Black;
        } else {
            firstPlayerColor = ChessManColor.Black;
            secondPlayerColor = ChessManColor.White;
        }
        if (isPlayWithAI) {
            players[0] = new AIPlayer("AI", firstPlayerColor);
            players[1] = new Player(PLAYER2_NAME, secondPlayerColor);
        } else {
            players[0] = new Player(PLAYER1_NAME, firstPlayerColor);
            players[1] = new Player(PLAYER2_NAME, secondPlayerColor);
        }
        rule = new Rule(players, boardGame);
        String[] boardChess = {"pawn_black_1", "pawn_black_2", "pawn_black_3", "pawn_black_4", "pawn_black_5", "pawn_black_6", "pawn_black_7",
            "pawn_black_8", "pawn_white_1", "pawn_white_2", "pawn_white_3", "pawn_white_4", "pawn_white_5", "pawn_white_6", "pawn_white_7",
            "pawn_white_8", "rook_black_1", "knight_black_1", "bishop_black_1", "king_black", "queen_black", "bishop_black_2", "knight_black_2",
            "rook_black_2", "rook_white_1", "knight_white_1", "bishop_white_1", "king_white", "queen_white", "bishop_white_2", "knight_white_2",
            "rook_white_2"};
        for (String name : boardChess) {
            int id = 1;
            if (name.contains("_1") || name.contains("_2")
                    || name.contains("_3") || name.contains("_4")
                    || name.contains("_5") || name.contains("_6")
                    || name.contains("_7") || name.contains("_8")) {
                id = Integer.parseInt(name.split("_")[2]);
                name = name.replace("_1", "");
                name = name.replace("_2", "");
                name = name.replace("_3", "");
                name = name.replace("_4", "");
                name = name.replace("_5", "");
                name = name.replace("_6", "");
                name = name.replace("_7", "");
                name = name.replace("_8", "");
            }
            String imageName = name;
            ChessManColor color = null;
            if (name.contains("_black")) {
                color = ChessManColor.Black;
                name = name.replace("_black", "");
            } else {
                color = ChessManColor.White;
                name = name.replace("_white", "");
            }
            Chessman chessman = null;
            switch (name) {
                case "pawn":
                    chessman = new Pawn(id, "Pawn", color, null, ImageManager.getInstance().images.get(imageName));
                    break;
                case "rook":
                    chessman = new Rook(id, "Rook", color, null, ImageManager.getInstance().images.get(imageName));
                    break;
                case "bishop":
                    chessman = new Bishop(id, "Bishop", color, null, ImageManager.getInstance().images.get(imageName));
                    break;
                case "king":
                    chessman = new King(id, "King", color, null, ImageManager.getInstance().images.get(imageName));
                    break;
                case "queen":
                    chessman = new Queen(id, "Queen", color, null, ImageManager.getInstance().images.get(imageName));
                    break;
                case "knight":
                    chessman = new Knight(id, "Knight", color, null, ImageManager.getInstance().images.get(imageName));
                    break;
            }
            if (chessman == null) {
                isRunning = false;
            }
            boardGame.getChessMans().put(chessman.toString().toLowerCase(), chessman);
        }
        if (boardGame.getChessMans().values().size() > 0) {
            for (int i = 0; i < rule.getPlayers().length; i++) {
                Player player = rule.getPlayer(i);
                if (player == null) {
                    isRunning = false;
                    return;
                }
                for (Chessman chessman : boardGame.getChessMans().values()) {
                    if (chessman.getColor() == player.getColor()) {
                        player.getOwnChessmans().put(chessman.toString().toLowerCase(), chessman);
                        chessman.setPlayer(player);
                    }
                }
                if (rule.getPlayerTurn().equals(player)) {
                    rule.setPlayerTurn(player);
                }
            }
        }
        initRenderPosition();
        render(getGraphics());
        for (int i = 0; i < rule.getPlayers().length; i++) {
            Player player = rule.getPlayer(i);
            if (player != null && !player.getName().equals("ai")) {
                if (player.getOwnChessmans().size() > 0) {
                    for (Chessman chessman : player.getOwnChessmans().values()) {
                        MouseHandler mouseHandler = new MouseHandler(boardGame, chessman, player);
                        boardGame.getMouseHandlers().put(chessman.toString().toLowerCase(), mouseHandler);
                        addMouseListener(mouseHandler);
                    }
                }
            }
        }
        if (isPlayWithAI) {
            ((AIPlayer) players[0]).getEnemiesChessmans().addAll(players[1].getOwnChessmans().values());
            if (rule.isPlayWithAI()) {
                if (rule.getAiPlayer() != null) {
                    if (rule.getPlayerTurn() != null && rule.getPlayerTurn().equals(rule.getAiPlayer())) {
                        rule.getAiPlayer().decisionMove(boardGame);
                    }
                }
            }
        }
    }

    public void resetGame() {
        boardGame = null;
        rule = null;
        init();
    }

    public void tick() {
        if (rule.checkWinCondition()) {
            System.out.println(rule.getWinPlayer().toString() + " is winning.");
            resetGame();
        }
//        System.out.println(boardGame.getChessMans().get("queen_black_1").isDeath());
    }

    public void render(Graphics g) {
        Image background = ImageManager.getInstance().images.get("board");
        if (background == null) {
            return;
        }
        g.drawImage(background, 0, 0, width - 10, height - 10, null);
        g.setColor(Color.red);
        if (boardGame.getChessmanLocation().length > 0) {
            for (int i = 0; i < boardGame.getChessmanLocation().length; i++) {
                for (int j = 0; j < boardGame.getChessmanLocation()[i].length; j++) {
                    Chessman chessman = boardGame.getChessmanLocation()[i][j];
                    if (chessman != null) {
                        if (!chessman.isDeath()) {
                            g.drawImage(chessman.getImage(), chessman.getPositionBoardGame().getxPosition(),
                                    chessman.getPositionBoardGame().getyPosition(),
                                    chessman.getPositionBoardGame().getWidth(),
                                    chessman.getPositionBoardGame().getHeight(), null);
                        }
                    }
                }
            }
        }
        if (boardGame.getChessManClick() != null) {
            Chessman chessmanClick = boardGame.getChessManClick();
            if (chessmanClick.getPositionCanMake() != null
                    && chessmanClick.getPositionCanMake().size() > 0) {
                g.setColor(Color.LIGHT_GRAY);
                int length = chessmanClick.getPositionCanMake().size();
                for (int i = 0; i < length; i++) {
                    BoardGamePosition position = chessmanClick.getPositionCanMake().get(i);
                    g.fillOval(position.getxPosition() - 2, position.getyPosition() + 1,
                            position.getWidth(), position.getHeight());
                }
            }
            if (chessmanClick.getPositionKillChessman() != null
                    && chessmanClick.getPositionKillChessman().size() > 0) {
                g.setColor(Color.red);
                int length = chessmanClick.getPositionKillChessman().size();
                for (int i = 0; i < length; i++) {
                    BoardGamePosition position = chessmanClick.getPositionKillChessman().get(i);
                    g.drawRect(position.getxPosition() - 2, position.getyPosition() + 1,
                            position.getWidth(), position.getHeight());
                }
            }
        }
    }

    private void initRenderPosition() {
        int x = width / 18;
        int y = height / 20;
        int width = this.width / 10 + 6;
        int height = this.height / 9;
        int blackChessmansKingPos = 0;
        int blackChessmansPawnPos = 1;
        int whiteChessmansKingPos = 7;
        int whiteChessmansPawnPos = 6;
        String whiteKingDefPos = "king_white_1", whiteQueenDefPos = "queen_white_1";
        String blackKingDefPos = "king_black_1", blackQueenDefPos = "queen_black_1";
        if (!rule.isWhiteBottom()) {
            blackChessmansKingPos = 7;
            blackChessmansPawnPos = 6;
            whiteChessmansKingPos = 0;
            whiteChessmansPawnPos = 1;
            whiteKingDefPos = "queen_white_1";
            whiteQueenDefPos = "king_white_1";
            blackKingDefPos = "queen_black_1";
            blackQueenDefPos = "king_black_1";
        }
        for (int i = 0; i < 8; i++) {
            if (i == blackChessmansKingPos) {
                initBoardGamePosition(x, y, width, height, blackChessmansKingPos,
                        new String[]{"rook_black_1", "knight_black_1", "bishop_black_1", blackKingDefPos, blackQueenDefPos, "bishop_black_2", "knight_black_2",
                            "rook_black_2"});
            } else if (i == blackChessmansPawnPos) {
                initBoardGamePosition(x, y, width, height, blackChessmansPawnPos,
                        new String[]{"pawn_black_1", "pawn_black_2", "pawn_black_3", "pawn_black_4", "pawn_black_5", "pawn_black_6", "pawn_black_7",
                            "pawn_black_8"});
            } else if (i == whiteChessmansPawnPos) {
                initBoardGamePosition(x, y, width, height, whiteChessmansPawnPos,
                        new String[]{"pawn_white_1", "pawn_white_2", "pawn_white_3", "pawn_white_4", "pawn_white_5", "pawn_white_6", "pawn_white_7",
                            "pawn_white_8"});
            } else if (i == whiteChessmansKingPos) {
                initBoardGamePosition(x, y, width, height, whiteChessmansKingPos,
                        new String[]{"rook_white_1", "knight_white_1", "bishop_white_1", whiteKingDefPos, whiteQueenDefPos, "bishop_white_2", "knight_white_2",
                            "rook_white_2"});
            } else {
                initBoardGamePosition(x, y, width, height, i, null);
            }
            y = y + height;
        }
    }

    private void initBoardGamePosition(int x, int y, int width, int height, int boardLine, String[] chess) {
        if (chess == null) {
            for (int i = 0; i < 8; i++) {
                boardGame.getBoardGamePositions()[boardLine][i] = new BoardGamePosition(x, y, width, height,
                        boardLine, i);
                x = x + width;
            }
        } else {
            for (int i = 0; i < 8; i++) {
                Chessman chessman = boardGame.getChessMans().get(chess[i]);
                BoardGamePosition boardGPos = new BoardGamePosition(x, y, width, height,
                        boardLine, i);
                boardGame.getBoardGamePositions()[boardLine][i] = boardGPos;
                if (chessman != null) {
                    boardGame.getChessmanLocation()[boardLine][i] = chessman;
                    chessman.setPostionBoardGame(boardGPos);
                }
                x = x + width;
            }
        }
    }
}

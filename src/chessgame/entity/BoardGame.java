package chessgame.entity;

import chessgame.Game;
import chessgame.handler.MouseHandler;
import java.util.HashMap;
import java.util.Map;

public class BoardGame {
    private BoardGamePosition[][] boardGamePositions;
    private Chessman[][] chessmanLocation;
    private Map<String, Chessman> chessMans = new HashMap();
    private Map<String, MouseHandler> mouseHandlers = new HashMap<>();
    private final Game game;
    private Chessman chessManClick;

    public BoardGame(BoardGamePosition[][] boardGamePositions, Chessman[][] chessmanLocation, Game game) {
        this.boardGamePositions = boardGamePositions;
        this.chessmanLocation = chessmanLocation;
        this.game = game;
    }
       
    public BoardGamePosition[][] getBoardGamePositions() {
        return boardGamePositions;
    }

    public void setBoardGamePositions(BoardGamePosition[][] boardGamePositions) {
        this.boardGamePositions = boardGamePositions;
    }

    public Map<String, Chessman> getChessMans() {
        return chessMans;
    }

    public void setChessMans(Map<String, Chessman> chessMans) {
        this.chessMans = chessMans;
    }

    public Map<String, MouseHandler> getMouseHandlers() {
        return mouseHandlers;
    }

    public void setMouseHandlers(Map<String, MouseHandler> mouseHandlers) {
        this.mouseHandlers = mouseHandlers;
    }

    public Game getGame() {
        return game;
    }
    
    public Chessman[][] getCloneChessmanLocation() {
        Chessman[][] nChessmanLoc = new Chessman[chessmanLocation.length][chessmanLocation[0].length];
        for(int i = 0; i < nChessmanLoc.length;i++) {
            for(int j = 0; j < nChessmanLoc[i].length; j++) {
                nChessmanLoc[i][j] = chessmanLocation[i][j];
            }
        }
        return nChessmanLoc;
    }

    public Chessman[][] getChessmanLocation() {
        return chessmanLocation;
    }

    public void setChessmanLocation(Chessman[][] chessmanLocation) {
        this.chessmanLocation = chessmanLocation;
    }

    public Chessman getChessManClick() {
        return chessManClick;
    }

    public void setChessManClick(Chessman chessManClick) {
        this.chessManClick = chessManClick;
    }
    
    
}

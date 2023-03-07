package chessgame.entity;

import chessgame.Rule;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Chessman {

    protected int id;
    protected String name;
    protected ChessManColor color;
    protected BoardGamePosition positionBoardGame;
    protected List<BoardGamePosition> positionCanMake;
    protected List<BoardGamePosition> positionKillChessman;
    protected List<Chessman> chessmanListProtect;
    protected BufferedImage image;
    protected Player player;
    protected Ability[] abilitiesMove;
    protected boolean death;
    protected int point;

    public Chessman(int id, String name, ChessManColor color, BoardGamePosition positionBoardGame, BufferedImage image) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.positionBoardGame = positionBoardGame;
        this.image = image;
    }

    public abstract void createPositionMove(BoardGamePosition[][] boardGamePosition,
            Chessman[][] chessmanLocation, Rule rule);

    protected boolean createKillChessmanMove(int boardColumn, int boardRow,
            Chessman[][] chessmanLocation) {
        if (boardColumn >= Rule.MIN_BOARD_GAME_INDEX && boardColumn <= Rule.MAX_BOARD_GAME_INDEX
                && boardRow >= Rule.MIN_BOARD_GAME_INDEX && boardRow <= Rule.MAX_BOARD_GAME_INDEX) {
            Chessman chessmanAtPos = chessmanLocation[boardRow][boardColumn];
            if (chessmanAtPos == null) {
                return false;
            }
            if (positionKillChessman == null) {
                positionKillChessman = new ArrayList<>();
            }
            if (chessmanAtPos.getColor() != color) {
                positionKillChessman.add(chessmanAtPos.getPositionBoardGame());
                if (chessmanAtPos.getName().toLowerCase().equals("king")) {
                    chessmanAtPos.getPlayer().addEnemyToCheckMateList(this);
                }
                return true;
            } else {
                if (chessmanAtPos.getChessmanListProtect() == null) {
                    chessmanAtPos.setChessmanListProtect(new ArrayList<>());
                }
                chessmanAtPos.getChessmanListProtect().add(this);
            }
        }
        return false;
    }

    protected boolean createCanMovePosition(int boardColumn, int boardRow, BoardGamePosition[][] boardGamePosition,
            Chessman[][] chessmanLocation) {
        if (boardRow >= Rule.MIN_BOARD_GAME_INDEX && boardRow <= Rule.MAX_BOARD_GAME_INDEX
                && boardColumn >= Rule.MIN_BOARD_GAME_INDEX && boardColumn <= Rule.MAX_BOARD_GAME_INDEX) {
            Chessman chessmanAtPos = chessmanLocation[boardRow][boardColumn];
            if (chessmanAtPos != null) {
                return false;
            }
            if (positionCanMake == null) {
                positionCanMake = new ArrayList<>();
            }
            if (boardGamePosition[boardRow][boardColumn] != null) {
                positionCanMake.add(boardGamePosition[boardRow][boardColumn]);
                return true;
            }
        }
        return false;
    }

    protected BoardGamePosition getEnemyChessmanPosition(Chessman[][] chessmanLocation, int boardRow, int boardColumn) {
        Chessman chessman = chessmanLocation[boardRow][boardColumn];
        if (chessman != null && chessman.getColor() != color) {
            return chessman.getPositionBoardGame();
        }
        return null;
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BoardGamePosition getPositionBoardGame() {
        return positionBoardGame;
    }

    public void setPostionBoardGame(BoardGamePosition positionBoardGame) {
        this.positionBoardGame = positionBoardGame;
        positionCanMake = null;
        positionKillChessman = null;
        chessmanListProtect = null;
    }

    public void makeMove(BoardGamePosition positionBoardGame, BoardGame boardGame) {
        Chessman[][] chessmanLoc = boardGame.getChessmanLocation();
        BoardGamePosition[][] boardGamePositions = boardGame.getBoardGamePositions();
        chessmanLoc[this.positionBoardGame.getBoardGameRow()][this.positionBoardGame.getBoardGameColumn()] = null;
        this.positionBoardGame = positionBoardGame;
        if (positionBoardGame.isDefaultPos()) {
            positionBoardGame.setDefaultPos(false);
        }
        Chessman chessmanAtPos = chessmanLoc[positionBoardGame.getBoardGameRow()][positionBoardGame.getBoardGameColumn()];
        if (chessmanAtPos != null) {
            chessmanAtPos.killSelf();
        }
        chessmanListProtect = null;
        chessmanLoc[positionBoardGame.getBoardGameRow()][positionBoardGame.getBoardGameColumn()] = this;
        createPositionMove(boardGamePositions, chessmanLoc, boardGame.getGame().getRule());
    }

    public ChessManColor getColor() {
        return color;
    }

    public void setColor(ChessManColor color) {
        this.color = color;
    }

    public List<BoardGamePosition> getPositionCanMake() {
        return positionCanMake;
    }

    @Override
    public String toString() {
        return String.format("%s_%s_%d", name, color.name(), id);
    }

    public List<BoardGamePosition> getPositionKillChessman() {
        return positionKillChessman;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void resetPotentialPosition() {
        positionCanMake = null;
        positionKillChessman = null;
        chessmanListProtect = null;
    }

    public void killSelf() {
        death = true;
        positionBoardGame = null;
        positionCanMake = null;
        positionKillChessman = null;
        chessmanListProtect = null;
    }

    public void revive(BoardGamePosition position, Chessman[][] chessmanLoc) {
        death = false;
        positionBoardGame = position;
        chessmanLoc[position.getBoardGameRow()][position.getBoardGameColumn()] = this;
    }

    public int getPoint() {
        return point;
    }

    public List<Chessman> getChessmanListProtect() {
        return chessmanListProtect;
    }
    
    public void setChessmanListProtect(List<Chessman> chessmanListProtect) {
        this.chessmanListProtect = chessmanListProtect;
    }


}

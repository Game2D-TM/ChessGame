package chessgame.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    protected String name;
    protected ChessManColor color;
    protected boolean isCheckmate;
    protected final Map<String, Chessman> ownChessmans = new HashMap<>();
    protected final Map<String, Chessman> enemyKilledChessmans = new HashMap<>();
    protected List<Chessman> enemiesCheckmate;
    protected final Map<String, List<BoardGamePosition>> allownChessmansMovePositions = new HashMap();

    public Player(String name, ChessManColor color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        Player objPlayer = ((Player) obj);
        return name.equalsIgnoreCase(objPlayer.getName()) && color.equals(objPlayer.getColor());
    }

    public void addEnemyToCheckMateList(Chessman enemy) {
        isCheckmate = true;
        if (enemiesCheckmate == null) {
            enemiesCheckmate = new ArrayList<>();
        }
        if (enemy == null) {
            return;
        }
        if (ownChessmans.containsValue(enemy)) {
            return;
        }
        if (enemiesCheckmate.contains(enemy)) {
            return;
        }
        enemiesCheckmate.add(enemy);
        System.out.println("Checkmate");
    }

    public boolean checkMateCheck(Chessman chessmanMakeMove, BoardGame boardGame) {
        Chessman[][] chessmanLocation = boardGame.getChessmanLocation();
        BoardGamePosition[][] boardGamePosition = boardGame.getBoardGamePositions();
        if (isCheckmate) {
            isCheckmate = false;
            if (enemiesCheckmate != null && enemiesCheckmate.size() > 0) {
                for (int i = 0; i < enemiesCheckmate.size(); i++) {
                    Chessman chessman = enemiesCheckmate.get(i);
                    if (chessman == null) {
                        continue;
                    }
                    if (chessman.getPositionBoardGame() == null) {
                        enemiesCheckmate.remove(chessman);
                        continue;
                    }
                    Chessman chessmanAtPos = chessmanLocation[chessman.getPositionBoardGame().getBoardGameRow()][chessman.getPositionBoardGame().getBoardGameColumn()];
                    if (chessmanAtPos != null && !chessmanAtPos.equals(chessman)) {
                        continue;
                    }
                    chessman.createPositionMove(boardGamePosition, chessmanLocation, boardGame.getGame().getRule());
                    if (isCheckmate) {
                        chessman.resetPotentialPosition();
                        System.out.println("Still checkmate");
                        return isCheckmate;
                    }
                    chessman.resetPotentialPosition();
                }
            }
//            if (chessmanMakeMove != null && chessmanMakeMove.getName().toLowerCase().equals("king") && !isCheckmate) {
//                if (allownChessmansMovePositions.size() > 0) {
//                    for (List<BoardGamePosition> canMove : allownChessmansMovePositions.values()) {
//                        if (canMove.contains(chessmanMakeMove.getPositionBoardGame())) {
//                            isCheckmate = true;
//                            System.out.println("Still checkmate");
//                            return isCheckmate;
//                        }
//                    }
//                }
//            }
        }
        return isCheckmate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChessManColor getColor() {
        return color;
    }

    public void setColor(ChessManColor color) {
        this.color = color;
    }

    public Map<String, Chessman> getOwnChessmans() {
        return ownChessmans;
    }

    public Map<String, Chessman> getEnemyKilledChessmans() {
        return enemyKilledChessmans;
    }

    public boolean isCheckmate() {
        return isCheckmate;
    }

    public void setIsCheckmate(boolean isCheckmate) {
        this.isCheckmate = isCheckmate;
    }

    public Map<String, List<BoardGamePosition>> getAllownChessmansMovePositions() {
        return allownChessmansMovePositions;
    }

    @Override
    public String toString() {
        return "Player{" + "name=" + name + ", color=" + color + ", Enemies Killed Point = " + countPoint(enemyKilledChessmans.values()) + '}';
    }

    public List<Chessman> getEnemiesCheckmate() {
        return enemiesCheckmate;
    }

    public int countPoint(Collection<Chessman> chessmanList) {
        int point = 0;
        if (chessmanList != null && chessmanList.size() > 0) {
            for (Chessman chessman : chessmanList) {
                point += chessman.getPoint();
            }
        }
        return point;
    }
}

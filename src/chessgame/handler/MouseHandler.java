package chessgame.handler;

import chessgame.entity.BoardGame;
import chessgame.entity.BoardGamePosition;
import chessgame.entity.Chessman;
import chessgame.entity.Player;
import chessgame.entity.chessmantype.King;
import chessgame.entity.chessmantype.Pawn;
import chessgame.entity.chessmantype.Rook;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class MouseHandler implements MouseListener {

    private final BoardGame boardGame;
    private Chessman chessman;
    private final Player player;
    private boolean isChoose;
    private boolean isClicked;

    public MouseHandler(BoardGame boardGame, Chessman chessman, Player player) {
        this.boardGame = boardGame;
        this.chessman = chessman;
        this.player = player;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
            if (chessman == null) {
                return;
            }
            if (chessman.isDeath()) {
                return;
            }
            if (boardGame.getGame().getRule().getPlayerTurn().equals(player)) {
                if (isChoose) {
                    System.out.println(player.getName() + " Move: ");
                    List<BoardGamePosition> positionCanMake = chessman.getPositionCanMake();
                    boolean haveMove = false;
                    BoardGamePosition oldPos = chessman.getPositionBoardGame();
                    boolean isDefPos = chessman.getPositionBoardGame().isDefaultPos();
                    if (positionCanMake != null && positionCanMake.size() > 0) {
                        for (BoardGamePosition position : positionCanMake) {
                            if (mouseValidChessmanPosition(e, position)) {
                                chessman.makeMove(position, boardGame);
                                if (player.checkMateCheck(chessman, boardGame)) {
                                    chessman.makeMove(oldPos, boardGame);
                                    chessman.getPositionBoardGame().setDefaultPos(isDefPos);
                                } else {
                                    haveMove = true;
                                }
                                break;
                            }
                        }
                    }
                    if (!haveMove) {
                        List<BoardGamePosition> positionKillChessman = chessman.getPositionKillChessman();
                        if (positionKillChessman != null && positionKillChessman.size() > 0) {
                            for (BoardGamePosition position : positionKillChessman) {
                                if (mouseValidChessmanPosition(e, position)) {
                                    Chessman chessmanOpponent = boardGame.getChessmanLocation()[position.getBoardGameRow()][position.getBoardGameColumn()];
                                    if (chessmanOpponent == null || player.getColor() == chessmanOpponent.getColor()
                                            || chessmanOpponent.getPositionBoardGame() == null
                                            || !chessmanOpponent.getPositionBoardGame().equals(position)) {
                                        break;
                                    }
                                    chessman.makeMove(position, boardGame);
                                    if (player.checkMateCheck(null, boardGame)) {
                                        chessmanOpponent.revive(position, boardGame.getChessmanLocation());
                                        chessman.makeMove(oldPos, boardGame);
                                        chessman.getPositionBoardGame().setDefaultPos(isDefPos);
                                    } else {
                                        player.getEnemyKilledChessmans().put(chessmanOpponent.toString().toLowerCase(), chessmanOpponent);
                                        boardGame.getGame().removeMouseListener(boardGame.getMouseHandlers().get(chessmanOpponent.toString().toLowerCase()));
                                        boardGame.getMouseHandlers().remove(chessmanOpponent.toString().toLowerCase());
                                        if (player.getEnemiesCheckmate() != null && player.getEnemiesCheckmate().contains(chessmanOpponent)) {
                                            player.getEnemiesCheckmate().remove(chessmanOpponent);
                                        }
                                        haveMove = true;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (chessman instanceof Pawn) {
                        Chessman levelUpChessman = ((Pawn) chessman).levelUp(boardGame);
                        if (levelUpChessman != null) {
                            System.out.println("Level Up " + levelUpChessman.getName());
                            boardGame.getMouseHandlers().remove(chessman.toString().toLowerCase());
                            boardGame.getChessMans().remove(chessman.toString().toLowerCase());
                            chessman = levelUpChessman;
                            boardGame.getChessmanLocation()[levelUpChessman.getPositionBoardGame().getBoardGameRow()][levelUpChessman.getPositionBoardGame().getBoardGameColumn()] = levelUpChessman;
                            boardGame.getChessMans().put(chessman.toString().toLowerCase(), levelUpChessman);
                            boardGame.getMouseHandlers().put(chessman.toString().toLowerCase(), this);
                        }
                    }
//                    player.getAllownChessmansMovePositions().put(chessman.toString().toLowerCase(), positionCanMake);
                    chessman.resetPotentialPosition();
                    isChoose = false;
                    if (haveMove) {
                        boardGame.getGame().getRule().endTurn();
                    }
                } else {
                    if (mouseValidChessmanPosition(e, chessman.getPositionBoardGame())) {
                        System.out.println(chessman.toString());
                        if (boardGame.getChessManClick() != null) {
                            if (chessman instanceof Rook) {
                                if (boardGame.getChessManClick() instanceof King) {
                                    boolean result = ((King) boardGame.getChessManClick()).castling((Rook) chessman, boardGame);
                                    if (result) {
                                        boardGame.getGame().getRule().endTurn();
                                        return;
                                    }
                                }
                            }
                        }
                        boardGame.setChessManClick(chessman);
                        chessman.createPositionMove(boardGame.getBoardGamePositions(), boardGame.getChessmanLocation(), boardGame.getGame().getRule());
                        isChoose = true;
                        isClicked = true;
                    }
                }
            }
        }
    }

    public boolean mouseValidChessmanPosition(MouseEvent e, BoardGamePosition position) {
        if ((e.getX() >= position.getxPosition() && e.getX() <= position.getEndXPosition())
                && (e.getY() >= position.getyPosition() && e.getY() <= position.getEndYPosition())) {
            return true;
        }
        return false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isClicked = false;
    }

    public boolean isClicked() {
        return isClicked;
    }

}

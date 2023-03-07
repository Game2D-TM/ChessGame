package chessgame.entity.chessmantype;

import chessgame.Rule;
import chessgame.entity.Ability;
import chessgame.entity.BoardGame;
import chessgame.entity.BoardGamePosition;
import chessgame.entity.ChessManColor;
import chessgame.entity.Chessman;
import chessgame.entity.PotentialPosition;
import java.awt.image.BufferedImage;

public class King extends Chessman {

    public King(int id, String name, ChessManColor color, BoardGamePosition positionBoardGame, BufferedImage image) {
        super(id, name, color, positionBoardGame, image);
        abilitiesMove = new Ability[]{Ability.TOP_RIGHT, Ability.TOP, Ability.TOP_LEFT,
            Ability.RIGHT, Ability.LEFT,
            Ability.BOTTOM_RIGHT, Ability.BOTTOM, Ability.BOTTOM_LEFT};
        point = 200;
    }

    @Override
    public void createPositionMove(BoardGamePosition[][] boardGamePosition,
            Chessman[][] chessmanLocation, Rule rule) {
        PotentialPosition nPosition = new PotentialPosition(positionBoardGame, rule.isWhiteBottom());
        for (int i = 0; i < abilitiesMove.length; i++) {
            Ability ability = abilitiesMove[i];
            boolean havePos = nPosition.setPosition(ability, color);
            if (!havePos) {
                nPosition.resetPosition();
                continue;
            }
            if (!createCanMovePosition(nPosition.getBoardGameColumn(), nPosition.getBoardGameRow(), boardGamePosition, chessmanLocation)) {
                createKillChessmanMove(nPosition.getBoardGameColumn(), nPosition.getBoardGameRow(), chessmanLocation);
            }
            nPosition.resetPosition();
        }
    }

    public boolean castling(Rook rook, BoardGame boardGame) {
        if(rook == null || boardGame == null) return false;
        if (positionBoardGame.isDefaultPos() && rook.getPositionBoardGame() != null &&
                rook.getPositionBoardGame().isDefaultPos()) {
            int row = positionBoardGame.getBoardGameRow();
            int kingBoardColumn = positionBoardGame.getBoardGameColumn();
            int rookBoardColumn = rook.getPositionBoardGame().getBoardGameColumn();
            Chessman[][] chessmanLocation = boardGame.getChessmanLocation();
            BoardGamePosition[][] boardGamePostions = boardGame.getBoardGamePositions();
            if (kingBoardColumn + 3 == rookBoardColumn) {
                for (int i = 1; i <= 2; i++) {
                    Chessman chessman = chessmanLocation[row][kingBoardColumn + i];
                    if (chessman != null) {
                        return false;
                    }
                }
                kingBoardColumn = kingBoardColumn + 2;
                rookBoardColumn = rookBoardColumn - 2;
            } else if (kingBoardColumn - 3 == rookBoardColumn) {
                for (int i = 1; i <= 2; i++) {
                    Chessman chessman = chessmanLocation[row][kingBoardColumn - i];
                    if (chessman != null) {
                        return false;
                    }
                }
                kingBoardColumn = kingBoardColumn - 2;
                rookBoardColumn = rookBoardColumn + 2;
            } else return false;
            makeMove(boardGamePostions[row][kingBoardColumn], boardGame);
            resetPotentialPosition();
            rook.makeMove(boardGamePostions[row][rookBoardColumn], boardGame);
            rook.resetPotentialPosition();
            return true;
        }
        return false;
    }
}

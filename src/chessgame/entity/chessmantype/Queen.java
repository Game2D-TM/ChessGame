package chessgame.entity.chessmantype;

import chessgame.Rule;
import chessgame.entity.Ability;
import chessgame.entity.BoardGamePosition;
import chessgame.entity.ChessManColor;
import chessgame.entity.Chessman;
import chessgame.entity.PotentialPosition;
import java.awt.image.BufferedImage;

public class Queen extends Chessman {

    public Queen(int id, String name, ChessManColor color, BoardGamePosition positionBoardGame, BufferedImage image) {
        super(id, name, color, positionBoardGame, image);
        abilitiesMove = new Ability[]{Ability.FORWARD, Ability.RIGHT, Ability.LEFT, Ability.BACK,
            Ability.FORWARD_RIGHT, Ability.FORWARD_LEFT, Ability.BACK_RIGHT, Ability.BACK_LEFT};
        point = 90;
    }

    @Override
    public void createPositionMove(BoardGamePosition[][] boardGamePosition,
            Chessman[][] chessmanLocation, Rule rule) {
        PotentialPosition nPosition = new PotentialPosition(positionBoardGame, rule.isWhiteBottom());
        for (int i = 0; i < abilitiesMove.length; i++) {
            Ability ability = abilitiesMove[i];
            for (int j = 0; j < Rule.DEF_QUEEN_MOVE_STEP; j++) {
                boolean havePos = nPosition.setPosition(ability, color);
                if (!havePos) {
                    break;
                }
                if (!createCanMovePosition(nPosition.getBoardGameColumn(), nPosition.getBoardGameRow(), boardGamePosition, chessmanLocation)) {
                    createKillChessmanMove(nPosition.getBoardGameColumn(), nPosition.getBoardGameRow(), chessmanLocation);
                    break;
                }
            }
            nPosition.resetPosition();
        }
    }
}

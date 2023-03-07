package chessgame.entity.chessmantype;

import chessgame.Rule;
import chessgame.entity.Ability;
import chessgame.entity.BoardGamePosition;
import chessgame.entity.ChessManColor;
import chessgame.entity.Chessman;
import chessgame.entity.PotentialPosition;
import java.awt.image.BufferedImage;

public class Knight extends Chessman {

    public Knight(int id, String name, ChessManColor color, BoardGamePosition positionBoardGame, BufferedImage image) {
        super(id, name, color, positionBoardGame, image);
        abilitiesMove = new Ability[]{Ability.FORWARD_LUP_RIGHT, Ability.FORWARD_LUP_LEFT,
            Ability.FORWARD_LDOWN_RIGHT, Ability.FORWARD_LDOWN_LEFT,
            Ability.BACKWARD_LUP_RIGHT, Ability.BACKWARD_LUP_LEFT,
            Ability.BACKWARD_LDOWN_RIGHT, Ability.BACKWARD_LDOWN_LEFT};
        point = 50;
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

}

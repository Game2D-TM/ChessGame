package chessgame.entity.chessmantype;

import chessgame.Rule;
import chessgame.entity.Ability;
import chessgame.entity.BoardGame;
import chessgame.entity.BoardGamePosition;
import chessgame.entity.ChessManColor;
import chessgame.entity.Chessman;
import chessgame.entity.PotentialPosition;
import chessgame.gui.ImageManager;
import java.awt.image.BufferedImage;

public class Pawn extends Chessman {

    public Pawn(int id, String name, ChessManColor color, BoardGamePosition positionBoardGame, BufferedImage image) {
        super(id, name, color, positionBoardGame, image);
        abilitiesMove = new Ability[]{Ability.FORWARD, Ability.FORWARD_RIGHT, Ability.FORWARD_LEFT};
        point = 10;
    }

    @Override
    public void createPositionMove(BoardGamePosition[][] boardGamePosition,
            Chessman[][] chessmanLocation, Rule rule) {
        PotentialPosition nPosition = new PotentialPosition(positionBoardGame, rule.isWhiteBottom());
        int canMoveAmount = Rule.DEF_PAWN_MOVE_STEP;
        if (positionBoardGame.isDefaultPos()) {
            canMoveAmount = Rule.FIR_PAWN_MOVE_STEP;
        }
        for (int i = 0; i < abilitiesMove.length; i++) {
            Ability ability = abilitiesMove[i];
            switch (ability) {
                case FORWARD:
                    for (int j = 0; j < canMoveAmount; j++) {
                        boolean havePos = nPosition.setPosition(ability, color);
                        if (!havePos) {
                            break;
                        }
                        if (!createCanMovePosition(nPosition.getBoardGameColumn(), nPosition.getBoardGameRow(), boardGamePosition, chessmanLocation)) {
                            break;
                        }
                    }
                    break;
                default:
                    if (nPosition.setPosition(ability, color)) {
                        createKillChessmanMove(nPosition.getBoardGameColumn(), nPosition.getBoardGameRow(), chessmanLocation);
                    }
                    break;
            }
            nPosition.resetPosition();
        }
    }

    public Chessman levelUp(BoardGame boardGame) {
        if (positionBoardGame.getBoardGameRow() == Rule.MIN_BOARD_GAME_INDEX
                || positionBoardGame.getBoardGameRow() == Rule.MAX_BOARD_GAME_INDEX) {
            String queenName = "";
            if (color == ChessManColor.White) {
                queenName = "queen_white";
            } else {
                queenName = "queen_black";
            }
            int id = 1;
            while (true) {
                Chessman chessman = boardGame.getChessMans().get("queen_" + color.name().toLowerCase() + "_" + id);
                if (chessman == null) {
                    break;
                }
                id++;
            }
            return new Queen(id, "Queen", color, positionBoardGame, ImageManager.getInstance().images.get(queenName));
        }
        return null;
    }
}

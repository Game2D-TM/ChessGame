package chessgame.entity;

import chessgame.Rule;

public class PotentialPosition {

    private int boardGameRow;
    private int boardGameColumn;
    private final boolean isWhiteBottom;
    private final ChessManColor defBottomColor;
    private final BoardGamePosition currPosition;

    public PotentialPosition(BoardGamePosition position, boolean isWhiteBottom) {
        this.currPosition = position;
        this.boardGameColumn = position.getBoardGameColumn();
        this.boardGameRow = position.getBoardGameRow();
        this.isWhiteBottom = isWhiteBottom;
        if (isWhiteBottom) {
            defBottomColor = ChessManColor.White;
        } else {
            defBottomColor = ChessManColor.Black;
        }
    }

    public boolean setPosition(Ability ability, ChessManColor color) {
        if (ability == null || color == null) {
            return false;
        }
        String abilityName = ability.name().toLowerCase();
        if (abilityName.contains("forward")) {
            if (abilityName.contains("right")) {
                switch (ability) {
                    case FORWARD_RIGHT:
                        return setPositionForwardRight(color);
                    case FORWARD_LUP_RIGHT:
                        return setPositionForwardLUpRight(color);
                    case FORWARD_LDOWN_RIGHT:
                        return setPositionForwardLDownRight(color);
                }
            } else {
                switch (ability) {
                    case FORWARD:
                        return setPositionForward(color);
                    case FORWARD_LEFT:
                        return setPositionForwardLeft(color);
                    case FORWARD_LUP_LEFT:
                        return setPositionForwardLUpLeft(color);
                    case FORWARD_LDOWN_LEFT:
                        return setPositionForwardLDownLeft(color);
                }
            }
        } else if (abilityName.contains("back")) {
            if (abilityName.contains("right")) {
                switch (ability) {
                    case BACK_RIGHT:
                        return setPositionBackRight(color);
                    case BACKWARD_LUP_RIGHT:
                        return setPositionBackwardLUpRight(color);
                    case BACKWARD_LDOWN_RIGHT:
                        return setPositionBackwardLDownRight(color);
                }
            } else {
                switch (ability) {
                    case BACK:
                        return setPositionBack(color);
                    case BACK_LEFT:
                        return setPositionBackLeft(color);
                    case BACKWARD_LUP_LEFT:
                        return setPositionBackwardLUpLeft(color);
                    case BACKWARD_LDOWN_LEFT:
                        return setPositionBackwardLDownLeft(color);
                }
            }
        } else {
            if (abilityName.contains("right")) {
                switch (ability) {
                    case RIGHT:
                        return setPositionRight();
                    case TOP_RIGHT:
                        return setPositionTopRight();
                    case BOTTOM_RIGHT:
                        return setPositionBottomRight();
                }
            } else {
                switch (ability) {
                    case TOP:
                        return setPositionTop();
                    case BOTTOM:
                        return setPositionBottom();
                    case LEFT:
                        return setPositionLeft();
                    case TOP_LEFT:
                        return setPositionTopLeft();
                    case BOTTOM_LEFT:
                        return setPositionBottomLeft();
                }
            }
        }
        return false;
    }

    private boolean setPositionBack(ChessManColor color) {
        if (color != defBottomColor) {
            if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow--;
                return true;
            }
        } else {
            if (boardGameRow < Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow++;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionBottomRight() {
        if(boardGameRow < Rule.MAX_BOARD_GAME_INDEX &&
                boardGameColumn < Rule.MAX_BOARD_GAME_INDEX) {
            boardGameRow++;
            boardGameColumn++;
            return true;
        }
        return false;
    }

    private boolean setPositionBottom() {
        if(boardGameRow < Rule.MAX_BOARD_GAME_INDEX) {
            boardGameRow++;
            return true;
        }
        return false;
    }

    private boolean setPositionBottomLeft() {
        if(boardGameRow < Rule.MAX_BOARD_GAME_INDEX &&
                boardGameColumn > Rule.MIN_BOARD_GAME_INDEX) {
            boardGameRow++;
            boardGameColumn--;
            return true;
        }
        return false;
    }

    private boolean setPositionTopRight() {
        if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX
                && boardGameColumn < Rule.MAX_BOARD_GAME_INDEX) {
            boardGameRow--;
            boardGameColumn++;
            return true;
        }
        return false;
    }

    private boolean setPositionTop() {
        if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX) {
            boardGameRow--;
            return true;
        }
        return false;
    }

    private boolean setPositionTopLeft() {
        if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX
                && boardGameColumn > Rule.MIN_BOARD_GAME_INDEX) {
            boardGameRow--;
            boardGameColumn--;
            return true;
        }
        return false;
    }

    private boolean setPositionLeft() {
        if (boardGameColumn > Rule.MIN_BOARD_GAME_INDEX) {
            boardGameColumn--;
            return true;
        }
        return false;
    }

    private boolean setPositionRight() {
        if (boardGameColumn < Rule.MAX_BOARD_GAME_INDEX) {
            boardGameColumn++;
            return true;
        }
        return false;
    }

    private boolean setPositionForward(ChessManColor color) {
        if (color != defBottomColor) {
            if (boardGameRow < Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow++;
                return true;
            }
        } else {
            if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow--;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionForwardRight(ChessManColor color) {
        if (color == defBottomColor) {
            if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX
                    && boardGameColumn < Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow--;
                boardGameColumn++;
                return true;
            }
        } else {
            if (boardGameRow < Rule.MAX_BOARD_GAME_INDEX
                    && boardGameColumn < Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow++;
                boardGameColumn++;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionForwardLeft(ChessManColor color) {
        if (color == defBottomColor) {
            if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX
                    && boardGameColumn > Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow--;
                boardGameColumn--;
                return true;
            }
        } else {
            if (boardGameRow < Rule.MAX_BOARD_GAME_INDEX
                    && boardGameColumn > Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow++;
                boardGameColumn--;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionBackRight(ChessManColor color) {
        if (color == defBottomColor) {
            if (boardGameRow < Rule.MAX_BOARD_GAME_INDEX
                    && boardGameColumn < Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow++;
                boardGameColumn++;
                return true;
            }
        } else {
            if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX
                    && boardGameColumn < Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow--;
                boardGameColumn++;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionBackLeft(ChessManColor color) {
        if (color == defBottomColor) {
            if (boardGameRow < Rule.MAX_BOARD_GAME_INDEX
                    && boardGameColumn > Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow++;
                boardGameColumn--;
                return true;
            }
        } else {
            if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX
                    && boardGameColumn > Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow--;
                boardGameColumn--;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionForwardLUpRight(ChessManColor color) {
        if (boardGameColumn < Rule.MAX_BOARD_GAME_INDEX) {
            boardGameColumn++;
        } else {
            return false;
        }
        if (color == defBottomColor) {
            if (boardGameRow - 2 >= Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow -= 2;
                return true;
            }
        } else {
            if (boardGameRow + 2 <= Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow += 2;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionForwardLDownRight(ChessManColor color) {
        if (boardGameColumn + 2 <= Rule.MAX_BOARD_GAME_INDEX) {
            boardGameColumn += 2;
        } else {
            return false;
        }
        if (color == defBottomColor) {
            if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow--;
                return true;
            }
        } else {
            if (boardGameRow < Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow++;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionForwardLUpLeft(ChessManColor color) {
        if (boardGameColumn > Rule.MIN_BOARD_GAME_INDEX) {
            boardGameColumn--;
        } else {
            return false;
        }
        if (color == defBottomColor) {
            if (boardGameRow - 2 >= Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow -= 2;
                return true;
            }
        } else {
            if (boardGameRow + 2 <= Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow += 2;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionForwardLDownLeft(ChessManColor color) {
        if (boardGameColumn - 2 >= Rule.MIN_BOARD_GAME_INDEX) {
            boardGameColumn -= 2;
        } else {
            return false;
        }
        if (color == defBottomColor) {
            if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow--;
                return true;
            }
        } else {
            if (boardGameRow < Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow++;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionBackwardLUpRight(ChessManColor color) {
        if (boardGameColumn < Rule.MAX_BOARD_GAME_INDEX) {
            boardGameColumn++;
        } else {
            return false;
        }
        if (color == defBottomColor) {
            if (boardGameRow + 2 <= Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow += 2;
                return true;
            }
        } else {
            if (boardGameRow - 2 >= Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow -= 2;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionBackwardLUpLeft(ChessManColor color) {
        if (boardGameColumn > Rule.MIN_BOARD_GAME_INDEX) {
            boardGameColumn--;
        } else {
            return false;
        }
        if (color == defBottomColor) {
            if (boardGameRow + 2 <= Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow += 2;
                return true;
            }
        } else {
            if (boardGameRow - 2 >= Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow -= 2;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionBackwardLDownRight(ChessManColor color) {
        if (boardGameColumn + 2 <= Rule.MAX_BOARD_GAME_INDEX) {
            boardGameColumn += 2;
        } else {
            return false;
        }
        if (color == defBottomColor) {
            if (boardGameRow < Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow++;
                return true;
            }
        } else {
            if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow--;
                return true;
            }
        }
        return false;
    }

    private boolean setPositionBackwardLDownLeft(ChessManColor color) {
        if (boardGameColumn - 2 >= Rule.MIN_BOARD_GAME_INDEX) {
            boardGameColumn -= 2;
        } else {
            return false;
        }
        if (color == defBottomColor) {
            if (boardGameRow < Rule.MAX_BOARD_GAME_INDEX) {
                boardGameRow++;
                return true;
            }
        } else {
            if (boardGameRow > Rule.MIN_BOARD_GAME_INDEX) {
                boardGameRow--;
                return true;
            }
        }
        return false;
    }

    public void resetPosition() {
        boardGameColumn = currPosition.getBoardGameColumn();
        boardGameRow = currPosition.getBoardGameRow();
    }

    public int getBoardGameRow() {
        return boardGameRow;
    }

    public int getBoardGameColumn() {
        return boardGameColumn;
    }

    public boolean isWhiteBottom() {
        return isWhiteBottom;
    }
}

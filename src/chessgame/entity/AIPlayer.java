package chessgame.entity;

import chessgame.entity.chessmantype.King;
import chessgame.entity.chessmantype.Pawn;
import chessgame.entity.chessmantype.Rook;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AIPlayer extends Player {

    private Map<Chessman, List<BoardGamePosition>> canMovePositions = new HashMap();
    private Map<Chessman, List<BoardGamePosition>> canKillMovePositions = new HashMap();
    private Map<Chessman, List<Chessman>> chessmanListProtects = new HashMap<>();
    private List<Chessman> enemiesChessmans = new ArrayList<>();
    private Map<Chessman, List<BoardGamePosition>> enemiesCanMovePositions = new HashMap<>();
    private Map<Chessman, List<BoardGamePosition>> enemiesKillMovePositions = new HashMap<>();
    private Map<Chessman, List<Chessman>> enemiesChessmanListProtects = new HashMap<>();
    private boolean isCastling;

    public AIPlayer(String name, ChessManColor color) {
        super(name, color);
    }

    public void decisionMove(BoardGame boardGame) {
        System.out.println(name + " Move: ");
        long startTime = System.currentTimeMillis();
        String kingName = "king_" + color.name().toLowerCase() + "_1";
        while (true) {
            if (ownChessmans.get(kingName).isDeath()) {
                break;
            }
            for (Chessman chessman : ownChessmans.values()) {
                if (chessman != null) {
                    if (chessman.isDeath()) {
                        continue;
                    }
                    chessman.createPositionMove(boardGame.getBoardGamePositions(), boardGame.getChessmanLocation(), boardGame.getGame().getRule());
                    if (chessman.getPositionCanMake() != null && chessman.getPositionCanMake().size() > 0) {
                        List<BoardGamePosition> currPositions = new ArrayList<>();
                        currPositions.addAll(chessman.getPositionCanMake());
                        canMovePositions.put(chessman, currPositions);
                    }
                    if (chessman.getPositionKillChessman() != null && chessman.getPositionKillChessman().size() > 0) {
                        List<BoardGamePosition> currKillPositions = new ArrayList<>();
                        currKillPositions.addAll(chessman.getPositionKillChessman());
                        canKillMovePositions.put(chessman, currKillPositions);
                    }
                    if (chessman.getChessmanListProtect() != null && chessman.getChessmanListProtect().size() > 0) {
                        List<Chessman> chessmanListProtect = new ArrayList<>();
                        chessmanListProtect.addAll(chessman.getChessmanListProtect());
                        chessmanListProtects.put(chessman, chessmanListProtect);
                    }
                    chessman.resetPotentialPosition();
                }
            }
            Chessman chessmanMakeMove = null;
            boolean makeMove = aiThinkKillMove(boardGame, chessmanMakeMove);
            if (!makeMove) {
                if (!isCastling) {
                    String rook1Name = "rook_" + color.name().toLowerCase() + "_1";
                    String rook2Name = "rook_" + color.name().toLowerCase() + "_2";
                    boolean result = ((King) ownChessmans.get(kingName)).castling((Rook) ownChessmans.get(rook1Name), boardGame);
                    if (result) {
                        isCastling = true;
                        boardGame.getGame().getRule().endTurn();
                        break;
                    }
                    result = ((King) ownChessmans.get(kingName)).castling((Rook) ownChessmans.get(rook2Name), boardGame);
                    if (result) {
                        isCastling = true;
                        boardGame.getGame().getRule().endTurn();
                        break;
                    }
                }
                makeMove = aiThinkMakeMove(boardGame, chessmanMakeMove);
            }
            if (chessmanMakeMove != null) {
                if (chessmanMakeMove instanceof Pawn) {
                    Chessman levelUpChessman = ((Pawn) chessmanMakeMove).levelUp(boardGame);
                    if (levelUpChessman != null) {
                        System.out.println("Level Up " + levelUpChessman.getName());
                        boardGame.getChessMans().remove(chessmanMakeMove.toString().toLowerCase());
                        boardGame.getChessmanLocation()[levelUpChessman.getPositionBoardGame().getBoardGameRow()][levelUpChessman.getPositionBoardGame().getBoardGameColumn()] = levelUpChessman;
                        boardGame.getChessMans().put(chessmanMakeMove.toString().toLowerCase(), levelUpChessman);
                    }
                }
            }
            canMovePositions.clear();
            canKillMovePositions.clear();
            chessmanListProtects.clear();
            if (makeMove) {
                if (chessmanMakeMove != null) {
                    chessmanMakeMove.resetPotentialPosition();
                }
                boardGame.getGame().getRule().endTurn();
                break;
            }
        }
        long endTime = System.currentTimeMillis() - startTime;
        float time = endTime / 1000f;

        System.out.println(time + "s");
    }

    private boolean aiThinkKillMove(BoardGame boardGame, Chessman chessmanMakeMove) {
        if (canKillMovePositions.size() > 0) {
            //best kill move
            int maxPointKillMove = Integer.MIN_VALUE;
            Chessman bestKillChessman = null;
            BoardGamePosition oldPos = null;
            boolean isDefPos = false;
            for (Chessman chessman : canKillMovePositions.keySet()) {
                if (chessman != null) {
                    if (chessman.isDeath()) {
                        continue;
                    }
                    List<BoardGamePosition> positions = canKillMovePositions.get(chessman);
                    if (positions != null && positions.size() > 0) {
                        for (BoardGamePosition position : positions) {
                            if (position == null) {
                                continue;
                            }
                            Chessman chessmanOpponent = boardGame.getChessmanLocation()[position.getBoardGameRow()][position.getBoardGameColumn()];
                            if (chessmanOpponent == null || chessmanOpponent.getPositionBoardGame() == null) {
                                continue;
                            }
                            if (color == chessmanOpponent.getColor()) {
                                continue;
                            }
                            if (chessmanOpponent.getPositionBoardGame().equals(position)) {
                                if (chessmanOpponent.getPoint() > maxPointKillMove) {
                                    oldPos = chessman.getPositionBoardGame();
                                    isDefPos = chessman.getPositionBoardGame().isDefaultPos();
                                    chessman.makeMove(position, boardGame);
                                    int threatPoint = analysisEnemiesKillMove(boardGame, position, chessman);
                                    chessman.makeMove(oldPos, boardGame);
                                    chessmanOpponent.revive(position, boardGame.getChessmanLocation());
                                    chessman.getPositionBoardGame().setDefaultPos(isDefPos);
                                    chessman.resetPotentialPosition();
                                    int point = chessmanOpponent.getPoint() - threatPoint;
                                    if (point >= 0) {
                                        maxPointKillMove = point;
                                        bestKillChessman = chessmanOpponent;
                                        chessmanMakeMove = chessman;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (bestKillChessman != null && chessmanMakeMove != null) {
                oldPos = chessmanMakeMove.getPositionBoardGame();
                isDefPos = chessmanMakeMove.getPositionBoardGame().isDefaultPos();
                BoardGamePosition position = bestKillChessman.getPositionBoardGame();
                chessmanMakeMove.makeMove(position, boardGame);
                if (checkMateCheck(null, boardGame)) {
                    chessmanMakeMove.makeMove(oldPos, boardGame);
                    bestKillChessman.revive(position, boardGame.getChessmanLocation());
                    chessmanMakeMove.getPositionBoardGame().setDefaultPos(isDefPos);
                } else {
                    enemyKilledChessmans.put(bestKillChessman.toString().toLowerCase(), bestKillChessman);
                    boardGame.getGame().removeMouseListener(boardGame.getMouseHandlers().get(bestKillChessman.toString().toLowerCase()));
                    boardGame.getMouseHandlers().remove(bestKillChessman.toString().toLowerCase());
                    if (enemiesCheckmate != null && enemiesCheckmate.contains(bestKillChessman)) {
                        enemiesCheckmate.remove(bestKillChessman);
                    }
                    if (enemiesChessmans != null && enemiesChessmans.contains(bestKillChessman)) {
                        enemiesChessmans.remove(bestKillChessman);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean aiThinkMakeMove(BoardGame boardGame, Chessman chessmanMakeMove) {
        if (canMovePositions.size() > 0) {
            while (true) {
                int maxPointThreat = 0;
                Chessman chessmanThreated = null;
                int index = 0;
                for (Chessman chessman : canMovePositions.keySet()) {
                    if (chessman == null) {
                        continue;
                    }
                    if (chessman.isDeath()) {
                        continue;
                    }
                    int threatPoint = analysisEnemiesKillMove(boardGame, chessman.getPositionBoardGame(), chessman);
                    if (threatPoint > maxPointThreat) {
                        maxPointThreat = threatPoint;
                        chessmanThreated = chessman;
                    }
                }
                if (chessmanThreated != null) {
                    BoardGamePosition oldPos = chessmanThreated.getPositionBoardGame();
                    boolean isDefPos = chessmanThreated.getPositionBoardGame().isDefaultPos();
                    List<BoardGamePosition> positions = canMovePositions.get(chessmanThreated);
                    if (positions != null && positions.size() > 0) {
                        for (BoardGamePosition position : positions) {
                            if (position == null) {
                                continue;
                            }
                            if (analysisEnemiesMove(boardGame, position)) {
                                continue;
                            }
                            chessmanThreated.makeMove(position, boardGame);
                            if (checkMateCheck(null, boardGame)) {
                                chessmanThreated.makeMove(oldPos, boardGame);
                                chessmanThreated.getPositionBoardGame().setDefaultPos(isDefPos);
                                break;
                            }
                            chessmanMakeMove = chessmanThreated;
                            return true;
                        }
                    }
                }
                int randomChessman = new Random().nextInt(canMovePositions.size());
                for (Chessman chessman : canMovePositions.keySet()) {
                    if (chessman != null) {
                        if (chessman.isDeath()) {
                            continue;
                        }
                        BoardGamePosition oldPos = chessman.getPositionBoardGame();
                        boolean isDefPos = chessman.getPositionBoardGame().isDefaultPos();
                        List<BoardGamePosition> positions = canMovePositions.get(chessman);
                        if (index == randomChessman) {
                            if (positions != null && positions.size() > 0) {
                                for (BoardGamePosition position : positions) {
                                    if (position == null) {
                                        continue;
                                    }
                                    if (analysisEnemiesMove(boardGame, position)) {
                                        continue;
                                    }
                                    chessman.makeMove(position, boardGame);
                                    if (checkMateCheck(null, boardGame)) {
                                        chessman.makeMove(oldPos, boardGame);
                                        chessman.getPositionBoardGame().setDefaultPos(isDefPos);
                                        continue;
                                    }
                                    chessmanMakeMove = chessman;
                                    return true;
                                }
                            }
                        }
                    }
                    index++;
                }
            }
        }
        return false;
    }

    public int analysisEnemiesKillMove(BoardGame boardGame, BoardGamePosition position, Chessman currChessman) {
        int threatPoint = 0;
        if (enemiesChessmans == null || enemiesChessmans.isEmpty()) {
            return threatPoint;
        }
        for (Chessman chessman : enemiesChessmans) {
            if (chessman != null) {
                if (chessman.isDeath()) {
                    continue;
                }
                chessman.createPositionMove(boardGame.getBoardGamePositions(), boardGame.getChessmanLocation(), boardGame.getGame().getRule());
                if (chessman.getPositionKillChessman() != null && chessman.getPositionKillChessman().size() > 0) {
                    List<BoardGamePosition> currKillPositions = new ArrayList<>();
                    currKillPositions.addAll(chessman.getPositionKillChessman());
                    enemiesKillMovePositions.put(chessman, currKillPositions);
                }
                if (chessman.getChessmanListProtect() != null && chessman.getChessmanListProtect().size() > 0) {
                    List<Chessman> chessmanListProtect = new ArrayList<>();
                    chessmanListProtect.addAll(chessman.getChessmanListProtect());
                    enemiesChessmanListProtects.put(chessman, chessmanListProtect);
                }
                chessman.resetPotentialPosition();
            }
        }

        for (Chessman chessmanEnemy : enemiesKillMovePositions.keySet()) {
            if (enemiesKillMovePositions.get(chessmanEnemy).contains(position)) {
                threatPoint = currChessman.getPoint();
                break;
            }
        }
        enemiesChessmanListProtects.clear();
        enemiesKillMovePositions.clear();
        return threatPoint;
    }

    public boolean analysisEnemiesMove(BoardGame boardGame, BoardGamePosition position) {
        boolean existMove = false;
        if (enemiesChessmans == null || enemiesChessmans.isEmpty()) {
            return existMove;
        }
        for (Chessman chessman : enemiesChessmans) {
            if (chessman != null) {
                if (chessman.isDeath()) {
                    continue;
                }
                chessman.createPositionMove(boardGame.getBoardGamePositions(), boardGame.getChessmanLocation(), boardGame.getGame().getRule());
                if (chessman.getPositionCanMake() != null && chessman.getPositionCanMake().size() > 0) {
                    List<BoardGamePosition> currMovePositions = new ArrayList<>();
                    currMovePositions.addAll(chessman.getPositionCanMake());
                    enemiesCanMovePositions.put(chessman, currMovePositions);
                }
                chessman.resetPotentialPosition();
            }
        }

        for (Chessman chessmanEnemy : enemiesCanMovePositions.keySet()) {
            if (enemiesCanMovePositions.get(chessmanEnemy).contains(position)) {
                existMove = true;
                break;
            }
        }
        enemiesCanMovePositions.clear();
        return existMove;
    }

    /*
    var minimaxRoot =function(depth, game, isMaximisingPlayer) {

    var newGameMoves = game.ugly_moves();
    var bestMove = -9999;
    var bestMoveFound;

    for(var i = 0; i < newGameMoves.length; i++) {
        var newGameMove = newGameMoves[i];
        game.ugly_move(newGameMove);
        var value = minimax(depth - 1, game, !isMaximisingPlayer);
        game.undo();
        if(value >= bestMove) {
            bestMove = value;
            bestMoveFound = newGameMove;
        }
    }
    return bestMoveFound;
};

var minimax = function (depth, game, isMaximisingPlayer) {
    positionCount++;
    if (depth === 0) {
        return -evaluateBoard(game.board());
    }

    var newGameMoves = game.ugly_moves();

    if (isMaximisingPlayer) {
        var bestMove = -9999;
        for (var i = 0; i < newGameMoves.length; i++) {
            game.ugly_move(newGameMoves[i]);
            bestMove = Math.max(bestMove, minimax(depth - 1, game, !isMaximisingPlayer));
            game.undo();
        }
        return bestMove;
    } else {
        var bestMove = 9999;
        for (var i = 0; i < newGameMoves.length; i++) {
            game.ugly_move(newGameMoves[i]);
            bestMove = Math.min(bestMove, minimax(depth - 1, game, !isMaximisingPlayer));
            game.undo();
        }
        return bestMove;
    }
};

var evaluateBoard = function (board) {
    var totalEvaluation = 0;
    for (var i = 0; i < 8; i++) {
        for (var j = 0; j < 8; j++) {
            totalEvaluation = totalEvaluation + getPieceValue(board[i][j]);
        }
    }
    return totalEvaluation;
};

var getPieceValue = function (piece) {
    if (piece === null) {
        return 0;
    }
    var getAbsoluteValue = function (piece) {
        if (piece.type === 'p') {
            return 10;
        } else if (piece.type === 'r') {
            return 50;
        } else if (piece.type === 'n') {
            return 30;
        } else if (piece.type === 'b') {
            return 30 ;
        } else if (piece.type === 'q') {
            return 90;
        } else if (piece.type === 'k') {
            return 900;
        }
        throw "Unknown piece type: " + piece.type;
    };

    var absoluteValue = getAbsoluteValue(piece, piece.color === 'w');
    return piece.color === 'w' ? absoluteValue : -absoluteValue;
};

/* board visualization and games state handling starts here

    var onDragStart = function(source, piece, position, orientation)

    {
        if (game.in_checkmate() == = true || game.in_draw() == = true
                || piece.search( / ^ b /) != = -1) {
            return false;
        }
    }
    ;

var makeBestMove = function()

    {
        var bestMove = getBestMove(game);
        game.ugly_move(bestMove);
        board.position(game.fen());
        renderMoveHistory(game.history());
        if (game.game_over()) {
            alert(
             
        
    
    'Game over');
    }
};

var positionCount;
    var getBestMove = function(game)

    {
        if (game.game_over()) {
            alert(
             
        
        'Game over');
    }

    positionCount = 0;
        var depth = parseInt($('#search-depth'
        ).find(':selected'
        ).text()
        );

    var d = new Date().getTime();
        var bestMove = minimaxRoot(depth, game, true);
        var d2 = new Date().getTime();
        var moveTime = (d2 - d);
        var positionsPerS = (positionCount * 1000 / moveTime);

        $('#position-count'
        ).text(positionCount);
        $('#time'
        ).text(moveTime / 1000 + 's');
        $('#positions-per-s'
        ).text(positionsPerS);
        return bestMove;
    }
    ;

     */
    public List<Chessman> getEnemiesChessmans() {
        return enemiesChessmans;
    }
}

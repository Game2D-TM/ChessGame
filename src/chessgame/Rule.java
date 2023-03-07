package chessgame;

import chessgame.entity.AIPlayer;
import chessgame.entity.BoardGame;
import chessgame.entity.ChessManColor;
import chessgame.entity.Player;
import java.util.Random;

public class Rule {

    public static int MIN_BOARD_GAME_INDEX = 0;
    public static int MAX_BOARD_GAME_INDEX = 7;
    public static int DEF_PAWN_MOVE_STEP = 1;
    public static int FIR_PAWN_MOVE_STEP = 2;
    public static int DEF_PAWN_KILL_MOVE_STEP = 2;
    public static int DEF_ROOK_MOVE_STEP = 7;
    public static int DEF_QUEEN_MOVE_STEP = 7;
    public static int DEF_KNIGHT_MOVE_STEP = 8;
    public static int DEF_KING_MOVE_STEP = 8;
    public static int DEF_BISHOP_MOVE_STEP = 7;
    public static int DEF_BISHOP_MOVE_ABILITY = 4;

    private Player[] players;
    private BoardGame boardGame;
    private Player winPlayer;
    private Player playerTurn;
    private boolean isWhiteBottom;
    private boolean playWithAI;
    private AIPlayer aiPlayer;

    public Rule(Player[] players, BoardGame boardGame) {
        if (players.length != 2) {
            boardGame.getGame().setIsRunning(false);
        }
        if (players[0] == null || players[1] == null) {
            boardGame.getGame().setIsRunning(false);
        }
        if (players[0].getColor() == players[1].getColor()) {
            boardGame.getGame().setIsRunning(false);
        }
        this.players = players;
        this.boardGame = boardGame;
        int randNum = new Random().nextInt(2);
        if (randNum == 0) {
            playerTurn = players[0];
        } else {
            playerTurn = players[1];
        }
        if(players[0].getColor() == ChessManColor.Black) {
            isWhiteBottom = true;
        } else {
            isWhiteBottom = false;
        }
        if(players[0] instanceof AIPlayer) {
            playWithAI = true;
            aiPlayer = (AIPlayer) players[0];
        } else if(players[1] instanceof AIPlayer) {
            playWithAI = true;
            aiPlayer = (AIPlayer) players[1];
        }
    }

    public void playWithAI() {

    }

    public boolean checkWinCondition() {
        if (players[0].getOwnChessmans().get("king_" + players[0].getColor().name().toLowerCase() + "_" + "1").isDeath()) {
            winPlayer = players[1];
            return true;
        }
        if (players[1].getOwnChessmans().get("king_" + players[1].getColor().name().toLowerCase() + "_" + "1").isDeath()) {
            winPlayer = players[0];
            return true;
        }
        return false;
    }

    public Player getOtherPlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (!players[i].equals(player)) {
                return players[i];
            }
        }
        return null;
    }

    public void endTurn() {
        Player otherPlayer = getOtherPlayer(playerTurn);
        if (otherPlayer != null) {
            playerTurn = otherPlayer;
        }
        if (playWithAI) {
            if (aiPlayer != null) {
                if (playerTurn != null && playerTurn.equals(aiPlayer)) {
                    aiPlayer.decisionMove(boardGame);
                }
            }
        }
    }

    public Player getCheckmatePlayer() {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            if (player.isCheckmate()) {
                return player;
            }
        }
        return null;
    }

    public Player getPlayer(int i) {
        return players[i];
    }

    public void setPlayer(int i, Player player) {
        players[i] = player;
    }

    public Player[] getPlayers() {
        return players;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public Player getWinPlayer() {
        return winPlayer;
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Player playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isWhiteBottom() {
        return isWhiteBottom;
    }

    public boolean isPlayWithAI() {
        return playWithAI;
    }

    public AIPlayer getAiPlayer() {
        return aiPlayer;
    }

}

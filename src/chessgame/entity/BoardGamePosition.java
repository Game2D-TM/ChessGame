package chessgame.entity;

public class BoardGamePosition {
    private int xPosition;
    private int yPosition;
    private int width;
    private int height;
    private int boardGameRow;
    private int boardGameColumn;
    private boolean isDefaultPos = true;

    public BoardGamePosition(int xPosition, int yPosition, int width, int height, int boardGameRow, int boardGameColumn) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.boardGameColumn = boardGameColumn;
        this.boardGameRow = boardGameRow;
    }
    
    public BoardGamePosition() {
        
    }

    @Override
    public boolean equals(Object obj) {
        BoardGamePosition boardGPos = ((BoardGamePosition)obj);
        return xPosition == boardGPos.getxPosition() &&
                yPosition == boardGPos.getyPosition() &&
                width == boardGPos.getWidth() &&
                height == boardGPos.getHeight();
    }

    @Override
    public String toString() {
        return "BoardGamePositon{" + "xPosition=" + xPosition + ", yPosition=" + yPosition + 
                ", width=" + width + ", height=" + height +
                ", EndXPosition: " + getEndXPosition() +
                ", EndYPosition: " + getEndYPosition() + '}';
    }
    
    
    public int getEndXPosition() {
        return xPosition + width;
    }
    public int getEndYPosition() {
        return yPosition + height;
    }
    
    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBoardGameRow() {
        return boardGameRow;
    }

    public int getBoardGameColumn() {
        return boardGameColumn;
    }

    public void setBoardGameRow(int boardGameRow) {
        this.boardGameRow = boardGameRow;
    }

    public void setBoardGameColumn(int boardGameColumn) {
        this.boardGameColumn = boardGameColumn;
    }

    public boolean isDefaultPos() {
        return isDefaultPos;
    }

    public void setDefaultPos(boolean isDefaultPos) {
        this.isDefaultPos = isDefaultPos;
    }
    
}

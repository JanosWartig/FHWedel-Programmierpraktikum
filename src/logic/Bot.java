package logic;

import logic.Types.BotDirectionTypes;

import java.util.ArrayList;
import java.util.List;

public class Bot {

    private final Position position;
    private BotDirectionTypes botRotation;
    private final List<Position> visited;

    public Bot(Position position, BotDirectionTypes botRotation) {
        this.position = position;
        this.botRotation = botRotation;
        this.visited = new ArrayList<>();
        this.visited.add(new Position(this.position.getRowIndex(),this.position.getColumnIndex()));
    }

    public Position walkTester(int steps) {
        int rowIndex = this.position.getRowIndex();
        int columnIndex = this.position.getColumnIndex();
        switch (this.botRotation) {
            case NORTH:
                rowIndex = rowIndex - steps;
                break;
            case EAST:
                columnIndex = columnIndex + steps;
                break;
            case SOUTH:
                rowIndex = rowIndex + steps;
                break;
            case WEST:
                columnIndex = columnIndex - steps;
                break;
        }
        return new Position(rowIndex,columnIndex);
    }

    public void walk() {
        switch (this.botRotation) {
            case NORTH:
                // North => Means -1 rowIndex
                this.position.setRowIndex(this.position.getRowIndex() - 1);
                break;
            case EAST:
                // East => Means +1 columnIndex
                this.position.setColumnIndex(this.position.getColumnIndex() + 1);
                break;
            case SOUTH:
                // South => Means +1 rowIndex
                this.position.setRowIndex(this.position.getRowIndex() + 1);
                break;
            case WEST:
                // West => Means -1 columnIndex
                this.position.setColumnIndex(this.position.getColumnIndex() - 1);
                break;
        }
        this.visited.add(new Position(this.position.getRowIndex(),this.position.getColumnIndex()));
    }

    public void left() {
        switch (this.botRotation) {
            case NORTH:
                this.botRotation = BotDirectionTypes.WEST;
                break;
            case EAST:
                this.botRotation = BotDirectionTypes.NORTH;
                break;
            case SOUTH:
                this.botRotation = BotDirectionTypes.EAST;
                break;
            case WEST:
                this.botRotation = BotDirectionTypes.SOUTH;
                break;
        }
    }

    public void right() {
       switch (this.botRotation) {
           case NORTH:
               this.botRotation = BotDirectionTypes.EAST;
               break;
           case EAST:
               this.botRotation = BotDirectionTypes.SOUTH;
               break;
           case SOUTH:
               this.botRotation = BotDirectionTypes.WEST;
               break;
           case WEST:
               this.botRotation = BotDirectionTypes.NORTH;
               break;
       }
    }

    public void jump() {
        this.walk();
        this.walk();
    }

    public void exit() {
        this.walk();
    }

    public List<Position> getVisited() {
        return this.visited;
    }

    public Position getPosition() {
        return this.position;
    }

    public BotDirectionTypes getBotRotation() {
        return this.botRotation;
    }
}

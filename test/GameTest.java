import logic.Game;
import logic.Position;
import logic.Types.BotDirectionTypes;
import logic.Types.FieldTypes;
import logic.Types.InstructionTypes;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameTest {

    /**
     * The Bot walks 2 Steps in EAST Direction.
     */
    @Test
    public void testBotWalks() {
        FieldTypes[][] gameField = Games.normal();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        game.getBot().walk();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0,1));
        game.getBot().walk();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0,2));
    }

    /**
     * The Bot turns right (North to East) and then walks 2 steps.
     */
    @Test
    public void testTurnAndWalks() {
        FieldTypes[][] gameField = Games.normal();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.NORTH);
        Assert.assertEquals(game.getBot().getBotRotation(), BotDirectionTypes.NORTH);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        game.getBot().right();
        Assert.assertEquals(game.getBot().getBotRotation(), BotDirectionTypes.EAST);
        game.getBot().walk();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0,1));
        game.getBot().walk();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0,2));
    }

    /**
     * The Bot walks one step and collides with a wall.
     */
    @Test
    public void testBotCollidesWithWall() {
        FieldTypes[][] gameField = Games.wall();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        game.getBot().walk();
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
    }

    /**
     * The Bot walks one step and collides with a chasm
     */
    @Test
    public void testBotWalksInChasm() {
        FieldTypes[][] gameField = Games.chasm();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        game.getBot().walk();
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
    }

    /**
     * The Bot jumps over a chasm
     */
    @Test
    public void testBotJumpsOverChasm() {
        FieldTypes[][] gameField = Games.chasm();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        game.getBot().jump();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 2));
    }

    /**
     * The Bot jumps over a normal field
     */
    @Test
    public void testBotJumpsOverNormalField() {
        FieldTypes[][] gameField = Games.normal();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        game.getBot().jump();
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
    }

    /**
     * The Bot collects a coin
     */
    @Test
    public void testBotCollectsCoin() {
        FieldTypes[][] gameField = Games.coin();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        Assert.assertEquals(game.getCoinPositions().size(), 1);
        game.getBot().walk();
        Assert.assertEquals(game.getCoinPositions().size(), 0);
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 1));
    }

    /**
     * The Bot exits next to a door
     */
    @Test
    public void testBotExitNextToDoor() {
        FieldTypes[][] gameField = Games.coin();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        game.getBot().walk();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 1));
        game.getBot().exit();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 2));

    }

    /**
     * The Bot exits not next to a door
     */
    @Test
    public void testBotExitNotNextToDoor() {
        FieldTypes[][] gameField = Games.coin();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        game.getBot().exit();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 0));
    }

    /**
     * The Bot collects coins and exits
     */
    @Test
    public void testBotCollectsCoinsAndExits() {
        FieldTypes[][] gameField = Games.coinExit();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        Assert.assertEquals(game.getCoinPositions().size(), 1);
        game.getBot().walk();
        Assert.assertEquals(game.getCoinPositions().size(), 0);
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 1));
        game.getBot().exit();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 2));
    }

    /**
     * The Bot tries to exit without collecting all coins
     */
    @Test
    public void testBotExitsWithoutCollectionAllCoins() {
        FieldTypes[][] gameField = Games.coinsExit();
        Position botStartPosition = new Position(0,0);
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST);
        Assert.assertEquals(game.getBot().getPosition(), botStartPosition);
        Assert.assertEquals(game.getCoinPositions().size(), 2);
        game.getBot().walk();
        Assert.assertEquals(game.getCoinPositions().size(), 1);
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 1));
        game.getBot().exit();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 1));
    }

    /**
     * Programm calls Method (WALK) => VALID
     */
    @Test
    public void testPCallsP1() {
        FieldTypes[][] gameField = Games.coinsExit();
        Position botStartPosition = new Position(0,0);
        List<InstructionTypes> p = new ArrayList<>(Arrays.asList(InstructionTypes.METHOD));
        List<InstructionTypes> p1 = new ArrayList<>(Arrays.asList(InstructionTypes.WALK));
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST, p, p1, null);
        Assert.assertEquals(game.getMethod().get(0), InstructionTypes.WALK);
        game.runInstructions();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 1));

    }

    /**
     * Programm call Method (WALK) Twice => Valid
     */
    @Test
    public void testPCallsP1Twice() {
        FieldTypes[][] gameField = Games.normal();
        Position botStartPosition = new Position(0,0);
        List<InstructionTypes> p = new ArrayList<>(Arrays.asList(InstructionTypes.METHOD, InstructionTypes.METHOD));
        List<InstructionTypes> p1 = new ArrayList<>(Arrays.asList(InstructionTypes.WALK));
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST, p, p1, null);
        Assert.assertEquals(game.getMethod().get(0), InstructionTypes.WALK);
        game.runInstructions();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 2));
    }

    /**
     * Programm calls Method (METHOD2) and Method calls Method2 (WALK) => Not Valid
     */
    @Test
    public void testPCallsP1AndP1CallsP2() {
        FieldTypes[][] gameField = Games.coinsExit();
        Position botStartPosition = new Position(0,0);
        List<InstructionTypes> p = new ArrayList<>(Arrays.asList(InstructionTypes.METHOD));
        List<InstructionTypes> p1 = new ArrayList<>(Arrays.asList(InstructionTypes.METHOD2));
        List<InstructionTypes> p2 = new ArrayList<>(Arrays.asList(InstructionTypes.WALK));
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST, p, p1, p2);
        Assert.assertEquals(game.getProgram().get(0), InstructionTypes.METHOD);
        Assert.assertEquals(game.getMethod().get(0), InstructionTypes.METHOD2);
        Assert.assertEquals(game.getMethod2().get(0), InstructionTypes.WALK);
        game.runInstructions();
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 1));
    }

    /**
     * Programm calls Method (METHOD) and Method calls Method => NOT VALID RECURSION
     */
    @Test
    public void testRecursion() {
        FieldTypes[][] gameField = Games.coinsExit();
        Position botStartPosition = new Position(0,0);
        List<InstructionTypes> p = new ArrayList<>(Arrays.asList(InstructionTypes.METHOD));
        List<InstructionTypes> p1 = new ArrayList<>(Arrays.asList(InstructionTypes.METHOD));
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST, p, p1, null);
        Assert.assertEquals(game.getProgram().get(0), InstructionTypes.METHOD);
        Assert.assertEquals(game.getMethod().get(0), InstructionTypes.METHOD);
        game.runInstructions();
        // There should be an error
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 0));
    }

    /**
     * Programm calls Method2 (METHOD2) and Method2 calls Method2 => NOT VALID RECURSION
     */
    @Test
    public void testRecursion2() {
        FieldTypes[][] gameField = Games.coinsExit();
        Position botStartPosition = new Position(0,0);
        List<InstructionTypes> p = new ArrayList<>(Arrays.asList(InstructionTypes.METHOD2));
        List<InstructionTypes> p2 = new ArrayList<>(Arrays.asList(InstructionTypes.METHOD2));
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST, p, null, p2);
        Assert.assertEquals(game.getProgram().get(0), InstructionTypes.METHOD2);
        Assert.assertEquals(game.getMethod().get(0), InstructionTypes.METHOD2);
        game.runInstructions();
        // There should be an error
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 0));
    }

    /**
     * Programm calls Method (METHOD) and Method calls WALK then Method => NOT VALID RECURSION
     */
    @Test
    public void testRecursion3() {
        FieldTypes[][] gameField = Games.coinsExit();
        Position botStartPosition = new Position(0,0);
        List<InstructionTypes> p = new ArrayList<>(Arrays.asList(InstructionTypes.METHOD));
        List<InstructionTypes> p1 = new ArrayList<>(Arrays.asList(InstructionTypes.WALK, InstructionTypes.METHOD));
        Game game = new Game(gameField, botStartPosition, BotDirectionTypes.EAST, p, p1, null);
        Assert.assertEquals(game.getProgram().get(0), InstructionTypes.METHOD);
        Assert.assertEquals(game.getMethod().get(0), InstructionTypes.WALK);
        game.runInstructions();
        // There should be an error
        Assert.assertEquals(game.getBot().getPosition(), new Position(0, 0));
    }


}




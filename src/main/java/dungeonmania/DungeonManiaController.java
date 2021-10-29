package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.collectable.Collectable;
import dungeonmania.entity.creature.Enemy;
import dungeonmania.entity.creature.Player;
import dungeonmania.entity.interfaces.BattleStat;
import dungeonmania.entity.interfaces.CollideActionEntity;
import dungeonmania.entity.interfaces.Guard;
import dungeonmania.entity.interfaces.RegularActionEntity;
import dungeonmania.entity.interfaces.Weapon;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Hashtable; 
import java.util.List;
import dungeonmania.game.*;
import static dungeonmania.util.FileLoader.listFileNamesInResourceDirectory;

import org.json.JSONArray;
import org.json.JSONObject;

<<<<<<< HEAD
public class DungeonManiaController{
    //all entities from map, can move to other objects at later stage of development depending on how map is implemented
    private Player player;
    private ArrayList<CollideActionEntity> collideActionEntities;
    private ArrayList<Enemy> battlingNPCs;
    private ArrayList<RegularActionEntity> regularActionEntities;

    private ArrayList<Entity> allEntities;

    //TODO GOAL
    //PRIVATE ...
    //

    //TODO NOT MENTIONED UML
    public void removeEntityFromMap(Entity entity){
        collideActionEntities.remove(entity);
        battlingNPCs.remove(entity);
        regularActionEntities.remove(entity);
        allEntities.remove(entity);
    }

    //TODO NOT MENTIONED UML
    public Entity getEntityFromId(String id){
        for (Entity entity : allEntities){
            if (entity.getId().equals(id))
                return entity;
        }
        return null;
    }

    //TODO NOT MENTIONED UML
    public void addToBattle(Enemy enemy){
        collideActionEntities.remove(enemy);
        regularActionEntities.remove(enemy);
        battlingNPCs.add(enemy);
    }

    public void retreatFromBattle(Enemy enemy){
        collideActionEntities.add(enemy);
        regularActionEntities.add(enemy);
        battlingNPCs.remove(enemy);        
    }

    public void resolveBattle(){
        //TODO
        BattleStat playerStat = player.getBattleStat();
        //now only allows character to battle, no allies
        for (Enemy enemy : battlingNPCs){
            BattleStat enemyStat = enemy.getBattleStat();
            if (player.isInvincible()){
                playerDefeatsEnemy(enemy);
            }
            else if(player.isInvisible()){
                retreatFromBattle(enemy);
            }
            else{
                // resolve numbers for battling
                int playerReceivedDamage = (playerStat.getReducedAttack(enemyStat.getAttack()) * enemyStat.getHealth()) / 10;
                int enemyReceivedDamage = (enemyStat.getReducedAttack(playerStat.getAttack()) * playerStat.getHealth()) / 5;
                playerStat.reduceHealth(playerReceivedDamage);
                enemyStat.reduceHealth(enemyReceivedDamage);
                if (playerStat.getHealth() <= 0){
                    //TODO player loses and game ends
                }
                else if(enemyStat.getHealth() <= 0){
                    playerDefeatsEnemy(enemy);
                }
            }
        }
    }

    private void playerDefeatsEnemy(Enemy enemy){
        BattleStat enemyStat = enemy.getBattleStat();
        //player receive rewards//
        for (Weapon weapon : enemyStat.getWeapons()){
            player.addCollectable((Collectable)weapon);
        }
        for (Guard guard : enemyStat.getGuards()){
            player.addCollectable((Collectable)guard);
        }
        //randomly give player an epic reward
        //TODO
        
        
        //receive reward end//        
        removeEntityFromMap(enemy);
    }

=======
public class DungeonManiaController {
    
    //key is the dungeonId -> use static variable for number of dungeons -> what happens if we finish a game?
    private Map<String,GameAPI> games;
    private GameAPI currentGame;
>>>>>>> origin/Hugh_mapBuilder
    public DungeonManiaController() {

        this.games = new Hashtable<>();
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
 
        try {    
            if (!(getGameModes().contains(gameMode))) {
                throw new IllegalArgumentException();
            } else if (!FileLoader.listFileNamesInResourceDirectory("dungeons").contains(dungeonName)) {
                throw new IllegalArgumentException();
            } else {
                GameAPI newGame = new Game(dungeonName, gameMode);
                currentGame = newGame; 
                return newGame.getInfo();
            }
        }
        catch(IOException e) {
            return null;
        } 
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        if (games.containsKey(name))
            throw new IllegalArgumentException();
        else
            games.put(name, currentGame);
            currentGame.setID(name);
            DungeonResponse returnInfo = currentGame.getInfo();
            currentGame = null;
            return returnInfo;
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        if(!games.containsKey(name))
            throw new IllegalArgumentException();
        else
            currentGame = games.get(name);
            return currentGame.getInfo();
    }

    public List<String> allGames() {
        List<String> ids = new ArrayList<>();
        games.entrySet().stream().forEach(e -> ids.add(e.getKey()));
        return ids;
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
}
package dungeonmania.entity.creature;

import dungeonmania.map.DungeonMapAPI;
import dungeonmania.entity.interfaces.BattleStat;
import dungeonmania.entity.interfaces.MovementNPC;
import dungeonmania.entity.interfaces.MovableNPC;
import dungeonmania.util.Position;

public class Enemy extends Creature implements MovableNPC{

    private MovementNPC movement;
    
    public Enemy(DungeonMapAPI game, String type, Position position,
                 MovementNPC movement, BattleStat battleStat) {
        super(game, type, position, battleStat);
        this.movement = movement;
    }

    @Override
    public void collideAction(Player player) {
        if (!player.isInvisible() && this.isHostile()){
            getGame().addToBattle(this);
        }
    }

    @Override
    public void move() {
        movement.move();
    }

}

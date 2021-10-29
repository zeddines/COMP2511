package dungeonmania.entity.square;

import dungeonmania.entity.Entity;
import dungeonmania.entity.creature.Player;
import dungeonmania.map.DungeonMapAPI;
import dungeonmania.entity.*;
import dungeonmania.util.*;
import org.json.JSONObject;

public class Wall extends Entity {
    public Wall(Position current, String type, DungeonMapAPI map) {
        super(map, current, type, false); 
    }

    @Override
    public void action(Player player) {
        return;
    }

    /*/**
     *  Blocks the movement of the character, enemies and boulders 
     */
   /* @Override
    public void movement(String s) {
        return; 
    }*/
}

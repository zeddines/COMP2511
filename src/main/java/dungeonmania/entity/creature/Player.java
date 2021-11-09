package dungeonmania.entity.creature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityAPI;
import dungeonmania.entity.collectable.Collectable;
import dungeonmania.entity.collectable.Effect;
import dungeonmania.entity.collectable.Key;
import dungeonmania.entity.collectable.Potion;
import dungeonmania.entity.interfaces.BattleStat;
import dungeonmania.entity.interfaces.BattleGear;
import dungeonmania.entity.interfaces.Usable;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.map.DungeonMap;
import dungeonmania.map.DungeonMapAPI;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Player extends Creature{

    Map<String, Integer> buffs;

    private ArrayList<Effect> effectsInAction;

    public Player(DungeonMapAPI map, String type, Position position) {
        super(map, type, position);
        effectsInAction = new ArrayList<>();
        buffs = new HashMap<>();
        buffs.put("invisibility", 0);
        buffs.put("invincibility", 0);
        map.setPlayer(this);
    }

    
    public void use(String id) throws IllegalArgumentException, InvalidActionException{
        Entity entity = getNonBattleItemFromInventory(id);
        if (entity == null){
            throw new InvalidActionException(id);
        }
        if (!(entity instanceof Usable))
            throw new IllegalArgumentException("item is not usable");
        Usable item = (Usable)entity;    
        item.use();
    }

    public ArrayList<ItemResponse> inventoryToItemResponseList(){
        ArrayList<ItemResponse> returnList = new ArrayList<>();

        for (Collectable item : getNonBattleItems()){
            returnList.add(new ItemResponse(item.getId(), item.getType()));
        }

        for (BattleGear battleGear : getBattleGears()){
            Entity battleGearAsEntity = (Entity)battleGear;
            returnList.add(new ItemResponse(battleGearAsEntity.getId(), battleGearAsEntity.getType()));
        }

        return returnList;
    }

    public ArrayList<Collectable> give(ArrayList<String> itemsRequired){
        ArrayList<Integer> positionOfItems = new ArrayList<>();
        for (String itemRequired : itemsRequired){
            boolean itemIsMissing = true;
            for (int i = 0; i < getNonBattleItems().size() && itemIsMissing; i++){
                Collectable itemAvailable = getNonBattleItems().get(i);
                if (itemRequired.equals(itemAvailable.getType()) && !positionOfItems.contains(i)){
                    itemIsMissing = false;
                    positionOfItems.add(i);
                }
            }
            if (itemIsMissing){

                return null;
            }
        }
        ArrayList<Collectable> collectablesToGive = new ArrayList<>();
        for (int i : positionOfItems){
            collectablesToGive.add(getNonBattleItems().get(i));
        }
        for (Collectable collectableToRemove : collectablesToGive){
            getNonBattleItems().remove(collectableToRemove);
        }
        return collectablesToGive;
    }
    
    public void addEffectInAction(Effect effect){
        effectsInAction.add(effect);
    }

    public void removeEffectInAction(Effect effect){
        effectsInAction.remove(effect);
    }

    public void updateEffects(){
        for (Effect effect : DungeonMap.shallowClone(effectsInAction)){
            effect.updateEffectDuration();    
        }
    }
    
    public void applyInvisibleBuff() {
        buffs.put("invisibility", buffs.get("invisibility").intValue() + 1);
    }


    public void applyInvincibleBuff() {
        buffs.put("invincibility", buffs.get("invincibility").intValue() + 1);
    }

    public void revertInvisibleBuff() {
        buffs.put("invisibility", buffs.get("invisibility").intValue() - 1);
    }


    public void revertInvincibleBuff() {
        buffs.put("invincibility", buffs.get("invincibility").intValue() - 1);
    }



    //getter setters
    public boolean isInvisible() {
        return buffs.get("invisibility") > 0;
    }

    public boolean isInvincible() {
        return buffs.get("invincibility") > 0;
    }

}

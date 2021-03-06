package polarity.shared.character;

import polarity.shared.ai.states.IdleState;
import polarity.shared.ai.states.MonsterState;
import polarity.shared.character.data.MonsterData;
import polarity.shared.entity.LivingEntity;
import polarity.shared.actions.Action;
import polarity.shared.events.ProjectileEvent;
import polarity.shared.spellforge.nodes.CoreVals;
import polarity.shared.tools.Sys;
import polarity.shared.world.GameWorld;

/**
 *
 * @author Sindusk
 */
public class Monster extends LivingCharacter {
    protected MonsterState state;
    protected MonsterData data;
    protected float stateCheckTimer = 0;
    
    // AI-Related Stuff (Testing)
    protected float leashRange = 20;
    protected float attackTimer = 0;
    protected float attackCD = 1;
    
    public Monster(MonsterData d){
        super(d.getID(), d.getName());
        this.data = d;
    }
    public void createEntity(GameWorld world){
        entity = world.addMonsterEntity(this);
        entity.moveInstant(data.getLocation());
        this.state = new IdleState(this);
    }
    
    public float getStateCheckTimer(){
        return stateCheckTimer;
    }
    public float getLeashRange(){
        return leashRange;
    }
    public MonsterState getState(){
        return state;
    }
    public MonsterData getData(){
        return data;
    }
    
    public void setState(MonsterState state){
        this.state = state;
        Sys.getNetwork().send(state.toMessage());
    }
    
    @Override
    public boolean isEnemy(GameCharacter other){
        if(other instanceof Player){
            return true;
        }
        return false;
    }
    
    public boolean attackReady(float tpf){
        if(attackTimer > attackCD){
            attackTimer -= attackCD;
            return true;
        }
        attackTimer += tpf;
        return false;
    }
    public void attack(GameWorld world, LivingEntity target, float tpf){
        ProjectileEvent event = new ProjectileEvent(this, entity.getLocation(), target.getLocation(), new CoreVals());
        event.addAction(new Action(){
            @Override
            public boolean onCollide(GameCharacter owner, LivingEntity entity){
                entity.damage(25);
                return false;
            }
        });
        // TODO: Re-implement
        /*if(Sys.getNetwork() instanceof ServerNetwork){
            ServerNetwork polarity.shared.network = (ServerNetwork) Sys.getNetwork();
            event.execute(polarity.shared.network.getServer(), polarity.shared.world);
        }*/
    }
    
    public void serverUpdate(GameWorld world, float tpf){
        super.serverUpdate(tpf);
        state.update(world, tpf);
    }
    
    public void addStateCheckTime(float tpf){
        stateCheckTimer += tpf;
    }
    public void subStateCheckTime(float time){
        stateCheckTimer -= time;
    }
}

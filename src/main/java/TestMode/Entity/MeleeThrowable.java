package TestMode.Entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Created by User on 18.06.2017.
 */
public class MeleeThrowable extends EntityThrowable {

    int range;
    ItemStack stack;

    public MeleeThrowable(World world, EntityLivingBase entity, ItemStack stack, int range){
        super(world, entity);
        this.stack = stack;
        this.range = range;

        setNoGravity(true);
        this.setSize(0.25F, 0.25F);
    }
    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit instanceof EntityLivingBase && !getThrower().equals(result.entityHit)){
            ((EntityPlayer)getThrower()).attackTargetEntityWithCurrentItem(result.entityHit);
        }
        setDead();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (getThrower().getPosition().getDistance((int)lastTickPosX, (int)lastTickPosY, (int)lastTickPosZ) > range){
            this.setDead();
        }
    }
}

package TestMode.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by User on 18.06.2017.
 */
public class ToolEffects {
    static Random r = new Random();

    public static void setOnFire(ItemStack stack, EntityPlayer player, Entity entity, int time){
        World world = player.worldObj;
        for (int i = 0; i < 5; i++){
            BlockPos pos = entity.getPosition().add(MathHelper.getRandomIntegerInRange(r, -5, 5),MathHelper.getRandomIntegerInRange(r, -5, 5),MathHelper.getRandomIntegerInRange(r, -5, 5));
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX(), pos.getY(), pos.getZ(), 1,1,1,new int[0]);
        }
        entity.setFire(time);
    }

    public static void spawnLightnings(ItemStack stack, EntityPlayer player, Entity entity){
        Vec3d initPos = player.getPositionVector();
        Vec3d entityPos = entity.getPositionVector();
        if (initPos.distanceTo(entityPos) > 5) {
            player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, entity.posX, entity.posY, entity.posZ, false));
            stack.damageItem(2, player);
        }
    }

    public static void tryToHeal(ItemStack stack, EntityPlayer player, Entity entity, int healAmount){
        if (!player.isSneaking()) {
            player.heal(healAmount);
            if (!player.capabilities.isCreativeMode)
                stack.damageItem(1, player);
        }
        else {
            player.heal(healAmount * 2);
            if (!player.capabilities.isCreativeMode)
                stack.damageItem(3, player);
        }
    }

    public static void poison(ItemStack stack, EntityPlayer player, Entity entity, int time){
        if (entity instanceof EntityLivingBase){
            EntityLivingBase target = (EntityLivingBase)entity;
            target.addPotionEffect(new PotionEffect(MobEffects.POISON, time));
        }
    }

    public static void slowDown(ItemStack stack, EntityPlayer player, Entity entity, int time){
        if (entity instanceof EntityLivingBase){
            EntityLivingBase target = (EntityLivingBase)entity;
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, time, 2));
        }
    }

    public static void attackPlayer(ItemStack stack, EntityPlayer player, Entity entity){
        if (entity instanceof EntityPlayer){
            EntityPlayer target = (EntityPlayer)entity;
            target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 7));
            target.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 10));
            target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 7));
            target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20, 2));
        }
    }


}

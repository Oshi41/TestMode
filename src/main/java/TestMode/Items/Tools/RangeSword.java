package TestMode.Items.Tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by User on 18.06.2017.
 */
public class RangeSword extends SwordBase {

    static int range;
    public RangeSword(ToolMaterial material, int range) {
        super(material);
        this.range = range;
    }

    //region Helping methods
    public static void findEntities(EntityPlayer player){
        Vec3d temp = Vec3d.fromPitchYaw(player.rotationPitch, player.rotationYaw).scale(range);
        Vec3d begin = player.getPositionVector(),
                end = begin.add(temp);
        final AxisAlignedBB aabb = new AxisAlignedBB(begin, end).expand(1,1,1);
        List<Entity> entities = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, aabb);

        if (entities != null && entities.size() > 0){
            List<EntityLivingBase> toHit = entities.stream().filter(x -> x instanceof EntityLivingBase)
                    .map(n -> (EntityLivingBase) n).collect(Collectors.toList());
            if (toHit.size() > 0)
                player.attackTargetEntityWithCurrentItem(entities.get(0));
        }
    }
    //endregion
}

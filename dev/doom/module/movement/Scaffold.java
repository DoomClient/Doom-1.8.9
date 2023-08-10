package dev.doom.module.movement;

import dev.doom.event.EventTarget;
import dev.doom.event.events.EventSendPacket;
import dev.doom.event.events.EventUpdate;
import dev.doom.module.Category;
import dev.doom.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

public class Scaffold extends Module {
    public Scaffold() {
        super("Scaffold", Keyboard.KEY_G, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(this.isToggled()) {
            Entity p = mc.thePlayer;
            BlockPos bp = new BlockPos(p.posX, p.getEntityBoundingBox().minY, p.posZ);
            if(valid(bp.add(0, -2, 0)))
                place(bp.add(0, -1, 0), EnumFacing.UP);

            else if(valid(bp.add(-1, -1, 0)))
                place(bp.add(0, -1, 0), EnumFacing.EAST);

            else if(valid(bp.add(1, -1, 0)))
                place(bp.add(0, -1, 0), EnumFacing.WEST);

            else if(valid(bp.add(0, -1, -1)))
                place(bp.add(0, -1, 0), EnumFacing.SOUTH);

            else if(valid(bp.add(0, -1, 1)))
                place(bp.add(0, -1, 0), EnumFacing.NORTH);

            else if (valid(bp.add(1, -1, 1))) {
                if(valid(bp.add(0, -1, 1)))
                    place(bp.add(0, -1, 1), EnumFacing.NORTH);

                place(bp.add(1, -1, 1), EnumFacing.EAST);

            } else if(valid(bp.add(-1, -1, 1))) {
                if(valid(bp.add(-1, -1, 0)))
                    place(bp.add(0, -1, 1), EnumFacing.WEST);

                place(bp.add(-1, -1, 1), EnumFacing.SOUTH);

            } else if(valid(bp.add(-1, -1, -1))) {
                if(valid(bp.add(0, -1, -1)))
                    place(bp.add(0, -1, 1), EnumFacing.SOUTH);

                place(bp.add(-1, -1, 1), EnumFacing.WEST);

            } else if(valid(bp.add(1, -1, -1))) {
                if(valid(bp.add(1, 1, 0)))
                    place(bp.add(1, -1, 0), EnumFacing.EAST);

                place(bp.add(1, -1, -1), EnumFacing.NORTH);
            }
        }
    }

    void place(BlockPos p, EnumFacing f) {
        if(f == EnumFacing.UP)
            p = p.add(0, -1, 0);
        if(f == EnumFacing.NORTH)
            p = p.add(0, 0, 1);
        if(f == EnumFacing.EAST)
            p = p.add(-1, 0, 0);
        if(f == EnumFacing.SOUTH)
            p = p.add(0, 0, -1);
        if(f == EnumFacing.WEST)
            p = p.add(1, 0, 0);

        EntityPlayerSP _p = mc.thePlayer;

        if(_p.getHeldItem() != null && _p.getHeldItem().getItem() instanceof ItemBlock) {
            mc.playerController.onPlayerRightClick(_p, mc.theWorld, _p.getHeldItem(), p, f, new Vec3(.5, .5, .5));
            double x = p.getX() + .25 - _p.posX;
            double y = p.getX() + .25 - _p.posY;
            double z = p.getX() + .25 - _p.posZ;
            double dist = MathHelper.sqrt_double(x * x + z * z);
            float yaw = (float) (Math.atan2(z, x) * 180 / Math.PI - 90);
            float pitch = (float) (Math.atan2(y, dist) * 180 / Math.PI);
            _p.swingItem();
            new EventSendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(_p.posX, _p.posY, _p.posZ, yaw, pitch, _p.onGround));
        }
    }

    boolean valid(BlockPos p) {
        Block b = mc.theWorld.getBlock(p);
        return !(b instanceof BlockLiquid) && b.getMaterial() != Material.air;
    }

//    public float[] getRotations (BlockPos bp) {
//        double deltaX = bp. + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
//                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
//                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
//                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
//
//        float yaw = (float) Math.toDegrees (-Math.atan (deltaX / deltaZ)),
//                pitch = (float) -Math.toDegrees (Math.atan (deltaY / distance));
//
//        if (deltaX < 0 && deltaZ < 0) {
//            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
//        }else if (deltaX > 0 && deltaZ < 0) {
//            yaw = (float) (-90+ Math.toDegrees(Math.atan(deltaZ / deltaX)));
//        }
//
//        return new float[] {yaw, pitch};
//    }
}

package com.dashomi.preventer.modules;

import com.dashomi.preventer.PreventerClient;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BreakBlockModule {
    public static ActionResult checkBlockBreak(PlayerEntity playerEntity, World world, Hand hand, BlockPos pos, Direction direction) {
        if (!PreventerClient.config.overrideKeyPressed) {
            Block targetBlock = world.getBlockState(pos).getBlock();
            if (PreventerClient.config.onlyMatureCropHarvest) {
                if (targetBlock instanceof CropBlock) {
                    if (!((CropBlock) targetBlock).isMature(world.getBlockState(pos))) {
                        if (PreventerClient.config.moduleUseInfoGroup.onlyMatureCropHarvest_msg) {
                            playerEntity.sendMessage(Text.translatable("config.preventer.onlyMatureCropHarvest.text"), true);
                        }
                        return ActionResult.FAIL;
                    }
                }
            }

            if (PreventerClient.config.preventBuddingAmethystBreaking) {
                if (targetBlock instanceof BuddingAmethystBlock) {
                    if (PreventerClient.config.moduleUseInfoGroup.preventBuddingAmethystBreaking_msg) {
                        playerEntity.sendMessage(Text.translatable("config.preventer.preventBuddingAmethystBreaking.text"), true);
                    }
                    return ActionResult.FAIL;
                }
            }

            if (PreventerClient.config.preventStemBreaking) {
                if (targetBlock instanceof AttachedStemBlock || targetBlock instanceof StemBlock) {
                    if (PreventerClient.config.moduleUseInfoGroup.preventStemBreaking_msg) {
                        playerEntity.sendMessage(Text.translatable("config.preventer.preventStemBreaking.text"), true);
                    }
                    return ActionResult.FAIL;
                }
            }

            if (PreventerClient.config.lowDurabilityProtection) {
                if (!playerEntity.isCreative() && !playerEntity.isSpectator()) {
                    ItemStack stack = playerEntity.getStackInHand(hand);
                    if (stack.isDamageable()) {
                        if (stack.getDamage() >= stack.getMaxDamage() - PreventerClient.config.moduleConfigGroup.lowDurabilityProtectionRange) {
                            if (PreventerClient.config.moduleUseInfoGroup.lowDurabilityProtection_msg) {
                                playerEntity.sendMessage(Text.translatable("config.preventer.lowDurabilityProtection.text"), true);
                            }
                            return ActionResult.FAIL;
                        }
                    }
                }
            }
        }
        return ActionResult.PASS;
    }
}

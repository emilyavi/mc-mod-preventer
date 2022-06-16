package com.dashomi.preventer.util;

import com.dashomi.preventer.PreventerClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;

public class LowDurabilityProtectionHelper {
    public static ActionResult doDurabilityCheck(ItemStack stack, PlayerEntity player) {
        if (!player.isCreative() && !player.isSpectator()) { // AttackBlockCallback does not do game mode check for us, so we need to do it by ourselves
            if (stack.isDamageable()) { // Check if the item is damageable
                if (stack.getDamage() >= stack.getMaxDamage() - PreventerClient.config.moduleConfigGroup.lowDurabilityProtectionRange) { // Check if the item is *almost* broken
                    player.sendMessage(new TranslatableText("config.preventer.lowDurabilityProtection.text"), true);
                    return ActionResult.FAIL;
                }
            }
        }
        return ActionResult.PASS;
    }
}

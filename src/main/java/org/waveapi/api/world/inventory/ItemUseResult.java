package org.waveapi.api.world.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;

public enum ItemUseResult {
    CONSUME,
    FAIL,
    PASS,
    SUCCESS;

    public static ItemUseResult from(TypedActionResult<net.minecraft.item.ItemStack> action) {
        ItemUseResult result = null;

        switch (action.getResult()) {
            case SUCCESS -> {
                result = SUCCESS;
            }
            case CONSUME, CONSUME_PARTIAL -> {
                result = CONSUME;
            }
            case PASS -> {
                result = PASS;
            }
            case FAIL -> {
                result = FAIL;
            }
        }

        return result;
    }

    public static TypedActionResult<net.minecraft.item.ItemStack> to(net.minecraft.item.ItemStack item, ItemUseResult action) {
        TypedActionResult<ItemStack> result = null;

        switch (action) {

            case CONSUME -> {
                result = TypedActionResult.consume(item);
            }
            case FAIL -> {
                result = TypedActionResult.fail(item);
            }
            case PASS -> {
                result = TypedActionResult.pass(item);
            }
            case SUCCESS -> {
                result = TypedActionResult.success(item);
            }
        }

        return result;
    }
}

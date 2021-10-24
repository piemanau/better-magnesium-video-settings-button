package com.limeshulkerbox.bmvsb.mixins;

import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoSettingsScreen.class)
abstract class VideoOptionsScreenMixin extends Screen {

    protected VideoOptionsScreenMixin(ITextComponent titleIn) {
        super(titleIn);
    }

    @Shadow
    @Final
    @Mutable
    private static AbstractOption[] OPTIONS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void removeOptions(CallbackInfo ci) {
        OPTIONS = ArrayUtils.removeElement(OPTIONS, AbstractOption.RENDER_CLOUDS);
    }

    @Inject(method = "init", at = @At("HEAD"))
    void mixinInit(CallbackInfo ci) {
        this.addButton(new Button(this.width / 2 + 5, this.height - 27, 150, 20, new TranslationTextComponent("text.bmvsb.magnesiumvideosettings"), (button) -> {
            assert this.minecraft != null;
            this.minecraft.displayGuiScreen(new SodiumOptionsGUI(this));
        }));
    }

    @ModifyConstant(method = "init", constant = @Constant(intValue = 100, ordinal = 0))
    private int modifyDonePos(int input) {
        return 155;
    }

    @ModifyConstant(method = "init", constant = @Constant(intValue = 200, ordinal = 0))
    private int modifyDoneWidth(int input) {
        return 150;
    }
}
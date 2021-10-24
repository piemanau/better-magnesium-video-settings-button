package com.limeshulkerbox.bmvsb.mixins;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OptionsScreen.class, priority = 99999)
class OptionsScreenMixin extends Screen {

    @Final
    @Shadow
    private GameSettings settings;

    protected OptionsScreenMixin(ITextComponent title) {
        super(title);
    }

    @Dynamic
    @Inject(method = "lambda$init$5", at = @At(value = "HEAD"), cancellable = true)
    private void disableSodiumSettings(Button p_213056_1_, CallbackInfo ci) {
        assert this.minecraft != null;
        this.minecraft.displayGuiScreen(new VideoSettingsScreen(this, this.settings));
        ci.cancel();
    }
}
package com.limeshulkerbox.bmvsb.mixin;

import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OptionsScreen.class, priority = 0)
public abstract class MixinOptionsScreen extends Screen {
    @Shadow
    @Final
    private Options options;

    protected MixinOptionsScreen(Component title) {
        super(title);
    }

    @Dynamic
    @Inject(method = "lambda$init$4(Lnet/minecraft/client/gui/components/Button;)V", at = @At("HEAD"), cancellable = true)
    private void disableSodiumSettings(Button widget, CallbackInfo ci) {
        assert this.minecraft != null;
        this.minecraft.setScreen(new VideoSettingsScreen(this, this.options));
        ci.cancel();
    }
}
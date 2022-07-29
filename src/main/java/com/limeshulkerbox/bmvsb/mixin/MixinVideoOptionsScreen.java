package com.limeshulkerbox.bmvsb.mixin;

import net.minecraft.client.Option;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Constructor;

@Mixin(VideoSettingsScreen.class)
public abstract class MixinVideoOptionsScreen extends Screen {
    @Shadow
    @Final
    @Mutable
    private static Option[] OPTIONS;
    @Unique
    Constructor<?> SodiumVideoOptionsScreenClassCtor;
    @Unique
    Constructor<?> SodiumOptionsGUIClassCtor;
    protected MixinVideoOptionsScreen(Component title) {
        super(title);
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void removeOptions(CallbackInfo ci) {
        OPTIONS = ArrayUtils.removeElement(OPTIONS, Option.USE_FULLSCREEN);
        OPTIONS = ArrayUtils.removeElement(OPTIONS, Option.RENDER_CLOUDS);
    }

    @Inject(method = "init", at = @At("HEAD"))
    void mixinInit(CallbackInfo callbackInfo) {
        this.addRenderableWidget(new Button(this.width / 2 + 5, this.height - 27, 150, 20, new TranslatableComponent("text.bmvsb.magnesiumvideosettings"), (button) -> {
            if (ModList.get().isLoaded("reeses-sodium-options")) {
                flashyReesesOptionsScreen();
            } else {
                sodiumVideoOptionsScreen();
            }
        }));
    }

    @Unique
    void flashyReesesOptionsScreen() {
        if (SodiumVideoOptionsScreenClassCtor == null) {
            try {
                SodiumVideoOptionsScreenClassCtor = Class.forName("me.flashyreese.mods.reeses_sodium_options.client.gui.SodiumVideoOptionsScreen").getConstructor(Screen.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            assert this.minecraft != null;
            this.minecraft.setScreen((Screen) SodiumVideoOptionsScreenClassCtor.newInstance(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Unique
    void sodiumVideoOptionsScreen() {
        if (SodiumOptionsGUIClassCtor == null) {
            try {
                SodiumOptionsGUIClassCtor = Class.forName("me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI").getConstructor(Screen.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            assert this.minecraft != null;
            this.minecraft.setScreen((Screen) SodiumOptionsGUIClassCtor.newInstance(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
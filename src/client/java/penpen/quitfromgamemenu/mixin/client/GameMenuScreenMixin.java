package penpen.quitfromgamemenu.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
	protected GameMenuScreenMixin(Text title) {
		super(title);
	}

	@Inject(at = @At("RETURN"), method = "initWidgets")
	private void addCustomButton(CallbackInfo ci) {
		List<ClickableWidget> buttons = this.children().stream()
				.filter(ClickableWidget.class::isInstance)
				.map(ClickableWidget.class::cast)
				.toList();

		if (!buttons.isEmpty()) {
			ClickableWidget lastButton = buttons.getLast();

			this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.quit"), button ->
					MinecraftClient.getInstance().scheduleStop())
					.position(lastButton.getX(), lastButton.getY() + lastButton.getHeight() + 4).size(lastButton.getWidth(), 20).build());
		}
	}
}


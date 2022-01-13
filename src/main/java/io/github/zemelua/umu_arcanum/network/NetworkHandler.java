package io.github.zemelua.umu_arcanum.network;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.util.ColorEffectMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Supplier;

public class NetworkHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			UMUArcanum.resource("main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void initialize() {
		NetworkHandler.CHANNEL.registerMessage(
				0, ColorEffectMessage.class,
				ColorEffectMessage::encode,
				ColorEffectMessage::decode,
				ColorEffectMessage::handle,
				Optional.of(NetworkDirection.PLAY_TO_CLIENT)
		);
	}

	public static void handlePacket(ColorEffectMessage message, Supplier<NetworkEvent.Context> context) {
		Level world = Minecraft.getInstance().level;

		if (world != null) {
			world.addParticle(
					ParticleTypes.ENTITY_EFFECT,
					message.getXPos(), message.getYPos(), message.getZPos(),
					(double) (message.getColor() >> 16 & 255) / 255.0D,
					(double) (message.getColor() >> 8 & 255) / 255.0D,
					(double) (message.getColor() & 255) / 255.0D
			);
		}
	}
}

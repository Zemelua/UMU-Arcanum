package io.github.zemelua.umu_arcanum.util;

import io.github.zemelua.umu_arcanum.network.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class ColorEffectMessage {
	private final double xPos;
	private final double yPos;
	private final double zPos;
	private final int color;

	public ColorEffectMessage(double xPos, double yPos, double zPos, int color) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.color = color;
	}

	public double getXPos() {
		return this.xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public double getZPos() {
		return zPos;
	}

	public int getColor() {
		return color;
	}

	public static void encode(ColorEffectMessage message, FriendlyByteBuf buffer) {
		buffer.writeDouble(message.getXPos());
		buffer.writeDouble(message.getYPos());
		buffer.writeDouble(message.getZPos());
		buffer.writeVarInt(message.getColor());
	}

	public static ColorEffectMessage decode(FriendlyByteBuf buffer) {
		return new ColorEffectMessage(
				buffer.readDouble(),
				buffer.readDouble(),
				buffer.readDouble(),
				buffer.readInt()
		);
	}

	public static void handle(ColorEffectMessage message, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(()
				-> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> NetworkHandler.handlePacket(message, context))
		);
		context.get().setPacketHandled(true);
	}

	public static void send(double xPos, double yPos, double zPos, int color) {
		NetworkHandler.CHANNEL.send(PacketDistributor.ALL.noArg(), new ColorEffectMessage(xPos, yPos, zPos, color));
	}
}

package io.github.zemelua.umu_arcanum.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public final class RecipeUtils {
	private RecipeUtils() {}

	public static List<Ingredient> ingredientsFromJson(JsonArray jsonArray) {
		List<Ingredient> list = new ArrayList<>();

		for (JsonElement jsonElement : jsonArray) {
			list.add(Ingredient.fromJson(jsonElement));
		}

		return list;
	}

	public static List<Ingredient> ingredientsFromBuffer(int size, FriendlyByteBuf buffer) {
		List<Ingredient> list = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			list.add(Ingredient.fromNetwork(buffer));
		}

		return list;
	}

	public static void ingredientsToBuffer(FriendlyByteBuf buffer, List<Ingredient> ingredients) {
		for (Ingredient ingredient : ingredients) {
			ingredient.toNetwork(buffer);
		}
	}

	public static List<Soup> soupsFromJson(JsonArray jsonArray) {
		List<Soup> list = new ArrayList<>();

		for (JsonElement jsonElement : jsonArray) {
			list.add(Soup.fromJson(jsonElement));
		}

		return list;
	}

	public static List<Soup> soupsFromBuffer(int size, FriendlyByteBuf buffer) {
		List<Soup> list = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			list.add(Soup.fromBuffer(buffer));
		}

		return list;
	}

	public static void soupsToBuffer(FriendlyByteBuf buffer, List<Soup> soups) {
		for (Soup soup : soups) {
			soup.toBuffer(buffer);
		}
	}
}

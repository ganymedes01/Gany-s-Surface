package ganymedes01.ganyssurface.integration;

import ganymedes01.ganyssurface.blocks.ModBlocks;
import ganymedes01.ganyssurface.items.ModItems;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ForestryManager extends Integration {

	@Override
	public void init() {
		FMLInterModComms.sendMessage("Forestry", "add-farmable-crop", "farmWheat@" + ModItems.camelliaSeeds.itemID + ".0." + ModBlocks.camelliaCrop.blockID + ".7");

		ItemStack fert = getItem("fertilizerCompound", 24);
		if (fert != null)
			GameRegistry.addRecipe(fert, "xxx", "xyx", "xxx", 'x', new ItemStack(ModItems.rot, 1, 1), 'y', new ItemStack(Item.dyePowder, 1, 4));

		addSqueezerRecipe(new ItemStack(ModItems.camelliaSeeds), 20);
	}

	@Override
	public void postInit() {
		addSqueezerRecipe(new ItemStack(ModItems.camelliaSeeds));
	}

	@Override
	public String getModID() {
		return "Forestry";
	}

	private void addSqueezerRecipe(ItemStack seeds) {
		try {
			Class<?> recipeManagers = Class.forName("forestry.api.recipes.RecipeManagers");
			Field field = recipeManagers.getField("squeezerManager");
			Object ret = field.get(null);
			Method addRecipe = ret.getClass().getMethod("addRecipe", int.class, ItemStack[].class, FluidStack.class);
			addRecipe.invoke(ret, 10, new ItemStack[] { seeds }, new FluidStack(FluidRegistry.getFluid("seedoil"), 10));

		} catch (Exception e) {
		}
	}

	private ItemStack getItem(String name, int size) {
		try {
			Class<?> items = Class.forName("forestry.core.config.ForestryItem");
			Field field = items.getField(name);
			Object ret = field.get(null);

			if (ret instanceof Item) {
				ItemStack newStack = new ItemStack((Item) ret, size);
				newStack.stackSize = size;
				return newStack;
			} else {
				Object[] enums = items.getEnumConstants();
				for (Object e : enums)
					if (name.equals(e.toString())) {
						items = e.getClass();
						Method getItemStack = items.getDeclaredMethod("getItemStack", int.class);
						return (ItemStack) getItemStack.invoke(e, size);
					}
			}
			return null;
		} catch (Exception e) {
		}
		return null;
	}

	private void addSqueezerRecipe(ItemStack seeds, int amount) {
		try {
			Class<?> recipeManagers = Class.forName("forestry.api.recipes.RecipeManagers");
			Field field = recipeManagers.getField("squeezerManager");
			Object ret = field.get(null);
			Method addRecipe = ret.getClass().getMethod("addRecipe", int.class, ItemStack[].class, FluidStack.class);
			addRecipe.invoke(ret, 10, new ItemStack[] { seeds }, new FluidStack(FluidRegistry.getFluid("seedoil"), amount));

		} catch (Exception e) {
		}
	}
}
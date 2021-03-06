package shadows.apotheosis.ench.objects;

import java.util.List;
import java.util.Locale;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.BookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.ench.EnchModule;

public class TomeItem extends BookItem {

	final ItemStack rep;
	final EnchantmentType type;

	public TomeItem(Item rep, EnchantmentType type) {
		super(new Item.Properties().group(ItemGroup.MISC));
		this.type = type;
		this.rep = new ItemStack(rep);
		this.setRegistryName(Apotheosis.MODID, (type == null ? "null" : type.name().toLowerCase(Locale.ROOT)) + "_book");
		EnchModule.TYPED_BOOKS.add(this);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return stack.getCount() == 1;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (type == null) return EnchModule.TYPED_BOOKS.stream().filter(b -> b != this).allMatch(b -> !enchantment.canApply(new ItemStack(b)));
		return enchantment.type == type || enchantment.canApplyAtEnchantingTable(rep);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("info.apotheosis." + getRegistryName().getPath()).mergeStyle(TextFormatting.GRAY));
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return !stack.isEnchanted() ? super.getRarity(stack) : Rarity.UNCOMMON;
	}

}
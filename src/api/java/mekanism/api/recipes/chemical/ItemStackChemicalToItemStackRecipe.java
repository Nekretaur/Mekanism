package mekanism.api.recipes.chemical;

import java.util.List;
import java.util.function.BiPredicate;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleItemChemicalRecipeInput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for defining item chemical to item recipes.
 * <br>
 * Input: ItemStack
 * <br>
 * Input: Chemical
 * <br>
 * Output: ItemStack
 *
 * @param <INGREDIENT> Input Ingredient type
 */
@NothingNullByDefault
public abstract class ItemStackChemicalToItemStackRecipe<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
      INGREDIENT extends ChemicalStackIngredient<CHEMICAL, STACK, ?>> extends MekanismRecipe<SingleItemChemicalRecipeInput<CHEMICAL, STACK>>
      implements BiPredicate<@NotNull ItemStack, @NotNull STACK> {

    /**
     * Gets the input item ingredient.
     */
    public abstract ItemStackIngredient getItemInput();

    /**
     * Gets the input chemical ingredient.
     */
    public abstract INGREDIENT getChemicalInput();

    /**
     * Gets a new output based on the given inputs.
     *
     * @param inputItem     Specific item input.
     * @param inputChemical Specific chemical input.
     *
     * @return New output.
     *
     * @apiNote While Mekanism does not currently make use of the inputs, it is important to support it and pass the proper value in case any addons define input based
     * outputs where things like NBT may be different.
     * @implNote The passed in inputs should <strong>NOT</strong> be modified.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public abstract ItemStack getOutput(ItemStack inputItem, STACK inputChemical);

    @NotNull
    @Override
    public abstract ItemStack getResultItem(@NotNull HolderLookup.Provider provider);

    @Override
    public abstract boolean test(ItemStack itemStack, STACK gasStack);

    @NotNull
    @Override
    public ItemStack assemble(SingleItemChemicalRecipeInput<CHEMICAL, STACK> input, HolderLookup.Provider provider) {
        if (!isIncomplete() && test(input.item(), input.chemical())) {
            return getOutput(input.item(), input.chemical());
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(SingleItemChemicalRecipeInput<CHEMICAL, STACK> input, Level level) {
        //Don't match incomplete recipes or ones that don't match
        return !isIncomplete() && test(input.item(), input.chemical());
    }

    /**
     * For JEI, gets the output representations to display.
     *
     * @return Representation of the output, <strong>MUST NOT</strong> be modified.
     */
    public abstract List<@NotNull ItemStack> getOutputDefinition();

    @Override
    public boolean isIncomplete() {
        return getItemInput().hasNoMatchingInstances() || getChemicalInput().hasNoMatchingInstances();
    }

}
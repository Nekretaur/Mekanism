package mekanism.common.network.to_client.container.property.chemical;

import mekanism.api.chemical.ChemicalStack;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.network.to_client.container.property.PropertyData;
import mekanism.common.network.to_client.container.property.PropertyType;
import org.jetbrains.annotations.NotNull;

public abstract class ChemicalStackPropertyData<STACK extends ChemicalStack<?>> extends PropertyData {

    @NotNull
    protected final STACK value;

    public ChemicalStackPropertyData(PropertyType propertyType, short property, @NotNull STACK value) {
        super(propertyType, property);
        this.value = value;
    }

    @Override
    public void handleWindowProperty(MekanismContainer container) {
        container.handleWindowProperty(getProperty(), value);
    }
}
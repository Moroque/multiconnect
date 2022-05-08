package net.earthcomputer.multiconnect.datafix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.SharedConstants;
import net.minecraft.util.Util;

public class MulticonnectDFU {
    private MulticonnectDFU() {}

    public static final DSL.TypeReference DIMENSION = () -> "dimension";
    public static final DSL.TypeReference REGISTRY_MANAGER = () -> "registry_manager";

    public static final DataFixer FIXER = Util.make(() -> {
        var builder = new DataFixerBuilder(SharedConstants.getGameVersion().getSaveVersion().getId());
        builder.addSchema(99, Schema99::new);
        builder.addSchema(2566, Schema2566::new);
        Schema schema16_2 = builder.addSchema(2578, Schema::new);
        builder.addFixer(new ExperimentalDynamicRegistriesFix(schema16_2, true, "1.16.2"));
        Schema schema_17 = builder.addSchema(2724, Schema::new);
        builder.addFixer(new ExperimentalDynamicRegistriesFix(schema_17, true, "1.17"));
        Schema schema_18 = builder.addSchema(2865, Schema::new);
        builder.addFixer(new ExperimentalDynamicRegistriesWithWorldHeightFix(schema_18, true, "1.18"));
        Schema schema_18_2 = builder.addSchema(2975, Schema::new);
        builder.addFixer(new ExperimentalDynamicRegistriesWithWorldHeightFix(schema_18_2, true, "1.18.2"));
        return builder.build(Util.getBootstrapExecutor());
    });
}

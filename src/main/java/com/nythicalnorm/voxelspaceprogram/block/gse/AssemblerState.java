package com.nythicalnorm.voxelspaceprogram.block.gse;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public enum AssemblerState {
    JUST_PLACED("just_placed"),
    ASSEMBLY_AREA_SEARCHING("assembly_area_searching"),
    ASSEMBLY_AREA_READY("assembly_area_ready"),
    SHIP_CONTROLLER_PLACED("ship_controller_placed"),
    SHIP_ASSEMBLY_STARTED("ship_assembly_started"),
    SHIP_ASSEMBLY_FINISHED("ship_assembly_finished"),
    SHIP_ROLLING_OUT("ship_rolling_out"),
    SHIP_ROLLING_BACK("ship_rolling_back");

    private final String name;

    private static final Map<String, AssemblerState> stringToEnum = new HashMap<>();
    static {
        for (AssemblerState status : values()) {
            stringToEnum.put(status.name, status);
        }
    }
    AssemblerState(String pName) {
        this.name = pName;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }

    public static AssemblerState getByValue(String value) {
        return stringToEnum.get(value);
    }
}

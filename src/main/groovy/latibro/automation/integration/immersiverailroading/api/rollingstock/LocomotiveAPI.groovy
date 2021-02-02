package latibro.automation.integration.immersiverailroading.api.rollingstock

import latibro.automation.api.core.lua.LuaMethod

interface LocomotiveAPI extends RollingStockAPI {

    @LuaMethod
    void setAirBrakeLevel(double level)

    @LuaMethod
    double getAirBrakeLevel()

    @LuaMethod
    void setThrottleLevel(double level)

    @LuaMethod
    double getThrottleLevel()

    @LuaMethod(
            name = "getCurrentSpeed"
    )
    double getCurrentSpeedInKMH()

}
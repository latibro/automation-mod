package latibro.automation.nativeimpl.context.location

import groovy.transform.CompileStatic
import latibro.automation.core.LinkType
import latibro.automation.nativeimpl.context.world.CoreWorldLinkContext
import net.minecraft.util.math.BlockPos

@CompileStatic
final class InstanceCoreLocationLinkContext extends CoreLocationLinkContext {

    private final BlockPos nativeLocation
    private final CoreWorldLinkContext world

    InstanceCoreLocationLinkContext(BlockPos location, CoreWorldLinkContext world) {
        nativeLocation = Objects.requireNonNull(location)
        this.world = Objects.requireNonNull(world)
    }

    InstanceCoreLocationLinkContext(int x, int y, int z, CoreWorldLinkContext world) {
        this(new BlockPos(Objects.requireNonNull(x), Objects.requireNonNull(y), Objects.requireNonNull(z)), world)
    }

    @Override
    BlockPos getNativeLocation() {
        return nativeLocation
    }

    @Override
    CoreWorldLinkContext getWorld() {
        return world
    }

    @Override
    LinkType getLinkType() {
        return LinkType.STATIC
    }

}

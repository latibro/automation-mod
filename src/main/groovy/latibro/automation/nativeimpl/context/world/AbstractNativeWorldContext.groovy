package latibro.automation.nativeimpl.context.world


import latibro.automation.nativeimpl.context.entity.collection.NativeEntityCollectionContext
import latibro.automation.nativeimpl.context.entity.collection.NativeWorldLoadedEntityCollectionContext
import latibro.automation.nativeimpl.context.location.NativeLocationContext
import latibro.automation.nativeimpl.context.location.NativeStaticLocationContext
import net.minecraft.world.World

abstract class AbstractNativeWorldContext implements NativeWorldContext {

    abstract World getNativeWorld()

    @Override
    NativeEntityCollectionContext getLoadedEntityCollectionContext() {
        return new NativeWorldLoadedEntityCollectionContext(this)
    }

    @Override
    NativeLocationContext getLocationContextByCoordinate(int x, int y, int z) {
        return new NativeStaticLocationContext(x, y, z, this)
    }

}
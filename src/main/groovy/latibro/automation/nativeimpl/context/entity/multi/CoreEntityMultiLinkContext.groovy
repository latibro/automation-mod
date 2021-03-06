package latibro.automation.nativeimpl.context.entity.multi

import latibro.automation.core.context.CoreContext
import latibro.automation.core.context.entity.multi.AbstractEntityMultiLinkContext
import latibro.automation.nativeimpl.context.entity.CoreEntityLinkContext
import latibro.automation.nativeimpl.context.entity.InstanceCoreEntityLinkContext
import latibro.automation.nativeimpl.context.server.CoreServerLinkContext
import latibro.automation.nativeimpl.context.server.DefaultCoreServerLinkContext
import net.minecraft.entity.Entity

abstract class CoreEntityMultiLinkContext extends AbstractEntityMultiLinkContext implements CoreContext {

    abstract List<Entity> getNativeEntityList()

    @Override
    boolean isLinked() {
        return nativeEntityList
    }

    @Override
    int count() {
        return nativeEntityList.size()
    }

    @Override
    List<CoreEntityLinkContext> asList() {
        def result = nativeEntityList.collect {
            wrapNativeEntity(it)
        }
        return result
    }

    @Override
    CoreServerLinkContext getServer() {
        return new DefaultCoreServerLinkContext()
    }

    protected static CoreEntityLinkContext wrapNativeEntity(Entity nativeEntity) {
        return new InstanceCoreEntityLinkContext(nativeEntity)
    }

}

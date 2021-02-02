package latibro.automation.nativeimpl.context.server


import latibro.automation.core.context.server.ServerContext
import latibro.automation.nativeimpl.context.NativeContext
import latibro.automation.nativeimpl.context.entity.collection.NativeEntityCollectionContext
import net.minecraft.server.MinecraftServer

interface NativeServerContext extends ServerContext, NativeContext {

    MinecraftServer getNativeServer()

    @Override
    NativeEntityCollectionContext getLoadedEntityCollectionContext()

}
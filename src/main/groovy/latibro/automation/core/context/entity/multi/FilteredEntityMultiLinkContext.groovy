package latibro.automation.core.context.entity.multi

import latibro.automation.core.LinkType
import latibro.automation.core.context.entity.EntityLinkContext
import latibro.automation.core.context.server.ServerLinkContext

class FilteredEntityMultiLinkContext extends AbstractEntityMultiLinkContext {

    private final EntityMultiLinkContext parentContext
    private final Closure filter

    FilteredEntityMultiLinkContext(EntityMultiLinkContext parentContext, Closure filter) {
        this.parentContext = Objects.requireNonNull(parentContext)
        this.filter = filter
    }

    @Override
    boolean isLinked() {
        return parentContext.isLinked()
    }

    @Override
    LinkType getLinkType() {
        return LinkType.DYNAMIC
    }

    @Override
    List<EntityLinkContext> asList() {
        return parentContext.asList().findAll(filter)
    }

    @Override
    List<EntityLinkContext> asList(int maxCount) {
        int toIndex = Math.min(maxCount, count())
        return asList().subList(0, toIndex)
    }

    @Override
    ServerLinkContext getServer() {
        return parentContext.getServer()
    }

}

package latibro.automation.core.lua

import javax.annotation.Nullable

final class LuaObjects {

    private LuaObjects() {
    }

    static boolean isSafeLuaObject(@Nullable Object object) {
        if (object == null) {
            // Null - No need to do anything
            return true
        } else if (object instanceof Boolean) {
            // Boolean is safe
            return true
        } else if (object instanceof String) {
            // String is safe
            return true
        } else if (object instanceof Double) {
            // Only Double is safe
            //TODO validate that OC also always uses Double as number
            return true
        } else if (object instanceof Map) {
            // LuaTable object
            return LuaTables.isSafeLuaTable(object)
        } else if (object instanceof LuaObjectProxy) {
            // Proxy object
            return true
        }
        return false
    }

    @Nullable
    static Object toLuaObject(@Nullable Object object) {
        if (isSafeLuaObject(object)) {
            // Safe - No need to do anything
            return object
        } else if (object instanceof Number) {
            // Number is safe in Double
            return ((Number) object).doubleValue()
        } else if (LuaTables.isLuaTableCandidate(object)) {
            // Turn to lua table
            return LuaTables.toLuaTable(object)
        }
        // Anything else - Wrapping it
        return new LuaObjectProxy(object)
    }

    @Nullable
    static Object fromLuaObject(@Nullable Object object) {
        if (object == null) {
            return null
        } else if (object instanceof Number) {
            return object
        } else if (object instanceof String) {
            return object
        } else if (object instanceof Boolean) {
            return object
        } else if (object instanceof LuaObjectProxy) {
            // Proxy - Unwrapping
            return ((LuaObjectProxy) object).getSource()
        } else if (object instanceof Map) {
            // Map - Lua tables
            return LuaTables.fromLuaTable(object)
        }
        throw new ClassCastException(object.toString())
    }

}

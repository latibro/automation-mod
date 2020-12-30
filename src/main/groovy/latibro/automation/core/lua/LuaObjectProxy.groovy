package latibro.automation.core.lua

import javax.annotation.Nonnull
import javax.annotation.Nullable
import java.lang.reflect.Method
import java.util.stream.Stream

class LuaObjectProxy {

    private final Object source

    LuaObjectProxy(@Nonnull Object source) {
        this.source = Objects.requireNonNull(source)
    }

    @Nonnull
    Object getSource() {
        return source
    }

    protected boolean isLuaMethod(Method method) {
        if (source.getClass().isAnnotationPresent(LuaObject.class)) {
            LuaObject annotation = source.getClass().getAnnotation(LuaObject.class)
            if (annotation.allMethods()) {
                return true
            }
        }
        return method.isAnnotationPresent(LuaMethod.class)
    }

    private Method[] getMethodList() {
        Stream<Method> stream = Arrays.stream(source.getClass().getMethods())
        stream = stream.filter(this::isLuaMethod)
        return stream.toArray(Method[]::new)
    }

    private Method findMethod(String methodName, Object[] arguments) throws NoSuchMethodException {
        Method[] matchingMethods = Arrays.stream(getMethodList()).filter(m -> m.getName().equals(methodName)).toArray(Method[]::new)
        if (matchingMethods.length == 0) {
            throw new NoSuchMethodException("No method match")
        } else if (matchingMethods.length > 1) {
            throw new NoSuchMethodException("Multiple methods match")
        }
        return matchingMethods[0]
    }

    private MetaMethod findMetaMethod(String methodName, Object[] arguments) throws NoSuchMethodException {
        def method = source.metaClass.getMetaMethod(methodName, arguments)
        if (!method) {
            throw new NoSuchMethodException("No method match")
        } else {
            return method
        }
    }

    @Nonnull
    String[] getMethodNames() {
        return Arrays.stream(getMethodList()).map(Method::getName).distinct().toArray(String[]::new)
    }

    private Object _callMethod(Method method, Object[] arguments) throws Exception {
        return method.invoke(source, arguments)
    }

    @Nullable
    Object callMethod(@Nonnull String methodName, @Nullable Object[] arguments) throws Exception {
        Object[] nativeArguments = fromLuaArguments(arguments)

        Method method = findMethod(methodName, nativeArguments)

        def metaMethod = findMetaMethod(methodName, nativeArguments)
        Object nativeResult = metaMethod.doMethodInvoke(source, nativeArguments)

        //Object nativeResult = source.invokeMethod(method.getName(), nativeArguments);
        //Object nativeResult = _callMethod(method, nativeArguments);

        Object luaResult = toLuaResult(nativeResult)

        return luaResult
    }

    private Object[] fromLuaArguments(Object[] arguments) {
        if (arguments == null) {
            return null
        } else {
            return Arrays.stream(arguments).map(LuaObjects::fromLuaObject).toArray()
        }
    }

    private Object toLuaResult(Object result) {
        return LuaObjects.toLuaObject(result)
    }

}
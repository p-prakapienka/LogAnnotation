package by.prakapienka.annotationlog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogAnnotationProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(LogAnnotationProcessor.class);

    public static <T> T process(T object) {

        Class<?> objectClass = object.getClass();

        return (T)Enhancer.create(objectClass, new LogHandler<T>(object));
    }

    private static class LogHandler<T> implements MethodInterceptor {

        private T object;

        private LogHandler(T object) {
            this.object = object;
        }

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (method.isAnnotationPresent(Log.class)) {
                Log annotation = method.getAnnotation(Log.class);
                Log.Level level = annotation.level();
                Object result = proxy.invoke(object, args);
                processLogging(method, level, args, result);
                return result;
            }
            return proxy.invoke(object, args);
        }

        private void processLogging(Method method, Log.Level level, Object[] args, Object result) {
            StringBuilder builder = new StringBuilder();
            builder.append("Method: ");
            builder.append(method.getName());
            builder.append(". Parameter values: ");
            for (int i = 0; i < args.length; i++) {
                builder.append(args[i]);
                builder.append(" ");
            }
            builder.append("Result: ");
            builder.append(result.toString());
            switch (level) {
                case INFO:
                    LOG.info(builder.toString());
                    break;
                case DEBUG:
                    LOG.debug(builder.toString());
                    break;
                case ERROR:
                    LOG.error(builder.toString());
                    break;
                case TRACE:
                    LOG.trace(builder.toString());
                    break;
                case WARN:
                    LOG.trace(builder.toString());
                    break;
                default:
                    LOG.info(builder.toString());
            }
        }
    }
}

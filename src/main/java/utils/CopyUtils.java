package utils;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.ReflectUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by CheN on 2017/10/30.
 * <p>
 * Copy工具类
 */
public class CopyUtils {
    private static volatile Map<String, BeanCopier> map = new HashMap<>();

    private static BeanCopier getBeanCopier(Class<?> srcClazz, Class<?> destClazz) {
        String key = generateKey(srcClazz, destClazz);
        BeanCopier beanCopier = map.get(key);
        if (beanCopier == null) {
            synchronized (map) {
                beanCopier = map.get(key);
                if (beanCopier == null) {
                    beanCopier = BeanCopier.create(srcClazz, destClazz, false);
                    map.put(key, beanCopier);
                }
            }
        }
        return beanCopier;
    }

    public static <T> T copy(Object from, Class<T> to) {
        Object rtn = ReflectUtils.newInstance(to);
        BeanCopier beanCopier = getBeanCopier(from.getClass(), to);
        beanCopier.copy(from, rtn, null);
        return (T) rtn;
    }

    /**
     * 生成key
     *
     * @param srcClazz
     * @param destClazz
     * @return
     */
    private static String generateKey(Class<?> srcClazz, Class<?> destClazz) {
        return String.format("%s-%s", srcClazz.getName(), destClazz.getName());
    }
}

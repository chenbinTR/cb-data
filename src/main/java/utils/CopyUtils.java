package utils;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.ReflectUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by CheN on 2017/10/30.
 *
 * Copy工具类
 */
public class CopyUtils {
    private static volatile Map<String, BeanCopier> map = new HashMap<>();
    private static final Set<Integer> set  = new HashSet<>(1);

    private static BeanCopier getBeanCopier(Class<?> srcClazz, Class<?> destClazz){
        String key = generateKey(srcClazz, destClazz);
        BeanCopier beanCopier = map.get(key);
        if(beanCopier == null){
            synchronized (map){
                beanCopier = map.get(key);
                if(beanCopier == null){
                    beanCopier = BeanCopier.create(srcClazz, destClazz, false);
                    map.put(key, beanCopier);
                }
            }
        }
        return beanCopier;
    }
    public static <T> T copy(Object from, Class<T> to) {
        Object rtn = ReflectUtils.newInstance(to);
        String key = generateKey(from.getClass(), to);
        // 不考虑多线程，不存在线程安全（不需要单例）
        BeanCopier beanCopier = getBeanCopier(from.getClass(), to);
        set.add(beanCopier.hashCode());
        beanCopier.copy(from, rtn, null);
        return (T) rtn;
    }

    /**
     * 生成key
     * @param srcClazz
     * @param destClazz
     * @return
     */
    private static String generateKey(Class<?> srcClazz, Class<?> destClazz) {
        return String.format("%s-%s", srcClazz.getName(), destClazz.getName());
    }
}

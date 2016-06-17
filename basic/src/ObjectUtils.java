

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * object对象工具类
 * @author fuhao
 *  2014年6月10日下午11:17:41
 */
public class ObjectUtils {
	
	/**
     * 验证是否为空
     * @author fuhao
     * 2014年6月10日上午9:12:16
     * @param object
     * @return
     */
	public static boolean isNull(Object object) {
		return Objects.equal(object, null) ? true : false;
	}
	
	/**
	 * 验证是否为空
	 * @author fuhao
	 * 2014年6月10日上午9:12:16
	 * @param object
	 * @return
	 */
	public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 验证是否为空
     * @author fuhao
     * 2014年6月10日上午9:12:16
     * @param object
     * @return
     */
    public static boolean arrayIsHasNull(Object... object) {
        for (Object o : object) {
            if (Objects.equal(o,null)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 验证是否为空
     * @author fuhao
     * 2014年6月10日上午9:12:16
     * @param object
     * @return
     */
    public static boolean arrayNotHasNull(Object... object) {
        return !arrayIsHasNull(object);
    }

    /**
     * Created by fuhao on 5/4/15.
     * 集合是否为空
     * @param objectList
     * @return
     */
    public static boolean listIsEmpty(List objectList) {
        if (ObjectUtils.isNull(objectList)) return true;
        if (objectList.isEmpty()) return true;
        return false;
    }

    /**
     * Created by fuhao on 5/4/15.
     * 集合是否为空
     * @param objectList
     * @return
     */
    public static boolean listNotEmpty(List objectList) {
        return !listIsEmpty(objectList);
    }

    /**
     * Created by fuhao on 5/4/15.
     * 集合是否为空
     * @param objectList
     * @return
     */
    public static boolean listIsEmpty(Set objectList) {
        if (ObjectUtils.isNull(objectList)) return true;
        if (objectList.isEmpty()) return true;
        return false;
    }

    public static void main(String[] args) {
        final Map<Integer,String> maps = Maps.newHashMapWithExpectedSize(1);
        maps.put(1, "2");
        maps.put(2, "2");
        maps.put(3, "2");
        System.out.println(maps.size());
    }

}

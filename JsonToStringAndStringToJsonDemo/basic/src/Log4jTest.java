import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Log4jTest {

    private static final Logger logger = Logger.getLogger(Log4jTest.class);
    
    public static void main(String[] args) {
    	try {
    		List<Integer> list = new ArrayList<Integer>();
    		list.add(1);
    		list.add(2);
    		String s = JsonUtil.list2json(list);
    		System.out.println(s);
		} catch (Exception e) {
			logger.error("This is debug message.",e);
		}
    }
}


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;

public class StringUtils {
    public static int DEFAULT_BUFFER_SIZE = 1024;
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

    /**
     * 基本功能：过滤所有以"<"开头以">"结尾的标签
     * <p/>
     *
     * @param str
     * @return String
     */
    public static String filterHtml(String str) {
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String conversionSpecialCharacters(String string) {
        return string.replaceAll("\\\\", "/");
    }

    public static String getString(String content) {
        if (content == null) {
            return "";
        }
        return content;
    }

    public static Boolean stringToBoolean(String booleanString) {
        if (StringUtils.isNotEmpty(booleanString)
                && booleanString.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static String listToString(List<String> params, String sepator) {
        if (params.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            for (String string : params) {
                buffer.append(string + sepator);
            }
            String result = buffer.toString();
            return result.substring(0, result.length() - 1);
        } else {
            return "";
        }
    }

    public static String getString(Object string, String replaceWord) {
        if (string == null) {
            return replaceWord;
        } else {
            return String.valueOf(string);
        }
    }

    public static String getString(Object object) {
        if (object == null)
            return "";
        if (object instanceof Integer) {
            return String.valueOf(object);
        } else if (object instanceof Float) {
            return String.valueOf(object);
        } else if (object instanceof Double) {
            return String.valueOf(object);
        } else if (object instanceof Float) {
            return String.valueOf(object);
        } else if (object instanceof Date) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object);
        } else if (object instanceof String) {
            return String.valueOf(object);
        } else {
            return "";
        }
    }

    /**
     * @param string
     * @return 判断一个字符串是否为NULL或者是是否为"" 用法： StringUtils.isEmpty(NULL) 结果:true
     * StringUtils.isEmpty("") 结果:true StringUtils.isEmpty(" ") 结果:false
     * StringUtils.isEmpty("aa") 结果:false
     * 当然，与isEmpty的相反函数为StringUtils.isNotEmpty();相当于!isEmpty()
     */
    public static boolean isNull(String string) {
        return StringUtils.isEmpty(string);
    }

    /**
     * 根据0/1返回true/false
     *
     * @param filter
     * @return
     */
    public static boolean returnFilter(int filter) {
        return filter == 0 ? false : true;
    }

    /**
     * 判断字符串是否都是数字组成
     *
     * @param numString
     * @return
     */
    public static boolean isNumber(String numString) {
        return StringUtils.isNumeric(numString);
    }

    /**
     * 判断字符串是否都是数字组成
     *
     * @param numString
     * @return
     */
    public static boolean isNotNumber(String numString) {
        return !StringUtils.isNumeric(numString);
    }

    /**
     * @param str
     * @return 去掉字符串两边的空格，如果输入的是""或者是NULL则返回NULL 用法 ： StringUtils.trim(" abc ")
     * 结果："abc" StringUtils.trimToNull("") 结果：NULL
     * StringButils.trimToNull(NULL) 结果：NULL
     */
    public static String trimToNull(String str) {
        return StringUtils.trimToNull(str);
    }

    /**
     * 统计单个字符在字符串中出现的次数 xiaoer
     *
     * @param string 字符串
     * @param c      单个字符串
     * @return 返回次数
     */
    public static int counter(String string, char c) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    /**
     * xiaoer 统计字符串中的中文个数，
     *
     * @param s 字符串
     * @return 返回中文出现的次数
     *
     * @throws UnsupportedEncodingException
     */
    public static int getChineseCount(String s)
            throws UnsupportedEncodingException {
        char c;
        int chineseCount = 0;
        if (!"".equals("")) {
            try {
                s = new String(s.getBytes(), "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (isChineseChar(c)) {
                chineseCount++;
            }
        }
        return chineseCount;
    }

    /**
     * xiaoer 统计字符串中的空白数，字母，数字
     *
     * @param s
     * @return
     */
    public static String StringCountBlankNumCharacter(String s) {
        char ch;
        int character = 0, blank = 0, number = 0, other = 0;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') {
                character++;
            } else if (ch == ' ') {
                blank++;
            } else if (ch >= '0' && ch <= '9') {
                number++;
            }
        }
        return "字符串共有:" + character + "个字母," + blank + "个空格," + number
                + "个数字,其他字符占有:" + other;
    }

    public static boolean isChineseChar(char c)
            throws UnsupportedEncodingException {
        return String.valueOf(c).getBytes("GBK").length > 1;
    }

    /**
     * xiaoer 从文件中读取文件内容，
     *
     * @param res
     * @param filePath
     * @return
     */
    public static boolean stringToFile(String res, String filePath) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists()) {
                distFile.getParentFile().mkdirs();
            }
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(new File(
                    filePath)));
            char buf[] = new char[1024];
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * xiaoer 截取带汉字的字符串 strings : 字符串 copynum:截取的长度
     */
    public static String subStringContainChinese(String strings, int copyNum) {
        String[] arr = strings.split("");
        strings = "";// 清空，用于存放已截取的字符串
        int cutNum = 0;
        int cc = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getBytes().length == 1) {
                cutNum = cutNum + 1;
                strings = strings + arr[i];
            } else if (arr[i].getBytes().length == 2) {
                cc = cc + 1;
                cutNum = cutNum + 2;
                strings = strings + arr[i];
            }
            if (cutNum >= copyNum) {
                break;
            }
        }
        if (cutNum > copyNum) {
            return strings.substring(0, copyNum - cc);
        } else {
            return strings;
        }
    }

    /**
     * 编码的转换
     *
     * @param string   转换的字符串
     * @param encoding 转换后的编码
     * @return 返回转换后的字符串
     *
     * @throws UnsupportedEncodingException
     */
    public static String transferEncoding(String string, String encoding)
            throws UnsupportedEncodingException {
        return new String(string.getBytes("ISO8859-1"), encoding);
    }

    public String StringtoSql(String str) {
        if (str == null) {
            return "";
        } else {
            try {
                str = str.trim().replace('\'', (char) 32);
            } catch (Exception e) {
                return "";
            }
        }
        return str;
    }

    /**
     * 整数的转换与0补齐
     *
     * @param str    转换的数字
     * @param length 转换的长度。不够0补齐.
     * @return
     */
    public static String formatNO(int str, int length) {
        float ver = Float.parseFloat(System
                .getProperty("java.specification.version"));
        String laststr = "";
        if (ver < 1.5) {
            try {
                NumberFormat formater = NumberFormat.getNumberInstance();
                formater.setMinimumIntegerDigits(length);
                laststr = formater.format(str).toString().replace(",", "");
            } catch (Exception e) {
                System.out.println("格式化字符串时的错误信息：" + e.getMessage());
            }
        } else {
            Integer[] arr = new Integer[1];
            arr[0] = new Integer(str);
            laststr = String.format("%0" + length + "d", arr);
        }
        return laststr;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return null == str || str.length() == 0 || "".equals(str)
                || "null".equals(str) || str.matches("\\s*");
    }

    /**
     * 判断字符串中是否含有空白字符。
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串中是否含有空白字符。
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 去掉字符串左右空格.
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * xiaoer 替换字符串
     *
     * @param line      字符串
     * @param oldString 替换字符串中的某字符
     * @param newString 替换后的新字符
     * @return
     */
    public static final String replace(String line, String oldString,
                                       String newString) {
        if (null == line && "".equals(line)) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int len = oldString.length();
            StringBuffer buffer = new StringBuffer(line2.length);
            buffer.append(line2, 0, i).append(newString2);
            i += len;
            int j = i;
            while (((i = line.indexOf(oldString, i))) > 0) {
                buffer.append(line2, j, i - j).append(newString2);
                i += len;
                j = i;
            }
            buffer.append(line2, j, line2.length - j);
            return buffer.toString();
        }
        return line;
    }

    /**
     * 密码加密 不可逆
     *
     * @param originString 要加密的字符串
     * @return
     */
    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] results = md.digest(originString.getBytes());
                String resultstring = byteArrayToHexString(results);
                return resultstring.toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 替换字符串全前缀面的字符
     *
     * @param string     startIntercept("12abc123","123") ==> abc123
     *                   startIntercept("21abc323","123") ==> abc123
     * @param charRemove
     * @return 如果字符串为空，直接返回
     */
    public static String startIntercept(String string, String charRemove) {
        int len = string.length();
        if (isEmpty(string)) {
            return string;
        }
        int start = 0;
        if (charRemove == null) {
            while ((start != len)
                    && Character.isWhitespace(string.charAt(start))) {
                start++;
            }
        } else if (charRemove.length() == 0) {
            return string;
        } else {
            while ((start != len)
                    && (charRemove.indexOf(string.charAt(start)) != -1)) {
                start++;
            }
        }
        return string.substring(start);
    }

    /**
     * 替换字符串后缀的字符
     *
     * @param str        endIntercept("12abc123","123") ==> 12abc
     *                   endIntercept("12abc323","123") ==> 12abc
     * @param stripChars
     * @return
     */
    public static String endIntercept(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((end != 0)
                    && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    /**
     * 替换多个字符串
     *
     * @param strs           替换前的字符串数组
     * @param interceptChars 要替换的字符串
     * @return
     */
    public static String[] interceptAll(String[] strs, String interceptChars) {
        int strsLen;
        if (strs == null || (strsLen = strs.length) == 0) {
            return strs;
        }
        String[] newArr = new String[strsLen];
        for (int i = 0; i < strsLen; i++) {
            newArr[i] = intercept(strs[i], interceptChars);
        }
        return newArr;
    }

    /**
     * 截取前后包含的字符串
     *
     * @param str        原始字符串
     * @param stripChars 要去掉的前后字符串。与顺序无关。 如：intercept("ac123ab","abc") ==>123
     *                   intercept("acb123acb","abc") ==>123
     *                   intercept("ac123abx","abc") ==>123abx
     * @return
     */
    public static String intercept(String str, String stripChars) {
        if (isEmpty(str)) {
            return str;
        }
        str = startIntercept(str, stripChars);
        return endIntercept(str, stripChars);
    }

    public static int indexOf(String str, char strChar) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(strChar);
    }

    /**
     * <pre>
     * ExmayStringUtils.indexOf(null, *)          = -1
     * ExmayStringUtils.indexOf(*, null)          = -1
     * ExmayStringUtils.indexOf("", "")           = 0
     * ExmayStringUtils.indexOf("aabaabaa", "a")  = 0
     * ExmayStringUtils.indexOf("aabaabaa", "b")  = 2
     * ExmayStringUtils.indexOf("aabaabaa", "ab") = 1
     * ExmayStringUtils.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str
     * @param searchStr
     * @return
     */
    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.indexOf(searchStr);
    }

    public static int indexOf(String str, char searchChar, int startPos) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(searchChar, startPos);
    }

    public static boolean containsAny(String str, char[] searchChars) {
        if (str == null || str.length() == 0 || searchChars == null
                || searchChars.length == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            for (int j = 0; j < searchChars.length; j++) {
                if (searchChars[j] == ch) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int indexOfDifference(String[] strs) {
        if (strs == null || strs.length <= 1) {
            return -1;
        }
        boolean anyStringNull = false;
        boolean allStringsNull = true;
        int arrayLen = strs.length;
        int IntegerestStrLen = Integer.MAX_VALUE;
        int longestStrLen = 0;

        // find the min and max string lengths; this avoids checking to make
        // sure we are not exceeding the length of the string each time through
        // the bottom loop.
        for (int i = 0; i < arrayLen; i++) {
            if (strs[i] == null) {
                anyStringNull = true;
                IntegerestStrLen = 0;
            } else {
                allStringsNull = false;
                IntegerestStrLen = Math.min(strs[i].length(), IntegerestStrLen);
                longestStrLen = Math.max(strs[i].length(), longestStrLen);
            }
        }

        // handle lists containing all nulls or all empty strings
        if (allStringsNull || (longestStrLen == 0 && !anyStringNull)) {
            return -1;
        }

        // handle lists containing some nulls or some empty strings
        if (IntegerestStrLen == 0) {
            return 0;
        }

        // find the position with the first difference across all strings
        int firstDiff = -1;
        for (int stringPos = 0; stringPos < IntegerestStrLen; stringPos++) {
            char comparisonChar = strs[0].charAt(stringPos);
            for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
                if (strs[arrayPos].charAt(stringPos) != comparisonChar) {
                    firstDiff = stringPos;
                    break;
                }
            }
            if (firstDiff != -1) {
                break;
            }
        }

        if (firstDiff == -1 && IntegerestStrLen != longestStrLen) {
            // we compared all of the characters up to the length of the
            // Integerest string and didn't find a match, but the string lengths
            // vary, so return the length of the Integerest string.
            return IntegerestStrLen;
        }
        return firstDiff;
    }

    /**
     * 返回一个数组中所有字符串中，前缀相同的前缀名
     *
     * @param strs System.out.println(getCommonPrefix(new String[]{"a","abc"}))
     *             ==>a
     * @return
     */
    public static String getCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int smallestIndexOfDiff = indexOfDifference(strs);
        if (smallestIndexOfDiff == -1) {
            // all strings were identical
            if (strs[0] == null) {
                return "";
            }
            return strs[0];
        } else if (smallestIndexOfDiff == 0) {
            // there were no common initial characters
            return "";
        } else {
            // we found a common initial character sequence
            return strs[0].substring(0, smallestIndexOfDiff);
        }
    }

    public static String replace(String text, String searchString,
                                 String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null
                || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuffer buf = new StringBuffer(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * 判断一个字符串是否存在数组中
     *
     * @param s
     * @return
     */
    public static boolean isHave(String s) {
        String[] strs = {"gbk", "gb2312", "utf-8", "big5"};
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 过来script元素
     *
     * @param str
     * @return
     */
    public static String filterHTML(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll(" ", "&nbsp;");
        str = str.replaceAll("\n", "<br>");
        str = str.replaceAll("'|\"|“", "");
        return str;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 根据不同的日期格式返回当然的日期 如果: pattern等于yyyy那么返回年份 pattern等于MM那么返回月份
     * pattern等于dd那么返回天
     *
     * @param pattern
     * @return
     */
    public static String getYMD(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 时间按照日期的格式转成不同的日期字符格式
     *
     * @return
     */
    public static String DateToString(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 时间按照yyyy-MM-dd HH:mm:ss格式转成字符形式
     *
     * @return
     */
    public static String DateToString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String dateToString(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date stringTODate(String pattern, String date) {
        try {
            if (ObjectUtils.isNotNull(date)) {
                return new SimpleDateFormat(pattern).parse(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串数组转换成字符串
     *
     * @param strings
     * @return
     */
    public static String arrToString(String[] strings, String separtor) {
        StringBuffer buffer = new StringBuffer();
        if (strings != null) {
            for (String string : strings) {
                buffer.append(string + separtor);
            }
            String result = buffer.toString();
            return result.substring(0, result.length() - 1);
        } else {
            return "";
        }
    }

    @SuppressWarnings("unchecked")
    public static String listToString(List strings) {
        StringBuffer buffer = new StringBuffer();
        if (strings != null) {
            for (int i = 0; i < strings.size(); i++) {
                buffer.append(strings.get(i) + ",");
            }
            return buffer.toString().substring(0,
                    buffer.toString().length() - 1);
        }
        return buffer.append(" ").toString();
    }

    public static final byte[] BOM = {-17, -69, -65};

    public static final char[] HexDigits = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final Pattern PTitle = Pattern.compile(
            "<title>(.+?)</title>", 34);

    public static Pattern patternHtmlTag = Pattern.compile("<[^<>]+>", 32);

    public static final Pattern PLetterOrDigit = Pattern.compile("^\\w*$", 34);

    public static final Pattern PLetter = Pattern.compile("^[A-Za-z]*$", 34);

    public static final Pattern PDigit = Pattern.compile("^\\d*$", 34);

    private static Pattern chinesePattern = Pattern.compile("[^一-龥]+", 34);

    private static Pattern idPattern = Pattern.compile("[\\w\\_\\.\\,]*", 34);

    public static byte[] md5(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md = md5.digest(src.getBytes());
            return md;
        } catch (Exception e) {
        }
        return null;
    }

    public static byte[] md5(byte[] src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md = md5.digest(src);
            return md;
        } catch (Exception e) {
        }
        return null;
    }

    public static String byteToBin(byte[] bs) {
        char[] cs = new char[bs.length * 9];
        for (int i = 0; i < bs.length; ++i) {
            byte b = bs[i];
            int j = i * 9;
            cs[j] = (((b >>> 7 & 0x1) == 1) ? 49 : '0');
            cs[(j + 1)] = (((b >>> 6 & 0x1) == 1) ? 49 : '0');
            cs[(j + 2)] = (((b >>> 5 & 0x1) == 1) ? 49 : '0');
            cs[(j + 3)] = (((b >>> 4 & 0x1) == 1) ? 49 : '0');
            cs[(j + 4)] = (((b >>> 3 & 0x1) == 1) ? 49 : '0');
            cs[(j + 5)] = (((b >>> 2 & 0x1) == 1) ? 49 : '0');
            cs[(j + 6)] = (((b >>> 1 & 0x1) == 1) ? 49 : '0');
            cs[(j + 7)] = (((b & 0x1) == 1) ? 49 : '0');
            cs[(j + 8)] = ',';
        }
        return new String(cs);
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i]));
            resultSb.append(" ");
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HexDigits[d1] + HexDigits[d2] + "";
    }

    public static String javaEncode(String txt) {
        if ((txt == null) || (txt.length() == 0)) {
            return txt;
        }
        txt = replaceEx(txt, "\\", "\\\\");
        txt = replaceEx(txt, "\r\n", "\n");
        txt = replaceEx(txt, "\n", "\\n");
        txt = replaceEx(txt, "\"", "\\\"");
        txt = replaceEx(txt, "'", "\\'");
        return txt;
    }

    public static String javaDecode(String txt) {
        if ((txt == null) || (txt.length() == 0)) {
            return txt;
        }
        txt = replaceEx(txt, "\\\\", "\\");
        txt = replaceEx(txt, "\\n", "\n");
        txt = replaceEx(txt, "\\r", "\r");
        txt = replaceEx(txt, "\\\"", "\"");
        txt = replaceEx(txt, "\\'", "'");
        return txt;
    }

    public static String[] splitEx(String str, String spilter) {
        if (str == null) {
            return null;
        }
        if ((spilter == null) || (spilter.equals(""))
                || (str.length() < spilter.length())) {
            String[] t = {str};
            return t;
        }
        ArrayList al = new ArrayList();
        char[] cs = str.toCharArray();
        char[] ss = spilter.toCharArray();
        int length = spilter.length();
        int lastIndex = 0;
        for (int i = 0; i <= str.length() - length; ) {
            boolean notSuit = false;
            for (int j = 0; j < length; ++j) {
                if (cs[(i + j)] != ss[j]) {
                    notSuit = true;
                    break;
                }
            }
            if (!(notSuit)) {
                al.add(str.substring(lastIndex, i));
                i += length;
                lastIndex = i;
            } else {
                ++i;
            }
        }
        if (lastIndex <= str.length()) {
            al.add(str.substring(lastIndex, str.length()));
        }
        String[] t = new String[al.size()];
        for (int i = 0; i < al.size(); ++i) {
            t[i] = ((String) al.get(i));
        }
        return t;
    }

    public static String replaceEx(String str, String subStr, String reStr) {
        if (str == null) {
            return null;
        }
        if ((subStr == null) || (subStr.equals(""))
                || (subStr.length() > str.length()) || (reStr == null)) {
            return str;
        }
        StringBuffer sb = new StringBuffer();
        String tmp = str;
        int index = -1;
        while (true) {
            index = tmp.indexOf(subStr);
            if (index < 0) {
                break;
            }
            sb.append(tmp.substring(0, index));
            sb.append(reStr);
            tmp = tmp.substring(index + subStr.length());
        }

        sb.append(tmp);
        return sb.toString();
    }

    public static String replaceAllIgnoreCase(String source, String oldstring,
                                              String newstring) {
        Pattern p = Pattern.compile(oldstring, 34);
        Matcher m = p.matcher(source);
        return m.replaceAll(newstring);
    }

    // public static String htmlEncode(String txt)
    // {
    // return StringEscapeUtils.escapeHtml(txt);
    // }

    public static String htmlDecode(String txt) {
        txt = replaceEx(txt, "&#8226;", "·");
        return StringEscapeUtils.unescapeHtml(txt);
    }

    public static String quotEncode(String txt) {
        if ((txt == null) || (txt.length() == 0)) {
            return txt;
        }
        txt = replaceEx(txt, "&", "&amp;");
        txt = replaceEx(txt, "\"", "&quot;");
        return txt;
    }

    public static String quotDecode(String txt) {
        if ((txt == null) || (txt.length() == 0)) {
            return txt;
        }
        txt = replaceEx(txt, "&quot;", "\"");
        txt = replaceEx(txt, "&amp;", "&");
        return txt;
    }

    public static String escape(String src) {
        StringBuffer sb = new StringBuffer();
        sb.ensureCapacity(src.length() * 6);
        for (int i = 0; i < src.length(); ++i) {
            char j = src.charAt(i);
            if ((Character.isDigit(j)) || (Character.isLowerCase(j))
                    || (Character.isUpperCase(j))) {
                sb.append(j);
            } else if (j < 256) {
                sb.append("%");
                if (j < '\16') {
                    sb.append("0");
                }
                sb.append(Integer.toString(j, 16));
            } else {
                sb.append("%u");
                sb.append(Integer.toString(j, 16));
            }
        }
        return sb.toString();
    }

    // public static String unescape(String src)
    // {
    // StringBuffer sb = new StringBuffer();
    // sb.ensureCapacity(src.length());
    // int lastPos = 0; int pos = 0;
    //
    // while (lastPos < src.length()) {
    // pos = src.indexOf("%", lastPos);
    // if (pos == lastPos)
    // {
    // char ch;
    // if (src.charAt(pos + 1) == 'u') {
    // ch = (char)Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
    // sb.append(ch);
    // lastPos = pos + 6;
    // } else {
    // ch = (char)Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
    // sb.append(ch);
    // lastPos = pos + 3;
    // }
    // }
    // else if (pos == -1) {
    // sb.append(src.substring(lastPos));
    // lastPos = src.length();
    // } else {
    // sb.append(src.substring(lastPos, pos));
    // lastPos = pos;
    // }
    // }
    //
    // return sb.toString();
    // }

    public static String leftPad(String srcString, char c, int length) {
        if (srcString == null) {
            srcString = "";
        }
        int tLen = srcString.length();

        if (tLen >= length)
            return srcString;
        int iMax = length - tLen;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iMax; ++i) {
            sb.append(c);
        }
        sb.append(srcString);
        return sb.toString();
    }

    public static String subString(String src, int length) {
        if (src == null) {
            return null;
        }
        int i = src.length();
        if (i > length) {
            return src.substring(0, length);
        }
        return src;
    }

    public static String subStringEx(String src, int length) {
        length *= 2;
        if (src == null) {
            return null;
        }
        int k = lengthEx(src);
        if (k > length) {
            int m = 0;
            boolean unixFlag = false;
            String osname = System.getProperty("os.name").toLowerCase();
            if ((osname.indexOf("sunos") > 0)
                    || (osname.indexOf("solaris") > 0)
                    || (osname.indexOf("aix") > 0))
                unixFlag = true;
            try {
                byte[] b = src.getBytes("Unicode");
                for (int i = 2; i < b.length; i += 2) {
                    byte flag = b[(i + 1)];
                    if (unixFlag) {
                        flag = b[i];
                    }
                    if (flag == 0)
                        ++m;
                    else {
                        m += 2;
                    }
                    if (m > length)
                        return src.substring(0, (i - 2) / 2);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException("执行方法getBytes(\"Unicode\")时出错！");
            }
        }
        return src;
    }

    public static int lengthEx(String src) {
        int length = 0;
        boolean unixFlag = false;
        String osname = System.getProperty("os.name").toLowerCase();
        if ((osname.indexOf("sunos") > 0) || (osname.indexOf("solaris") > 0)
                || (osname.indexOf("aix") > 0))
            unixFlag = true;
        try {
            byte[] b = src.getBytes("Unicode");
            for (int i = 2; i < b.length; i += 2) {
                byte flag = b[(i + 1)];
                if (unixFlag) {
                    flag = b[i];
                }
                if (flag == 0)
                    ++length;
                else
                    length += 2;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("执行方法getBytes(\"Unicode\")时出错！");
        }
        return length;
    }

    public static String rightPad(String srcString, char c, int length) {
        if (srcString == null) {
            srcString = "";
        }
        int tLen = srcString.length();

        if (tLen >= length)
            return srcString;
        int iMax = length - tLen;
        StringBuffer sb = new StringBuffer();
        sb.append(srcString);
        for (int i = 0; i < iMax; ++i) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static String rightTrim(String src) {
        if (src != null) {
            char[] chars = src.toCharArray();
            for (int i = chars.length - 1; i > 0; --i) {
                if (chars[i] == ' ')
                    continue;
                if (chars[i] != '\t') {
                    return new String(ArrayUtils.subarray(chars, 0, i + 1));
                }
            }
        }
        return src;
    }

    public static void printStringWithAnyCharset(String str) {
        Map map = Charset.availableCharsets();
        Object[] keys = map.keySet().toArray();
        for (int i = 0; i < keys.length; ++i) {
            for (int j = 0; j < keys.length; ++j) {
                System.out.print("\t");
                try {
                    System.out.println("From "
                            + keys[i]
                            + " To "
                            + keys[j]
                            + ":"
                            + new String(str.getBytes(keys[i].toString()),
                            keys[j].toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String toSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; ++i)
            if (c[i] == ' ') {
                c[i] = 12288;
            } else {
                if ((c[i] > '@') && (c[i] < '['))
                    continue;
                if ((c[i] > '`') && (c[i] < '{')) {
                    continue;
                }

                if (c[i] < '')
                    c[i] = (char) (c[i] + 65248);
            }
        return new String(c);
    }

    public static String toNSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; ++i) {
            if (c[i] == ' ') {
                c[i] = 12288;
            } else if (c[i] < '')
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    // 转半角的函数(DBC case)
    //
    // 任意字符串
    // 半角字符串
    //
    // 全角空格为12288，半角空格为32
    // 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
    //
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    // public static String getChineseFullSpell(String cnStr)
    // {
    // if ((cnStr == null) || ("".equals(cnStr.trim()))) {
    // return cnStr;
    // }
    // return ChineseSpelling.convert(cnStr);
    // }
    //
    // public static String getChineseFamilyNameSpell(String cnStr)
    // {
    // if ((cnStr == null) || ("".equals(cnStr.trim()))) {
    // return cnStr;
    // }
    // return ChineseSpelling.convertName(cnStr);
    // }
    //
    // public static String getChineseFirstAlpha(String cnStr) {
    // if ((cnStr == null) || ("".equals(cnStr.trim()))) {
    // return cnStr;
    // }
    // return ChineseSpelling.getFirstAlpha(cnStr);
    // }

    public static String getHtmlTitle(String html) {
        Matcher m = PTitle.matcher(html);
        if (m.find()) {
            return m.group(1).trim();
        }
        return null;
    }

    public static String clearHtmlTag(String html) {
        String text = patternHtmlTag.matcher(html).replaceAll("");
        if (isEmpty(text)) {
            return "";
        }
        text = htmlDecode(html);
        return text.replaceAll("[\\s　]{2,}", " ");
    }

    public static String capitalize(String str) {
        int strLen = 0;
        if (str != null)
            if ((strLen = str.length()) != 0)
                return str;

        return strLen + Character.toTitleCase(str.charAt(0)) + str.substring(1);
    }

    public static final String noNull(String string, String defaultString) {
        return ((isEmpty(string)) ? defaultString : string);
    }

    public static final String noNull(String string) {
        return noNull(string, "");
    }

    public static String join(Object[] arr) {
        return join(arr, ",");
    }

    public static String join(Object[][] arr) {
        return join(arr, "\n", ",");
    }

    public static String join(Object[] arr, String spliter) {
        if (arr == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            if (i != 0) {
                sb.append(spliter);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static String join(Object[][] arr, String spliter1, String spliter2) {
        if (arr == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            if (i != 0) {
                sb.append(spliter2);
            }
            sb.append(join(arr[i], spliter2));
        }
        return sb.toString();
    }

    public static String join(List list) {
        return join(list, ",");
    }

    public static String join(List list, String spliter) {
        if (list == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); ++i) {
            if (i != 0) {
                sb.append(spliter);
            }
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    public static int count(String str, String findStr) {
        int lastIndex = 0;
        int length = findStr.length();
        int count = 0;
        int start = 0;
        while ((start = str.indexOf(findStr, lastIndex)) >= 0) {
            lastIndex = start + length;
            ++count;
        }
        return count;
    }

    public static boolean isLetterOrDigit(String str) {
        return PLetterOrDigit.matcher(str).find();
    }

    public static boolean isLetter(String str) {
        return PLetter.matcher(str).find();
    }

    public static boolean isDigit(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return PDigit.matcher(str).find();
    }

    public static boolean containsChinese(String str) {
        return (!(chinesePattern.matcher(str).matches()));
    }

    public static boolean checkID(String str) {
        if (isEmpty(str)) {
            return true;
        }

        return (idPattern.matcher(str).matches());
    }

    // public static Mapx splitToMapx(String str, String entrySpliter, String
    // keySpliter)
    // {
    // Mapx map = new Mapx();
    // String[] arr = splitEx(str, entrySpliter);
    // for (int i = 0; i < arr.length; ++i) {
    // String[] arr2 = splitEx(arr[i], keySpliter);
    // String key = arr2[0];
    // if (isEmpty(key)) {
    // continue;
    // }
    // key = key.trim();
    // String value = null;
    // if (arr2.length > 1) {
    // value = arr2[1];
    // }
    // map.put(key, value);
    // }
    // return map;
    // }

    public static String getURLExtName(String url) {
        if (isEmpty(url)) {
            return null;
        }
        int index1 = url.indexOf(63);
        if (index1 == -1) {
            index1 = url.length();
        }
        int index2 = url.lastIndexOf(46, index1);
        if (index2 == -1) {
            return null;
        }
        int index3 = url.indexOf(47, 8);
        if (index3 == -1) {
            return null;
        }
        String ext = url.substring(index2 + 1, index1);
        if (ext.matches("[^\\/\\\\]*")) {
            return ext;
        }
        return null;
    }

    public static String getURLFileName(String url) {
        if (isEmpty(url)) {
            return null;
        }
        int index1 = url.indexOf(63);
        if (index1 == -1) {
            index1 = url.length();
        }
        int index2 = url.lastIndexOf(47, index1);
        if ((index2 == -1) || (index2 < 8)) {
            return null;
        }
        String ext = url.substring(index2 + 1, index1);
        return ext;
    }

    // public static byte[] GBKToUTF8(String chinese)
    // {
    // return GBKToUTF8(chinese, false);
    // }

    // public static byte[] GBKToUTF8(String chinese, boolean bomFlag)
    // {
    // return CharsetConvert.GBKToUTF8(chinese, bomFlag);
    // }
    //
    // public static byte[] UTF8ToGBK(String chinese)
    // {
    // return CharsetConvert.UTF8ToGBK(chinese);
    // }

    public static String clearForXML(String str) {
        char[] cs = str.toCharArray();
        char[] ncs = new char[cs.length];
        int j = 0;
        for (int i = 0; i < cs.length; ++i) {
            if (cs[i] > 65533)
                continue;
            if (cs[i] < ' ')
                if ((((cs[i] != '\t') ? 1 : 0) & ((cs[i] != '\n') ? 1 : 0) & ((cs[i] != '\r') ? 1
                        : 0)) != 0) {
                    continue;
                }
            ncs[(j++)] = cs[i];
        }
        ncs = ArrayUtils.subarray(ncs, 0, j);
        return new String(ncs);
    }

    /**
     * 首字母转换为大写 *
     */
    public static String toUpperCaseFirst(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /**
     * 首字母转换为小写 *
     */
    public static String toLowerCaseFirst(String text) {
        return text.substring(0, 1).toLowerCase() + text.substring(1);
    }

    /**
     * 字符串替换 *
     */
    public static String replaceAll(String content, String targetWord,
                                    String replaceWord) {
        int lastIndex;
        if (content == null)
            return null;
        StringBuffer text = new StringBuffer();
        int contentLength = content.length();
        int targetWordLenght = targetWord.length();
        int startIndex = 0;

        while ((lastIndex = content.indexOf(targetWord, startIndex)) >= 0) {
            text.append(content.substring(startIndex, lastIndex));
            text.append(replaceWord);
            startIndex = lastIndex + targetWordLenght;
        }
        if (startIndex < contentLength) {
            text.append(content.substring(startIndex));
        }
        return text.toString();
    }

    /**
     * 替换所有空格 *
     */
    public static String replaceAllSpace(String content) {
        return replaceAll(content, " ", "");
    }

    /**
     * 字符检测 *
     */
    public static boolean checkStringRule(String word) {
        boolean flag = true;

        if ((word == null) || (word.trim().equals(""))) {
            return true;
        }

        if ((word.indexOf("<") > -1) || (word.indexOf(">") > -1)
                || (word.indexOf(".") > -1) || (word.indexOf("\"") > -1)
                || (word.indexOf("'") > -1) || (word.indexOf(",") > -1)
                || (word.indexOf("$") > -1) || (word.indexOf("#") > -1)
                || (word.indexOf("%") > -1) || (word.indexOf("!") > -1)
                || (word.indexOf("~") > -1) || (word.indexOf("^") > -1)
                || (word.indexOf("&") > -1) || (word.indexOf("*") > -1)
                || (word.indexOf("(") > -1) || (word.indexOf(")") > -1)
                || (word.indexOf("=") > -1) || (word.indexOf("+") > -1)
                || (word.indexOf("|") > -1) || (word.indexOf("\\") > -1)
                || (word.indexOf(";") > -1) || (word.indexOf(":") > -1)
                || (word.indexOf("?") > -1) || (word.indexOf("　") > -1)) {
            flag = false;
        }

        return flag;
    }

    /**
     * 定义HTML escape 替换字符
     */
    private final static byte[] val = {0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
            0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F};

    /**
     * 随即生产随即数，可以用来生产tocken字符串等.
     *
     * @param length 生成长度
     * @return 随即数字符串.
     */
    public static String getRandomString(int length) {
        StringBuffer bu = new StringBuffer();
        String[] arr = {"2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c",
                "d", "e", "f", "g", "h", "i", "j", "k", "m", "n", "p", "q",
                "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "P",
                "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        Random random = new Random();
        while (bu.length() < length) {
            String temp = arr[random.nextInt(57)];
            if (bu.indexOf(temp) == -1) {
                bu.append(temp);
            }
        }
        return bu.toString();
    }

    /**
     * 获取某个区间的随机整数.
     *
     * @param sek 随机种子
     * @param min 最小整数
     * @param max 最大整数
     * @return 随机整数
     */
    public static int getRandomInt(int sek, int min, int max) {
        Random random = new Random();
        int temp = 0;
        do {
            temp = random.nextInt(sek);
        } while (temp < min || temp > max);
        return temp;
    }

    /**
     * 实现了js 版本的unescape方法.
     * <p/>
     * 用于前台JS对传入的参数做了escape，JAVA后台取到参数后需要unescape。
     *
     * @param s
     * @return
     */
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            int ch = s.charAt(i);
            if ('A' <= ch && ch <= 'Z') {
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') {
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') {
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_' || ch == '.' || ch == '!'
                    || ch == '~' || ch == '*' || ch == '\'' || ch == '('
                    || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != s.charAt(i + 1)) {
                    cint = (cint << 4) | val[s.charAt(i + 1)];
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    i += 2;
                } else {
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    cint = (cint << 4) | val[s.charAt(i + 3)];
                    cint = (cint << 4) | val[s.charAt(i + 4)];
                    cint = (cint << 4) | val[s.charAt(i + 5)];
                    i += 5;
                }
                sbuf.append((char) cint);
            } else {
                sbuf.append((char) ch);
            }
            i++;
        }
        return sbuf.toString();
    }

    public static List splitToList(String str, String spilter) {
        if (str == null) {
            return null;
        }

        ArrayList<Integer> al = new ArrayList<Integer>();
        char[] cs = str.toCharArray();
        char[] ss = spilter.toCharArray();
        int length = spilter.length();
        int lastIndex = 0;
        for (int i = 0; i <= str.length() - length; ) {
            boolean notSuit = false;
            for (int j = 0; j < length; ++j) {
                if (cs[(i + j)] != ss[j]) {
                    notSuit = true;
                    break;
                }
            }
            if (!(notSuit)) {
                al.add(Integer.valueOf(str.substring(lastIndex, i)));
                i += length;
                lastIndex = i;
            } else {
                ++i;
            }
        }
        if (lastIndex <= str.length()) {
            al.add(Integer.valueOf(str.substring(lastIndex, str.length())));
        }
        return al;
    }

    public static enum ENCODING {
        GBK, UTF_8, ISO_8859_1;
    }

    /**
     * @作用:null 或者 'null' 转换成 ""
     */
    public static final String nullToEmpty(Object str) {
        return (((null == str) || ("null".equals(str))) ? "" : str.toString());
    }

    /**
     * @作用:判断是否为数字
     */
    public static boolean isNumeric(String str) {
        Matcher isNum = Pattern.compile("(-|\\+)?[0-9]+(.[0-9]+\\+)?").matcher(
                str);
        return isNum.matches();
    }

    public static Integer parseInt(Object str) {
        return str == null ? 0 : Integer
                .valueOf((isNumeric(str.toString())) ? Integer.parseInt(str
                        .toString()) : 0);
    }

    /* 如果为空，或者不是数字就返回null而不是0 */
    public static Integer parseInteger(Object str) {
        if (str == null) {
            return null;
        } else {
            if (isNumeric(str.toString())) {
                return Integer.parseInt(str.toString());
            } else {
                return null;
            }
        }
    }

    public static Float parseFloat(Object str) {
        try {
            return Float.valueOf(Float.parseFloat(str.toString()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Float.valueOf(0.0F);
    }

    public static Double parseDouble(Object str) {
        try {
            return Double.valueOf(Double.parseDouble(str.toString()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Double.valueOf(0.0D);
    }

    public static Boolean parseBoolean(Object str) {
        String s = str.toString().toLowerCase();
        if (("y".equalsIgnoreCase(s)) || ("yes".equalsIgnoreCase(s))
                || ("true".equalsIgnoreCase(s)) || ("t".equalsIgnoreCase(s))
                || ("1".equalsIgnoreCase(s))) {
            return true;
        }
        return false;
    }

    public static double formatDoubleValue(int medianAfterTheDecimalPoint,
                                           String doubleStringValue) {
        Double doubleValue = Double.valueOf(doubleStringValue);
        return new BigDecimal(doubleValue.doubleValue()).setScale(
                medianAfterTheDecimalPoint, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    public static String formatAnyseValue(String formatExpression, Object value) {
        DecimalFormat df = new DecimalFormat(formatExpression);
        return df.format(value);
    }

    /**
     * 过滤html标签
     *
     * @param string
     * @return
     */
    public static String filterHtmlTags(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        boolean lastWasWhite = false;
        boolean modified = false;
        int l = string.length();
        for (int i = 0; i < l; i++) {
            int c = string.codePointAt(i);
            if (Character.isWhitespace(c)) {
                if (lastWasWhite) {
                    modified = true;
                    continue;
                }
                if (c != ' ')
                    modified = true;
                sb.append(' ');
                lastWasWhite = true;
            } else {
                sb.appendCodePoint(c);
                lastWasWhite = false;
            }
        }
        return modified ? sb.toString() : string;
    }

    /**
     * @作用: 字符编码转换
     */
    public static String fromEncodingToAnotherEncoding(String content,
                                                       ENCODING fromEncoding, ENCODING toEncoding) {
        String from = fromEncoding.toString().replace("_", "-");
        String to = toEncoding.toString().replace("_", "-");
        if (null != content)
            try {
                byte[] tmpbyte = content.getBytes(from);
                content = new String(tmpbyte, to);
            } catch (Exception e) {
                System.out.println("Error: from: " + from + " To: " + to + " :"
                        + e.getMessage());
            }

        return content;
    }

    /**
     * @作用:转换成html编码
     */
    public static String htmlEncode(String txt) {
        if (null != txt) {
            txt = txt.replace("&", "&amp;").replace("&amp;amp;", "&amp;")
                    .replace("&amp;quot;", "&quot;").replace("\"", "&quot;")
                    .replace("&amp;lt;", "&lt;").replace("<", "&lt;")
                    .replace("&amp;gt;", "&gt;").replace(">", "&gt;")
                    .replace("&amp;nbsp;", "&nbsp;");
        }
        return txt;
    }

    /**
     * @作用:去除html编码
     */
    public static String unHtmlEncode(String txt) {
        if (null != txt) {
            txt = txt.replace("&amp;", "&").replace("&quot;", "\"")
                    .replace("&lt;", "<").replace("&gt;", ">")
                    .replace("&nbsp;", " ");
        }
        return txt;
    }

    public static String getDate(Date beginTime, String pattern) {
        return new SimpleDateFormat(pattern).format(beginTime);
    }

    /**
     * @作用:转换字符串首字母,默认是转换成小写字母,如boolean... bs 设置成true 转大写
     */
    public static String firstChar2xCase(String str, boolean... bs) {
        if ((null == str) || ("".equalsIgnoreCase(str))) {
            return "";
        }
        return ((bs.length == 0) ? str.substring(0, 1).toLowerCase() : str
                .substring(0, 1).toUpperCase()).concat(str.substring(1));
    }

    /**
     * 获取项目的实际目录
     *
     * @return
     */
    public static String getUserDir() {
        String dir = System.getProperty("user.dir");
        return conversionSpecialCharacters(dir);
    }

    /**
     * 获取项目的实际目录
     *
     * @param path 追加目录名
     * @return
     */
    public static String getUserDir(String path) {
        String dir = System.getProperty("user.dir");
        return conversionSpecialCharacters(dir) + path;
    }

    /**
     * 字符串排序
     *
     * @param str
     * @return
     */
    public static String Sort(String str) {
        char[] chr = str.toCharArray();
        Arrays.sort(chr);
        return new String(chr);
    }

    /**
     * 将double类型转成String类型
     *
     * @param number
     * @return
     */
    public static String doubleToString(double number) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(number);
    }

    /**
     * 根据小时和分转换成秒数
     *
     * @param hourMin 12:12 ==>12*60*60+12*60
     * @return
     */
    public static int getSeconds(String hourMin) {
        if (StringUtils.isEmpty(hourMin))
            return 0;
        String[] times = hourMin.split(":");
        if (times != null && times.length > 0) {
            return Integer.parseInt(times[0]) * 60 * 60
                    + Integer.parseInt(times[1]) * 60;
        } else {
            return 0;
        }
    }

    /**
     * 将天数和月数不满10的补零 如:1 2 3 4 5 6 7 8 9===>01 02 03 04 05 06 07 08 09
     *
     * @param daymonth
     * @return
     */
    public static String getAutoDayOrMonth(Integer daymonth) {
        String result = String.valueOf(daymonth);
        if (daymonth < 10)
            result = "0" + daymonth;
        return result;
    }

    public static String getNotExtName(String fileName) {
        if (fileName.lastIndexOf(".") != -1) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }

    /**
     * 获取一个文件的后缀(不带有点)
     *
     * @param fileName 文件名
     * @return 返回文件的后缀
     */
    public static String getExtNoPoint(String fileName) {
        if (fileName.lastIndexOf(".") == -1)
            return "";
        int pos = fileName.lastIndexOf(".") + 1;
        return fileName.substring(pos, fileName.length());
    }

    /**
     * 将秒转换成时分秒
     *
     * @param @param  seconds
     * @param @return 设定文件
     * @return String 返回类型
     *
     * @throws
     * @Title: getVideoTime
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public static String getVideoTime(String seconds) {
        if (isNotEmpty(seconds)) {
            Calendar c = Calendar.getInstance();
            c.clear();
            c.set(Calendar.SECOND, Integer.parseInt(seconds));
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            return sdf.format(c.getTime());
        } else {
            return "0";
        }
    }

    public static String getVideoSeconds(String hms) {
        if (isNotEmpty(hms)) {
            if (StringUtils.isEmpty(hms))
                return "0";
            String[] times = hms.split(":");
            if (times != null && times.length > 0) {
                return String.valueOf(Integer.parseInt(times[0]) * 60 * 60
                        + Integer.parseInt(times[1]) * 60
                        + Integer.parseInt(times[2]));
            } else {
                return "0";
            }
        } else {
            return "0";
        }
    }

    public static void main(String[] args) {
        String str = "<html style='co'>bbb</html>aaaa";
        System.out.println(filterHtml(str));
    }
    // public static void main(String[] args) {
    // // String[] s = interceptAll(new String[]{"cbaxy","xsaaxz"}, "xyz");
    // // for (String string : s) {
    // // System.out.println(string);
    // // }
    // // System.out.println(intercept("acb123acb","abc"));
    //
    // // System.out.println(containsAny("234dfg3453", new char[]{'1','2'}));
    // // String[] strings =
    // // substringBetween("<html>ddd</html><html>ssss</html>", "<html>",
    // // "</html>");
    // // for (String string : strings) {
    // // System.out.println(string);
    // // }
    // // System.out.println(getYMDPath());
    // System.out.println(Sort("123"));
    //
    // }


    /**
     * @param : @param obj
     * @param : @return
     * @param : @throws Exception
     * @throws
     * @Title : 复制对象
     * @user : huguodong
     * @time : 2013-9-12上午10:07:46
     * @return: Object
     */
    public static Object cloneObject(Object obj) throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(obj);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        return in.readObject();
    }


    /**
     * word转换html时，会留下很多格式，有些格式并不是我们所需要的，然而这些格式比真正的文章内容还要多，严重影响页面的加载速度，
     * 因此就需要找个一个好的解决方案把这些多余的格式个去掉。网上有很多去除word冗余格式的js版的正则表达式，这里只提供java版的正则表达式。
     *
     * @param content
     * @return
     */
    public static String clearWordFormat(String content) {
        // 把<P></P>转换成</div></div>保留样式
        // content = content.replaceAll("(<P)([^>]*>.*?)(<\\/P>)",
        // "<div$2</div>");
        // 把<P></P>转换成</div></div>并删除样式
        content = content.replaceAll("(<P)([^>]*)(>.*?)(<\\/P>)", "<p$3</p>");
        // 删除不需要的标签
        content = content.replaceAll(
                "<[/]?(font|FONT|span|SPAN|xml|XML|del|DEL|ins|INS|meta|META|[ovwxpOVWXP]:\\w+)[^>]*?>", "");
        // 删除不需要的属性
        content = content
                .replaceAll(
                        "<([^>]*)(?:lang|LANG|class|CLASS|style|STYLE|size|SIZE|face|FACE|[ovwxpOVWXP]:\\w+)=(?:'[^']*'|\"\"[^\"\"]*\"\"|[^>]+)([^>]*)>",
                        "<$1$2>");
        // 删除<STYLE TYPE="text/css"></STYLE>及之间的内容
        if (content.indexOf("<STYLE") != -1) {
            int styleBegin = content.indexOf("<STYLE");
            int styleEnd = content.indexOf("</STYLE>") + 8;
            String style = content.substring(styleBegin, styleEnd);
            content = content.replace(style, "");
        }
        return content;
    }

    public static String getUUID(){
    	return java.util.UUID.randomUUID().toString();
    }
    
}

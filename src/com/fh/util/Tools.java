package com.fh.util;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 说明：常用工具
 * 创建人：FH Q313596790
 * 修改时间：2017年11月24日
 */
public class Tools {

    /** 英文和数字组合正则表达式 */
    private static Pattern ENGLISH_AND_NUMBERS_PATTERN = Pattern.compile("^[A-Za-z0-9]{6,40}$");
    /** 手机号码 */
    private static Pattern PHONE_PATTERN = Pattern.compile("^1[3-9][0-9]{9}$");


    /**
     * 功能描述：英文和数字组合正则表达式，最少6位数
     * @author Ajie
     * @date 2020/4/11 0011
     * @param input 需要验证的值
     * @return 匹配返回true/不匹配返回false
     */
    public static boolean checkEnglishAngNumber(String input) {
        return ENGLISH_AND_NUMBERS_PATTERN.matcher(input).matches();
    }

    /**
     * 功能描述：纯数字正则表达式
     * @author Ajie
     * @date 2020/4/11 0011
     * @param input 需要验证的值
     * @return 匹配返回true/不匹配返回false
     */
    public static boolean checkPureNumber(String input) {
        String pattern = "^\\d{1,40}$";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(input);
        return matcher.matches();
    }

    /**
     * 功能描述：保留N位小数并四舍五入
     *
     * @param Num 浮点数
     * @param i   位数
     * @author Ajie
     * @date 2019/12/28 0028
     */
    public static double afterDecimal(Double Num, int i) {
        BigDecimal bigDecimal = new BigDecimal(Num);
        // 这里的 2 就是你要保留几位小数。
        return bigDecimal.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 功能描述：在此范围内随机生成一个数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 结果
     * @author Ajie
     * @date 2019/12/28 0028
     */
    public static double getRandomRange(double min, double max) {
        // 使用随机0~1浮点类 Math.random(), 公式 Math.random() * (max - min+1) + min;
        return afterDecimal(Math.random() * (max - min) + min, 2);
    }

    /**
     * 随机生成六位数验证码
     *
     * @return
     */
    public static int getRandomNum() {
        Random r = new Random();
        return r.nextInt(900000) + 100000;//(Math.random()*(999999-100000)+100000)
    }

    /**
     * 随机生成四位数验证码
     *
     * @return
     */
    public static int getRandomNum4() {
        Random r = new Random();
        return r.nextInt(9000) + 1000;
    }

    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s) {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }

    /**
     * 字符串转换为字符串数组
     *
     * @param str        字符串
     * @param splitRegex 分隔符
     * @return
     */
    public static String[] str2StrArray(String str, String splitRegex) {
        if (isEmpty(str)) {
            return null;
        }
        return str.split(splitRegex);
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     *
     * @param str 字符串
     * @return
     */
    public static String[] str2StrArray(String str) {
        return str2StrArray(str, ",\\s*");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String date2Str(Date date) {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
     *
     * @param date
     * @return
     */
    public static Date str2Date(String date) {
        if (notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        } else {
            return null;
        }
    }

    /**
     * 字符串日期 转 时间戳
     *
     * @param date yyyy-MM-dd HH:mm:ss 格式的字符串
     * @return long 格式
     */
    public static long strTimeStamp(String date) {
        if (notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date time = sdf.parse(date);
                // 日期转时间戳（毫秒）
                return time.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return System.currentTimeMillis();
        } else {
            return System.currentTimeMillis();
        }
    }

    /**
     * 按照参数format的格式，日期转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 把时间根据时、分、秒转换为时间段
     *
     * @param StrDate
     */
    public static String getTimes(String StrDate) {
        String resultTimes = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date now;
        try {
            now = new Date();
            java.util.Date date = df.parse(StrDate);
            long times = now.getTime() - date.getTime();
            long day = times / (24 * 60 * 60 * 1000);
            long hour = (times / (60 * 60 * 1000) - day * 24);
            long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            StringBuffer sb = new StringBuffer();
            //sb.append("发表于：");
            if (hour > 0) {
                sb.append(hour + "小时前");
            } else if (min > 0) {
                sb.append(min + "分钟前");
            } else {
                sb.append(sec + "秒前");
            }
            resultTimes = sb.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultTimes;
    }

    /**
     * 往文件里的内容
     *
     * @param fileP   文件路径
     * @param content 写入的内容
     */
    public static void writeFile(String fileP, String content) {
        String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../";    //项目路径
        filePath = filePath.replaceAll("file:/", "");
        filePath = filePath.replaceAll("%20", " ");
        filePath = filePath.trim() + fileP.trim();
        if (filePath.indexOf(":") != 1) {
            filePath = File.separator + filePath;
        }
        try {
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 往文件里的内容（ClassResource下）
     *
     * @param fileP   文件路径
     * @param content 写入的内容
     */
    public static void writeFileCR(String fileP, String content) {
        String filePath = PathUtil.getClassResources() + fileP;
        try {
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber 手机号
     * @return 匹配返回true/不匹配返回false
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        return PHONE_PATTERN.matcher(mobileNumber).matches();
    }

    /**
     * 检测KEY是否正确
     *
     * @param paraname 传入参数
     * @param FKEY     接收的 KEY
     * @return 为空则返回true，不否则返回false
     */
    public static boolean checkKey(String paraname, String FKEY) {
        paraname = (null == paraname) ? "" : paraname;
        return MD5.md5(paraname + DateUtil.getDays() + ",fh,").equals(FKEY);
    }

    /**
     * 读取txt里的单行内容
     *
     * @param fileP 文件路径
     */
    public static String readTxtFile(String fileP) {
        try {
            String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../";    //项目路径
            filePath = filePath.replaceAll("file:/", "");
            filePath = filePath.replaceAll("%20", " ");
            filePath = filePath.trim() + fileP.trim();
            if (filePath.indexOf(":") != 1) {
                filePath = File.separator + filePath;
            }
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {        // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);    // 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    return lineTxt;
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件,查看此路径是否正确:" + filePath);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
        }
        return "";
    }

    /**
     * 读取txt里的全部内容
     *
     * @param fileP    文件路径
     * @param encoding 编码
     * @return
     */
    public static String readTxtFileAll(String fileP, String encoding) {
        StringBuffer fileContent = new StringBuffer();
        try {
            String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../";    //项目路径
            filePath = filePath.replaceAll("file:/", "");
            filePath = filePath.replaceAll("%20", " ");
            filePath = filePath.trim() + fileP.trim();
            if (filePath.indexOf(":") != 1) {
                filePath = File.separator + filePath;
            }
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {        // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);    // 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    fileContent.append(lineTxt);
                    fileContent.append("\n");
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件,查看此路径是否正确:" + filePath);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
        }
        return fileContent.toString();
    }

    /**
     * 读取ClassResources某文件里的全部内容
     *
     * @param fileP 文件路径
     */
    public static String readFileAllContent(String fileP) {
        StringBuffer fileContent = new StringBuffer();
        try {
            String encoding = "utf-8";
            File file = new File(PathUtil.getClassResources() + fileP);//文件路径
            if (file.isFile() && file.exists()) {        // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);    // 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    fileContent.append(lineTxt);
                    fileContent.append("\n");
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件,查看此路径是否正确:" + fileP);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
        }
        return fileContent.toString();
    }

}


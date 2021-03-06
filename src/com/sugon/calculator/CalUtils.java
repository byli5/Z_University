package com.sugon.calculator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: byLi
 * @date: 2021/1/13 8:59
 * @description: Used to evaluate expressions
 */
public class CalUtils {

    /**
     * Evaluate multiple expressions
     *
     * @param list
     * @return
     */
    public static List<String> calculate(List<String> list) throws IOException {
        long startTime = System.currentTimeMillis();

        String name = "E:\\result.txt";
        File file = new File(name);
       // If the file does not exist, create it
        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        List<String> results = new ArrayList<>();
        for (String str : list) {
            String value = calculate(str);
//            fw.write(value+"\r\n");
            bw.write(value);
            bw.newLine();
            // Update data to file
            bw.flush();
            results.add(value);
        }
        bw.close();
        fw.close();
        System.out.println("文件写入完毕！");
        long endTime = System.currentTimeMillis();
        System.out.println("请求耗时:" + (endTime - startTime) + "ms");

        return results;
    }

    /**
     *  Calculates the value of a single expression
     * @param str
     */
    public static  String calculate(String str){
        str = str.trim();
        Stack<Integer> numStack = new Stack<>();

        char lastOp = '+';
        char[] arr = str.toCharArray();
        int length = arr.length;
        int tempNum = 0;
        for (int i = 0; i < length; i++) {
            if (arr[i] == ' ') {
                continue;
            }
            if (Character.isDigit(arr[i])) {
                tempNum = tempNum * 10 + (arr[i] - '0');
            }
            if (!Character.isDigit(arr[i])|| i>=length-1){
                if (lastOp == '+'){
                    numStack.push(tempNum);
                }else if (lastOp == '-'){
                    numStack.push(-tempNum);
                }else if (lastOp == '*'){
                    int a = numStack.pop();
                    numStack.push(a*tempNum);
                }else if (lastOp == '/'){
                    if (tempNum == 0){
                        return CalConstant.DIVIDE_BYZERO_INFO;
                    }
                    int a = numStack.pop();
                    numStack.push(a/tempNum);
                }
                if (i>arr.length-1){
                    break;
                }
                lastOp = arr[i];
                tempNum = 0;
            }
        }
        int sum = 0;
        for (int num : numStack) {
            sum += num;
        }
        return sum + "";
    }
}

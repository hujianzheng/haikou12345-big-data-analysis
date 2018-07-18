package com.csvtotxt;

import java.io.BufferedReader;   
import java.io.BufferedWriter;   
import java.io.File;   
import java.io.FileInputStream;
import java.io.FileNotFoundException;   
import java.io.FileReader;   
import java.io.FileWriter;   
import java.io.IOException;   
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;   
import java.util.List;   
import java.util.regex.Matcher;   
import java.util.regex.Pattern;   
 
  
public class CsvToTxt {   
  
     private static final String SPECIAL_CHAR_A = "[^\",//n 　]";   
     private static final String SPECIAL_CHAR_B = "[^\",//n]";   
       
    /**  
     * 构造，禁止实例化  
     */  
    public CsvToTxt() {   
    }   
  
//    public static void main(String[] args) {   
//  
//        // test   
//        try {   
//            List list = readCsvFile("D:/挖掘热点测试数据.csv");   
//            System.out.println(list.toString());
//        } catch (FileNotFoundException ex) {   
//            Logger.getLogger(CsvToTxt.class.getName()).log(Level.SEVERE, null, ex);   
//        } catch (IOException ex) {   
//            Logger.getLogger(CsvToTxt.class.getName()).log(Level.SEVERE, null, ex);   
//        }   
//    }   
    /**  
     * csv文件读取<BR/>  
     * 读取绝对路径为argPath的csv文件数据，并以List返回。  
     *  
     * @param argPath csv文件绝对路径  
     * @return csv文件数据（List<String[]>）  
     * @throws FileNotFoundException  
     * @throws IOException  
     */  
    //每一行生成一个文档
    public static List<List<String>> readCsvFile(String argPath,String OutPath) throws FileNotFoundException, IOException {   
        CsvToTxt util = new CsvToTxt();   
        File cvsFile = new File(argPath); 
        List<List<String>> list = new ArrayList<List<String>>(); 
        FileReader fileReader = null;   
        BufferedReader bufferedReader = null;   
        try {   
            fileReader = new FileReader(cvsFile);   
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(cvsFile),"GBK"));   
            String regExp = util.getRegExp();   
  
            // test   
            String strLine = "";   
            String str = ""; 
            int i = 0;
            while ((strLine = bufferedReader.readLine()) != null) {   
                Pattern pattern = Pattern.compile(regExp);   
                Matcher matcher = pattern.matcher(strLine);   
                List<String> listTemp = new ArrayList<String>(); 
                
                while(matcher.find()) {   
                    str = matcher.group();   
                    str = str.trim();   
                    if (str.endsWith(",")){   
                        str = str.substring(0, str.length()-1);   
                        str = str.trim();   
                    }   
                    if (str.startsWith("\"") && str.endsWith("\"")) {   
                        str = str.substring(1, str.length()-1);   
                        if (util.isExisted("\"\"", str)) {   
                            str = str.replaceAll("\"\"", "\"");   
                        }   
                    }   
                    if (!"".equals(str)) {   
                        //test   
                        listTemp.add(str);  
                        //System.out.print(listTemp);
                    } 
                    
                }   
                //test 
                //listTemp 是每一行的内容
                //System.out.println(listTemp);
                
                  File fp=new File(OutPath+i+".txt");
      	          PrintWriter pfp= new PrintWriter(fp);
      	          pfp.println(listTemp);
      	          pfp.close();
                  i++;
                
                list.add(listTemp);
                
              //在D盘生成一个txt文件
                FileWriter fw = null;
                try {
                //如果文件存在，则追加内容；如果文件不存在，则创建文件
                File f=new File("D:\\dd.txt");
                fw = new FileWriter(f, true);
                } catch (IOException e) {
                e.printStackTrace();
                }
                PrintWriter pw = new PrintWriter(fw);
                pw.println(listTemp);
                pw.flush();
                try {
                fw.flush();
                pw.close();
                fw.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
            }   
        } catch (FileNotFoundException e) {   
            throw e;   
        } catch (IOException e) {   
            throw e;   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
                if (fileReader != null) {   
                    fileReader.close();   
                }   
            } catch (IOException e) {   
                throw e;   
            }   
        }  
      
        
        return list;   
    }   
    
    //生成一个文档   
    public static List<List<String>> readTXT(String argPath,String OutPath) throws FileNotFoundException, IOException {   
        CsvToTxt util = new CsvToTxt();   
        File cvsFile = new File(argPath); 
        List<List<String>> list = new ArrayList<List<String>>(); 
        FileReader fileReader = null;   
        BufferedReader bufferedReader = null;   
        try {   
            fileReader = new FileReader(cvsFile);   
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(cvsFile),"GBK"));   
            String regExp = util.getRegExp();   
  
            // test   
            String strLine = "";   
            String str = ""; 
            int i = 0;
            while ((strLine = bufferedReader.readLine()) != null) {   
                Pattern pattern = Pattern.compile(regExp);   
                Matcher matcher = pattern.matcher(strLine);   
                List<String> listTemp = new ArrayList<String>(); 
                
                while(matcher.find()) {   
                    str = matcher.group();   
                    str = str.trim();   
                    if (str.endsWith(",")){   
                        str = str.substring(0, str.length()-1);   
                        str = str.trim();   
                    }   
                    if (str.startsWith("\"") && str.endsWith("\"")) {   
                        str = str.substring(1, str.length()-1);   
                        if (util.isExisted("\"\"", str)) {   
                            str = str.replaceAll("\"\"", "\"");   
                        }   
                    }   
                    if (!"".equals(str)) {   
                        //test   
                        listTemp.add(str);  
                        //System.out.print(listTemp);
                    } 
                    
                }   
                //test 
                //listTemp 是每一行的内容
                //System.out.println(listTemp);
                
//                  File fp=new File(OutPath);
//      	          PrintWriter pfp= new PrintWriter(fp);
//      	          pfp.println(listTemp);
//      	          pfp.close();
                  i++;
                
                list.add(listTemp);
                
              //在D盘生成一个txt文件
                FileWriter fw = null;
                try {
                //如果文件存在，则追加内容；如果文件不存在，则创建文件
                File f=new File(OutPath);
                fw = new FileWriter(f, true);
                } catch (IOException e) {
                e.printStackTrace();
                }
                PrintWriter pw = new PrintWriter(fw);
                pw.println(listTemp);
                pw.flush();
                try {
                fw.flush();
                pw.close();
                fw.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
            }   
        } catch (FileNotFoundException e) {   
            throw e;   
        } catch (IOException e) {   
            throw e;   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
                if (fileReader != null) {   
                    fileReader.close();   
                }   
            } catch (IOException e) {   
                throw e;   
            }   
        }  
      
        
        return list;   
    }
    
    
    //返回字符串
    public String  readTXT(String argPath) throws FileNotFoundException, IOException {   
        CsvToTxt util = new CsvToTxt();   
        File cvsFile = new File(argPath); 
        List<List<String>> list = new ArrayList<List<String>>(); 
        String result ="";
        FileReader fileReader = null;   
        BufferedReader bufferedReader = null;   
        try {   
            fileReader = new FileReader(cvsFile);   
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(cvsFile),"GBK"));   
            String regExp = util.getRegExp();   
  
            // test   
            String strLine = "";   
            String str = ""; 
            int i = 0;
            while ((strLine = bufferedReader.readLine()) != null) {   
                Pattern pattern = Pattern.compile(regExp);   
                Matcher matcher = pattern.matcher(strLine);   
                List<String> listTemp = new ArrayList<String>(); 
                
                while(matcher.find()) {   
                    str = matcher.group();   
                    str = str.trim();   
                    if (str.endsWith(",")){   
                        str = str.substring(0, str.length()-1);   
                        str = str.trim();   
                    }   
                    if (str.startsWith("\"") && str.endsWith("\"")) {   
                        str = str.substring(1, str.length()-1);   
                        if (util.isExisted("\"\"", str)) {   
                            str = str.replaceAll("\"\"", "\"");   
                        }   
                    }   
                    if (!"".equals(str)) {   
                        //test   
                        listTemp.add(str);  
                        //System.out.print(listTemp);
                    } 
                    
                }   

                result = result+"\n"+listTemp;

                  i++;
                
                list.add(listTemp);
                
            }   
        } catch (FileNotFoundException e) {   
            throw e;   
        } catch (IOException e) {   
            throw e;   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
                if (fileReader != null) {   
                    fileReader.close();   
                }   
            } catch (IOException e) {   
                throw e;   
            }   
        }  
      
        
        return result;   
    }
    
    
    /**  
     * csv文件做成<BR/>  
     * 将argList写入argPath路径下的argFileName文件里。  
     *  
     * @param argList  要写入csv文件的数据（List<String[]>）  
     * @param argPath csv文件路径  
     * @param argFileName csv文件名  
     * @param isNewFile 是否覆盖原有文件  
     * @throws IOException  
     * @throws Exception  
     */  
    public static void writeCsvFile(List argList, String argPath, String argFileName, boolean isNewFile)   
        throws IOException, Exception {   
        CsvToTxt util = new CsvToTxt();   
        // 数据check   
        if (argList == null || argList.size() == 0) {   
            throw new Exception("没有数据");   
        }   
        for (int i = 0; i < argList.size(); i++) {   
            if (!(argList.get(i) instanceof String[])) {   
                throw new Exception("数据格式不对");   
            }   
        }   
        FileWriter fileWriter = null;   
        BufferedWriter bufferedWriter = null;   
        String strFullFileName = argPath;   
        if (strFullFileName.lastIndexOf("//") == (strFullFileName.length() - 1)) {   
            strFullFileName += argFileName;   
        } else {   
            strFullFileName += "//" + argFileName;   
        }   
        File file = new File(strFullFileName);   
        // 文件路径check   
        if (!file.getParentFile().exists()) {   
            file.getParentFile().mkdirs();   
        }   
        try {   
            if (isNewFile) {   
                // 覆盖原有文件   
                fileWriter = new FileWriter(file);   
            } else {   
                // 在原有文件上追加数据   
                fileWriter = new FileWriter(file, true);   
            }   
            bufferedWriter = new BufferedWriter(fileWriter);   
            for (int i = 0; i < argList.size(); i++) {   
                String[] strTemp = (String[]) argList.get(i);   
                for (int j = 0; j < strTemp.length; j++) {   
                    if (util.isExisted("\"",strTemp[j])){
                        strTemp[j] = strTemp[j].replaceAll("\"", "\"\"");   
                        bufferedWriter.write("\""+strTemp[j]+"\"");   
                    } else if (util.isExisted(",",strTemp[j])   
                            || util.isExisted("/n",strTemp[j])   
                            || util.isExisted(" ",strTemp[j])   
                            || util.isExisted("��",strTemp[j])){   
                        bufferedWriter.write("\""+strTemp[j]+"\"");   
                    } else {   
                        bufferedWriter.write(strTemp[j]);   
                    }   
                    if (j < strTemp.length - 1) {   
                        bufferedWriter.write(",");   
                    }   
                }   
                bufferedWriter.newLine();   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedWriter != null) {   
                    bufferedWriter.close();   
                }   
                if (fileWriter != null) {   
                    fileWriter.close();   
                }   
            } catch (IOException e) {   
                throw e;   
            }   
        }   
    }   
       
    /**  
     * @param argChar  
     * @param argStr  
     * @return  
     */  
    private boolean isExisted(String argChar, String argStr) {   
           
        boolean blnReturnValue = false;   
        if ((argStr.indexOf(argChar) >= 0)   
                && (argStr.indexOf(argChar) <= argStr.length())) {   
            blnReturnValue = true;   
        }   
        return blnReturnValue;   
    }   
       
    /**  
     * 正则表达式。  
     * @return 匹配csv文件里最小单位的正则表达式。  
     */  
    private String getRegExp() {   
           
        String strRegExp = "";   
           
        strRegExp =   
            "\"(("+ SPECIAL_CHAR_A + "*[,//n 　])*("+ SPECIAL_CHAR_A + "*\"{2})*)*"+ SPECIAL_CHAR_A + "*\"[ 　]*,[ 　]*"  
            +"|"+ SPECIAL_CHAR_B + "*[ 　]*,[ 　]*"  
            + "|\"(("+ SPECIAL_CHAR_A + "*[,//n 　])*("+ SPECIAL_CHAR_A + "*\"{2})*)*"+ SPECIAL_CHAR_A + "*\"[ 　]*"  
            + "|"+ SPECIAL_CHAR_B + "*[ 　]*";   
           
        return strRegExp;   
    }      
}  

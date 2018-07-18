package com.license;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import com.lingjoin.demo.NlpirLib;
import com.lingjoin.util.OSInfo;
import com.sun.jna.Native;

public class LicenseChecker {  
    public static void main(String[] args) {  
        if (args.length < 1) {  
            System.out.println("java -jar LicenseChecker.jar libPath [DataPath]");  
            System.exit(2);  
        }  
  
        System.exit(run(args[0], args.length == 1 ? "" : args[1]));  
    }  
  
    /** 
     * try initial NLPIR 
     *  
     * @param lib 
     * @param data 
     * @return 0 if license invalid or other exception happened else return 1 
     *         represent initial NLPIR success 
     */  
    public static int run(String lib, String data) {  
        int status = 0;  
        //NlpirLib instance = (NlPIRLib) Native.loadLibrary(lib,NlPIRLibrary.class); 
        NlpirLib instance = (NlpirLib) Native.loadLibrary(lib, NlpirLib.class);        
        if (instance.NLPIR_Init(data, 1, "")) {  
            status = 1;  
        }  
        instance.NLPIR_Exit();  
        return status;  
    }  
} 



/** 
 * get NLPIR Object 
 *  
 *  
 */  
class NlPIRIniter {  
    // license update url  
    public static final String latestLicenseUrl = "https://github.com/NLPIR-team/NLPIR/raw/master" +
    "/License/license%20for%20a%20month/NLPIR-ICTCLAS%E5%88%86%E8%AF%8D%E7%B3%BB%E7%BB%9F%E6%8E%88%E6%9D%83/NLPIR.user";  
  
    /** 
     * initialization NLPIR (handle license expired problem) 
     *  
     * @param lib 
     * @param data 
     * @param licenseCheckerJarPath 
     * @return 
     * @throws Exception 
     */  
    public static NlpirLib getInstance(String lib, String data,String licenseCheckerJarPath)  
            throws Exception {  
        // call another jar to check if license is expired  
        Process p = Runtime.getRuntime().exec(  
                "java -jar " + licenseCheckerJarPath + " " + lib + " " + data);  
        p.waitFor();  
        // return 0 when initial failed  
        if (p.exitValue() == 0) {  
            FileDownLoader.saveUrlAs(latestLicenseUrl,Paths.get(data, "Data", "NLPIR.user").toString());  
        }  
        NlpirLib instance = (NlpirLib) Native.loadLibrary(lib,  
        		NlpirLib.class);  
  
        if (instance.NLPIR_Init(data, 1, "0")) {  
            return instance;  
        }  
        throw new Exception(instance.NLPIR_GetLastErrorMsg());  
    }  
} 

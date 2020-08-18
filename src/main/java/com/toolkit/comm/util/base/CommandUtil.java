package com.toolkit.comm.util.base;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:
 */
public class CommandUtil {

	private static Logger logger = LoggerFactory.getLogger(CommandUtil.class);

    @Inject
    private static Gson gson;

    /**
     * 绑定IP 参数
     * ssh 192.168.100.111 natc add eth0 116.254.242.27 192.168.101.27 100K
     * @param command
     * @return 00:OK,IP binding is successful. (in_ip=116.254.242.27,out_ip=192.168.101.27,bandwidth=100K)
     */
    public static Map addIP(String command){
        String res = "";

        res = CommandUtil.exec("",command);
//        res = "00:OK,IP binding is successful. (in_ip=116.254.242.27,out_ip=192.168.101.27,bandwidth=100K)";
        Map map = new HashMap<String,String>();
        if(res.contains("successful")){
            res = res.substring(res.indexOf("("),res.lastIndexOf(")"));
            String [] param_arr =  res.split(",");

            if(param_arr.length > 0){
                for( String param : param_arr){
                    map.put(param.split("=")[0],param.split("=")[1]);
                }
            }
        }
        return map;
    }
    public static void main(String [] args){
       String  res = "00:OK,IP binding is successful. (in_ip=116.254.242.27,out_ip=192.168.101.27,bandwidth=100K)";
        if(res.contains("successful")){
            res = res.substring(res.indexOf("(")+1,res.lastIndexOf(")"));
            String [] param_arr =  res.split(",");

            if(param_arr.length > 0){
                for( String param : param_arr){
                    System.out.println(param.split("=")[0] + " --- " + param.split("=")[1]);
                }
            }
        }
    }
	// execute command and return the output stuff.
	public static String exec(String subtaskId, String cmd) {
		logger.debug(subtaskId + "exec: " + cmd);

		StringBuffer sb = new StringBuffer();
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd);
			try {
				// any error message?
				StreamGobbler2 errorGobbler = new StreamGobbler2(subtaskId, proc.getErrorStream());

				// any output?
				StreamGobbler2 outputGobbler = new StreamGobbler2(subtaskId, proc.getInputStream());

				// kick them off
				errorGobbler.start();
				outputGobbler.start();
				while (!errorGobbler.isFinish() || !outputGobbler.isFinish()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}

				// any error???
				proc.waitFor();
				sb.append(errorGobbler.getOutputString()).append(outputGobbler.getOutputString());
			} finally {
				try {
					proc.getErrorStream().close();
				} catch (Exception e) {
				}
				try {
					proc.getInputStream().close();
				} catch (Exception e) {
				}
				try {
					proc.getOutputStream().close();
				} catch (Exception e) {
				}
			}
			proc.destroy();
			proc = null;
			System.gc();
		} catch (Throwable t) {
			logger.error(subtaskId + " : " + cmd, t);
		}
		logger.info(subtaskId + "output=" + sb.toString().replace("\n", ","));
		return sb.toString();
	}	

}

class StreamGobbler extends Thread {

	private static final String SYNC_OK = "SEEDING";

	private static Logger logger = LoggerFactory.getLogger(StreamGobbler.class);

	private InputStream is;

	private StringBuffer output = new StringBuffer();

	private boolean finish = false;

	private String subTaskId;

	StreamGobbler(String subTaskId, InputStream is) {
		logger.info(subTaskId + " : new StreamGobbler");
		this.subTaskId = subTaskId;
		this.is = is;
	}

	public boolean isFinish() {
		return finish;
	}

	public String getOutputString() {
		return output.toString();
	}

	public void run() {
		logger.info(subTaskId + " : start read Output");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.info(subTaskId + " : " + line);
				if (line.indexOf(SYNC_OK) != -1) {
					logger.info(subTaskId + " : SYNC OK !!!!!!");
					break;
				}
			}
			logger.info(subTaskId + " : tooles exit");
		} catch (IOException e) {
			logger.error(subTaskId + " : StreamGobbler error", e);
		} catch (Throwable e) {
			logger.error(subTaskId + " : StreamGobbler error", e);
		} finally {
			finish = true;
		}
		logger.info(subTaskId + " : end read Output");
	}
}

class StreamGobbler2 extends Thread {

	private static Logger logger = LoggerFactory.getLogger(StreamGobbler2.class);
	InputStream is;

	StringBuffer output = new StringBuffer();

	private boolean finish = false;
	private String subtaskId;

	StreamGobbler2(String subtaskId, InputStream is) {
		this.is = is;
		this.subtaskId = subtaskId;
	}

	public boolean isFinish() {
		return finish;
	}

	public String getOutputString() {
		return output.toString();
	}

	public void run() {
		try {
			byte[] buf = new byte[1024];
			int n;
			while ((n = is.read(buf)) != -1) {
				output.append(new String(buf, 0, n));
			}
		} catch (IOException ioe) {
			logger.error(subtaskId + " : StreamGobbler2 error", ioe);
		} catch (Throwable e) {
			logger.error(subtaskId + " : StreamGobbler2 error", e);
		} finally {
			finish = true;
		}
	}
}


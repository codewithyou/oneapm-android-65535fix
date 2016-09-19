package com.oneapm.agent.android.delayload;

import java.io.*;

/**
 *
 * @author  :haoqqmeail@qq.com
 *
 */
public class FileUtil {

    /**
     * 读取并复制文件
     * @param src
     * @param destFileName
     * @param override
     * @return 是否操作成功！
     */
    public static boolean copyFile(InputStream src, String destFileName, boolean override) {
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            if (override) {
                new File(destFileName).delete();
            }
        } else {
            if (!destFile.getParentFile().exists()) {
                if (!destFile.getParentFile().mkdirs()) {
                    return false;
                }
            }
        }
        int byteread = 0;
        InputStream in = null;
        OutputStream out = null;

        try {
            in = src;
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

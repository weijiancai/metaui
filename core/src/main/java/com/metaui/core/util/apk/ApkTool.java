package com.metaui.core.util.apk;

import brut.androlib.Androlib;
import brut.apktool.Main;
import com.metaui.core.util.UFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

/**
 * Apktool v2.0.0-RC3 - a tool for reengineering Android apk files
 * with smali v2.0.3 and baksmali v2.0.3
 * Copyright 2014 Ryszard Wi?niewski <brut.alll@gmail.com>
 * Updated by Connor Tumbleson <connor.tumbleson@gmail.com>
 *
 * usage: apktool
 * -advance,--advanced   prints advance information.
 * -version,--version    prints the version then exits
 * usage: apktool if|install-framework [options] <framework.apk>
 * -p,--frame-path <dir>   Stores framework files into <dir>.
 * -t,--tag <tag>          Tag frameworks using <tag>.
 * usage: apktool d[ecode] [options] <file_apk>
 * -f,--force              Force delete destination directory.
 * -o,--output <dir>       The name of folder that gets written. Default is apk.out
 * -p,--frame-path <dir>   Uses framework files located in <dir>.
 * -r,--no-res             Do not decode resources.
 * -s,--no-src             Do not decode sources.
 * -t,--frame-tag <tag>    Uses framework files tagged by <tag>.
 * usage: apktool b[uild] [options] <app_path>
 * -f,--force-all          Skip changes detection and build all files.
 * -o,--output <dir>       The name of apk that gets written. Default is dist/name.apk
 * -p,--frame-path <dir>   Uses framework files located in <dir>.
 *
 * For additional info, see: http://code.google.com/p/android-apktool/
 * For smali/baksmali info, see: http://code.google.com/p/smali/
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class ApkTool {
    /**
     * 反编译apk
     *
     * @param apkFile
     * @throws Exception
     */
    public static String decode(File apkFile) throws Exception {
        File outDir = new File(apkFile.getParentFile(), UFile.getFileNameNoExt(apkFile));
        String[] args = new String[]{"d", "-f", apkFile.getAbsolutePath(), "-o", outDir.getAbsolutePath()};
        PrintStream out = System.out;
        // 重定向输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        Main.main(args);
        // 输出流重置默认
        System.setOut(out);

        return new String(bos.toByteArray());
    }

    public String getVersion() {
        return Androlib.getVersion();
    }
}

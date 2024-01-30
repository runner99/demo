package com.runner.testworks.controller.wangzhuan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author weizhenqiang
 * @date 2024/1/23 11:44
 */
@Slf4j
public class SqlDataUtil {


    private static final String mysqlUser = "root";
    private static final String mysqlPassword = "6124vlKyBLYl";
    public static final String exportDir = "/data/wangzhuan";

    static {
        File directory = new File(exportDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * 导入zip压缩包
     * @param file
     * @return
     */
    public static List<String> importZip(MultipartFile file) {
        ArrayList<String> fileNames = new ArrayList<>();

        try {

            // 1. 创建临时目录用于解压缩
            Path tempDir = Files.createTempDirectory("tempDir");

            // 2. 解压缩上传的 ZIP 文件到临时目录
            unzip(file.getInputStream(), tempDir);

            // 3. 遍历临时目录下的文件，修改文件名并移动到指定目录
            Files.walk(tempDir)
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        String originalFileName = filePath.getFileName().toString();
                        String modifiedFileName = modifyFileName(originalFileName);
                        fileNames.add(modifiedFileName);
                        // 目标目录
                        Path destPath = Paths.get(exportDir, modifiedFileName);

                        try {
                            // 移动文件到目标目录
                            Files.move(filePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            // 4. 删除临时目录
            Files.walk(tempDir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(filePath -> {
                        try {
                            Files.delete(filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            return fileNames;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void unzip(InputStream zipInputStream, Path destDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(zipInputStream)) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                Path entryPath = destDir.resolve(zipEntry.getName());
                Files.createDirectories(entryPath.getParent());

                if (!zipEntry.isDirectory()) {
                    // 如果是文件，就写入文件
                    try (OutputStream os = Files.newOutputStream(entryPath)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = zis.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
    }

    private static String modifyFileName(String originalFileName) {

        return "import_"+System.currentTimeMillis()+"_"+originalFileName;
    }

    /**
     * 将多个（一个）sql文件压缩成zip导出
     *
     * @param fileNames
     * @param response
     * @param zipName
     */
    public static void exportZip(List<String> fileNames, HttpServletResponse response, String zipName) {


        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            // 设置 HTTP 响应头
            response.setContentType("application/zip");
//            response.setHeader("Content-Disposition", "attachment; filename=wangzhuan_files.zip");

            zipName = URLEncoder.encode(zipName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + zipName);


            // 循环添加文件到压缩包
            for (String fileName : fileNames) {
                File file = new File(exportDir, fileName);
                if (file.exists()) {
                    addToZip(zipOut, file);
                } else {
                    log.error("文件不存在" + fileName);
                }
            }

            // 完成压缩
            zipOut.finish();
            zipOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    private static void addToZip(ZipOutputStream zipOut, File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

            zipOut.closeEntry();
        }
    }

    /**
     * 执行sql文件
     *
     * @param sqlFileName
     * @return
     */
    public static boolean executeSqlFile(String sqlFileName) {
        return executeSqlFile("soc", sqlFileName);
    }

    public static boolean executeSqlFile(String database, String sqlFileName) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "mysql",
                    "-u" + mysqlUser,
                    "-p" + mysqlPassword,
                    database,
                    "--execute=source " + exportDir + "/" + sqlFileName
            );

            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            log.error("executesql is fail:" + e.getMessage(), e);
            return false;
        }

        return true;
    }


    /**
     * 生成sql文件
     *
     * @param tableName
     * @return
     */
    public static String exportMysqldump(String tableName) {

        return exportMysqldump("soc", tableName);
    }


    public static String exportMysqldump(String database, String tableName) {

        long currented = System.currentTimeMillis();
        String fileName = null;

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "mysqldump",
                    "-u" + mysqlUser,
                    "-p" + mysqlPassword,
                    database,
                    tableName
            );

            // 设置导出目录
            processBuilder.directory(new File(exportDir));

            fileName = database + "_" + tableName + "_" + currented + ".sql";

            // 输出到文件
            File outputFile = new File(exportDir, fileName);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.to(outputFile));

            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            log.error("export sql fail:" + e.getMessage(), e);
            return null;
        }

        return fileName;
    }

    /**
     * 清空某张表
     *
     * @param tableName
     * @return
     */
    public static boolean truncateTable(String tableName) {
        return truncateTable("soc", tableName);
    }

    public static boolean truncateTable(String database, String tableName) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "mysql",
                    "-u" + mysqlUser,
                    "-p" + mysqlPassword,
                    "--execute=TRUNCATE TABLE " + tableName,
                    database
            );

            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            log.error("truncate tables is fail:" + e.getMessage(), e);
            return false;
        }

        return true;
    }
}

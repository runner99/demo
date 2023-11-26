package com.runner.testworks.controller.quzhou;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.runner.testworks.controller.quzhou.vo.RuleExport;
import lombok.extern.slf4j.Slf4j;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;

import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RestController
@RequestMapping("/quzhou")
@Slf4j
public class QuZhouController {

    private static final String excelName = "自定义规则";
    private static final String password = "hzmc123";

    @GetMapping("/export")
    public void exportZip(HttpServletResponse response) {
        try {
            // 创建用户列表
            List<RuleExport> ruleExportList = createSampleRuleExportList();

//          TODO 将ruleExportList生成为一个excel文件，然后压缩成zip文件，zipBytes就是这个zip文件的字节数组
//            byte[] zipBytes = null;
            // 将ruleExportList生成为一个excel文件
            byte[] excelBytes = exportToExcel(ruleExportList);

            // 压缩成zip文件
            byte[] zipBytes = compressToZipWithPassword(excelBytes, password);
            // 设置响应头
            response.setContentType("application/zip");
            String fileName = URLEncoder.encode(excelName + ".zip", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);


            try (ServletOutputStream write = response.getOutputStream()) {
                write.write(zipBytes);
            }


        } catch (IOException e) {
            e.printStackTrace();
            // 处理异常，返回适当的响应
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private List<RuleExport> createSampleRuleExportList() {
        List<RuleExport> ruleExportList = new ArrayList<>();
        ruleExportList.add(new RuleExport(1, "哈哈哈"));
        ruleExportList.add(new RuleExport(2, "Bob"));
        ruleExportList.add(new RuleExport(3, "Charlie"));
        return ruleExportList;
    }

    private byte[] exportToExcel(List<RuleExport> ruleExportList) throws IOException {
        // 使用 EasyExcel 导出 Excel
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            EasyExcel.write(bos, RuleExport.class).sheet("自定义规则").doWrite(ruleExportList);
            return bos.toByteArray();
        }
    }

    private byte[] compressToZipWithPassword(byte[] data, String password) throws IOException, ZipException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            // 创建临时文件
            File tempFile = File.createTempFile("temp", ".xlsx");
            tempFile.deleteOnExit();

            // 将数据写入临时文件
            Files.write(tempFile.toPath(), data);

            // 创建 ZIP 参数
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(EncryptionMethod.AES);
            zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
//            zipParameters.setPassword(password);

            // 创建 ZIP 文件
            ZipFile zipFile = new ZipFile(tempFile);

            // 添加文件到 ZIP
            zipFile.addFile(tempFile, zipParameters);

            // 读取 ZIP 文件内容为 byte[]
            Files.copy(tempFile.toPath(), bos);

            return bos.toByteArray();
        }
    }


    @GetMapping("/export2")
    public void exportZip2(HttpServletResponse response) {
        try {
            response.setContentType("application/zip");
            String fileName = URLEncoder.encode(excelName + ".zip", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);


            try (ServletOutputStream write = response.getOutputStream()) {
//                write.write(zipBytes);
            }
        }catch (Exception e){
            log.error(e+"");
        }

    }
    public static void main(String[] args) {
        compress(new File("C:\\test\\自定义规则.xlsx"),password);
    }


    /**
     * 压缩
     *
     * @param sourceFile 压缩源文件，会在源文件所在目录下新建一个zip文件夹存放压缩后的文件
     * @param password   密码
     */
    public static void compress(File sourceFile, String password) {
        File dir = new File(sourceFile.getParent() + File.separator + "zip");
        dir.mkdir();

        // 文件名
        String fileName = sourceFile.getName();
        // 文件真实名（不含扩展名）
        String realName = fileName.substring(0, fileName.lastIndexOf("."));
        String targetPathname = dir.getAbsolutePath() + File.separator + realName + ".zip";
        File targetFile = new File(targetPathname);

        ZipParameters zipParameters = new ZipParameters();
        ZipFile zipFile = new ZipFile(targetFile);
        // 是否加密
        if (StringUtils.isNotBlank(password)) {
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(EncryptionMethod.AES);
            zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
            zipFile.setPassword(password.toCharArray());
        }
        try {
            zipFile.addFile(sourceFile, zipParameters);
        } catch (Exception e) {
            log.error("压缩文件异常：", e);
        }

    }





























    @PostMapping("/import")
    public void importZip(@RequestParam("file") MultipartFile file) {
        try {
            File zipFile = convertMultipartFileToFile(file);

            try {
                ZipFile zip = new ZipFile(zipFile);

                zip.setPassword(password.toCharArray());

                List<FileHeader> fileHeaders = zip.getFileHeaders();
                for (FileHeader fileHeader : fileHeaders) {
                    String entryName = fileHeader.getFileName();

                    // 获取 InputStream
                    InputStream inputStream = zip.getInputStream(fileHeader);

                    // 处理 InputStream
                    processInputStream(inputStream);
                }

            } catch (Exception e) {
                log.error("处理ZIP文件时出错：{}", e.getMessage());
                // 处理异常或返回错误响应
            } finally {
            }

        } catch (IOException e) {
            log.error("转换MultipartFile到File时出错：{}", e.getMessage());
            // 处理异常或返回错误响应
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (OutputStream os = new FileOutputStream(convertedFile)) {
            os.write(file.getBytes());
        }
        return convertedFile;
    }

    private void processInputStream(InputStream inputStream) {
        ArrayList<RuleExport> list = new ArrayList<>();
        EasyExcel.read(inputStream, RuleExport.class, new AnalysisEventListener() {

            //          在读取完一行数据后调用
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
                list.add((RuleExport) o);
            }

            //           在读取完所有数据后调用
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("读完了");
            }
        }).sheet().doRead();

    }


}

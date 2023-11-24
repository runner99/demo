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

import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
            byte[] excelBytes = exportToExcel(ruleExportList);

            // 创建 Zip 文件并将 Excel 文件添加到 Zip 文件中
            byte[] zipBytes = createZipFile(excelBytes, excelName);

            // 设置响应头
            response.setContentType("application/zip");
            String fileName = URLEncoder.encode(excelName + ".zip", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

            try (ByteArrayInputStream bis = new ByteArrayInputStream(zipBytes);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }

                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    outputStream.write(bos.toByteArray());
                    response.getOutputStream().write(outputStream.toByteArray());
                }
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

    private byte[] createZipFile(byte[] sourceBytes, String entryName) throws IOException {
        return compressAndEncryptToZip(sourceBytes, password);
    }

    // 压缩并加密成 ZIP
    private static byte[] compressAndEncryptToZip(byte[] data, String password) throws IOException, ZipException {
        // 创建临时文件
        File tempFile = File.createTempFile("temp", ".zip");
        tempFile.deleteOnExit();

        // 将数据写入临时文件
        Files.write(tempFile.toPath(), data);

        // 创建 ZIP 文件
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
//         zipParameters.setAesKeyStrength(EncryptionMethod.AES.getAesKeyStrength()); // 此行可能不再需要
//        zipParameters.setPassword(password);
//        zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);


        ZipFile zipFile = new ZipFile(tempFile,password.toCharArray());
        zipFile.addFile(tempFile, zipParameters);

        // 读取 ZIP 文件内容为 byte[]
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Files.copy(tempFile.toPath(), outputStream);

        return outputStream.toByteArray();
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

package com.runner.testworks.controller.quzhou;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.alibaba.fastjson.JSONArray;
import com.runner.testworks.controller.quzhou.vo.RuleExport;
import com.runner.testworks.controller.suzhou.utils.TimeFormatEnum;
import com.runner.testworks.controller.suzhou.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;

import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
            // TODO 获取导入的数据
            List<RuleExport> ruleExportList = createSampleRuleExportList();

//            将excel文件写入临时文件夹
            File file = exportToExcelTempFile(ruleExportList);

//            将excel压缩并将压缩加密后的zip文件写入临时文件夹
            compress(file, password);

            // 设置响应头
            response.setContentType("application/zip");
            String fileName = URLEncoder.encode(excelName + ".zip", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
//            获取zip文件的字节数组
            byte[] bytes = fileToBytes(new File(System.getProperty("java.io.tmpdir") + File.separator + "zip", excelName + ".zip"));

            try (ServletOutputStream write = response.getOutputStream()) {
                write.write(bytes);
            }

        } catch (IOException e) {
            log.error("export is file:{}", e);
        }

    }


    private List<RuleExport> createSampleRuleExportList() {
        List<RuleExport> ruleExportList = new ArrayList<>();

        return ruleExportList;
    }

    private File exportToExcelTempFile(List<RuleExport> ruleExportList) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            EasyExcel.write(bos, RuleExport.class).sheet("自定义规则").doWrite(ruleExportList);
            byte[] bytesExcel = bos.toByteArray();
            String tempDir = System.getProperty("java.io.tmpdir");
            File file = new File(tempDir, excelName + ".xlsx");

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytesExcel);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }
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

        String fileName = sourceFile.getName();
        String realName = fileName.substring(0, fileName.lastIndexOf("."));
        String targetPathname = dir.getAbsolutePath() + File.separator + realName + ".zip";
        File targetFile = new File(targetPathname);

        ZipParameters zipParameters = new ZipParameters();
        ZipFile zipFile = new ZipFile(targetFile);
        if (StringUtils.isNotBlank(password)) {
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(EncryptionMethod.AES);
            zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
            zipFile.setPassword(password.toCharArray());
        }
        try {
            zipFile.addFile(sourceFile, zipParameters);
        } catch (Exception e) {
            log.error("压缩文件异常：{}", e);
        }
    }


    private byte[] fileToBytes(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream(1024)) {

            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            byte[] data = bos.toByteArray();
            return data;
        } catch (Exception e) {
            log.error("fileToBytes is file:{}", e);
        }
        return null;
    }


    @PostMapping("/import")
    public void importZip(@RequestParam("file") MultipartFile file) {
        checkFileValid(file.getOriginalFilename(), "zip");
        try {
            File zipFile = convertMultipartFileToFile(file);
            ZipFile zip = new ZipFile(zipFile);

            zip.setPassword(password.toCharArray());

            List<FileHeader> fileHeaders = zip.getFileHeaders();
            for (FileHeader fileHeader : fileHeaders) {
                checkFileValid(fileHeader.getFileName(), "xlsx");
                InputStream inputStream = zip.getInputStream(fileHeader);
                processInputStream(inputStream);
            }

        } catch (Exception e) {
            log.error("" + e);
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
        try {
            EasyExcel.read(inputStream, RuleExport.class, new AnalysisEventListener() {

                @Override
                public void invoke(Object o, AnalysisContext analysisContext) {
                    list.add((RuleExport) o);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                    System.out.println("读完了");
                }
            }).sheet().doRead();
        } catch (Exception e) {
            log.error("文件无法解析，请重新导入！");
            throw new RuntimeException("文件无法解析，请重新导入！");
        }


//        TODO 需要处理导入的数据
        list.stream().forEach(obj -> {
            System.out.println(obj);
        });
    }

    public void checkFileValid(String originalFilename, String suffix) {
        int i = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(i + 1, originalFilename.length());
        if (!suffix.equals(substring)) {
            throw new RuntimeException("文件格式错误，导入失败!");

        }
    }




    public static void main(String[] args) {
// 假设给定的时间戳是当前时间的时间戳
        long currentTimestamp = System.currentTimeMillis();
        long[] time = getMSRangeBeforeTwoMonth(1675180800000L);
        System.out.println(time[0]);
        System.out.println(time[1]);



    }

    private static long[] getMSRangeBeforeTwoQuarter(Long currentTimestamp){

        long[] msRange = new long[2];

        // 将时间戳转换为 Instant
        Instant instant = Instant.ofEpochMilli(currentTimestamp);

        // 将 Instant 转换为 LocalDate
        LocalDate currentDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        // 获取当前季度
        int currentQuarter = (currentDate.getMonthValue() - 1) / 3 + 1;

        // 计算前两个季度的季度号
        int previousTwoQuarters = currentQuarter - 2;

        // 如果季度号为负数，表示当前是年初，将其修正为上一年的最后一个季度
        if (previousTwoQuarters <= 0) {
            previousTwoQuarters += 4;
        }

        // 得到前两个季度的第一个月
        Month startMonth = Month.of((previousTwoQuarters - 1) * 3 + 1);

        // 得到前两个季度的起始时间戳
        LocalDate startOfPreviousTwoQuarters = LocalDate.of(currentDate.getYear(), startMonth, 1);
        long startOfPreviousTwoQuartersTimestamp = startOfPreviousTwoQuarters
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        // 得到前两个季度的结束时间戳
        LocalDate endOfPreviousTwoQuarters = startOfPreviousTwoQuarters.plusMonths(6).minusDays(1);
        long endOfPreviousTwoQuartersTimestamp = endOfPreviousTwoQuarters
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();


        msRange[0]=startOfPreviousTwoQuartersTimestamp;
        msRange[1]=endOfPreviousTwoQuartersTimestamp+1000;

        return msRange;

    }

    public static long[] getMSRangeBeforeTwoMonth(Long decemberTimestamp){

        long[] msRange = new long[2];

        // 将时间戳转换为 Instant
        Instant instant = Instant.ofEpochMilli(decemberTimestamp);

        // 将 Instant 转换为 LocalDate
        LocalDate decemberDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        // 减去两个月
        LocalDate octoberDate = decemberDate.minusMonths(2);

        // 得到前两个月的起始时间戳
        long startOfOctoberTimestamp = octoberDate
                .withDayOfMonth(1)
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        // 得到前两个月的结束时间戳
        long endOfOctoberTimestamp = octoberDate
                .withDayOfMonth(octoberDate.lengthOfMonth())
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        msRange[0]=startOfOctoberTimestamp;
        msRange[1]=endOfOctoberTimestamp+1000;

        return msRange;

    }
    private static String formatDouble(Long a, Long b) {
        if (b.intValue() == 0) {
            return String.format("%.2f", 0D) + "%";
        }
        return String.format("%.2f", (double) a / b * 100) + "%";
    }

    private static String formatDouble(Double d) {
        return String.format("%.2f", Math.abs(d) * 100) + "%";
    }

}

package com.runner.testworks.controller.suzhou;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  写csv文件
 * @Author lizian
 * @Date 2020-07-26
 */
public class ExportCSVUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExportCSVUtil.class);

    /**
     * 写CSV并转换为字节流
     * @param tableHeaderArr 表头
     * @param cellList 数据
     * @return
     */
    public static byte[] writeDataAfterToBytes(String[] tableHeaderArr, List<String> cellList) {
    	byte[] bytes = new byte[0];
    	ByteArrayOutputStream byteArrayOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;
        try {
        	byteArrayOutputStream = new ByteArrayOutputStream();
        	outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream,StandardCharsets.UTF_8);
        	bufferedWriter = new BufferedWriter(outputStreamWriter);
        	//excel文件需要通过文件头的bom来识别编码，而CSV文件格式不自带bom,所以写文件时，需要先写入bom头，否则excel打开乱码
        	bufferedWriter.write(new String(ByteOrderMark.UTF_8.getBytes()));
			//写表头
        	StringBuilder sb = new StringBuilder();
        	String tableHeader = String.join(",", tableHeaderArr);
        	sb.append(tableHeader + StringUtils.CR + StringUtils.LF);
        	for (String rowCell : cellList) {
        		sb.append(rowCell + StringUtils.CR + StringUtils.LF);
        	}
        	bufferedWriter.write(sb.toString());
			bufferedWriter.flush();
			//把输出流转换字节流
			bytes = byteArrayOutputStream.toString(StandardCharsets.UTF_8.name()).getBytes();
			return bytes;
		} catch (IOException e) {
			logger.error("writeDataAfterToBytes IOException:{}", e.getMessage(), e);
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
				if (outputStreamWriter != null) {
					outputStreamWriter.close();
				}
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
				}
			} catch (IOException e) {
				logger.error("iostream close IOException:{}", e.getMessage(), e);
			}
		}
		return bytes;
    }



    /**
     * 设置下载响应
     * @param fileName
     * @param bytes
     * @param response
     */
    public static void responseSetProperties(String fileName, byte[] bytes, HttpServletResponse response) {
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
			response.setContentType("application/csv");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "max-age=30");
			response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(bytes);
			outputStream.flush();
		} catch (IOException e) {
			logger.error("iostream error:{}", e.getMessage(), e);
		}
    }

}

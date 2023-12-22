package com.runner.testworks.controller.bjgw.bug;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author weizhenqiang
 * @date 2023/12/15 15:54
 */
@Slf4j
public class MonitorEpcDropTask {


    public static AtomicLong count = new AtomicLong(0L);

    private static boolean isFirst = true;

    static {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                long curent = count.get();
                if (isFirst) {
                    synchronized (MonitorEpcDropTask.class) {
                        if (isFirst) {
                            try {
                                Process process = Runtime.getRuntime().exec("sh /data/monitor/monitor.sh");
                                process.waitFor();
                                if (process.exitValue() == 0) {
                                    log.info("命令执行成功");
                                } else {
                                    log.info("命令执行失败");
                                }
                            } catch (Exception e) {
                                log.error("" + e);
                            } finally {
                                isFirst = false;
                            }
                        }
                    }


                }
                count.getAndAdd(-curent);

                String poll = LocalDateTime.now() + "::" + curent + "\r\n";
                String filename = "/data/epc/log/epc_data.txt";
                BufferedWriter bw = null;
                FileWriter fw = null;
                try {
                    fw = new FileWriter(filename, true);
                    bw = new BufferedWriter(fw);
                    bw.write(poll);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bw != null)
                            bw.close();
                        if (fw != null)
                            fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (Exception e) {
                log.error("epc count drop is fail:{}", e);
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

}

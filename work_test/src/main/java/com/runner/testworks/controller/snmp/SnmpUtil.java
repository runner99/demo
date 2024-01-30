package com.runner.testworks.controller.snmp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author weizhenqiang
 * @date 2024/1/22 17:47
 */
@Slf4j
public class SnmpUtil {
    private static final List<String> DIR_LIST=new ArrayList<String>(){{
        add("/data");
        add("/");
    }};

    private static final String community = "hzmc+Ra2$yuL";



    private static String getFormantMem(Long size) {

        StringBuffer buffer = new StringBuffer();

        int count = 0;
        double newSize = size.doubleValue();
        while (newSize - 1024 > 0) {
            newSize = newSize / 1024;
            count++;
        }
        buffer.append(String.format("%.2f", newSize));

        if (count == 0) {

            buffer.append("kb");
        } else if (count == 1) {
            buffer.append("MB");
        } else if (count == 2) {
            buffer.append("G");
        } else if (count == 3) {
            buffer.append("T");
        } else {
            buffer.append("PT");
        }


        return buffer.toString();


    }

    public static DeviceSnmpInfo getDeviceSnmpInfo(String ipAddress) {

        DeviceSnmpInfo info = new DeviceSnmpInfo();

        try {
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();

            Snmp snmp = new Snmp(transport);

            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            target.setVersion(SnmpConstants.version2c);
            target.setAddress(new UdpAddress(ipAddress + "/161"));
            target.setRetries(2);
            target.setTimeout(100);

            // 空闲cpu
            Long cpuUsage = getSNMPData(snmp, target, SnmpOID.IDLE_CPU.getOID());
            log.info("CPU free: " + cpuUsage);

            // 总内存
            Long memoryAll = getSNMPData(snmp, target, SnmpOID.TOTAL_MEMORY.getOID());
            if (new Long(0).equals(memoryAll)) {
                return info;
            }

            Long memoryFree = getSNMPData(snmp, target, SnmpOID.MEM_AVAIL_REAL.getOID());

            Long memoryCached = getSNMPData(snmp, target, SnmpOID.MEM_CACHED.getOID());

            Long memoryBuffer = getSNMPData(snmp, target, SnmpOID.MEM_BUFFER.getOID());

            long realMemUsd = memoryAll - memoryFree - memoryCached - memoryBuffer;

            if (realMemUsd > 0) {
                log.info("real mem free :" + realMemUsd);
            } else {
                realMemUsd = realMemUsd - memoryCached - memoryBuffer;
                log.info("real mem free :" + (realMemUsd - memoryCached - memoryBuffer));
            }

            //获取磁盘和内存描述信息
            List<Entry> memoryAndDiskDescs = snmpWalk(snmp, target, SnmpOID.DISK_DESC.getOID());


            Map<String, String> tarCollect = memoryAndDiskDescs.stream().filter(obj -> DIR_LIST.contains(obj.getValue())).collect(Collectors.toMap(Entry::getValue, Entry::getIndex));
            Map<String, String> bufferCollect = snmpWalk(snmp, target, SnmpOID.MEMORY_DISK_ALLOCATION_UNITS.getOID()).stream().collect(Collectors.toMap(Entry::getIndex, Entry::getValue));
            Map<String, String> useCollect = snmpWalk(snmp, target, SnmpOID.USED_MEMORY_DISK_COUNT.getOID()).stream().collect(Collectors.toMap(Entry::getIndex, Entry::getValue));

            AtomicLong usedTotal = new AtomicLong(0L);
            tarCollect.keySet().stream().forEach(obj->{
                String index = tarCollect.get(obj);
                String bufferSize = bufferCollect.get(index);
                String used = useCollect.get(index);

                if (StringUtils.isNotBlank(bufferSize)&&StringUtils.isNotBlank(used)){
                    usedTotal.addAndGet(Long.parseLong(used) * Long.parseLong(bufferSize) / 1024);
                }

                log.info("dir:{}, bufferSize:{}, used:{}",obj,bufferSize,used);

            });

            //获取总磁盘和内存簇数目
//            String total = snmpWalk(snmp, target, SnmpOID.TOTAL_MEMORY_DISK_COUNT.getOID()).stream().collect(Collectors.toMap(Entry::getIndex, Entry::getValue)).get(index);


            info.setCpu(100 - cpuUsage + "%");
            info.setMemory(getFormantMem(realMemUsd));
            info.setDisk(getFormantMem(usedTotal.get()));

            snmp.close();
        } catch (Exception e) {
            log.error("poll snmp fail:"+e.getMessage(),e);
        }

        return info;

    }


    private static Long getSNMPData(Snmp snmp, CommunityTarget target, String oid) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);

        ResponseEvent event = snmp.send(pdu, target);
        PDU response = event.getResponse();

        if (response != null) {
            Variable variable = response.get(0).getVariable();
            return variable.toLong();
        }

        return 0L;
    }

    private static List<Entry> snmpWalk(Snmp snmp, CommunityTarget target, String oid) throws IOException {

        List<Entry> result = new ArrayList<>();
        try {
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(oid)));
            pdu.setType(PDU.GETNEXT);

            boolean matched = true;

            while (matched) {

                ResponseEvent responseEvent = snmp.send(pdu, target);
                if (responseEvent == null || responseEvent.getResponse() == null) {
                    break;
                }
                PDU response = responseEvent.getResponse();


                String nextOid = null;
                List<? extends VariableBinding> variableBindings = response.getVariableBindings();
                for (VariableBinding variableBinding : variableBindings) {
                    Variable variable = variableBinding.getVariable();
                    nextOid = variableBinding.getOid().toDottedString();
                    // Fix: 239.139未安装snmp，oid和nextOid是相等的，导致死循环
                    if (nextOid.equals(oid)) {

                        matched = false;
                    }
                    //如果不是这个节点下的oid则终止遍历，否则会输出很多，直到整个遍历完。
                    if (!nextOid.startsWith(oid) && checkWalkFinished(new OID(oid), pdu, variableBinding)) {
                        matched = false;
                        break;
                    }
                    result.add(new Entry(oid, nextOid, variable.toString()));
                }
                pdu.clear();
                pdu.add(new VariableBinding(new OID(nextOid)));

            }
        } catch (Exception e) {
            log.error("snmp walk error:" + e.getMessage(), e);
        }

        return result;

    }


    public static class Entry {
        private String sourceOid;
        private String nextOid;
        private String index;
        private String value;

        public String getSourceOid() {
            return sourceOid;
        }

        public String getNextOid() {
            return nextOid;
        }

        public String getIndex() {
            return index;
        }

        public String getValue() {
            return value;
        }

        Entry(String sourceOid, String nextOid, String value) {
            this.sourceOid = sourceOid;
            this.nextOid = nextOid;
            parseIndex(sourceOid, nextOid);
            this.value = value;
        }

        private void parseIndex(String sourceOid, String nextOid) {
            String index = "";
            if (StringUtils.isNotBlank(sourceOid) && StringUtils.isNotBlank(nextOid) && !sourceOid.equals(nextOid)
                    && nextOid.compareTo(sourceOid) > 0) {
                index = nextOid.replace(sourceOid + ".", "");
            }

            this.index = index;
        }
    }


    private static boolean checkWalkFinished(OID targetOID, PDU pdu, VariableBinding vb) {
        boolean finished = false;
        if (pdu.getErrorStatus() != 0) {
            log.error("[true] responsePDU.getErrorStatus() != 0 ");
            log.error(pdu.getErrorStatusText());
            finished = true;
        } else if (vb.getOid() == null) {
            log.error("[true] vb.getOid() == null");
            finished = true;
        } else if (vb.getOid().size() < targetOID.size()) {
            //log.error("[true] vb.getOid().size() < targetOID.size()");
            finished = true;
        } else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
            //log.debug("[true] targetOID.leftMostCompare() != 0");
            finished = true;
        } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
            log.error("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
            finished = true;
        } else if (vb.getOid().compareTo(targetOID) <= 0) {
            log.info(vb.toString() + " <= " + targetOID);
            finished = true;
        } else if (vb.getVariable().toString().equals("noSuchObject")) {
            log.error(targetOID.toString() + " noSuchObject ");
            finished = true;
        }
        return finished;
    }

}

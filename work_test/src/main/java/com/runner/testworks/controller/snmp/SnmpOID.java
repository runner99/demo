package com.runner.testworks.controller.snmp;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * soc
 * 2019/4/11 16:04
 * snmp OID枚举
 *
 * @author lanhaifeng
 * @since
 **/
@Getter
public enum SnmpOID {
	IDLE_CPU("1.3.6.1.4.1.2021.11.11.0", SnmpRequestType.GET,"percent","空闲cpu"),//UCD-SNMP-MIB::ssCpuIdle.0 = INTEGER: 28
	TOTAL_MEMORY("1.3.6.1.4.1.2021.4.5.0", SnmpRequestType.GET,"kb","总内存"),//UCD-SNMP-MIB::memTotalReal.0 = INTEGER: 32703428 kB
	USED_MEMORY_DISK_COUNT("1.3.6.1.2.1.25.2.3.1.6", SnmpRequestType.WALK,"int","已用内存和磁盘簇的数目"),//HOST-RESOURCES-MIB::hrStorageUsed.1 = INTEGER: 27935068
	TOTAL_MEMORY_DISK_COUNT("1.3.6.1.2.1.25.2.3.1.5", SnmpRequestType.WALK,"int","总内存和总磁盘簇的数目"),//HOST-RESOURCES-MIB::hrStorageSize.1 = INTEGER: 32703428
	MEMORY_DISK_ALLOCATION_UNITS("1.3.6.1.2.1.25.2.3.1.4", SnmpRequestType.WALK,"byte","内存和磁盘簇的大小"),//HOST-RESOURCES-MIB::hrStorageAllocationUnits.1 = INTEGER: 1024 Bytes
	RESOURCE_TYPE("1.3.6.1.2.1.25.2.3.1.2", SnmpRequestType.WALK,"String","设备类型"),//HOST-RESOURCES-MIB::hrStorageType.1 = OID: HOST-RESOURCES-TYPES::hrStorageRam
	DISK_DESC("1.3.6.1.2.1.25.2.3.1.3", SnmpRequestType.WALK,"String","存储设备描述"),//HOST-RESOURCES-MIB::hrStorageDescr.36 = STRING: /dev/shm
	FLOW_IN("1.3.6.1.2.1.2.2.1.10", SnmpRequestType.WALK,"byte","接收流量"),//IF-MIB::ifInOctets.1 = Counter32: 161367553
	FLOW_OUT("1.3.6.1.2.1.2.2.1.16", SnmpRequestType.WALK,"byte","发送流量"),//IF-MIB::ifOutOctets.1 = Counter32: 164441438
	NETWORK_CARD(".1.3.6.1.2.1.2.2.1.2", SnmpRequestType.WALK,"String","网卡描述"),//IF-MIB::ifDescr.1 = STRING: lo
	SYSTEM_NAME(".1.3.6.1.2.1.1.5.0", SnmpRequestType.GET,"String","机器名"),//SNMPv2-MIB::sysName.0 = STRING: MCDCAP
	MEM_AVAIL_REAL(".1.3.6.1.4.1.2021.4.6.0", SnmpRequestType.GET,"kb","可用内存"),//UCD-SNMP-MIB::memTotalReal.0 = INTEGER: 15960312 kB
	MEM_BUFFER(".1.3.6.1.4.1.2021.4.14.0", SnmpRequestType.GET,"kb","mem buffer"),//UCD-SNMP-MIB::memBuffer.0 = INTEGER: 490284 kB
	MEM_CACHED(".1.3.6.1.4.1.2021.4.15.0", SnmpRequestType.GET,"kb","mem cached"),//UCD-SNMP-MIB::memCached.0 = INTEGER: 10110896 kB
	;


	private String OID;
	private SnmpRequestType requestType;
	private String unit;
	private String desc;

	private SnmpOID(String OID, SnmpRequestType requestType, String unit, String desc) {
		this.OID = OID;
		this.requestType = requestType;
		this.unit = unit;
		this.desc = desc;
	}

	enum SnmpRequestType{
		GET,WALK
	}

	public static class SnmpConstant{
		static {
			init();
		}

		public static List<String> excludeNetworkCardList;

		private static void init(){
			excludeNetworkCardList = new ArrayList<>();
			excludeNetworkCardList.add("lo");
		}
	}
}

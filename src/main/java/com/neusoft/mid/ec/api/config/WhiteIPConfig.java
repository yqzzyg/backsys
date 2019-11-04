package com.neusoft.mid.ec.api.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class WhiteIPConfig {

	private static Logger LOGGEER = Logger.getLogger(WhiteIPConfig.class);

	/**
	 * @author zhao.zhenyu
	 * @description 该方法已停用，已改为读取文件方式遍历白名单ip，新方法在util/ScheduledTasks.java里,该方法可用于记录各个ip的说明。
	 * @date 2018年11月14日 下午3:31:07
	 * @param userIP
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean getWhiteIP(String userIP) {

		boolean flag = false;

		List ips = new ArrayList<>();

		ips.add("127.0.0.1");

		// 安全检查IP
		ips.add("47.106.117.196");
		ips.add("101.207.134.153");

		// 内部IP
		ips.add("10.10.126");

		// 系统IP段
		ips.add("10.102.64");
		ips.add("223.87.179");

		LOGGEER.info("====将要与白名单校验的IP为：" + userIP);

		for (int i = 0; i < ips.size(); i++) {
			if (userIP.contains(ips.get(i).toString())) {
				flag = true;
				break;
			}
		}

		return flag;

	}

}

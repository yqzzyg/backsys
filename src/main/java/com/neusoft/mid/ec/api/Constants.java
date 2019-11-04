package com.neusoft.mid.ec.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {
	
	public static final String ACCESS_TOKEN_KEY = "Ronsl^5asdf mxa,7e81d";

	public static final String DEFAULT_USER = "admin";

	public static final int IDENTIFY_CODE_ERROR = 1;

	public static final String MSG_PREFIX = "MSG";

	public static final String CHARSET = "utf-8";

	public static final String TOKEN_PREFIX = "LOGIN:TOKEN";
	
	public static HashMap<String,String> oprMap =new HashMap<>();
	
	static{
		oprMap.put("000", "登录系统");
		oprMap.put("001", "退出登录");
		oprMap.put("101", "保存并分析信息");
		oprMap.put("102", "修改竞品信息");
		oprMap.put("103", "删除竞品信息");
		oprMap.put("104", "导出竞品信息");
		oprMap.put("105", "保存竞品信息");
		oprMap.put("106", "分析竞品信息");
		
	}

	/**
	 * 管理系统的验证码
	 */
	public static final String MANAGER_CODE = "Mng:code";


	/**
	 * 登录次数限制
	 */

	public static final String LOG_LIMIT_PREFIX = "LOG:LIMIT:NUMS";
	/**
	 * 白名单缓存key
	 */
	public static final String WHITE_IP_PREFIX_API = "WHITE:IP:CONF:API";
	
	/**
	 * 敏感词缓存key
	 */
	public static final String SENSITIVE_KEY = "SENSITIVE:KEY";
	
	public static  List SensitiveWordsList = new ArrayList<>();
	
	/**
	      *  河南电子社保卡相关
	 */
    //第三方BIZID-标识
    public static final String BIZID = "SDSJJ";
    //AESKEY-加密秘钥
    public static final String AESKEY = "32d64cff1ab04622";
    //RSAPUBLICKEY-接入方测试公钥
    public static final String RSAPUBLICKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlkLiRLjTckjG3zjDdc6n3es9kWaqgQ5hfUiHkjYdOKEmTVD/QkuLHWWtaRyAMKnzdvfbsT4KbuHXneiGToXekEgXCl8gSxkdDwhlW4UkfqVu9HOFIfWeje0qBJtbvPSnFiBQP2l1g0S0d2igfNSH2cFSD+3Hh6Y7aNhe0xEnV4bOSseBH10HWrGqdH2aejWgr0ZWaTbBH6DX3uunte6iWk7kdroXBiEVYEzBp93a0Wh4gdfPe4Ii/tuhaV2ef8ISRhySp7UNY1el9RccpH4vrRw8D9Qc9Czyb7dpYkTXejjZbeH6ww1Pyr3MSPpX+Gxkzoja1mlN20xqc+DkWswIHwIDAQAB";
    //RSAPRIVATEKEY-接入方测试私钥
    public static final String RSAPRIVATEKEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCWQuJEuNNySMbfOMN1zqfd6z2RZqqBDmF9SIeSNh04oSZNUP9CS4sdZa1pHIAwqfN299uxPgpu4ded6IZOhd6QSBcKXyBLGR0PCGVbhSR+pW70c4Uh9Z6N7SoEm1u89KcWIFA/aXWDRLR3aKB81IfZwVIP7ceHpjto2F7TESdXhs5Kx4EfXQdasap0fZp6NaCvRlZpNsEfoNfe66e17qJaTuR2uhcGIRVgTMGn3drRaHiB1897giL+26FpXZ5/whJGHJKntQ1jV6X1Fxykfi+tHDwP1Bz0LPJvt2liRNd6ONlt4frDDU/KvcxI+lf4bGTOiNrWaU3bTGpz4ORazAgfAgMBAAECggEANH2c6YDfbLb7shL0RP0yKxX4ZgjFW1wYtllV8r+wqY2yAaRf51rYeCaC04s/RMC56j9foaUYo++FEQaflGKzgxeL5PWiJFnbitrV/YifXRaRkheg1GInR1EoMn/LJlEY5WPYZK6EfJzNpkEIf1MHOgyMjD/2Se7KJjAfv//oDGzj439KkVC4XbLTm4LBdeXE507pm/k598uovb1rp249q08X4sbDpDb70ZlqZ6GA8xF6OPA+MiUNW77jS/9ElZ7CB40dlINd7U7NOUCeTCCpFMUbC0RnAi7kvbZrMVz0G5Wwed2FWgRYm/vzubQgxQJ6/9qn3oJFLQXMtWk1qxovwQKBgQDRhtZEOIf40UTiFmj9XOT4jZFAZdBzsAMXgLg/x1v2heFNJHm6wC/KgC4C+qsN1hX4Q3WBbA40gfqJ1j2cQB6FvOLgccQ7D77QlvOppUQ09nAwf7hQofuzdAOGXj8BIzx8tQtt0ldQzaPTTpYFlOLzycwSgKn+tSA3p96jXuCz8QKBgQC3luNfHJpmVTyNKpg8rIUdFUCxnJVP6C8zU0kPoSwqPF39dXejsVPCsecDi7h8YC3JjEt1aqOYPljRJAl7VuHhlSeofI4xEzP9ECg9eMOGw047A9kgzjOVeusMae8CdTOlKe68FrAyYJejh4KNdiynRQmgzgqW97Y7TdUWx6JNDwKBgBMkEUDsNSe63mMVDwROTzCRpjpO0ssuwimMfch9yt3rfxiQA3rXAcarDQkCo0vBSgKDDyICoA2V7jBXXQuAJAmtpsBARF/s4U6m652M1n1MIhWcJVdgZOEQ8R1+UJ0m/eialW+z7nwYPSYZoMTjxBPnVHhAPsdg/o93C3S7PyXRAoGAQ8n0RN9j55tbk2iB9A1jzXxWef6uZ2X9X79F//5sN2A4+GjSd1/35LEZ8wY5ZbuBzKUAC+gHZiTHWNosoh1PpKMVlLIBMv+7N5sqsKAR2oXtMf9WwjVBeNV9SwUmmsq5+ieEnnDmOIPQGjLkMuXK0Sy9xGGwKVRoNnej0hh+xmMCgYBgE4K57a2ZFhctjzsg8KILm+xR2fYBMBIh+JPPGtXurqtxQnVGB9/wobPgVTPPatNFuYHxckRxnDd/LYZ24gdQA1Ioz/Q6cVkdpKDhsQsFhz/qkpkAgjjUiLXalyXirBq9j/MiJSub/HJThhGZFFmQycZ5KFkDb/HeltgqMBissg==";
    //河南省电子社保卡平台地址
    public static final String HNSESSCPLTFMURL = "http://222.143.25.143:80/cardcommand/";
    
    public static final String ELEC_CARD_URL = "https://yshb.hnzwfw.gov.cn:7778/staging/html/static/html/hnessc.html#/hnessc/home";
    

	
}

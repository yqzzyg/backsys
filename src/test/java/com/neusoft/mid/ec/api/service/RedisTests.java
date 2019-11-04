/*


package com.neusoft.mid.ec.api.service;

import com.neusoft.mid.ec.api.Application;
import com.sun.corba.se.impl.activation.ServerMain;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedisTests {

    @Test
    public  void testxml() throws IOException {

        System.out.println(System.currentTimeMillis());
        System.out.println(DateUtils.addYears(new Date(),-1).getTime());
        String a = null;
        System.out.println(String.valueOf(a));


        System.out.println(StringEscapeUtils.escapeXml("{\"AAC002\": \"410403196908075542\",\"AAC003\": \"孙俊美\"}"));
        
        System.out.println(StringEscapeUtils.unescapeXml("<?xml version='1.0' encoding='ISO-8859-1'?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns0:queryResponse xmlns:ns0=\"http://service.server.employment.neusoft.com\"><ns0:queryresult>&lt;?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "\n" +
                "&lt;response>\n" +
                "  &lt;head>\n" +
                "    &lt;function>SERCH_JYDJ&lt;/function>\n" +
                "    &lt;code>-41&lt;/code>\n" +
                "    &lt;err>&#x5165;&#x53c2;&#x683c;&#x5f0f;&#x9519;&#x8bef;!&lt;/err>\n" +
                "  &lt;/head>\n" +
                "&lt;/response>"));
        System.out.println(removeHtmlTag(StringEscapeUtils.unescapeHtml("  {\n" +
                "        \"办事依据\": \"1．中华人民共和国就业促进法（2007年8月30日，中华人民共和国主席令第70号）；\\r\\n2．河南省就业促进条例（2009年3月26日，省十一届人大常务委员会第8次会议审议通过）；\\r\\n3．财政部关于印发《普惠金融发展专项资金管理办法》的通知（财金〔2016〕85号）；\\r\\n4．财政部人力资源和社会保障部中国人民银行关于进一步做好创业担保贷款财政贴息工作的通知（财金〔2018〕22号）；\\r\\n5．河南省人民政府关于进一步做好新形势下就业创业工作的实施意见（豫政〔2015〕59号）；\\r\\n6．河南省人民政府关于做好当前和今后一段时期就业创业工作的实施意见（豫政〔2017〕33号）；\\r\\n7．中国人民银行郑州中心支行河南省财政厅河南省人力资源社会保障厅转发中国人民银行财政部人力资源社会保障部关于实施创业担保贷款支持创业就业工作的通知（郑银〔2016〕246号）；\\r\\n8．河南省人力资源和社会保障厅河南省财政厅中国人民银行郑州中心支行关于印发《河南省小额担保贷款操作规程（试行）》的通知（豫人社就业〔2014〕45号）。\",\n" +
                "        \"办事要件\": null,\n" +
                "        \"办事流程\": \"&lt;p&rt;1．申请。线上申请：申请人通过网上受理平台（手机APP）实名注册、提交完善贷款资料。线下申请：申请人到受理窗口递交完善贷款资料。&nbsp;&lt;/p&rt;&lt;p&rt;2．审核受理（及时审核）。经办银行查询申请人及相关人员征信，担保机构查询所持营业执照信息情况和社保信息。核查申请人身份是否符合国家政策及相关业务规定。审核通过的予以受理，不符合条件的告知申请人说明原因。&nbsp;&lt;/p&rt;&lt;p&rt;3．调查（10个工作日内）。调查人员对申请人创业项目的经营场所、经营规模、带动就业等情况实地核实。 &nbsp;&lt;/p&rt;&lt;p&rt;4．评审（每周）。根据贷款申请材料、调查核实情况，对贷款进行集体研究评审，对符合规定条件的确定额度、期限、贴息等结果。&nbsp;&lt;/p&rt;&lt;p&rt;5．公示（2天）。及时对评审会审批通过人员进行公示。&nbsp;&lt;/p&rt;&lt;p&rt;&nbsp;6．承诺担保（当日办理）。签订相关担保合同（协议）等。&nbsp;&lt;/p&rt;&lt;p&rt;7．贷款发放（每周及时放款）。经办银行及时办理贷款发放手续，并将发放情况及时反馈担保机构。&nbsp;&lt;/p&rt;&lt;p&rt;8．贷后管理（放款后至到期还款）。相关机构做好借款人的贷后回访和记录工作。经办银行对创业担保贷款借款人资金使用情况跟踪管理，确保借款人严格按规定用途使用贷款。&nbsp;&lt;/p&rt;&lt;p&rt;9．到期回收。一般要求借款人贷款到期提前3日将借款本金存入其在银行的贷款发放账户内。担保机构和经办银行提醒借款人还款。经办银行对到期还款情况做好登记并及时反馈担保机构。&nbsp;&lt;/p&rt;\",\n" +
                "        \"适用对象\": \"&lt;p&rt;&lt;span lang=\\\"EN-US\\\"&rt;1&lt;/span&rt;．个人创业：在法定劳动年龄内（城乡参加新农保的人员男女劳动年龄上线均应按照新农保享受待遇规定的&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;60&lt;/span&rt;岁执行），具有完全民事行为能力&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;,&lt;/span&rt;诚实守信，且自主创业时不在机关事业或其他单位就业的各类创业人员。具体包括：城镇登记失业人员、就业困难人员（含残疾人）、复员转业退役军人、刑满释放人员、高校毕业生（含大学生村官和留学回国学生）、化解过剩产能企业职工和失业人员、返乡创业农民工、自主创业农民、网络商户、建档立卡贫困人口。符合中央、省财政贴息条件的个人创业贷款最高额度为&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;10&lt;/span&rt;万元&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;,&nbsp;&lt;/span&rt;期限最长为&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;3&lt;/span&rt;年。&lt;/p&rt;&lt;p&rt;&lt;span lang=\\\"EN-US\\\" style=\\\"background-color: transparent;\\\"&rt;2&lt;/span&rt;&lt;span style=\\\"background-color: transparent;\\\"&rt;．&lt;/span&rt;&lt;span style=\\\"background-color: transparent;\\\"&rt;合伙创业：符合第一项条件的人员&lt;/span&rt;&lt;span lang=\\\"EN-US\\\" times=\\\"\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\" style=\\\"background-color: transparent;\\\"&rt;,&lt;/span&rt;&lt;span style=\\\"background-color: transparent;\\\"&rt;并具备创业条件的人员合伙创业，且持有《合伙企业营业执照》或合伙协议。合伙创业贷款最高额度为&lt;/span&rt;&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\" style=\\\"background-color: transparent;\\\"&rt;50&lt;/span&rt;&lt;span style=\\\"background-color: transparent;\\\"&rt;万元&lt;/span&rt;&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\" style=\\\"background-color: transparent;\\\"&rt;,&nbsp;&lt;/span&rt;&lt;span style=\\\"background-color: transparent;\\\"&rt;期限最长为&lt;/span&rt;&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\" style=\\\"background-color: transparent;\\\"&rt;3&lt;/span&rt;&lt;span style=\\\"background-color: transparent;\\\"&rt;年。&lt;/span&rt;&lt;/p&rt;&lt;p&rt;&lt;span lang=\\\"EN-US\\\"&rt;3&lt;/span&rt;．组织创业：组织符合第一项条件的人员&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;,&lt;/span&rt;并签订&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;1&lt;/span&rt;年以上的劳动合同的经济实体。组织创业贷款最高额度为&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;50&lt;/span&rt;万元&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;,&nbsp;&lt;/span&rt;期限最长为&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;3&lt;/span&rt;年。&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;&lt;o:p&rt;&lt;/o:p&rt;&lt;/span&rt;&lt;/p&rt;&lt;p&rt;&lt;span lang=\\\"EN-US\\\"&rt;4&lt;/span&rt;．小微企业：一年内新招用符合创业担保贷款申请条件的人员数量达到企业现有在职职工人数&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;25%(&lt;/span&rt;超过&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;100&lt;/span&rt;人的企业达到&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;15%)&lt;/span&rt;、并与其签订&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;1&lt;/span&rt;年以上劳动合同。小微企业是指符合《中小企业划型标准规定》（工信部联企业〔&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;2011&lt;/span&rt;〕&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;300&lt;/span&rt;号）规定的小型、微型企业。小微企业创业贷款最高为&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;200&lt;/span&rt;万元&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;,&nbsp;&lt;/span&rt;期限最长为&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;2&lt;/span&rt;年。&lt;span lang=\\\"EN-USTimes\\\" new=\\\"\\\" roman\\\",serif;=\\\"\\\" mso-font-kerning:0pt'=\\\"\\\"&rt;&lt;o:p&rt;&lt;/o:p&rt;&lt;/span&rt;&lt;/p&rt;&lt;p&rt;&lt;/p&rt;&lt;p&rt;&lt;span lang=\\\"EN-US\\\"&rt;&nbsp; &nbsp;&nbsp;&lt;/span&rt;对以上符合财政贴息条件的贷款按规定给予贴息。各省辖市省直管县市、县（市）可结合本地实际，自行制定贴息政策，扩大支持对象范围，提高各类人群贷款额度。&lt;/p&rt;\"\n" +
                "      }").replaceAll(Matcher.quoteReplacement("&rt;"),">")));

        System.out.println(StringEscapeUtils.unescapeJavaScript("&rt;"));
        System.out.println(StringEscapeUtils.escapeXml("<>"));


    }

    public static String removeHtmlTag(String htmlStr) {
        //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
        //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
        //定义HTML标签的正则表达式
        String regEx_html = "<[^>]+>";
        //定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        String regEx_special = "\\&[a-zA-Z]{1,10};";

        //1.过滤script标签
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        //2.过滤style标签
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        //3.过滤html标签
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        //4.过滤特殊标签
        Pattern p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
        Matcher m_special = p_special.matcher(htmlStr);
        htmlStr = m_special.replaceAll("");

        return htmlStr;
    }


    @Test
    public  void testRedis2() throws IOException {

        // 添加集群的服务节点Set集合
        Set<HostAndPort> hostAndPortsSet = new HashSet<HostAndPort>();
        // 添加节点
        hostAndPortsSet.add(new HostAndPort("172.31.103.11", 7000));
        hostAndPortsSet.add(new HostAndPort("172.31.103.11", 7001));
        hostAndPortsSet.add(new HostAndPort("172.31.103.11", 7002));
        hostAndPortsSet.add(new HostAndPort("172.31.103.11", 7003));
        hostAndPortsSet.add(new HostAndPort("172.31.103.11", 7004));
        hostAndPortsSet.add(new HostAndPort("172.31.103.11", 7005));

     

        // Jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(100);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(500);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);
        JedisCluster jedis = new JedisCluster(hostAndPortsSet, jedisPoolConfig);
        System.out.println(jedis.set("111","123123123"));
        System.out.println(jedis.get("111"));
    }

}


*/

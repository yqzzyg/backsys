package com.neusoft.mid.ec.api.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.domain.UserLog;
import com.neusoft.mid.ec.api.serviceInterface.userlog.UserLogService;
import com.neusoft.mid.ec.api.util.TableNameUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class RocketMQListener implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${rocketmq.name-server}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.name}")
    private String pushConsumer;
    @Value("${rocketmq.consumer.group}")
    private String topic;
    @Autowired
    private UserLogService userLogService;
    /**
     * 消费者实体对象
     */
    private DefaultMQPushConsumer consumer;

    /**
     * 通过构造函数 实例化对象
     *  异步记录操作日志
     */
    public void messageListener() throws MQClientException {
        consumer = new DefaultMQPushConsumer(pushConsumer);
        consumer.setNamesrvAddr(namesrvAddr);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumer.subscribe(topic, "*");
        // //注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            // msgs中只收集同一个topic，同一个tag，并且key相同的message
            // 会把不同的消息分别放置到不同的队列中
            try {
                for (Message msg : msgs) {
                    //消费者获取消息 这里只输出 不做后面逻辑处理
                    String body = new String(msg.getBody(), "utf-8");
                    logger.info("MQ消息解密, body=" + body);
                    JSONObject jo = JSON.parseObject(body);
                    logger.info("入参："+ jo);
                    UserLog userLog = JSONObject.parseObject(JSON.toJSONString(jo.get("userLog")),UserLog.class);
                    RequestInfo header = JSONObject.parseObject(JSON.toJSONString(jo.get("header")),RequestInfo.class);
                    String interfaceName = getInterfaceName(userLog.getInterfaceUrl());
                    userLog.setInterfaceName(StringUtils.isBlank(interfaceName)?null:interfaceName.substring(0,interfaceName.indexOf(",")));
                    userLog.setInterfaceCode(StringUtils.isBlank(interfaceName)?null:interfaceName.substring(interfaceName.indexOf(",")+1));
                    userLog.setDescription("用户：["+header.getName()==null?"":header.getName()+"]执行了：["+userLog.getInterfaceName()+"接口]");
                    userLog.setTableName(TableNameUtil.getTableName());
                    userLog.setUserName(header.getName());
                    userLog.setSysid(header.getSysid());
                    userLog.setFuncid(header.getFuncid());
                    userLog.setToserverid(header.getToserverid());
                    userLog.setToken(header.getToken());
                    userLog.setIdtype(header.getIdtype());
                    userLog.setIdno(header.getIdno());
                    userLog.setFromserverid(header.getFromserverid());
                    userLog.setVersion(header.getVersion());
                    getDept(userLog);
                    userLogService.insertLog(userLog);
                }
            } catch (Exception e) {
                logger.error("接受MQ消息，处理数据失败：",e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        logger.info("消费者 启动成功=======");
    }

    @Override
    public void run(String... args) throws Exception {
        this.messageListener();
    }

    /**
     *  根据接口路径，获取部门名称、编码
     * @param userLog
     */
    public void getDept(UserLog userLog){
        if (userLog.getInterfaceUrl().contains("/gov_api/edu/query")) {
            userLog.setDeptCode("1001");
            userLog.setDeptName("教育厅");
            userLog.setType("query");
        }else if (userLog.getInterfaceUrl().contains("/gov_api/edu/apply")) {
            userLog.setDeptCode("1001");
            userLog.setDeptName("教育厅");
            userLog.setType("apply");
        }else if(userLog.getInterfaceUrl().contains("/gov_api/civiladministration")){
            userLog.setDeptCode("1002");
            userLog.setDeptName("民政厅");
        }else if (userLog.getInterfaceUrl().contains("/gov_api/health")) {
            userLog.setDeptCode("1003");
            userLog.setDeptName("卫健厅");
        }else if (userLog.getInterfaceUrl().contains("/gov_api/hz/apply")) {
            userLog.setDeptCode("1004");
            userLog.setDeptName("公安厅");
            userLog.setType("apply");
        }else if (userLog.getInterfaceUrl().contains("/gov_api/hz/query")) {
            userLog.setDeptCode("1004");
            userLog.setDeptName("公安厅");
            userLog.setType("query");
        }else if (userLog.getInterfaceUrl().contains("/gov_api/zj/query")) {
            userLog.setDeptCode("1005");
            userLog.setDeptName("住建厅");
            userLog.setType("query");
        }else if (userLog.getInterfaceUrl().contains("/gov_api/judicial/apply")
                || userLog.getInterfaceUrl().contains("/gov_api/assess/query/getAppAssess")) {
            userLog.setDeptCode("1006");
            userLog.setDeptName("云证厅");
            userLog.setType("apply");
        } else if (userLog.getInterfaceUrl().contains("/gov_api/judicial/query")
                || userLog.getInterfaceUrl().contains("/gov_api/assess/query/getAppAssessType")) {
            userLog.setDeptCode("1006");
            userLog.setDeptName("云证厅");
            userLog.setType("query");
        } else if (userLog.getInterfaceUrl().contains("/gov_api/rs/jy/apply")) {
            userLog.setDeptCode("1007");
            userLog.setDeptName("就业");
            userLog.setType("apply");
        } else if (userLog.getInterfaceUrl().contains("/gov_api/rs/jy/query")) {
            userLog.setDeptCode("1007");
            userLog.setDeptName("就业");
            userLog.setType("query");
        } else if (userLog.getInterfaceUrl().contains("/gov_api/rs/apply")) {
            userLog.setDeptCode("1008");
            userLog.setDeptName("社保");
            userLog.setType("apply");
        } else if (userLog.getInterfaceUrl().contains("/gov_api/rs/query")) {
            userLog.setDeptCode("1008");
            userLog.setDeptName("社保");
            userLog.setType("query");
        } else if (userLog.getInterfaceUrl().contains("/gov_api/rs/sbk/apply")) {
            userLog.setDeptCode("1009");
            userLog.setDeptName("社保卡");
            userLog.setType("apply");
        } else if (userLog.getInterfaceUrl().contains("/gov_api/rs/sbk/query")) {
            userLog.setDeptCode("1009");
            userLog.setDeptName("社保卡");
            userLog.setType("query");
        } else if (userLog.getInterfaceUrl().contains("/gov_api/tax")) {
            userLog.setDeptCode("1010");
            userLog.setDeptName("税务");
        } else if (userLog.getInterfaceUrl().contains("/gov_api/travel")) {
            userLog.setDeptCode("1011");
            userLog.setDeptName("旅游");
        } else if (userLog.getInterfaceUrl().contains("/gov_api/rs/bat")) {
            userLog.setDeptCode("1012");
            userLog.setDeptName("养老生存认证");
        }else if (userLog.getInterfaceUrl().contains("/gov_api/reserved/fund")
                || userLog.getInterfaceUrl().contains("/gov_api/province/reserved/fund")){
            userLog.setDeptCode("1013");
            userLog.setDeptName("公积金");
        }
    }
    /**
     *  根据接口路径映射接口名称、编号
     * @param str
     * @return
     */
    public static String getInterfaceName(String str){
        JSONObject obj = new JSONObject();
        //住建  接口编号以0-29
        obj.put("/gov_api/zj/query/architectInfo","二级建造师查询,0");
        obj.put("/gov_api/zj/query/architectWork","建筑施工特种作业,1");
        obj.put("/gov_api/zj/query/architectCard","安全生产考核合格证书,2");
        //旅游  接口编号以30-49
        obj.put("/gov_api/travel/ospNursingGetdaysponum","景区日停留人数接口OSP_NURSING_GETDAYSPONUM,30");
        obj.put("/gov_api/travel/getDaySpotNumber","景区日停留人数接口,31");

        //养老生存认证  接口编号以60-69
        obj.put("/gov_api/rs/bat/saveFaceVfyResult","养老生存认证保存比对信息接口,60");
        //评价查询接口  以 70-79
        obj.put("/gov_api/assess/query/getAppAssessType","查询评价信息,70");
        obj.put("/gov_api/assess/query/getAppAssess","好差评接口,71");
        //验证码  以80-89
        obj.put("/gov_api/verify/getVerifyCode","生成验证码,80");
        obj.put("/gov_api/verify/verifyCode","校验验证码,81");
        //儿童疫苗  以90-99
        obj.put("/gov_api/childrenHealth/getAvailableReservationSchedule","儿童疫苗,90");

        //教育厅查询  接口编号以100-199
        obj.put("/gov_api/edu/query/getPthcj","教育-普通话成绩查询接口,101");
        obj.put("/gov_api/edu/query/getDegreeCertificate","教育-学位证书认证查询接口,102");
        obj.put("/gov_api/edu/query/xxshCheck","教育-小学学校名查询接口,103");
        obj.put("/gov_api/edu/query/ptgzCheck","教育-普通高中学校名单查询接口,104");
        obj.put("/gov_api/edu/query/yeyshCheck","教育-幼儿园学校名单查询接口,105");
        obj.put("/gov_api/edu/query/zswglxscheck/getInfo","教育-招收国际留学生资格高等学校名单查询,106");
        obj.put("/gov_api/edu/query/zwhzbxjgxx/getInfo","教育-中外及港澳台合作办学机构名单查询,107");
        obj.put("/gov_api/edu/query/hwkzxyxx/getInfo","教育-海外孔子学院名单查询,108");
        obj.put("/gov_api/edu/query/czmd/getInfo","教育-初级中学学校名单查询,109");
        obj.put("/gov_api/edu/query/zzmd/getInfo","教育-中等专业学校名单查询,100");
        obj.put("/gov_api/edu/query/gjgpcglxmd/getInfo","教育-国家公派出国留学录取信息查询,101");
        obj.put("/gov_api/edu/query/teacherZigeCheck/getInfo","教育-特岗教师服务期满证书查询,102");
        obj.put("/gov_api/edu/query/mbptgxCheck/getInfo","教育-民办普通高校名单信息查询,103");
        obj.put("/gov_api/edu/query/zwjygathzbxxmCheck/getInfo","教育-中外及与港澳台合作办学项目名单信息查询,104");
        obj.put("/gov_api/edu/query/queryPZCJ","高考成绩查询,105");
        obj.put("/gov_api/edu/query/queryPZL","高考录取查询,106");
        //公安厅申报  接口编号以200-299
        obj.put("/gov_api/hz/apply/changeHouseHolder","户政申报_变更户主或与户主关系,200");
        obj.put("/gov_api/hz/apply/changeNation","户政申报_变更民族成分,201");
        obj.put("/gov_api/hz/apply/changeCulture","户政申报_变更文化程度、婚姻状况,202");
        obj.put("/gov_api/hz/apply/changeSex","户政申报_变更性别,203");
        obj.put("/gov_api/hz/apply/changeName","户政申报_变更姓名,204");
        obj.put("/gov_api/hz/apply/logoutHouseholdRegister","户政申报_出国（境）定居注销户口,205");
        obj.put("/gov_api/hz/apply/birthRregistration","户政申报_出生登记（持省内新版出生证）,206");
        obj.put("/gov_api/hz/apply/graduatesMoveIn","户政申报_大中专院校毕业学生迁入,207");
        obj.put("/gov_api/hz/apply/admissionStudentsMoveOut","户政申报_大中专院校录取学生迁出,208");
        obj.put("/gov_api/hz/apply/admissionStudentsMoveIn","户政申报_大中专院校录取学生迁入,209");
        obj.put("/gov_api/hz/apply/children","子女投靠父母,210");
        obj.put("/gov_api/hz/apply/replacement","准迁证补发,211");
        obj.put("/gov_api/hz/apply/households","户政申报_转业、复员、退伍军人入户,212");
        obj.put("/gov_api/hz/apply/emancipist","户政申报_刑满释放人员恢复户口,213");
        obj.put("/gov_api/hz/apply/lostReplenishment","户口本遗失补办,214");
        obj.put("/gov_api/hz/apply/returnHome","回国（入境）,215");
        obj.put("/gov_api/hz/apply/abandonBabyHome","弃婴入户,216");
        obj.put("/gov_api/hz/apply/move","迁往市（县）外有准迁证,217");
        obj.put("/gov_api/hz/apply/relocationCertificate","迁移证补发,218");
        obj.put("/gov_api/hz/apply/enlisted","入伍注销户口,219");
        obj.put("/gov_api/hz/apply/collective","设立单位集体户口,220");
        obj.put("/gov_api/hz/apply/adoption","收养入户,221");
        obj.put("/gov_api/hz/apply/deathRegistration","死亡登记,222");
        obj.put("/gov_api/hz/apply/workers","务工人员入户,223");
        obj.put("/gov_api/hz/apply/ledgerAccount","户政申报_分户、立户,224");
        obj.put("/gov_api/hz/apply/couple","户政申报_夫妻投靠,225");
        obj.put("/gov_api/hz/apply/parents","户政申报_父母投靠子女,226");
        obj.put("/gov_api/hz/apply/transfer"," 工作调动入户,227");
        obj.put("/gov_api/hz/apply/housePurchase","户政申报_购房入户,228");
        obj.put("/gov_api/hz/apply/uploadEnclosure","上传附件,229");
        obj.put("/gov_api/hz/query/xsexx","新生儿查询,230");
        obj.put("/gov_api/hz/query/getSecurityScores","保安员成绩查询,231");
        obj.put("/gov_api/hz/query/getSecurityProgress","保安员进度查询,232");
        obj.put("/gov_api/hz/query/getIdCardProgress","身份证办理进度查询,234");
        obj.put("/gov_api/hz/query/getResidentsCityDept","获取市机构代码,235");
        obj.put("/gov_api/hz/query/getResidentsBureauDept","获取下级机构代码,236");
        obj.put("/gov_api/hz/query/getSameName","同名查询接口,237");
        obj.put("/gov_api/hz/query/mattersList","事项列表_户政,238");
        obj.put("/gov_api/hz/query/handlingGuide","办事指南_户政,239");
        obj.put("/gov_api/hz/query/selectEnclosure","查看附件,240");
        obj.put("/gov_api/hz/query/dict","类别字典,241");
        obj.put("/gov_api/hz/query/dictMsg","字典值获取,242");
        obj.put("/gov_api/hz/query/uerMsg","用户获取,243");
        obj.put("/gov_api/hz/query/cityMsg","市局列表,244");
        obj.put("/gov_api/hz/query/downMsg","分局列表,245");
        obj.put("/gov_api/hz/query/pliceMsg","派出所列表,246");
        obj.put("/gov_api/hz/query/eventQuery","办理事项查询,247");
        //社保查询 接口编号以400-499
        obj.put("/gov_api/rs/query/personInfo","人员基本信息查询,400");
        obj.put("/gov_api/rs/query/inuredInfo","人员参保信息查询,401");
        obj.put("/gov_api/rs/query/accountInfo","养老账户信息查询,402");
        obj.put("/gov_api/rs/query/payInfo","个人缴费明细查询,403");
        obj.put("/gov_api/rs/query/lostInfo","失业待遇发放信息查询,404");
        obj.put("/gov_api/rs/query/oldInfo","养老待遇发放信息查询,405");
        //社保卡查询 接口编号以500-549
        obj.put("/gov_api/rs/sbk/query/cardInfo","获取社保卡状态,500");
        obj.put("/gov_api/rs/sbk/query/checkcardInfo","验证获取社保卡状态,501");
        obj.put("/gov_api/rs/sbk/query/alipayAuth","支付宝授权地址,502");
        obj.put("/gov_api/rs/sbk/query/alipayAuthRedirect","支付宝授权回调,503");
        obj.put("/gov_api/rs/sbk/query/alipayCardQuery","电子社保卡签发查询,504");
        obj.put("/gov_api/rs/sbk/apply/changeUserInfo","修改人员信息,505");
        obj.put("/gov_api/rs/sbk/query/reportLoss","社保卡挂失,506");
        obj.put("/gov_api/rs/sbk/query/alipayInstcardBind","电子社保卡签发,507");
        //就业       以550-599
        obj.put("/gov_api/rs/jy/query/serchJycyz","就业创业证查询,550");
        obj.put("/gov_api/rs/jy/query/serchJydj","就业登记查询,551");
        obj.put("/gov_api/rs/jy/query/serchSydj","失业登记查询,552");
        obj.put("/gov_api/rs/jy/query/queryJyjnpxstandard","就业技能培训补贴标准查询,553");
        obj.put("/gov_api/rs/jy/query/queryCypxstandard","创业培训补贴标准查询,554");
        obj.put("/gov_api/rs/jy/query/queryLhjysbbtstandard","灵活就业社保补贴标准查询,555");
        obj.put("/gov_api/rs/jy/query/queryDwsbbtstandard","单位社保补贴标准查询,556");
        obj.put("/gov_api/rs/jy/query/queryJyjxstandard","就业见习补贴标准查询,557");
        obj.put("/gov_api/rs/jy/query/queryQzcystandard","求职创业补贴标准查询,558");
        obj.put("/gov_api/rs/jy/query/querySbbtpolicy","灵活就业社保补贴申请相关政策查询,559");
        obj.put("/gov_api/rs/jy/query/queryKnrypolicy","就业困难人员申请相关政策查询,560");
        obj.put("/gov_api/rs/jy/query/queryJnpxpolicy","就业技能培训人员报名相关政策查询,561");
        obj.put("/gov_api/rs/jy/query/queryDbdkpolicy","个人创业担保贷款申请相关政策查询,562");
        obj.put("/gov_api/rs/jy/query/businessCredentials","创业证查询,563");
        obj.put("/gov_api/rs/jy/query/employmentRegistration","就业登陆查询,564");
        obj.put("/gov_api/rs/jy/query/unemploymentRegistration","失业登记查询,565");
        obj.put("/gov_api/rs/jy/query/employmentHard","就业困难查询,566");
        obj.put("/gov_api/rs/jy/query/publicWelfareJob","公益性岗位查询,567");
        obj.put("/gov_api/rs/jy/query/employmentSubsidy","享受就业补贴查询,568");
        obj.put("/gov_api/rs/jy/query/guaranteePayment","创业担保货款查询,569");
        obj.put("/gov_api/rs/jy/query/graduate","高校毕业生查询,570");
        obj.put("/gov_api/rs/jy/query/noviciate","就业见习查询,571");
        obj.put("/gov_api/rs/jy/query/training","创业培训查询,572");
        obj.put("/gov_api/rs/jy/query/employmentTraining","就业培训查询,573");
        obj.put("/gov_api/rs/jy/query/laborDispatch","劳务派遣查询,574");
        //税务  以600-649
        obj.put("/gov_api/tax/checkFpxx","发票查询接口,600");
        obj.put("/gov_api/tax/findNsrztxx","纳税人状态查询接口,601");
        obj.put("/gov_api/tax/findYbnsrzgxx","一般纳税人资格查询,602");
        obj.put("/gov_api/tax/findQsxx","欠税查询,603");
        obj.put("/gov_api/tax/findQsxxSsq","公告所属期查询接口,604");
        obj.put("/gov_api/tax/findCktsl","出口退税率查询,605");
        obj.put("/gov_api/tax/checkWswszm","证明信息查询-完税证明文书式,606");
        obj.put("/gov_api/tax/checkWswszmMx","完税证明文书式明细,607");
        obj.put("/gov_api/tax/checkWswszmBgs","完税证明表格式,608");
        obj.put("/gov_api/tax/checkWswszmBgsMx","完税证明表格式明细,609");
        obj.put("/gov_api/tax/getWsBgsPzzg","完税证明表格式获取证明字轨,610");
        obj.put("/gov_api/tax/findBsrlxx","办税日历查询,611");
        obj.put("/gov_api/tax/getVerify","生成验证码,612");
        //民政  以650-699
        obj.put("/gov_api/civiladministration/getDeptInfo","预约登记机关,650");
        obj.put("/gov_api/civiladministration/getBookTime","预约登记时间,651");
        obj.put("/gov_api/civiladministration/queryBookMarryInfo","预约信息查询(预结、 预离),652");
        obj.put("/gov_api/civiladministration/cancelBook","撤销预约(预结、 预离),653");
        obj.put("/gov_api/civiladministration/area","查询所有地区,654");
        obj.put("/gov_api/civiladministration/saveBookMarryInfo","登记信息录入,655");
        //公积金 以700-749
        obj.put("/gov_api/reserved/fund/authorization","个人公积金数据查询授权,700");
        obj.put("/gov_api/reserved/fund/userMsg","获取个人基本信息,701");
        obj.put("/gov_api/reserved/fund/userAccount","查询个人公积金缴存账户,702");
        obj.put("/gov_api/reserved/fund/userDetail","查询个人缴存账户变动明细,703");
        obj.put("/gov_api/reserved/fund/extractAccount","获取个人公积金提取业务记录,704");
        obj.put("/gov_api/reserved/fund/deposite","获取个人公积金缴存记录,705");
        obj.put("/gov_api/reserved/fund/loanApplication","获取个人贷款申请记录,706");
        obj.put("/gov_api/reserved/fund/loanDetails","获取公积金贷款合同详情 ,707");
        obj.put("/gov_api/reserved/fund/accountDetails","获取公积金贷款账户详情 ,708");
        obj.put("/gov_api/reserved/fund/repaymentRecords","获取个人公积金还款记录 ,709");
        obj.put("/gov_api/reserved/fund/approvals","个人公积金审批进度跟踪 ,710");
        obj.put("/gov_api/reserved/fund/userMessage","个人基本信息获取 ,711");
        obj.put("/gov_api/reserved/fund/changeUser","个人基本信息变更 ,712");
        obj.put("/gov_api/reserved/fund/authCode","获取动态验证码 ,713");
        obj.put("/gov_api/reserved/fund/checkAuth","校验动态验证码 ,714");
        obj.put("/gov_api/reserved/fund/extractFund","公积金在线提取验证 ,715");
        obj.put("/gov_api/reserved/fund/transactionExtract","办理提取 ,717");
        obj.put("/gov_api/reserved/fund/loanMsg","贷款基本信息查询 ,718");
        obj.put("/gov_api/reserved/fund/provideFundMsg","借款人公积金账户信息查询 ,719");
        obj.put("/gov_api/reserved/fund/RepaymentInterest","提前还款利息计算 ,720");
        obj.put("/gov_api/reserved/fund/fundprepayment","个人公积金提前还款，变更方式 ,721");
        obj.put("/gov_api/reserved/fund/prepayment","个人公积金提前还款 ,722");
        obj.put("/gov_api/reserved/fund/acceptingInstitution","查询受理机构等相关信息 ,723");
        obj.put("/gov_api/reserved/fund/coding","查询编码名称和编码值 ,724");
        obj.put("/gov_api/reserved/fund/bankMsg","查询个人银行账户信息 ,725");
        obj.put("/gov_api/reserved/fund/depositeMsg","查询个人公积金缴存账户信息 ,726");
        obj.put("/gov_api/province/reserved/fund/userInfo","获取个人基本信息 ,727");
        //司法 以750-799
        obj.put("/gov_api/judicial/apply/register","收件数据接收 ,750");
        obj.put("/gov_api/judicial/apply/nodeinfo","办件环节信息接收 ,751");
        obj.put("/gov_api/judicial/apply/specialStart","特别程序开始 ,752");
        obj.put("/gov_api/judicial/apply/getTransData","办结数据接收 ,753");
        obj.put("/gov_api/judicial/apply/patchApply","补件数据接收 ,754");
        obj.put("/gov_api/judicial/apply/getSpecialEnd","特别程序结束 ,755");
        obj.put("/gov_api/judicial/apply/forminfo","表单数据接收 ,756");
        obj.put("/gov_api/judicial/apply/patchEndAccept","补齐补正结束 ,757");
        obj.put("/gov_api/judicial/query/queryInfoData","基本信息查询 ,758");


        return obj.get(str)==null?null:obj.getString(str);
    }
}

package com.neusoft.mid.ec.api.domain;

/**
 *  教育申请实体类
 */
public class Education {
    private String name;  //支付宝授权姓名
    private String idno;  //支付宝授权身份证号
    private String userName;  //申报名字  个别接口试用
    private String idCode;  //申报证件号  个别接口试用
    private String mobile;  //申报联系电话
    private String isPost;  //是否邮寄 0：否；1：是 默认否
    private String postAddr;  //邮寄地址
    private String applyReason;  //申请理由
    private String aName;  //申报姓名 个别接口试用
    private String gender;  //性别 个别接口试用
    private String linkerPhone;  //申报联系电话 个别接口试用
    private String educationType;  //学历类型
    private String educationLevel;  //学历层次
    private String subjectType;  //学历层次
    private String admissionYear;  //录取年份
    private String isExtra ;  //是否补录
    private String admissionSchool ;  //录取院校名称
    private String admissionMajor ;  //录取专业名称
    private String examCity ;  //考试所在城市
    private String enrolmentCode ;  //招生代码
    private String examScore ;  //高考分数
    private String examCode ;  //考号
    private String remark ;  //备注
    private String idCard; //身份证号 个别接口试用
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsPost() {
        return isPost;
    }

    public void setIsPost(String isPost) {
        this.isPost = isPost;
    }

    public String getPostAddr() {
        return postAddr;
    }

    public void setPostAddr(String postAddr) {
        this.postAddr = postAddr;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLinkerPhone() {
        return linkerPhone;
    }

    public void setLinkerPhone(String linkerPhone) {
        this.linkerPhone = linkerPhone;
    }

    public String getEducationType() {
        return educationType;
    }

    public void setEducationType(String educationType) {
        this.educationType = educationType;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(String admissionYear) {
        this.admissionYear = admissionYear;
    }

    public String getIsExtra() {
        return isExtra;
    }

    public void setIsExtra(String isExtra) {
        this.isExtra = isExtra;
    }

    public String getAdmissionSchool() {
        return admissionSchool;
    }

    public void setAdmissionSchool(String admissionSchool) {
        this.admissionSchool = admissionSchool;
    }

    public String getAdmissionMajor() {
        return admissionMajor;
    }

    public void setAdmissionMajor(String admissionMajor) {
        this.admissionMajor = admissionMajor;
    }

    public String getExamCity() {
        return examCity;
    }

    public void setExamCity(String examCity) {
        this.examCity = examCity;
    }

    public String getEnrolmentCode() {
        return enrolmentCode;
    }

    public void setEnrolmentCode(String enrolmentCode) {
        this.enrolmentCode = enrolmentCode;
    }

    public String getExamScore() {
        return examScore;
    }

    public void setExamScore(String examScore) {
        this.examScore = examScore;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}


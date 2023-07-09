package com.deta.achi.pojo;

import com.deta.achi.pojo.excel.ExcelImport;

/**
 * @author DJWang
 * @date 2022/08/10 22:55
 **/
public class ExcelPojo {
    @ExcelImport("考核任务名称")
    private String achiName;
    @ExcelImport("考核人姓名")
    private String work_name;
    @ExcelImport("考核人账号")
    private String work_id;
    @ExcelImport("考核人部门")
    private String department;
    @ExcelImport("考核类型")
    private String typeName;
    @ExcelImport("考核开始时间")
    private String achi_start;
    @ExcelImport("考核结束时间")
    private String achi_stop;
    @ExcelImport("考核积分")
    private Long kaohe;
    @ExcelImport("难度系数")
    private Long nandu;
    @ExcelImport("效率系数")
    private Long xiaolv;
    @ExcelImport("效益系数")
    private Long xiaoyi;
    @ExcelImport("公平系数")
    private Long gongping;
    @ExcelImport("风险系数")
    private Long fengxian;
    @ExcelImport("双选系数")
    private Long shuangxuan;
    @ExcelImport("额外贡献")
    private Long ewai;
    @ExcelImport("工作量系数")
    private Long gongzuoliang;
    @ExcelImport("工作质量系数")
    private Long gongzuozhiliang;
    @ExcelImport("额外贡献系数")
    private Long ewaigongxianxishu;
    @ExcelImport("招标代理工作")
    private Long zhaobiao;
    @ExcelImport("协作")
    private Long xiezuo;
    @ExcelImport("额外积分")
    private Long ewaijifen;


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAchiName() {
        return achiName;
    }

    public void setAchiName(String achiName) {
        this.achiName = achiName;
    }

    public Long getKaohe() {
        return kaohe;
    }

    public void setKaohe(Long kaohe) {
        this.kaohe = kaohe;
    }

    public Long getNandu() {
        return nandu;
    }

    public void setNandu(Long nandu) {
        this.nandu = nandu;
    }

    public Long getXiaolv() {
        return xiaolv;
    }

    public void setXiaolv(Long xiaolv) {
        this.xiaolv = xiaolv;
    }

    public Long getXiaoyi() {
        return xiaoyi;
    }

    public void setXiaoyi(Long xiaoyi) {
        this.xiaoyi = xiaoyi;
    }

    public Long getGongping() {
        return gongping;
    }

    public void setGongping(Long gongping) {
        this.gongping = gongping;
    }

    public Long getFengxian() {
        return fengxian;
    }

    public void setFengxian(Long fengxian) {
        this.fengxian = fengxian;
    }

    public Long getShuangxuan() {
        return shuangxuan;
    }

    public void setShuangxuan(Long shuangxuan) {
        this.shuangxuan = shuangxuan;
    }

    public Long getEwai() {
        return ewai;
    }

    public void setEwai(Long ewai) {
        this.ewai = ewai;
    }

    public String getAchi_start() {
        return achi_start;
    }

    public void setAchi_start(String achi_start) {
        this.achi_start = achi_start;
    }

    public String getAchi_stop() {
        return achi_stop;
    }

    public void setAchi_stop(String achi_stop) {
        this.achi_stop = achi_stop;
    }

    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getGongzuoliang() {
        return gongzuoliang;
    }

    public void setGongzuoliang(Long gongzuoliang) {
        this.gongzuoliang = gongzuoliang;
    }

    public Long getGongzuozhiliang() {
        return gongzuozhiliang;
    }

    public void setGongzuozhiliang(Long gongzuozhiliang) {
        this.gongzuozhiliang = gongzuozhiliang;
    }

    public Long getEwaigongxianxishu() {
        return ewaigongxianxishu;
    }

    public void setEwaigongxianxishu(Long ewaigongxianxishu) {
        this.ewaigongxianxishu = ewaigongxianxishu;
    }

    public Long getZhaobiao() {
        return zhaobiao;
    }

    public void setZhaobiao(Long zhaobiao) {
        this.zhaobiao = zhaobiao;
    }

    public Long getXiezuo() {
        return xiezuo;
    }

    public void setXiezuo(Long xiezuo) {
        this.xiezuo = xiezuo;
    }

    public Long getEwaijifen() {
        return ewaijifen;
    }

    public void setEwaijifen(Long ewaijifen) {
        this.ewaijifen = ewaijifen;
    }

    @Override
    public String toString() {
        return "ExcelPojo{" +
                "achiName='" + achiName + '\'' +
                ", kaohe=" + kaohe +
                ", nandu=" + nandu +
                ", xiaolv=" + xiaolv +
                ", xiaoyi=" + xiaoyi +
                ", gongping=" + gongping +
                ", fengxian=" + fengxian +
                ", shuangxuan=" + shuangxuan +
                ", ewai=" + ewai +
                ", achi_start=" + achi_start +
                ", achi_stop=" + achi_stop +
                '}';
    }
}



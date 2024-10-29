package com.gf.biz.totalDeptIndicatorScore;

import java.util.ArrayList;
import java.util.List;

public enum FuncPerformanceIndocatorEnum {
    PI0006("PI0006","部门服务满意度"),
    PI0021("PI0021","核心指标"),
    PI0018("PI0018","员工满意度"),
    PI0020("PI0020","办公室检查"),
    PI0005("PI0005","管理费用合计"),
    PI0022("PI0022","管理人员工作鉴定"),
    PI0007("PI0007","月度计划达成率"),
    PI0008("PI0008","协作数量"),
    PI0009("PI0009","协作质量"),
    PI0010("PI0010","部门培训计划"),
    PI0011("PI0011","樊登读书量");

    private String piCode;
    private String piName;

    FuncPerformanceIndocatorEnum(String piCode, String piName) {
        this.piCode = piCode;
        this.piName = piName;
    }

    public String getPiCode() {
        return piCode;
    }

    public void setPiCode(String piCode) {
        this.piCode = piCode;
    }

    public String getPiName() {
        return piName;
    }

    public void setPiName(String piName) {
        this.piName = piName;
    }


    public static List<String> getPiCodeList() {
        List<String> piList = new ArrayList<>();
        for (OptPerformanceIndocatorEnum piEnum : OptPerformanceIndocatorEnum.values()) {
            piList.add(piEnum.getPiCode());
        }
        return piList;

    }
}

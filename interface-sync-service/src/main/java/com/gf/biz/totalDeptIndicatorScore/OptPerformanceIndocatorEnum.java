package com.gf.biz.totalDeptIndicatorScore;

import java.util.ArrayList;
import java.util.List;

public enum OptPerformanceIndocatorEnum {
    PI0014("PI0014","主营业务总收入"),
    PI0015("PI0015","毛利率"),
    PI0016("PI0016","可控费用率"),
    PI0017("PI0017","平均人效"),
    PI0001("PI0001","顾客反馈"),
    PI0003("PI0003","神秘访客"),
    PI0013("PI0013","美团大众评分"),
    PI0002("PI0002","外卖"),
    PI0007("PI0007","月度计划达成率"),
    PI0019("PI0019","门店QSC"),
    PI0012("PI0012","食安检查"),
    PI0004("PI0004","新员工一证获取率"),
    PI0011("PI0011","樊登读书量");

    private String piCode;
    private String piName;

    OptPerformanceIndocatorEnum(String piCode, String piName) {
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

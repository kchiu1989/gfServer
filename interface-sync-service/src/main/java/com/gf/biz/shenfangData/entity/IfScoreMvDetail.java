package com.gf.biz.shenfangData.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gf
 * @since 2024-10-15 16:25:57
 */

@TableName("if_score_mv_detail")
    public class IfScoreMvDetail implements Serializable {

    private static final long serialVersionUID = 1L;

                @TableId(value = "id", type = IdType.AUTO)
                private Long id;

        /**
         * 父表id
         */
    @TableField("master_id")
        private Long masterId;

        /**
         * 一级分类
         */
    @TableField("first_classify")
        private String firstClassify;

        /**
         * 二级分类
         */
    @TableField("second_classify")
        private String secondClassify;

        /**
         * 三级分类
         */
    @TableField("thirdly_classify")
        private String thirdlyClassify;

        /**
         * 四级分类
         */
    @TableField("fourthly_classfy")
        private String fourthlyClassfy;

        /**
         * 考核点
         */
    @TableField("examine")
        private String examine;

    @TableField("examine_id")
        private String examineId;

        /**
         * 考核点总分
         */
    @TableField("score")
        private BigDecimal score;

        /**
         * 评分所在级别	（ABCDE）	详见文档第 7 项注	意事项
         */
    @TableField("score_sort")
        private String scoreSort;

        /**
         * 考核点总分
         */
    @TableField("experience_score")
        private BigDecimal experienceScore;

        /**
         * 用户体验文案
         */
    @TableField("experience_comment")
        private String experienceComment;

        /**
         * 文件地址
         */
    @TableField("file_list")
        private String fileList;

        /**
         * 是否 N/A
         */
    @TableField("experience_score_valid")
        private Integer experienceScoreValid;

    @TableField("experience_comment_list")
        private String experienceCommentList;

        /**
         * 用户选择答案（仅	针对问卷式）
         */
    @TableField("option_question")
        private String optionQuestion;

        /**
         * 选择题集合（仅针	对问卷式）
         */
    @TableField("option_list")
        private String optionList;

        /**
         * 创建时间
         */
    @TableField("created_time")
        private Date createdTime;

        /**
         * 更新时间
         */
    @TableField("updated_time")
        private Date updatedTime;

        /**
         * 创建者
         */
    @TableField("created_by")
        private String createdBy;

        /**
         * 更新者
         */
    @TableField("updated_by")
        private String updatedBy;

        /**
         * '0'正常 '1'删除
         */
    @TableField("deleted_flag")
        private String deletedFlag;

    public Long getId() {
        return id;
    }
    @TableField("year")
    private String year;
    @TableField("month")
    private String month;


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public String getFirstClassify() {
        return firstClassify;
    }

    public void setFirstClassify(String firstClassify) {
        this.firstClassify = firstClassify;
    }

    public String getSecondClassify() {
        return secondClassify;
    }

    public void setSecondClassify(String secondClassify) {
        this.secondClassify = secondClassify;
    }

    public String getThirdlyClassify() {
        return thirdlyClassify;
    }

    public void setThirdlyClassify(String thirdlyClassify) {
        this.thirdlyClassify = thirdlyClassify;
    }

    public String getFourthlyClassfy() {
        return fourthlyClassfy;
    }

    public void setFourthlyClassfy(String fourthlyClassfy) {
        this.fourthlyClassfy = fourthlyClassfy;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getScoreSort() {
        return scoreSort;
    }

    public void setScoreSort(String scoreSort) {
        this.scoreSort = scoreSort;
    }

    public BigDecimal getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(BigDecimal experienceScore) {
        this.experienceScore = experienceScore;
    }

    public String getExperienceComment() {
        return experienceComment;
    }

    public void setExperienceComment(String experienceComment) {
        this.experienceComment = experienceComment;
    }

    public String getFileList() {
        return fileList;
    }

    public void setFileList(String fileList) {
        this.fileList = fileList;
    }

    public Integer getExperienceScoreValid() {
        return experienceScoreValid;
    }

    public void setExperienceScoreValid(Integer experienceScoreValid) {
        this.experienceScoreValid = experienceScoreValid;
    }

    public String getExperienceCommentList() {
        return experienceCommentList;
    }

    public void setExperienceCommentList(String experienceCommentList) {
        this.experienceCommentList = experienceCommentList;
    }

    public String getOptionQuestion() {
        return optionQuestion;
    }

    public void setOptionQuestion(String optionQuestion) {
        this.optionQuestion = optionQuestion;
    }

    public String getOptionList() {
        return optionList;
    }

    public void setOptionList(String optionList) {
        this.optionList = optionList;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }
}
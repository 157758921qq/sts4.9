package com.markus.wx.account.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 */
public class Account implements Serializable {
    private Integer accountId;

    private String accountName;

    private String phoneNumber;

    private String loginPassword;

    private String validateCode;

    private Date registerTime;

    private String validateCounter;

    private String msgtype;

    private String event;

    private String eventKey;

    private String tousername;

    private String fromusername;

    private static final long serialVersionUID = 1L;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getValidateCounter() {
        return validateCounter;
    }

    public void setValidateCounter(String validateCounter) {
        this.validateCounter = validateCounter;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTousername() {
        return tousername;
    }

    public void setTousername(String tousername) {
        this.tousername = tousername;
    }

    public String getFromusername() {
        return fromusername;
    }

    public void setFromusername(String fromusername) {
        this.fromusername = fromusername;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Account other = (Account) that;
        return (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getAccountName() == null ? other.getAccountName() == null : this.getAccountName().equals(other.getAccountName()))
            && (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
            && (this.getLoginPassword() == null ? other.getLoginPassword() == null : this.getLoginPassword().equals(other.getLoginPassword()))
            && (this.getValidateCode() == null ? other.getValidateCode() == null : this.getValidateCode().equals(other.getValidateCode()))
            && (this.getRegisterTime() == null ? other.getRegisterTime() == null : this.getRegisterTime().equals(other.getRegisterTime()))
            && (this.getValidateCounter() == null ? other.getValidateCounter() == null : this.getValidateCounter().equals(other.getValidateCounter()))
            && (this.getMsgtype() == null ? other.getMsgtype() == null : this.getMsgtype().equals(other.getMsgtype()))
            && (this.getEvent() == null ? other.getEvent() == null : this.getEvent().equals(other.getEvent()))
            && (this.getEventKey() == null ? other.getEventKey() == null : this.getEventKey().equals(other.getEventKey()))
            && (this.getTousername() == null ? other.getTousername() == null : this.getTousername().equals(other.getTousername()))
            && (this.getFromusername() == null ? other.getFromusername() == null : this.getFromusername().equals(other.getFromusername()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getAccountName() == null) ? 0 : getAccountName().hashCode());
        result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
        result = prime * result + ((getLoginPassword() == null) ? 0 : getLoginPassword().hashCode());
        result = prime * result + ((getValidateCode() == null) ? 0 : getValidateCode().hashCode());
        result = prime * result + ((getRegisterTime() == null) ? 0 : getRegisterTime().hashCode());
        result = prime * result + ((getValidateCounter() == null) ? 0 : getValidateCounter().hashCode());
        result = prime * result + ((getMsgtype() == null) ? 0 : getMsgtype().hashCode());
        result = prime * result + ((getEvent() == null) ? 0 : getEvent().hashCode());
        result = prime * result + ((getEventKey() == null) ? 0 : getEventKey().hashCode());
        result = prime * result + ((getTousername() == null) ? 0 : getTousername().hashCode());
        result = prime * result + ((getFromusername() == null) ? 0 : getFromusername().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accountId=").append(accountId);
        sb.append(", accountName=").append(accountName);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", loginPassword=").append(loginPassword);
        sb.append(", validateCode=").append(validateCode);
        sb.append(", registerTime=").append(registerTime);
        sb.append(", validateCounter=").append(validateCounter);
        sb.append(", msgtype=").append(msgtype);
        sb.append(", event=").append(event);
        sb.append(", eventKey=").append(eventKey);
        sb.append(", tousername=").append(tousername);
        sb.append(", fromusername=").append(fromusername);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
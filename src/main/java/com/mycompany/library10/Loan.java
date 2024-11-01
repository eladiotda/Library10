package com.mycompany.library10;

public class Loan {
    public String userId;
    public String materialCode;
    public String loanDate;
    public String returnDate;

    public Loan(String userId, String materialCode, String loanDate, String returnDate) {
        this.userId = userId;
        this.materialCode = materialCode;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "User ID: " + userId + ", Material Code: " + materialCode + ", Loan Date: " + loanDate + ", Return Date: " + returnDate;
    }
}

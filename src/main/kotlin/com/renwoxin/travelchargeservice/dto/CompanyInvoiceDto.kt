package com.renwoxin.travelchargeservice.dto

import com.renwoxin.travelchargeservice.model.entity.CompanyInvoice
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

data class CompanyInvoiceDto(
    val id: Long?,
    val orderId: Long,
    val companyId: String,
    val payerName: String,
    val payerRegisterNo: String,
    val payerAddress: String,
    val payerTelPhone: String,
    val payerBankName: String,
    val payerBankAccount: String,
    val userEmail: String,
    val userMobileNo: String,
    val amount: String,
    val mShortName: String,
    val status: String
) {
    constructor(invoice: CompanyInvoice) : this(
        id = invoice.id,
        orderId = invoice.orderId,
        companyId = invoice.companyId,
        payerName = invoice.payerName,
        payerRegisterNo = invoice.payerRegisterNo,
        payerAddress = invoice.payerAddress,
        payerTelPhone = invoice.payerTelPhone,
        payerBankName = invoice.payerBankName,
        payerBankAccount = invoice.payerBankAccount,
        userEmail = invoice.userEmail,
        userMobileNo = invoice.userMobileNo,
        amount = invoice.amount,
        mShortName = invoice.mShortName,
        status = invoice.status
    )
}

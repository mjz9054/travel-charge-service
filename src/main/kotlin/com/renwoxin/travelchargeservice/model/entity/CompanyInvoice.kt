package com.renwoxin.travelchargeservice.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "company_invoice")
data class CompanyInvoice(
    @Id
    val id: Long? = 0,
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
)

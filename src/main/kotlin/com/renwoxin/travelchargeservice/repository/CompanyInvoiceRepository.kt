package com.renwoxin.travelchargeservice.repository

import com.renwoxin.travelchargeservice.model.entity.CompanyInvoice
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyInvoiceRepository : CrudRepository<CompanyInvoice, Long> {
    fun findByStatus(status: String): List<CompanyInvoice>
}

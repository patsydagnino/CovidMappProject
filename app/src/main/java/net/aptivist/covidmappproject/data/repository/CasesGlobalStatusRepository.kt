package net.aptivist.covidmappproject.data.repository

import net.aptivist.covidmappproject.data.repository.dao.ICasesGlobalStatus

class CasesGlobalStatusRepository(private val casesGlobalStatus: ICasesGlobalStatus) {
    val data = casesGlobalStatus.getAllGlobalStatuses()
}
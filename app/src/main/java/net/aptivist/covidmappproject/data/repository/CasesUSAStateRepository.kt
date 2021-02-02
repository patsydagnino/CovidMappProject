package net.aptivist.covidmappproject.data.repository

import net.aptivist.covidmappproject.data.repository.dao.ICasesUSAStateDao

class CasesUSAStateRepository(private val casesUSAState: ICasesUSAStateDao) {
    val data = casesUSAState.getAllStates()
}
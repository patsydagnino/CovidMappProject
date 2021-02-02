package net.aptivist.covidmappproject.data.api.interfaces

import net.aptivist.covidmappproject.data.api.models.CasesUSAState

interface ICaseUSAStatesService {
    fun getCases(): List<CasesUSAState>?
}
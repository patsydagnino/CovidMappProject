package net.aptivist.covidmappproject.data.repository

import net.aptivist.covidmappproject.data.repository.dao.ICasesCountry

class CasesCountryRepository(private val casesCountry : ICasesCountry) {
    val data = casesCountry.getAllCountries()
}
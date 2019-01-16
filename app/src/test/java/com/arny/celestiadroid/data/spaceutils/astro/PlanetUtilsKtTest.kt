package com.arny.celestiadroid.data.spaceutils.astro

import org.assertj.core.api.Assertions.assertThat
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.MethodSorters

@RunWith(JUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PlanetUtilsKtTest {

    @Test
    fun aa_getPlantRadius() {
        val parseRad = 5.56
        val exp = 24
        val planetRadius = getPlanetRadius(parseRad, exp)
        assertThat(planetRadius).isEqualTo(5.56e24)
    }

    @Test
    fun ab_getMant() {
        val radius = 23500000.0
        val exp = getExpVal(radius)
        val value = getNotExpVal(radius)
        assertThat(exp).isEqualTo("")
        assertThat(value).isEqualTo("2.35")
    }
}
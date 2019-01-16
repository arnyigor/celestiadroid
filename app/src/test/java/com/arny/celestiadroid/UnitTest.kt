package com.arny.celestiadroid

import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.MethodSorters

import java.util.Stack

import junit.framework.Assert.assertTrue
import org.assertj.core.api.Assertions.assertThat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(JUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UnitTest {

    @Test
    @Throws(Exception::class)
    fun get() {
        var input = "1.25e56"
    }


    @Test
    @Throws(Exception::class)
    fun substring() {
        var input = "1.25e56"
        input = input.substring(0, input.length - 1)
        assertThat(input).isEqualTo("1.25e5")
        input = "555.0"
        input = cut(input)
        assertThat(input).isEqualTo("55")
        input = "55e2"
        input = cut(input)
        assertThat(input).isEqualTo("55")
        input = "55e"
        input = cut(input)
        assertThat(input).isEqualTo("55")
        input = "0.123"
        input = cut(input)
        assertThat(input).isEqualTo("0.12")
        input = "0.123e2"
        input = cut(input)
        assertThat(input).isEqualTo("0.123")
        input = "0.123e"
        input = cut(input)
        assertThat(input).isEqualTo("0.123")
        input = "123e-2"
        input = cut(input)
        assertThat(input).isEqualTo("123")
        input = cut(input)
        assertThat(input).isEqualTo("12")
        input = cut(input)
        assertThat(input).isEqualTo("1")
    }

    @Test
    @Throws(Exception::class)
    fun validNumber() {
        var input = "1.25e56"
        assertTrue(isValidInput(input))
        input = "1.25ee56"
        assertTrue(!isValidInput(input))
        input = "1.25e-56"
        assertTrue(isValidInput(input))
        input = "1.25e--56"
        assertTrue(!isValidInput(input))
        input = "1.25e"
        assertTrue(!isValidInput(input))
    }

    private fun isValidInput(input: String): Boolean {
        val bra = Stack<Char>()
        for (c in input.toCharArray()) {
            if ((c < '(' || c > '9') && c.toInt() != 101) {
                return false
            }
            if ('(' == c) {
                bra.push(c)
            } else {
                if (')' == c) {
                    if (bra.isEmpty()) return false
                    bra.pop()
                }
            }
        }

        return bra.isEmpty()
    }

    private fun cut(input: String): String {
        var input = input
        if (input.endsWith(".0")) {
            input = input.substring(0, input.length - 2)
        }
        input = input.substring(0, input.length - 1)
        if (input.endsWith("-")) {
            input = cut(input)
        }
        if (input.endsWith("e")) {
            input = cut(input)
        }
        return input
    }

}
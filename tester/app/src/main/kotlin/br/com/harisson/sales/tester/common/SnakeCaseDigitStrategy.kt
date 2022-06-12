package br.com.harisson.sales.tester.common

import com.fasterxml.jackson.databind.PropertyNamingStrategies

class SnakeCaseDigitStrategy : PropertyNamingStrategies.SnakeCaseStrategy() {
    override fun translate(input: String): String {
        val length = input.length
        val result = StringBuilder(length * 2)
        var resultLength = 0
        var wasPrevLetter = false
        var wasPrevDigit = false
        var wasPrevUpperCase = false
        var wasPrevTranslated = false
        for (i in 0 until length) {
            var c = input[i]
            if (i > 0 || c != '_') // skip first starting underscore
            {
                val isLetter = Character.isLetter(c)
                val isDigit = Character.isDigit(c)
                val isUpperCase = Character.isUpperCase(c)
                val isCharTypeChanged =
                    isDigit && wasPrevLetter && !wasPrevUpperCase || isLetter && isUpperCase && wasPrevDigit
                if (isUpperCase || isCharTypeChanged) {
                    if ((!wasPrevTranslated || isCharTypeChanged) && resultLength > 0 && result[resultLength - 1] != '_') {
                        result.append('_')
                        resultLength++
                    }
                    c = Character.toLowerCase(c)
                    wasPrevTranslated = true
                } else {
                    wasPrevTranslated = false
                }
                wasPrevLetter = isLetter
                wasPrevDigit = isDigit
                wasPrevUpperCase = isUpperCase
                result.append(c)
                resultLength++
            }
        }
        return if (resultLength > 0) result.toString() else input
    }
}
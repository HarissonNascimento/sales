package br.com.harisson.sales.fraudcheck.adapter.out.mapper

import br.com.harisson.CheckedSale
import br.com.harisson.InvalidatedSale
import br.com.harisson.ValidatedSale
import org.apache.avro.specific.SpecificRecord

fun SpecificRecord.toCheckedSale(): SpecificRecord =
    run {
        when (this) {
            is ValidatedSale -> CheckedSale.newBuilder()
                .setPrice(price)
                .setPaymentType(paymentType)
                .setProduct(product)
                .setSaleId(saleId)
                .setAmount(amount)
                .build()
            else -> throw RuntimeException("Type not allowed") //TODO adicionar exception especifica
        }
    }

fun SpecificRecord.toInvalidatedSale(message: String?): SpecificRecord =
    run {
        when (this) {
            is ValidatedSale -> InvalidatedSale.newBuilder()
                .setPrice(price)
                .setPaymentType(paymentType)
                .setProduct(product)
                .setSaleId(saleId)
                .setMotive(message)
                .build()
            else -> throw RuntimeException("Type not allowed") //TODO adicionar exception especifica
        }
    }
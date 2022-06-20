package br.com.harisson.sales.router.adapter.out.mapper

import br.com.harisson.RequestSale
import br.com.harisson.ValidatedSale
import org.apache.avro.generic.GenericRecord

fun GenericRecord.toRouterResult(): GenericRecord = runCatching{
    when(this){
        is RequestSale -> ValidatedSale.newBuilder()
            .setPrice(price)
            .setPaymentType(paymentType)
            .setProduct(product)
            .setSaleId(saleId)
            .setAmount(amount)
            .build()
        else -> throw RuntimeException("Type not allowed") //TODO adicionar exception especifica
    }
}.getOrThrow()
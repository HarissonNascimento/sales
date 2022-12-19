package br.com.harisson.sales.process.adapter.out.mapper

import br.com.harisson.CheckedSale
import br.com.harisson.ProcessedSale
import br.com.harisson.StatusEnum
import org.apache.avro.specific.SpecificRecord

fun SpecificRecord.toSaleToBeDispatched(status: StatusEnum): SpecificRecord =
    run {
        when (this) {
            is CheckedSale -> ProcessedSale.newBuilder()
                .setPrice(price)
                .setPaymentType(paymentType)
                .setProduct(product)
                .setSaleId(saleId)
                .setAmount(amount)
                .setStatus(status)
                .build()
            else -> throw RuntimeException("Type not allowed") //TODO adicionar exception especifica
        }
    }
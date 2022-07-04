package br.com.harisson.sales.router.application.port.out

import br.com.harisson.sales.router.domain.IdempotencyEntity

interface IdempotencyOutputPort {
    fun putIfNotExists(idempotencyEntity: IdempotencyEntity)
}
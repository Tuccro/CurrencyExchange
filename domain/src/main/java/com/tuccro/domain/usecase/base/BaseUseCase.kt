package com.tuccro.domain.usecase.base

abstract class BaseUseCase<in Params, out Type> where Type : Any {

    abstract suspend operator fun invoke(params: Params): Type
}

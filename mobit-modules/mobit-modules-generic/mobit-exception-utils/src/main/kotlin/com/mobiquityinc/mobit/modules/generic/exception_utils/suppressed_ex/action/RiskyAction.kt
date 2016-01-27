package com.mobiquityinc.mobit.modules.generic.exception_utils.suppressed_ex.action

interface RiskyAction<R> {

    @Throws(Exception::class)
    fun execute(): R

}
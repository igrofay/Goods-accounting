package com.example.core.domain.use_case.user

import com.example.core.domain.repos.UserRepos
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class GetUserFlowUseCase(
    private val userRepos: UserRepos,
) {
    fun execute(delayMillisecond: Long = 10_000) = flow {
        while (true){
            emit(userRepos.getUser())
            delay(delayMillisecond)
        }
    }
}
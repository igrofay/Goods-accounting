package com.example.core.domain.use_case.user

import com.example.core.domain.repos.AppRepos

class ExitUseCase(
    private val appRepos: AppRepos,
) {
    fun execute() = runCatching {
        appRepos.reset()
    }
}
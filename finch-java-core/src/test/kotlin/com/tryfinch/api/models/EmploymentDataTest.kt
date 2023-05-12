package com.tryfinch.api.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EmploymentDataTest {

    @Test
    fun createEmploymentData() {
        val employmentData =
            EmploymentData.builder()
                .id("string")
                .firstName("string")
                .middleName("string")
                .lastName("string")
                .title("string")
                .manager(
                    EmploymentData.Manager.builder()
                        .id("e8b90071-0c11-471c-86e8-e303ef2f6782")
                        .build()
                )
                .department(EmploymentData.Department.builder().name("string").build())
                .employment(
                    EmploymentData.Employment.builder()
                        .type(EmploymentData.Employment.Type.EMPLOYEE)
                        .subtype(EmploymentData.Employment.Subtype.FULL_TIME)
                        .build()
                )
                .startDate("string")
                .endDate("string")
                .isActive(true)
                .classCode("string")
                .location(
                    Location.builder()
                        .line1("string")
                        .line2("string")
                        .city("string")
                        .state("string")
                        .postalCode("string")
                        .country("string")
                        .name("string")
                        .sourceId("string")
                        .build()
                )
                .income(
                    Income.builder()
                        .unit(Income.Unit.YEARLY)
                        .amount(123L)
                        .currency("string")
                        .effectiveDate("string")
                        .build()
                )
                .incomeHistory(
                    listOf(
                        Income.builder()
                            .unit(Income.Unit.YEARLY)
                            .amount(123L)
                            .currency("string")
                            .effectiveDate("string")
                            .build()
                    )
                )
                .workId("string")
                .workId2("string")
                .payGroupIds(listOf("string"))
                .build()
        assertThat(employmentData).isNotNull
        assertThat(employmentData.id()).contains("string")
        assertThat(employmentData.firstName()).contains("string")
        assertThat(employmentData.middleName()).contains("string")
        assertThat(employmentData.lastName()).contains("string")
        assertThat(employmentData.title()).contains("string")
        assertThat(employmentData.manager())
            .contains(
                EmploymentData.Manager.builder().id("e8b90071-0c11-471c-86e8-e303ef2f6782").build()
            )
        assertThat(employmentData.department())
            .contains(EmploymentData.Department.builder().name("string").build())
        assertThat(employmentData.employment())
            .contains(
                EmploymentData.Employment.builder()
                    .type(EmploymentData.Employment.Type.EMPLOYEE)
                    .subtype(EmploymentData.Employment.Subtype.FULL_TIME)
                    .build()
            )
        assertThat(employmentData.startDate()).contains("string")
        assertThat(employmentData.endDate()).contains("string")
        assertThat(employmentData.isActive()).contains(true)
        assertThat(employmentData.classCode()).contains("string")
        assertThat(employmentData.location())
            .contains(
                Location.builder()
                    .line1("string")
                    .line2("string")
                    .city("string")
                    .state("string")
                    .postalCode("string")
                    .country("string")
                    .name("string")
                    .sourceId("string")
                    .build()
            )
        assertThat(employmentData.income())
            .contains(
                Income.builder()
                    .unit(Income.Unit.YEARLY)
                    .amount(123L)
                    .currency("string")
                    .effectiveDate("string")
                    .build()
            )
        assertThat(employmentData.incomeHistory().get())
            .containsExactly(
                Income.builder()
                    .unit(Income.Unit.YEARLY)
                    .amount(123L)
                    .currency("string")
                    .effectiveDate("string")
                    .build()
            )
        assertThat(employmentData.workId()).contains("string")
        assertThat(employmentData.workId2()).contains("string")
        assertThat(employmentData.payGroupIds().get()).containsExactly("string")
    }
}

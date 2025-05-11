package com.benecia.lifetracker.infra.config

import org.quartz.spi.TriggerFiredBundle
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.scheduling.quartz.SpringBeanJobFactory

@Configuration
class QuartzConfig(
    private val beanFactory: AutowireCapableBeanFactory
) {

    @Bean
    fun jobFactory(): SpringBeanJobFactory {
        return object : SpringBeanJobFactory() {
            override fun createJobInstance(bundle: TriggerFiredBundle): Any {
                val job = super.createJobInstance(bundle)
                beanFactory.autowireBean(job)
                return job
            }
        }
    }

    @Bean
    fun schedulerFactoryBean(jobFactory: SpringBeanJobFactory): SchedulerFactoryBean {
        val factory = SchedulerFactoryBean()
        factory.setJobFactory(jobFactory)
        return factory
    }
}
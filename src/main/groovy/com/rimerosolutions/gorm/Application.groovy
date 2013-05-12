/*
 * Copyright 2013 Rimero Solutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rimerosolutions.gorm

import com.rimerosolutions.gorm.domain.Person
import com.rimerosolutions.gorm.service.PersonService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.context.MessageSource
import org.springframework.validation.FieldError
import org.springframework.core.io.ClassPathResource
import grails.spring.BeanBuilder

/**
 * Application launcher
 *
 * @author Yves Zoundi
 */
class Application {
        private static final Logger LOG = LoggerFactory.getLogger(Application.class)

        static main(args) {
                // Load the spring beans
                BeanBuilder beanBuilder = new BeanBuilder()
                beanBuilder.loadBeans("classpath:SpringBeans.groovy")

                ApplicationContext context = beanBuilder.createApplicationContext()
                MessageSource msg = context.getBean("messageSource") as MessageSource

                // Alternative to transactional services would be DomainClass.withTransaction
                PersonService personService = context.getBean("personService") as PersonService

                // Dummy user objects to persist
                def persons = [
                        new Person("firstName":"Yves", "lastName":"Zoundi"),
                        new Person("firstName":"Rimero", "lastName":"Solutions")
                ]

                LOG.info("About to load users: ${persons}")

                // Save person if validation constraints are met
                persons.each { person ->
                        if (personService.validate(person)) {
                                personService.save(person)
                                LOG.info("Successfully saved ${person}")
                        }
                        else {
                                // print validation errors
                                person.errors.allErrors.each { FieldError error ->
                                        LOG.error(msg.getMessage(error, Locale.getDefault()))
                                }
                        }
                }
                
                LOG.info ("All Persons:  ${personService.findAll()}")
        }

}

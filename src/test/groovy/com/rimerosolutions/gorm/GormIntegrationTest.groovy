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
import org.springframework.context.ApplicationContext
import grails.spring.BeanBuilder
import org.junit.Test
import static org.junit.Assert.assertEquals
import com.rimerosolutions.gorm.utils.GormInterceptorsHelper

/**
 * Simple integration test
 *
 * @author Yves Zoundi
 */
class GormIntegrationTest {

        @Test
        void testPersistence() {
                BeanBuilder beanBuilder = new BeanBuilder()
                beanBuilder.loadBeans("classpath:SpringBeans.groovy")

                ApplicationContext context = beanBuilder.createApplicationContext()

                def sf = context.getBean("sessionFactory")
                GormInterceptorsHelper.initializeInterceptors(sf)
                
                // Alternative to transactional services would be DomainClass.withTransaction
                PersonService personService = context.getBean("personService") as PersonService

                Person person = new Person("firstName": "Rimero", "lastName":"Solutions")


                personService.save(person)

                List<Person> persons = personService.findAll()

                assertEquals(persons.size(), 1)
                Person persistedPerson = persons[0]

                assertEquals(persistedPerson.firstName, "Rimero")
                assertEquals(persistedPerson.lastName,"Solutions")
        }

}
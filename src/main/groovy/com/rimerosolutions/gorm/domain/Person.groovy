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
package com.rimerosolutions.gorm.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

/**
 * Dummy Person domain class
 *
 * @author Yves Zoundi
 */
@Entity
@EqualsAndHashCode(includes="id")
class Person {
        /** The first name */
        String firstName

        /** The last name */
        String lastName

        // Test autotimestamp...
        Date dateCreated
        Date lastUpdated

        /** Validation constraints */
        static constraints = {
                firstName size: 5..10, blank: false
                lastName size: 5..10, blank: false

                // REQUIRED to pass validation
                dateCreated nullable:true
                lastUpdated nullable:true
        }

        def beforeUpdate = {
                if (firstName == "Ludovic") {
                        firstName = "Rimero1"
                }
        }

        String toString() {
                "Person [firstName=${firstName}, lastName=${lastName}}, dateCreated=${dateCreated}, lastUpdated=${lastUpdated}]"
        }
}
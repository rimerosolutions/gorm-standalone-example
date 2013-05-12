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
package com.rimerosolutions.gorm.service

import com.rimerosolutions.gorm.domain.Person

/**
 * Person Service
 *
 * @author Yves Zoundi
 */
interface PersonService {
        /**
         * Persists a person
         * @param person A person
         */
        void save(Person person)

        /**
         * Validates the person domain constraints
         * @param person A person
         */
        boolean validate(Person person)

        /**
         * Returns all persons
         * @return All persons in the database
         */
        public List<Person> findAll()
}

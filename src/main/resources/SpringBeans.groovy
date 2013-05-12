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

import org.apache.commons.dbcp.BasicDataSource
import org.springframework.context.support.ResourceBundleMessageSource

beans {
        xmlns gorm:"http://grails.org/schema/gorm"
        xmlns context:"http://www.springframework.org/schema/context"
        xmlns tx:"http://www.springframework.org/schema/tx"

        messageSource(ResourceBundleMessageSource) {
                basename = "messages"
        }

        dataSource(BasicDataSource) {
                driverClassName = "org.h2.Driver"
                url = "jdbc:h2:mem:grailsDB"
                username = "sa"
                password = ""
        }

        gorm.sessionFactory("data-source-ref": "dataSource",
                            "base-package": "com.rimerosolutions.gorm.domain",
                            "message-source-ref": "messageSource") {
                hibernateProperties = ["hibernate.hbm2ddl.auto": "update"]
        }

        context."component-scan"("base-package": "com.rimerosolutions.gorm")
        context."annotation-config"()

        tx."annotation-driven"()
}
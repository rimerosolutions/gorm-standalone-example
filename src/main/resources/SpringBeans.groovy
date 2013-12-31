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
import org.codehaus.groovy.grails.orm.hibernate.events.PatchedDefaultFlushEventListener
import org.codehaus.groovy.grails.orm.hibernate.support.ClosureEventTriggeringInterceptor

beans {
        xmlns gorm:"http://grails.org/schema/gorm"
        xmlns context:"http://www.springframework.org/schema/context"
        xmlns tx:"http://www.springframework.org/schema/tx"

        context."annotation-config"()
        tx."annotation-driven"()

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

                hibernateProperties = ["hibernate.hbm2ddl.auto": "create-drop",
                                       "hibernate.dialect": "org.hibernate.dialect.H2Dialect"]

                eventListeners = ["flush": new PatchedDefaultFlushEventListener(),
                                  "pre-load": new ClosureEventTriggeringInterceptor(),
                                  "post-load": new ClosureEventTriggeringInterceptor(),
                                  "save": new ClosureEventTriggeringInterceptor(),
                                  "save-update": new ClosureEventTriggeringInterceptor(),
                                  "post-insert": new ClosureEventTriggeringInterceptor(),
                                  "pre-update": new ClosureEventTriggeringInterceptor(),
                                  "post-update": new ClosureEventTriggeringInterceptor(),
                                  "pre-delete": new ClosureEventTriggeringInterceptor(),
                                  "post-delete": new ClosureEventTriggeringInterceptor()]
        }
        
        context."component-scan"("base-package": "com.rimerosolutions.gorm")
}